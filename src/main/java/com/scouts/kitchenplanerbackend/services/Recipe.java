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

import java.util.List;

public record Recipe(Long id, String name, String description, Integer number_of_people, List<String> traces, List<String> allergens, List<String> freeOfAllergen, List<String> instructions, List<Ingredient> ingredients) {
    public Recipe {
        //register ingredients with this recipe
        for (Ingredient loose : ingredients) {
            ingredients.remove(loose);
            Ingredient registered = new Ingredient(this, loose.name(), loose.ingredientGroup(), loose.amount(), loose.unit());
            ingredients.add(registered);
        }

    }
}
