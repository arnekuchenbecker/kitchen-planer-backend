/*
 * KitchenPlanerApp is the android app frontend for the KitchenPlaner, a tool
 * to cooperatively plan a meal plan for a campout.
 * Copyright (C) 2023  Arne Kuchenbecker, Antonia Heiming
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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController("/media")
public class ImageController {

    @PostMapping("/upload/project")
    public String uploadProjectPicture(@RequestHeader("userName") String userName,
                                       @RequestPart("projectId") Long projectId,
                                       @RequestPart("image") MultipartFile image) {
        return "";
    }

    @PostMapping("/upload/recipe")
    public String uploadRecipePicture(@RequestPart("recipeId") Long recipeId,
                                      @RequestPart("image") MultipartFile image) {
        return "";
    }

    @GetMapping("/projectpicture/{projectId}")
    public ResponseEntity<byte[]> getProjectPicture(@RequestHeader("userName") String userName,
                                                    @PathVariable("projectId") Long projectId) {
        byte[] data = {1, 2, 4};
        return ResponseEntity.ok(data);
    }

    @GetMapping("/recipepicutre/{recipeId}")
    public ResponseEntity<byte[]> getRecipePicture(@PathVariable("recipeId") Long recipeId) {
        byte[] data = {1, 2, 4};
        return ResponseEntity.ok(data);
    }

}
