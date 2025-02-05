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
import com.scouts.kitchenplanerbackend.repositories.projects.ProjectRepository;
import com.scouts.kitchenplanerbackend.repositories.recipes.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ProjectRepository projectRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public ImageIOService(
            ProjectRepository projectRepository,
            RecipeRepository recipeRepository
    ) {
        this.projectRepository = projectRepository;
        this.recipeRepository = recipeRepository;
    }

    public long saveProjectImage(MultipartFile image, Long projectID) throws IOException {
        Path filePath = createFilePath(image.getOriginalFilename(), PROJECT_DIRECTORY);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String fileName = filePath.subpath(filePath.getNameCount() - 1, filePath.getNameCount()).toString();

        return projectRepository.updateImagePath(projectID, fileName);
    }

    public long saveRecipeImage(MultipartFile image, Long recipeID) throws IOException {
        Path filePath = createFilePath(image.getOriginalFilename(), RECIPE_DIRECTORY);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String fileName = filePath.subpath(filePath.getNameCount() - 1, filePath.getNameCount()).toString();

        return recipeRepository.updateImagePath(recipeID, fileName);
    }

    public byte[] getProjectImage(Long projectID) throws ImageFileNotFoundException, IOException {
        String imageName = projectRepository.getImageURIById(projectID);

        Path filePath = Path.of(PROJECT_DIRECTORY).resolve(imageName);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "project");
        }
    }

    public byte[] getRecipeImage(Long recipeID) throws ImageFileNotFoundException, IOException {
        String imageName = recipeRepository.getImageURIById(recipeID);

        Path filePath = Path.of(RECIPE_DIRECTORY).resolve(imageName);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "recipe");
        }
    }

    public void deleteProjectImage(Long projectID) throws ImageFileNotFoundException, IOException {
        String imageName = projectRepository.getImageURIById(projectID);

        Path filePath = Path.of(PROJECT_DIRECTORY).resolve(imageName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "project");
        }
    }

    public void deleteRecipeImage(Long recipeID) throws ImageFileNotFoundException, IOException {
        String imageName = recipeRepository.getImageURIById(recipeID);

        Path filePath = Path.of(RECIPE_DIRECTORY).resolve(imageName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new ImageFileNotFoundException(imageName, "recipe");
        }
    }

    private Path createFilePath(String name, String directory) throws IOException {
        String fileName = UUID.randomUUID() + "_" + name;

        Path directoryPath = Path.of(directory);
        Path filePath = directoryPath.resolve(fileName);

        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        return filePath;
    }
}
