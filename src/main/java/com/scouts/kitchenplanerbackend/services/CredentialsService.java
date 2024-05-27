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
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    private final CredentialsForUserRepository userRepository;

    @Autowired
    public CredentialsService(CredentialsForUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean registerUser(String username, String password){
        if(userRepository.existsByUsername(username)){
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.setName(username);
        userRepository.save(new CredentialsForUser(newUser, password));
        return true;
    }

    @Transactional
    public boolean loginUser(String username, String password){
        if(!userRepository.existsByUsername(username)){
            return false;
        }
        String savedPassword = userRepository.getPasswordByUsername(username);
        savedPassword.equals(password); //TODO compare the passwords
        return true;
    }
}
