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


import com.scouts.kitchenplanerbackend.entities.CredentialsForUser;
import com.scouts.kitchenplanerbackend.repositories.CredentialsForUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;

/**
 * Contains the security configurations and needed classes for registration and authentication of a user.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CredentialsForUserRepository credentialsRepo;

    /**
     * Constructor for the security config
     *
     * @param credentialsRepo Database access for the user credentials
     */
    @Autowired
    public SecurityConfig(CredentialsForUserRepository credentialsRepo) {
        this.credentialsRepo = credentialsRepo;
    }

    /**
     * Defines the security regulations for each endpoint. This includes which filters are used on which endpoint.
     *
     * @param http Security parameter to apply the filters
     * @return Filter chain for http request, which gets applied on the endpoints.
     * @throws Exception If building of the security filter chain is not successfully
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final AuthenticationProvider authenticationProvider = authenticationProvider();
        http.authenticationProvider(authenticationProvider).securityMatcher("/auth/")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return http.build();
    }

    /**
     * Provides a password encoder.
     *
     * @return The password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a class to perform authentications and configures it to use the correct password encoder and correct database for users
     *
     * @return The class to perform authentications
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        final UserDetailsService userDetailsService = userDetailsService();
        authenticationProvider.setUserDetailsService(userDetailsService);

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Provides a user details service which is able to get users from the database.
     *
     * @return The requested user service
     */
    @Bean
    @Transactional
    public UserDetailsService userDetailsService() {
        return username -> {
            CredentialsForUser user = credentialsRepo.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("Could not find user " + username));
            return new User(user.getUser().getName(), user.getPassword(), new ArrayList<>());
        };
    }
}
