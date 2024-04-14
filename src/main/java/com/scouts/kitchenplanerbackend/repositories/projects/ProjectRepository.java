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

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {


    @Query("select p.imageUri from ProjectEntity p where p.id = :id")
    String getImageURIById(@Param("id") long id);

    @Query("select p from ProjectEntity p inner join p.participants participants where participants.name = :user")
    Collection<ProjectStubDTO> findByParticipants_Name(@Param("user") String user);

    @Transactional
    @Modifying
    @Query("update ProjectEntity p set p.participants = :participants where p.id = :id")
    void updateParticipantsById(@Param("participants") Collection<UserEntity> participants, @Param("id") Long id);

   @Query("select p from ProjectEntity p where p.id = :id")
   Collection<UserEntity> findParticipantsById(@Param("id") long id);

   @Transactional
   default void joinProject(UserEntity user, long id){
       Collection<UserEntity> users = findParticipantsById(id);
       users.add(user);
       updateParticipantsById(users,id);
   }

    @Transactional
    default void leaveProject(UserEntity user, long id){
        Collection<UserEntity> users = findParticipantsById(id);
        users.remove(user);
        updateParticipantsById(users,id);
    }

}