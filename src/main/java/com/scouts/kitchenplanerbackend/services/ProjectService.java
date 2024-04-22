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

import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    /**
     * Stores a new project from a user
     * @param project The project that should be stored
     * @return online ID of the project
     */
    public long saveNewProject(){
        return 0;
    }

    /**
     * Rewrites all data of a project, so the latest version of a project is saved
     * @param project The new version of the project
     * @return whether the new version could be saved
     */
    public boolean updateProject(){
    return false;
    }
    /**
     * Provides a project for a user if the user is part of the project
     * @param projectID Online ID of a project
     * @param username Name of the user who wants to get the project
     * @return the project
     */
    public void getProject(long projectID, String username){
        return; //project
    }
}
