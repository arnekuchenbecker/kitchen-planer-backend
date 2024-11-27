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

package com.scouts.kitchenplanerbackend.entities.projects.ids;

import com.scouts.kitchenplanerbackend.entities.projects.MealEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * ID class for {@link com.scouts.kitchenplanerbackend.entities.projects.MainRecipeProjectMealEntity}
 */
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Setter
public class MainRecipeProjectMealID implements Serializable {
    private ProjectEntity project;
    private MealEntity meal;
    private Date date;
}
