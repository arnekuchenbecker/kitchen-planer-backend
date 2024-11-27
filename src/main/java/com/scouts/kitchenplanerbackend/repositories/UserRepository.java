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

package com.scouts.kitchenplanerbackend.repositories;

import com.scouts.kitchenplanerbackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository provides access to all users which might be part of a project.
 * </p>
 * It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * provides the database entry for a user queried by their name
     * @param name The name of the user
     * @return The database representation of the user.
     */
    UserEntity findByName(String name);
}