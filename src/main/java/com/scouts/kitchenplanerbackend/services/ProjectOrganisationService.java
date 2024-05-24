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

/**
 * This service is used for project organisation such as joining/leaving a project.
 * It provides the invitation link for a project and the current version number.
 */
@Service
public class ProjectOrganisationService {

    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    /**
     * Initializes the needed repositories
     * @param projectRepository Database access for projects
     * @param userRepository Database access for users of the app
     */
    @Autowired
    ProjectOrganisationService(final ProjectRepository projectRepository, final UserRepository userRepository){
        this.projectRepo = projectRepository;
        this.userRepo = userRepository;
    }

    /**
     * Provides an invitation link for the project with the given projectID
     * @param projectId ID of the project to join
     * @return The requested invitation Link
     */
    public String getInvitationLink(long projectId){
        return ""; //TODO create invitation link;
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
        long projectID = 0; // TODO find which projectID belongs to the invitation link

        if (projectRepo.existsByParticipants_IdAndId(projectID, user.getId())){
           return projectID;
        }
        projectRepo.findParticipantsById(projectID).add(user);
        projectRepo.findById(projectID).ifPresent(projectRepo::save);
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
        projectRepo.findParticipantsById(projectID).remove(user);
        projectRepo.findById(projectID).ifPresent(projectRepo::save);
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
        return projectRepo.getReferenceById(projectId).getProjectVersion();
    }
}
