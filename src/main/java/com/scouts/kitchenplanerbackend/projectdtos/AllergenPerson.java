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
import java.util.List;

/**
 * Representation of a allergen person used by the controllers and services
 * @param name Name of the person
 * @param arrivalDate Date when the person arrives
 * @param departureDate Date when the person leaves
 * @param arrivalMeal First meal the person has
 * @param departureMeal Last meal the person has
 * @param allergen all allergens the person has
 * @param traces all relevant traces for the person
 */
public record AllergenPerson(String name,
                             Date arrivalDate,
                             Date departureDate,
                             String arrivalMeal,
                             String departureMeal,
                             List<String> allergen,
                             List<String> traces) {
}
