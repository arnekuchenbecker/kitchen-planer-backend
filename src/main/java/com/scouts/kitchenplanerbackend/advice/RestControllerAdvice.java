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

package com.scouts.kitchenplanerbackend.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Controller Advice for turning exceptions into HTTP responses
 */
@ControllerAdvice
public class RestControllerAdvice {
    /**
     * Handles NoSuchElementExceptions
     *
     * @return A response entity with HTTP status code 404 and no body.
     */
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Void> handleNoSuchElement() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles IllegalArgumentExceptions
     *
     * @param ex The exception that should be handled
     * @return A response entity with HTTP status code 400 and a body containing the exception's message
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Handles FileNotFoundExceptions
     *
     * @return A response entity with HTTP status code 404 and no body.
     */
    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<Void> handleFileNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles IOExceptions
     *
     * @param ex The exception that should be handled
     * @return A response with the HTTP status code 500 and a body containing the exception's message
     */
    @ExceptionHandler({IOException.class})
    public ResponseEntity<String> handleIOException(IOException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}