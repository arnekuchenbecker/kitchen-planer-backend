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

import com.scouts.kitchenplanerbackend.entities.projects.PersonNumberChangeEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ids.PersonNumberChangeID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Repository that provides how the amount of persons change during a project.
 * <p> It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */
public interface PersonNumberChangeRepository extends JpaRepository<PersonNumberChangeEntity, PersonNumberChangeID> {

    /**
     * Provides all changes for a project.
     * @param id of the requested project
     * @return all changes.
     */
    @Query("select p from PersonNumberChangeEntity p where p.project.id = :id")
    Collection<PersonNumberChangeEntity> findByProject_Id(@Param("id") long id);

    /**
     * Deletes all person number changes associated with the given project
     * @param project Project entity for which the changes should be deleted
     */
    @Transactional
    @Modifying
    @Query("delete from PersonNumberChangeEntity p where p.project = :project")
    void deleteByProject(ProjectEntity project);
}