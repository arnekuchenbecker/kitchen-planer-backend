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
import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import com.scouts.kitchenplanerbackend.repositories.UserRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectOrganisationService {

    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;
    private final ProjectService projectService;

    @Autowired
    ProjectOrganisationService(final ProjectRepository projectRepository, final UserRepository userRepository, final ProjectService projectService){
        this.projectRepo = projectRepository;
        this.userRepo = userRepository;
        this.projectService = projectService;
    }

    /**
     * Provides an invitation link for the project with the given projectID
     * @param projectId ID of the project to join
     * @return The requested invitation Link
     */
    public String getInvitationLink(long projectId){
        return "";
    }
    /**
     * The user joins the project.
     *
     * @param invitationLink Link that covers the projectID
     * @param username Unique identifier of the user
     * @return The projectID of the joined project or 0 if the joining was not successful
     */
    @Transactional
    public long joinProject(String invitationLink, String username){
        UserEntity user = userRepo.findByName(username);
        long projectID = 0; //
        projectRepo.joinProject(user,projectID);
        return projectID;
    }

    /**
     * Leaving a specified project
     *
     * @param projectID ID of the project the user wants to leave
     * @param username Unique identifier of the user
     */
    @Transactional
    public void leaveProject(long projectID, String username){
        UserEntity user = userRepo.findByName(username);
        projectRepo.leaveProject(user,projectID);
        if ( projectRepo.countMembersById(projectID) < 1){
            projectRepo.findById(projectID).ifPresent(projectRepo::delete);
        }
    }

    /**
     * Provides the current version of the given project
     * @param projectId ID for the project
     *
     * @return The version number of the project
     */
    public long getCurrentProjectVersion(long projectId){
        return 0;
    }
}
