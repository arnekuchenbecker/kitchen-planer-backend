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

package com.scouts.kitchenplanerbackend.entities.projects;

import lombok.Getter;

/**
 * Projection for {@link ProjectEntity}
 */
public interface ProjectStubDTO {

    /**
     * Provides the id of a project stub
     * @return The id of the project
     */
    Long getId();

    /**
     * Provides the name of a project stub
     * @return The name of the project
     */
    String getName();

    /**
     * Provides the image URI as String of a project stub
     * @return The image URI
     */
    String getImageUri();

    /**
     * Provides the image version of a project stub
     * @return The image version
     */
    Long getImageVersion();

    /**
     * Provides the project version of a project stub
     * @return The project version
     */
    Long getProjectVersion();

}