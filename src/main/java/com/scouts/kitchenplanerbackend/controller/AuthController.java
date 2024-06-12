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

package com.scouts.kitchenplanerbackend.controller;

import com.scouts.kitchenplanerbackend.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which provides all methods for authentication and login of a user
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final CredentialsService service;

    /**
     * Creates a new Controller
     *
     * @param service Service that handles the authentication
     */
    @Autowired
    public AuthController(CredentialsService service) {
        this.service = service;
    }


    /**
     * Login endpoint.
     *
     * @param dto Provides the needed information to log in a new user
     * @return 200: if the user is successfully logged in and provides a token to identify that they are logged in.
     * 401: If the username or password is incorrect; the user is not logged in.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationDTO dto) {
        String token = service.loginUser(dto.username(), dto.password());
        if (!token.isEmpty()) {
            return ResponseEntity.ok("Token");
        } else {
            return new ResponseEntity<>("Wrong password/username", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Endpoint to register a new user.
     * THe user needs a unique username
     *
     * @param dto Provides all necessary information to register a new user
     * @return 201: A new user is created.
     * 409: If the username is already taken
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthenticationDTO dto) {
        if (service.registerUser(dto.username(), dto.password())) {
            return new ResponseEntity<>(dto.username() + " is registered", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Username already taken, please try another", HttpStatus.CONFLICT);
        }
    }


}
