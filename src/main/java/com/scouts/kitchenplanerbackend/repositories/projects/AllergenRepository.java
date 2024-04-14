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

import com.scouts.kitchenplanerbackend.entities.projects.AllergenEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ids.AllergenEntityID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Repository to interact with the allergen table, which contains all allergens and their reference to
 * their allergen person.
 * <p>
 * It contains the basic CRUD methods, like adding, deleting and finding without that they are written down.
 */
public interface AllergenRepository extends JpaRepository<AllergenEntity, AllergenEntityID> {

    /**
     * Provides all allergens which are relevant for a given project
     *
     * @param id of the requested project
     * @return all allergens
     */
    @Query("select a from AllergenEntity a where a.project.id = :id")
    Collection<AllergenEntity> findByProject_Id(@Param("id") long id);

    /**
     * Returns all allergens for an allergen person in a project
     *
     * @param projectId the id of the requested project
     * @param name      the name of the allergen person
     * @return all allergens of the allergen person
     */
    @Query("select a from AllergenEntity a where a.project.id = :projectId and a.allergenPerson.name = :name")
    Collection<AllergenEntity> findByProject_IdAndAllergenPerson_Name(@Param("projectId") long projectId,
                                                                      @Param("name") String name);


}