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

package com.scouts.kitchenplanerbackend.repositories.projects;

import com.scouts.kitchenplanerbackend.entities.projects.AlternativeRecipeProjectMeal;
import com.scouts.kitchenplanerbackend.entities.projects.ids.AlternativeRecipeProjectMealID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Repository which provides access to the alternative recipes for a meal slot (a meal on a date).
 * Note that a meal slot can have multiple alternative recipes.
 * <p>
 * It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */
public interface AlternativeRecipeProjectMealRepository
        extends JpaRepository<AlternativeRecipeProjectMeal, AlternativeRecipeProjectMealID> {

    /**
     * Provides all alternative recipes for a project
     *
     * @param id of the project
     * @return all alternative recipes
     */
    @Query("select a from AlternativeRecipeProjectMeal a where a.project.id = :id")
    Collection<AlternativeRecipeProjectMeal> findByProject_Id(@Param("id") long id);
}