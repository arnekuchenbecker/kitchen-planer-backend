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

import com.scouts.kitchenplanerbackend.entities.UserEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectStubDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * Provides access to the general information of a project and contains the possibility to let people join and leave it.
 *
 * <p> It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {


    /**
     * Provides the image URI for a project
     *
     * @param id of the requested project
     * @return the URI of the image
     */
    @Query("select p.imageUri from ProjectEntity p where p.id = :id")
    String getImageURIById(@Param("id") long id);

    /**
     * Provides all projects stubs where the given participant is part of
     *
     * @param user who is participating in all the requested projects
     * @return all projects stubs
     */
    @Query("select p from ProjectEntity p inner join p.participants participants where participants.name = :user")
    Collection<ProjectStubDTO> findByParticipants_Name(@Param("user") String user);

    /**
     * Queries if a person participates in a project
     *
     * @param project     id of the project
     * @param participant or person who might be participating
     * @return whether the participant is really participating
     */
    @Query("select (count(p) > 0) from ProjectEntity p inner join p.participants participants where participants.id = :participant and p.id = :project")
    boolean existsByParticipants_IdAndId(@Param("project") long project, @Param("participant") long participant);

    /**
     * Returns all participants from a project
     *
     * @param id of the project
     * @return all participants
     */
    @Query("select p from ProjectEntity p where p.id = :id")
    Collection<UserEntity> findParticipantsById(@Param("id") long id);

    /**
     * A user can join the project
     *
     * @param user who wants to join
     * @param id   of the project
     */
    @Transactional
    @Modifying
    @Query("update ProjectEntity p set p.participants = (select users from UserEntity users where users IN (select u from p.participants u) or users = :user) where p.id = :id")
    void joinProject(UserEntity user, long id);

    /**
     * A user can leave the project
     *
     * @param user who wants to leave
     * @param id   of the project
     */
    @Transactional
    @Modifying
    @Query("update ProjectEntity p set p.participants = (select participants from p.participants participants where participants <> :user)  where p.id = :id")
    void leaveProject(UserEntity user, long id);

    /**
     * Updates all metadata of a project and increases the project version number
     * @param name The new name of the project
     * @param startDate New start date of the project
     * @param endDate New end date of the project
     * @param id Id of the project where the metadata is changed
     */
    @Transactional
    @Modifying
    @Query("update ProjectEntity p set p.name = :name, p.startDate = :startDate, p.endDate = :endDate, p.projectVersion = (p.projectVersion + 1) where p.id = :id")
    void updateMetaData(@Param("name") String name, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                        @Param("id") Long id);


    /**
     * Provides the number of participants in a projects
     * @param id Id of the requested project
     * @return number of participant in the project
     */
    @Transactional
    @Query("select count(p.participants) from ProjectEntity p where p.id = :id")
    int countMembersById(@Param("id") Long id);

    /**
     * increases the  image version number for the project by one
     * @param id ID of the requested project
     */
    @Transactional
    @Modifying
    @Query("update ProjectEntity p set p.imageVersion = (p.imageVersion + 1) where p.id = :id")
    void increaseImageVersionById(@Param("id") Long id);
}