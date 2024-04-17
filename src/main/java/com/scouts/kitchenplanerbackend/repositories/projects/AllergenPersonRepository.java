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

import com.scouts.kitchenplanerbackend.entities.projects.AllergenPersonEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ids.AllergenPersonEntityID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * repository that provides CRUD methods to interact with the allergen person table.
 * </p>
 * It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */
public interface AllergenPersonRepository extends JpaRepository<AllergenPersonEntity, AllergenPersonEntityID> {

    /**
     * Provides all allergen persons who belong to a specified project
     *
     * @param id of the requested project
     * @return all allergen persons
     */
    @Query("select a from AllergenPersonEntity a where a.project.id = :id")
    Collection<AllergenPersonEntity> findByProject_Id(@Param("id") long id);


}