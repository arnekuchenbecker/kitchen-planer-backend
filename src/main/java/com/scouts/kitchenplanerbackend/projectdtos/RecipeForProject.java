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

package com.scouts.kitchenplanerbackend.projectdtos;

import java.util.Date;

/**
 * Representation of a recipe with its meal slot and if it is the main recipe.
 * It is used by the controllers and the services.
 * @param date Date when the recipe is used
 * @param meal For which meal the recipe is used
 * @param recipeID ID of the used recipe
 * @param mainRecipe Whether the recipe is the main recipe for the meal slot
 */
public record RecipeForProject(Date date, String meal, long recipeID, boolean mainRecipe) {
}
