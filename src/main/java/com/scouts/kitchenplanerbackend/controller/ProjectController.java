/*
 * KitchenPlanerBackend is the Java Backend for the KitchenPlaner, a tool
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
 *
 */

package com.scouts.kitchenplanerbackend.controller;

import com.scouts.kitchenplanerbackend.entities.projects.ProjectStubDTO;
import com.scouts.kitchenplanerbackend.projectdtos.Project;
import com.scouts.kitchenplanerbackend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

/**
 * Controller for handling projects
 */
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    /**
     * Creates a new Controller
     *
     * @param projectService The service used for fulfilling the queries
     */
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Creates a new project with the given data
     *
     * @param project The data of the project that should be created
     * @return A response entity containing information about the newly created project. If creation was successful,
     *         HTTP status code 201 is returned, the request body contains the ID of the new project and the location
     *         header contains the endpoint at which to get or update the project
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createProject(@RequestBody Project project) {
        long id = projectService.saveNewProject(project);
        URI resourceLocation = URI.create("/projects/" + id);
        return ResponseEntity.created(resourceLocation).body(id);
    }

    /**
     * Reads and returns the project with the given ID
     *
     * @param id The project that should be read
     * @return A response entity containing the requested project or information that reading was not successful. If
     *         a project with the given ID exists, HTTP status code 200 is returned and the request body contains the
     *         requested project. Otherwise, HTTP status code 404 is returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable long id) {
        Project project = projectService.getProject(id);
        return ResponseEntity.ok(project);
    }

    /**
     * Updates an existing project
     *
     * @param id The id of the project that should be updated
     * @param project The project as it should be after the update
     * @return A response entity containing the current version number of the project or an error message if id !=
     *         project.id. If the request was successful, HTTP status code 200 is returned and the request body contains
     *         the new version number. Otherwise, HTTP status code 400 is returned and the request body contains a
     *         message describing the problem.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProject(@PathVariable long id, @RequestBody Project project) {
        if(id != project.id()) {
            throw new IllegalArgumentException("Supplied project's ID did not match endpoint's ID");
        }
        long version = projectService.updateProject(project);
        return ResponseEntity.ok(version);
    }

    /**
     * Returns project stubs for all projects the given user participates in
     *
     * @param user The user for which to return the project stubs
     * @return A response entity containing a collection of project stubs representing all projects the user is
     *         participating in. Returns HTTP status code 200 and the request body contains the project stubs.
     */
    @GetMapping("/")
    public ResponseEntity<Collection<ProjectStubDTO>> getStubsForUsername(@RequestParam String user) {
        Collection<ProjectStubDTO> stubs = projectService.getProjectStubs(user);
        return ResponseEntity.ok(stubs);
    }
}
