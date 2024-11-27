/*
 * KitchenPlanerApp is the android app frontend for the KitchenPlaner, a tool
 * to cooperatively plan a meal plan for a campout.
 * Copyright (C) 2023-2024 Arne Kuchenbecker, Antonia Heiming, Anton Kadelbach, Sandra Lanz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.scouts.kitchenplanerbackend.controller;

import com.scouts.kitchenplanerbackend.exceptions.ImageFileNotFoundException;
import com.scouts.kitchenplanerbackend.services.ImageIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Controller class for up- and downloading images for both recipes and projects
 */
@RestController("/media")
public class ImageController {
    private final ImageIOService imageIOService;

    /**
     * Creates a new ImageController
     *
     * @param imageIOService The service to be used for storing and loading the images
     */
    @Autowired
    public ImageController(ImageIOService imageIOService) {
        this.imageIOService = imageIOService;
    }

    /**
     * Updates the image of the project with the given ID.
     *
     * @param projectID The ID of the project
     * @param image     The new project image
     * @return The updated image version number of the project
     * @throws IOException When saving the image fails for some reason
     */
    @PostMapping("/projects/{projectID}")
    public ResponseEntity<Integer> uploadProjectPicture(
            @PathVariable("projectID") Long projectID,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        int newVersion = imageIOService.saveProjectImage(image, projectID);
        return ResponseEntity.ok(newVersion);
    }

    /**
     * Updates the image of the recipe with the given ID.
     *
     * @param recipeID The ID of the recipe
     * @param image    The new recipe image
     * @return The updated image version number of the recipe
     * @throws IOException When saving the image fails for some reason
     */
    @PostMapping("/recipes/{recipeID}")
    public ResponseEntity<Integer> uploadRecipePicture(
            @PathVariable("recipeID") Long recipeID,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        int newVersion = imageIOService.saveRecipeImage(image, recipeID);
        return ResponseEntity.ok(newVersion);
    }

    /**
     * Get the current image of the project with the given ID.
     *
     * @param projectID The ID of the project
     * @return The project's image
     * @throws IOException                When reading the image fails for some reason
     * @throws ImageFileNotFoundException When no image exists matching the file name stored for the project
     */
    @GetMapping("/projects/{projectID}")
    public ResponseEntity<byte[]> getProjectPicture(@PathVariable("projectID") Long projectID)
            throws IOException, ImageFileNotFoundException {
        byte[] image = imageIOService.getProjectImage(projectID);
        return ResponseEntity.ok(image);
    }

    /**
     * Get the current image of the recipe with the given ID.
     *
     * @param recipeID The ID of the recipe
     * @return The recipe's image
     * @throws IOException                When reading the image fails for some reason
     * @throws ImageFileNotFoundException When no image exists matching the file name stored for the recipe
     */
    @GetMapping("/recipes/{recipeID}")
    public ResponseEntity<byte[]> getRecipePicture(@PathVariable("recipeID") Long recipeID)
            throws IOException, ImageFileNotFoundException {
        byte[] image = imageIOService.getRecipeImage(recipeID);
        return ResponseEntity.ok(image);
    }
}
