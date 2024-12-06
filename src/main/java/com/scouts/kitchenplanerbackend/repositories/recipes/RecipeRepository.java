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

package com.scouts.kitchenplanerbackend.repositories.recipes;

import com.scouts.kitchenplanerbackend.entities.recipe.RecipeEntity;
import com.scouts.kitchenplanerbackend.entities.recipe.RecipeEntityDTO;
import com.scouts.kitchenplanerbackend.entities.recipe.RecipeStubDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    /**
     * Updates the path to a recipe's image, increasing the image version number of the recipe
     * @param recipeID The ID of the recipe
     * @param path The new path to the recipe's image
     * @return The updated version number of the recipe
     */
    @Transactional
    default Long updateImagePath(long recipeID, String path) {
        updateImageUriById(path, recipeID);
        increaseImageVersionById(recipeID);
        return getImageVersionById(recipeID);
    }

    @Query("select r.imageURI  from RecipeEntity r where r.id = :id")
    String getImageURIById(@Param("id") long id);


    @Query("select r from RecipeEntity r where r.id = :id")
    RecipeStubDTO getRecipeStubDTOById(@Param("id") long id);

    @Query("select r from RecipeEntity r")
    RecipeEntityDTO getRecipeEntityBy(@Param("id") long id);

    /**
     * increases the  image version number for the recipe by one
     * @param id ID of the requested recipe
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update RecipeEntity r set r.imageVersion = (r.imageVersion + 1) where r.id = :id")
    void increaseImageVersionById(@Param("id") Long id);

    /**
     * Updates the path to a recipe's image
     * @param imageUri The new path
     * @param id The id of the recipe
     * @deprecated Changing the image path should always be accompanied by incrementing the recipe's image version
     *             number. Use updateImagePath instead
     */
    @Deprecated
    @Transactional
    @Modifying
    @Query("update RecipeEntity r set r.imageURI = ?1 where r.id = ?2")
    void updateImageUriById(String imageUri, Long id);

    /**
     * Gets the image version number for a recipe
     * @param id The id of the recipe
     * @return The current image version number for the recipe
     */
    @Transactional
    @Query("select r.imageVersion from RecipeEntity r where r.id = ?1")
    Long getImageVersionById(Long id);
}