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

import com.scouts.kitchenplanerbackend.entities.UserEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectStubDTO;
import com.scouts.kitchenplanerbackend.repositories.UserRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;


    @BeforeEach
    void setUp() {
        ProjectEntity project = new ProjectEntity();
        project.setName("Test Project");
        projectRepo.save(project);

        assertEquals(1, projectRepo.findAll().size());
        assertEquals("Test Project", projectRepo.findAll().get(0).getName());

    }


    @Test
    void findCorrectAmountOfParticipants() {
        UserEntity user1 = new UserEntity("user1");
        UserEntity user2 = new UserEntity("user2");

        user1 = userRepo.save(user1);
        user2 = userRepo.save(user2);


        assertEquals(2, userRepo.findAll().size());
        long userId1 = user1.getId();
        long userId2 = user2.getId();

        ProjectEntity project = projectRepo.findAll().get(0);
        long projectID = project.getId();


        assertFalse(projectRepo.existsByParticipants_IdAndId(projectID, userId1));
        project.getParticipants().add(user1);
        project = projectRepo.save(project);
        assertTrue(projectRepo.existsByParticipants_IdAndId(projectID, userId1));


        assertEquals(1, projectRepo.countMembersById(projectID));
        Collection<UserEntity> u = projectRepo.findParticipantsById(projectID);
        assertEquals(1, u.size());
        assertEquals(user1, u.toArray()[0]);

        project.getParticipants().add(user2);
        project = projectRepo.save(project);
        assertEquals(2, projectRepo.countMembersById(projectID));
        u = projectRepo.findParticipantsById(projectID);
        assertEquals(2, u.size());
        assertEquals(user1, u.toArray()[0]);
        assertEquals(user2, u.toArray()[1]);

        project.getParticipants().remove(user1);
        project = projectRepo.save(project);
        assertEquals(1, projectRepo.countMembersById(projectID));
        u = projectRepo.findParticipantsById(projectID);
        assertEquals(1, u.size());
        assertEquals(user2, u.toArray()[0]);

        userRepo.delete(user2);
        userRepo.delete(user1);
    }

    @Test
    void findByParticipantsName() {
        UserEntity user1 = new UserEntity("user1");
        UserEntity user2 = new UserEntity("user2");

        user1 = userRepo.save(user1);
        user2 = userRepo.save(user2);

        assertEquals(2, userRepo.findAll().size());

        ProjectEntity project = projectRepo.findAll().get(0);
        project.getParticipants().add(user1);
        project = projectRepo.save(project);

        Collection<ProjectStubDTO> dtos = projectRepo.findByParticipants_Name("user2");
        assertEquals(0, dtos.size());
        dtos = projectRepo.findByParticipants_Name("user1");
        assertEquals(1, dtos.size());

        userRepo.delete(user2);
        userRepo.delete(user1);
    }

    @Test
    void updateProject() {
        ProjectEntity project = projectRepo.findAll().get(0);
        long projectID = project.getId();
        assertEquals(0, project.getProjectVersion());
        assertEquals(0, project.getImageVersion());

        projectRepo.increaseImageVersionById(projectID);


        assertEquals(1, projectRepo.findById(projectID).get().getImageVersion());
        assertEquals(0, project.getProjectVersion());

        projectRepo.updateMetaData("new Title", new Date(), new Date(), projectID);
        project = projectRepo.findById(1L).get();

        assertEquals(1, project.getProjectVersion());
        assertEquals(1, project.getImageVersion());
        assertEquals("new Title", projectRepo.findById(projectID).get().getName());

    }

    @AfterEach
    void tearDown() {
        projectRepo.deleteAll();
    }


}
