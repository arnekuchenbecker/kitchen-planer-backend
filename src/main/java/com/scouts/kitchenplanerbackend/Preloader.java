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

package com.scouts.kitchenplanerbackend;

import com.scouts.kitchenplanerbackend.projectdtos.AllergenPerson;
import com.scouts.kitchenplanerbackend.projectdtos.Project;
import com.scouts.kitchenplanerbackend.services.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;


/**
 * Class to initalize the database
 */
@Configuration
public class Preloader {
    private final Logger logger = LoggerFactory.getLogger(Preloader.class);


    /**
     * Initializes the project database by storing a new project
     *
     * @param projectService service, to store a new project
     * @return The command line runner to let the bean run
     */
    @Bean
    CommandLineRunner initDatabase(ProjectService projectService) {
        AllergenPerson bob =
                new AllergenPerson("Bob", new Date(2024, 07, 22), new Date(2024, 07, 25), "Mittagessen", "Abendessen",
                        List.of("Ei"), List.of("Laktose"));
        Project project = new Project("Testprojekt", List.of("Mittagessen", "Abendessen"), new Date(2024, 07, 22),
                new Date(2024, 07, 28), List.of(bob), List.of(), List.of(), List.of());
        return args -> {
            logger.info("Insert new Project with Id: " + projectService.saveNewProject(project));
        };
    }
}
