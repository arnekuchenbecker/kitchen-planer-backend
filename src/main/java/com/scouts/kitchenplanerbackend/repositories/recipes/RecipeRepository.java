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

import java.util.Date;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    @Query("select r.imageURI  from RecipeEntity r where r.id = :id")
    String getImageURIById(@Param("id") long id);


    @Query("select r from RecipeEntity r where r.id = :id")
    RecipeStubDTO getRecipeStubDTOById(@Param("id") long id);

    @Query("select r from RecipeEntity r")
    RecipeEntityDTO getRecipeEntityBy(@Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update RecipeEntity r set r.name = :name, r.description = :description, r.numberOfPeople = :numberOfPeople, r.version = (r.version + 1) where r.id = :id")
    void updateMetaData(@Param("name") String name, @Param("description") String description, @Param("numberOfPeople") int numberOfPeople,
                        @Param("id") Long id);

    //Todo add increaseImageVersionById
}