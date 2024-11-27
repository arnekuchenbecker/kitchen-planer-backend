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

import com.scouts.kitchenplanerbackend.entities.projects.ids.AlternativeRecipeProjectMealID;
import com.scouts.kitchenplanerbackend.entities.recipe.RecipeEntity;
import jakarta.persistence.Column;
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
 * This entity represents an alternative recipe for a specified meal slot in a project. Note that there could be
 * multiple alternative recipes for one meal slot.
 */
@Getter
@Setter
@Entity
@IdClass(AlternativeRecipeProjectMealID.class)
@Table(name = "alternative_recipe_project_meal")
public class AlternativeRecipeProjectMeal {
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
    @Id
    @ManyToOne
    @OnDelete(action =OnDeleteAction.CASCADE)
    private RecipeEntity recipe;

}