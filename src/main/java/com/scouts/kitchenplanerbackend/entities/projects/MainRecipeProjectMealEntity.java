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

package com.scouts.kitchenplanerbackend.entities.projects;

import com.scouts.kitchenplanerbackend.entities.projects.ids.MainRecipeProjectMealID;
import com.scouts.kitchenplanerbackend.entities.recipe.RecipeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

/**
 * Represents a main recipe for a specified meal slot. Note that there is at most one main recipe per meal slot.
 */
@Getter
@Setter
@Entity
@IdClass(MainRecipeProjectMealID.class)
@Table(name = "main_recipe_project_meal_entity")
public class MainRecipeProjectMealEntity {
    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProjectEntity project;

    @Id
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MealEntity meal;

    @Id
    private Date date;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RecipeEntity recipe;

}