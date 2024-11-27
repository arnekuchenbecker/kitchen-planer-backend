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
 *
 */

package com.scouts.kitchenplanerbackend.services;

import com.scouts.kitchenplanerbackend.exceptions.ImageFileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageIOService {
    private static final String PROJECT_DIRECTORY = "images/projects/";
    private static final String RECIPE_DIRECTORY = "images/recipes/";

    public int saveProjectImage(MultipartFile image, Long projectID) throws IOException {
        Path filePath = getFilePath(image.getOriginalFilename(), PROJECT_DIRECTORY);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // TODO - store fileName to the database for the correct project, update the image version

        return 0;
    }

    public int saveRecipeImage(MultipartFile image, Long recipeID) throws IOException {
        Path filePath = getFilePath(image.getOriginalFilename(), RECIPE_DIRECTORY);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // TODO - store fileName to the database for the correct recipe, update the image version

        return 0;
    }

    public byte[] getProjectImage(Long projectID) throws ImageFileNotFoundException, IOException {
        String imageName = "TODO"; // TODO - get fileName from database for the correct project

        Path filePath = getFilePath(imageName, PROJECT_DIRECTORY);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "project");
        }
    }

    public byte[] getRecipeImage(Long recipeID) throws ImageFileNotFoundException, IOException {
        String imageName = "TODO"; // TODO - get fileName from database for the correct recipe

        Path filePath = getFilePath(imageName, RECIPE_DIRECTORY);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "recipe");
        }
    }

    public void deleteProjectImage(Long projectID) throws ImageFileNotFoundException, IOException {
        String imageName = "TODO"; // TODO - get fileName from database for the correct project

        Path filePath = getFilePath(imageName, PROJECT_DIRECTORY);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "project");
        }
    }

    public void deleteRecipeImage(Long recipeID) throws ImageFileNotFoundException, IOException {
        String imageName = "TODO"; // TODO - get fileName from database for the correct recipe

        Path filePath = getFilePath(imageName, RECIPE_DIRECTORY);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "recipe");
        }
    }

    private Path getFilePath(String name, String directory) throws IOException {
        String fileName = UUID.randomUUID() + "_" + name;

        Path directoryPath = Path.of(directory);
        Path filePath = directoryPath.resolve(fileName);

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        return filePath;
    }
}
