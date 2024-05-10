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

import com.scouts.kitchenplanerbackend.AllergenPerson;
import com.scouts.kitchenplanerbackend.PersonNumberChange;
import com.scouts.kitchenplanerbackend.Project;
import com.scouts.kitchenplanerbackend.RecipeForProject;
import com.scouts.kitchenplanerbackend.UnitConversion;
import com.scouts.kitchenplanerbackend.entities.projects.AllergenEntity;
import com.scouts.kitchenplanerbackend.entities.projects.AllergenPersonEntity;
import com.scouts.kitchenplanerbackend.entities.projects.AlternativeRecipeProjectMeal;
import com.scouts.kitchenplanerbackend.entities.projects.MainRecipeProjectMealEntity;
import com.scouts.kitchenplanerbackend.entities.projects.MealEntity;
import com.scouts.kitchenplanerbackend.entities.projects.PersonNumberChangeEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectEntity;
import com.scouts.kitchenplanerbackend.entities.projects.ProjectStubDTO;
import com.scouts.kitchenplanerbackend.entities.projects.UnitConversionEntity;
import com.scouts.kitchenplanerbackend.repositories.projects.AllergenPersonRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.AllergenRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.AlternativeRecipeProjectMealRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.MainRecipeProjectMealRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.MealRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.PersonNumberChangeRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.ProjectRepository;
import com.scouts.kitchenplanerbackend.repositories.projects.UnitConversionRepository;
import com.scouts.kitchenplanerbackend.repositories.recipes.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class ProjectService {
    private final ProjectRepository projectRepo;
    private final MealRepository mealRepository;

    private final AllergenPersonRepository allergenPersonRepo;

    private final AllergenRepository allergenRepo;
    private final MainRecipeProjectMealRepository mainRecipeProjectMealRepository;
    private final RecipeRepository recipeRepository;
    private final AlternativeRecipeProjectMealRepository alternativeRecipeProjectMealRepository;
    private final UnitConversionRepository unitConversionRepository;
    private final PersonNumberChangeRepository personNumberChangeRepo;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, MealRepository mealRepository,
                          AllergenPersonRepository allergenPersonRepository, AllergenRepository allergenRepository,
                          MainRecipeProjectMealRepository mainRecipeProjectMealRepository,
                          RecipeRepository recipeRepository,
                          AlternativeRecipeProjectMealRepository alternativeRecipeProjectMealRepository,
                          UnitConversionRepository unitConversionRepository,
                          PersonNumberChangeRepository personNumberChangeRepository) {
        this.projectRepo = projectRepository;
        this.mealRepository = mealRepository;
        this.allergenPersonRepo = allergenPersonRepository;
        this.allergenRepo = allergenRepository;
        this.mainRecipeProjectMealRepository = mainRecipeProjectMealRepository;
        this.recipeRepository = recipeRepository;
        this.alternativeRecipeProjectMealRepository = alternativeRecipeProjectMealRepository;
        this.unitConversionRepository = unitConversionRepository;
        this.personNumberChangeRepo = personNumberChangeRepository;
    }

    /**
     * Stores a new project from a user
     *
     * @param project The project that should be stored
     * @return online ID of the project
     */
    @Transactional
    public long saveNewProject(Project project) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(project.name());
        projectEntity.setStartDate(project.startDate());
        projectEntity.setEndDate(project.endDate());
        projectEntity = projectRepo.save(projectEntity);
        long projectID = projectEntity.getId();

        for (int i = 0; i < project.meals().size(); i++) {
            MealEntity mealEntity = new MealEntity();
            mealEntity.setProject(projectEntity);
            mealEntity.setName(project.meals().get(i));
            mealEntity.setSequence(i);
            mealRepository.save(mealEntity);
        }

        for (AllergenPerson allergenPerson : project.allergenPeople()) {
            AllergenPersonEntity person = new AllergenPersonEntity();
            person.setName(allergenPerson.name());
            person.setProject(projectEntity);
            person.setArrivalDate(allergenPerson.arrivalDate());
            person.setArrivalMeal(
                    mealRepository.findByProject_IdAndName(projectID, allergenPerson.arrivalMeal()).orElseThrow());
            person.setDepartureMeal(
                    mealRepository.findByProject_IdAndName(projectID, allergenPerson.leaveMeal()).orElseThrow());
            person.setDepartureDate(allergenPerson.leaveDate());
            person = allergenPersonRepo.save(person);

            for (String allergen : allergenPerson.allergen()) {
                AllergenEntity allergenEntity = new AllergenEntity();
                allergenEntity.setProject(projectEntity);
                allergenEntity.setAllergenPerson(person);
                allergenEntity.setAllergen(allergen);
                allergenEntity.setTraces(false);
                allergenRepo.save(allergenEntity);
            }

            for (String trace : allergenPerson.traces()) {
                AllergenEntity traceEntity = new AllergenEntity();
                traceEntity.setProject(projectEntity);
                traceEntity.setAllergenPerson(person);
                traceEntity.setAllergen(trace);
                traceEntity.setTraces(true);
                allergenRepo.save(traceEntity);
            }
            for (RecipeForProject recipe : project.recipes()) {
                if (recipe.mainRecipe()) {
                    MainRecipeProjectMealEntity mainRecipe = new MainRecipeProjectMealEntity();
                    mainRecipe.setProject(projectEntity);
                    mainRecipe.setDate(recipe.date());
                    mainRecipe.setMeal(mealRepository.findByProject_IdAndName(projectID, recipe.meal()).orElseThrow());
                    mainRecipe.setRecipe(recipeRepository.findById(recipe.recipeID()).orElseThrow());
                    mainRecipeProjectMealRepository.save(mainRecipe);
                } else {
                    AlternativeRecipeProjectMeal alterativeRecipe = new AlternativeRecipeProjectMeal();
                    alterativeRecipe.setDate(recipe.date());
                    alterativeRecipe.setProject(projectEntity);
                    alterativeRecipe.setMeal(
                            mealRepository.findByProject_IdAndName(projectID, recipe.meal()).orElseThrow());
                    alterativeRecipe.setRecipe(recipeRepository.findById(recipe.recipeID()).orElseThrow());
                    alternativeRecipeProjectMealRepository.save(alterativeRecipe);
                }
            }

            for (UnitConversion conversion : project.unitConversions()) {
                UnitConversionEntity conversionEntity = new UnitConversionEntity();
                conversionEntity.setProject(projectEntity);
                conversionEntity.setFactor(conversion.factor());
                conversionEntity.setIngredient(conversion.ingredient());
                conversionEntity.setSourceUnit(conversion.startUnit());
                conversionEntity.setDestinationUnit(conversion.endUnit());
                unitConversionRepository.save(conversionEntity);
            }

            for (PersonNumberChange change : project.personNumberChange()) {
                PersonNumberChangeEntity entity = new PersonNumberChangeEntity();
                entity.setDate(change.date());
                entity.setProject(projectEntity);
                entity.setMeal(mealRepository.findByProject_IdAndName(projectID, change.meal()).orElseThrow());
                entity.setDifferenceBefore(change.differenceBefore());
                personNumberChangeRepo.save(entity);
            }
        }
        return projectID;
    }

    /**
     * Rewrites all data of a project, so the latest version of a project is saved
     *
     * @param project The new version of the project
     * @return whether the new version could be saved
     */
    @Transactional
    public boolean updateProject(Project project) {
        projectRepo.updateNameAndStartDateAndEndDateById(project.name(), project.startDate(), project.endDate(),
                project.id());
        ProjectEntity entity = projectRepo.findById(project.id()).orElseThrow();
        return false;
    }

    /**
     * Provides a project for a user if the user is part of the project
     *
     * @param projectID Online ID of a project
     * @return the project
     */
    @Transactional
    public Project getProject(long projectID) {
        ProjectEntity project = projectRepo.findById(projectID).orElseThrow();
        List<AllergenPerson> allergenPeople = new ArrayList<>();
        for (AllergenPersonEntity allergenPersonEntity : allergenPersonRepo.findByProject_Id(projectID)) {
            List<String> allergen = new ArrayList<>();
            List<String> traces = new ArrayList<>();
            Collection<AllergenEntity> allergenEntities =
                    allergenRepo.findByProject_IdAndAllergenPerson_Name(projectID, allergenPersonEntity.getName());
            for (AllergenEntity allergenEntity : allergenEntities) {
                if (allergenEntity.getTraces()) {
                    traces.add(allergenEntity.getAllergen());
                } else {
                    allergen.add(allergenEntity.getAllergen());
                }
            }
            allergenPeople.add(new AllergenPerson(
                    allergenPersonEntity.getName(),
                    allergenPersonEntity.getArrivalDate(),
                    allergenPersonEntity.getDepartureDate(),
                    allergenPersonEntity.getArrivalMeal().getName(),
                    allergenPersonEntity.getDepartureMeal().getName(),
                    allergen,
                    traces));
        }
        List<String> meals = mealRepository.findByProject_Id(projectID).stream().map(MealEntity::getName).toList();

        List<PersonNumberChange> personNumberChanges = personNumberChangeRepo.findByProject_Id(projectID).stream()
                .map(entity -> new PersonNumberChange(entity.getDate(), entity.getMeal().getName(),
                        entity.getDifferenceBefore())).toList();

        List<RecipeForProject> recipes =
                new ArrayList<>(mainRecipeProjectMealRepository.findByProject_Id(projectID).stream().map(
                        entity -> new RecipeForProject(entity.getDate(), entity.getMeal().getName(),
                                entity.getRecipe().getId(),
                                true)).toList());
        recipes.addAll(alternativeRecipeProjectMealRepository.findByProject_Id(projectID).stream()
                .map(entity -> new RecipeForProject(entity.getDate(), entity.getMeal().getName(),
                        entity.getRecipe().getId(), false)).toList());

        List<UnitConversion> unitConversions =
                unitConversionRepository.findByProject_Id(projectID).stream().map(entity ->
                        new UnitConversion(entity.getSourceUnit(),
                                entity.getDestinationUnit(),
                                entity.getIngredient(),
                                entity.getFactor())
                ).toList();

        return new Project(0, 0, project.getName(),
                project.getId(), meals, project.getStartDate(), project.getEndDate()
                , allergenPeople, recipes, unitConversions, personNumberChanges);
    }

    /**
     * Provides all project stubs for the project a user is participating in
     *
     * @param username The user who participates in the projects
     * @return all project Stubs
     */
    public Collection<ProjectStubDTO> getProjectStubs(String username) {
        return projectRepo.findByParticipants_Name(username);
    }
}
