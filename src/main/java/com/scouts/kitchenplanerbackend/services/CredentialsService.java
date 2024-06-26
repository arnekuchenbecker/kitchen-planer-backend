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

import com.scouts.kitchenplanerbackend.entities.CredentialsForUser;
import com.scouts.kitchenplanerbackend.entities.UserEntity;
import com.scouts.kitchenplanerbackend.repositories.CredentialsForUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This service provides methods to register and log in a user.
 */
@Service
public class CredentialsService {

    private final CredentialsForUserRepository userRepository;

    private final AuthenticationProvider authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new Credential service
     *
     * @param userRepository Repository which provides access to the credentials of the users and persists them
     */
    @Autowired
    public CredentialsService(CredentialsForUserRepository userRepository, AuthenticationProvider authenticationManager,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user if the username is not yet taken
     *
     * @param username The new username of the user
     * @param password The password of the user
     * @return Whether the user was created
     */
    @Transactional
    public boolean registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.setName(username);
        CredentialsForUser credentials = new CredentialsForUser();
        credentials.setPassword(passwordEncoder.encode(password));
        credentials.setUser(newUser);
        userRepository.save(credentials);
        return true;
    }

    /**
     * Log's in a user. This method checks if the password is correct and if the user exists.
     *
     * @param username THe username of user
     * @param password Provided password from the user
     * @return The token for to prove that the user is logged in or an empty string if it doesn't worked out.
     */
    @Transactional
    public String loginUser(String username, String password) {

        if (!userRepository.existsByUsername(username)) {
            return "";
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException auth){
            return "";
        }
        return "Token";

    }



}
