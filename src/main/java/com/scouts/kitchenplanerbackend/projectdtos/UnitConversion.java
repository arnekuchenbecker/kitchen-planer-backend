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

/**
 * Representation of the unit conversions used by the controllers and the services.
 * @param startUnit From which unit the conversion starts
 * @param endUnit To which unit the start unit is going to be converted
 * @param ingredient for which ingredient the unit is used for
 * @param factor How to convert the units into each other
 */
public record UnitConversion( String startUnit, String endUnit, String ingredient, double factor) {
}
