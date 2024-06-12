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

import com.scouts.kitchenplanerbackend.entities.CredentialsForUser;
import com.scouts.kitchenplanerbackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * This repository provides access to all credential information of the users.
 * </p>
 * It contains the basic CRUD methods, like adding, deleting and writing without explicitly defining methods for those operations.
 */

public interface CredentialsForUserRepository extends JpaRepository<CredentialsForUser, UserEntity> {


    /**
     * Checks if there is a user with the given username
     *
     * @param username the username to be checked
     * @return whether the user exists
     */
    @Query("select count(credentails)>0 from CredentialsForUser credentails Where credentails.user.name = :username ")
    boolean existsByUsername(@Param("username") String username);

    /**
     * Returns a user by specified by their name
     *
     * @param username The username to search for
     * @return The user, or an empty optional if they don't exist
     */
    @Query("select user from CredentialsForUser user where user.user.name = :username")
    Optional<CredentialsForUser> findByUsername(@Param("username") String username);

}