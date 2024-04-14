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

import com.scouts.kitchenplanerbackend.entities.recipe.IngredientEntity;
import com.scouts.kitchenplanerbackend.entities.recipe.IngredientID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IngredientRepository extends JpaRepository<IngredientEntity, IngredientID> {

    Collection<IngredientEntity> getIngredientEntitiesByRecipeId(long recipeId);

}