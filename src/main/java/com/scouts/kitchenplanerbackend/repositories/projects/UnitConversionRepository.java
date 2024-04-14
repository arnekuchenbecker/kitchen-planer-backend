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

import com.scouts.kitchenplanerbackend.entities.projects.UnitConversionEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ids.UnitConversionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

/**
 * Repository that provides access to the unit conversion for a project
 * <p> It contains the basic CRUD methods, like adding, deleting and finding without that they are written down.
 */
public interface UnitConversionRepository extends JpaRepository<UnitConversionEntity, UnitConversionID> {

    /**
     * provides all unit conversions for a project
     *
     * @param id of the project
     * @return all requested unit conversions
     */
    @Query("select u from UnitConversionEntity u where u.project.id = ?1")
    Collection<UnitConversionEntity> findByProject_Id(Long id);
}