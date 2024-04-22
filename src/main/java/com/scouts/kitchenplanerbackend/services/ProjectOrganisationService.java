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

package com.scouts.kitchenplanerbackend.services;

import com.scouts.kitchenplanerbackend.entities.UserEntity;
import com.scouts.kitchenplanerbackend.repositories.UserRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectOrganisationService {
    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    @Autowired
    ProjectOrganisationService(final ProjectRepository projectRepository, final UserRepository userRepository){
       this.projectRepo = projectRepository;
       this.userRepo = userRepository;
    }

    /**
     * The user joins the project.
     *
     * @param projectID ID of the project the user wants to join
     * @param username Unique identifier of the user
     */
    public void joinProject(long projectID, String username){
        UserEntity user = userRepo.findByName(username);
        projectRepo.joinProject(user,projectID);
    }

    /**
     * Leaving a specified project
     * @param projectID ID of the project the user wants to leave
     * @param username Unique identifier of the user
     */
    public void leaveProject(long projectID, String username){
        UserEntity user = userRepo.findByName(username);
        projectRepo.leaveProject(user,projectID);
    }
}
