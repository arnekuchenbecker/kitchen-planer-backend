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

import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import com.scouts.kitchenplanerbackend.entities.projects.UnitConversionEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ids.UnitConversionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Repository that provides access to the unit conversion for a project
 * <p> It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */
public interface UnitConversionRepository extends JpaRepository<UnitConversionEntity, UnitConversionID> {

    /**
     * provides all unit conversions for a project
     *
     * @param id of the project
     * @return all requested unit conversions
     */
    @Query("select u from UnitConversionEntity u where u.project.id = :id")
    Collection<UnitConversionEntity> findByProject_Id(@Param("id") long id);


    /**
     * Deletes all unit conversions associated with the given project
     * @param project Project entity for which the unit conversions should be deleted
     */
    @Transactional
    @Modifying
    @Query("delete from UnitConversionEntity u where u.project = :project")
    void deleteByProject(ProjectEntity project);
}