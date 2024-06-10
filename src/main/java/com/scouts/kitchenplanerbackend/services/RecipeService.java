package com.scouts.kitchenplanerbackend.services;

import com.scouts.kitchenplanerbackend.entities.recipe.*;
import com.scouts.kitchenplanerbackend.repositories.recipes.DietarySpecialityRepository;
import com.scouts.kitchenplanerbackend.repositories.recipes.IngredientRepository;
import com.scouts.kitchenplanerbackend.repositories.recipes.InstructionRepository;
import com.scouts.kitchenplanerbackend.repositories.recipes.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public class RecipeService {
    RecipeRepository recipeRepository;
    DietarySpecialityRepository dietarySpecialityRepository;
    InstructionRepository instructionRepository;
    IngredientRepository ingredientRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, DietarySpecialityRepository dietarySpecialityRepository, InstructionRepository instructionRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.dietarySpecialityRepository = dietarySpecialityRepository;
        this.instructionRepository = instructionRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public void saveNewRecipe(Recipe recipe) {
        RecipeEntity recipeEntity = new RecipeEntity();

        //set all info from the recipe that is directly stored in the recipe entity
        recipeEntity.setName(recipe.name());
        recipeEntity.setDescription(recipe.description());
        recipeEntity.setNumberOfPeople(recipe.number_of_people());
        this.recipeRepository.save(recipeEntity);

        updateRecipeEntityFromRecipe(recipeEntity, recipe);
    }

    @Transactional
    public void updateRecipe(Recipe recipe) {
        RecipeEntity oldRecipeEntity = this.recipeRepository.findById(recipe.id()).orElseThrow();

        //update all info from the recipe that may be changed by the frontend and is directly stored in the recipe entity
        this.recipeRepository.updateMetaData(recipe.name(), recipe.description(), recipe.number_of_people(), oldRecipeEntity.getId());

        // deletion of old data in secondary repositories to avoid duplications when override happens next
        //Todo make deletions happen in Repository functions
        Collection<DietarySpecialityEntity> oldDietarySpecialities = this.dietarySpecialityRepository.getDietarySpecialityEntitiesByRecipeId(recipe.id());
        this.dietarySpecialityRepository.deleteAll(oldDietarySpecialities);
        Collection<InstructionEntity> oldInstructions = this.instructionRepository.getInstructionEntitiesByRecipeIdOrderByStepNumber(recipe.id());
        this.instructionRepository.deleteAll(oldInstructions);
        Collection<IngredientEntity> oldIngredients = this.ingredientRepository.getIngredientEntitiesByRecipeId(recipe.id());
        this.ingredientRepository.deleteAll(oldIngredients);

        updateRecipeEntityFromRecipe(oldRecipeEntity, recipe);
    }

    private void updateRecipeEntityFromRecipe(RecipeEntity recipeEntity, Recipe recipe) {

        writeDietaryRestrictionType(recipeEntity, recipe.traces(), DietaryTypes.TRACE);
        writeDietaryRestrictionType(recipeEntity, recipe.allergens(), DietaryTypes.ALLERGEN);
        writeDietaryRestrictionType(recipeEntity, recipe.freeOfAllergen(), DietaryTypes.FREE_OF);

        for (int i = 0; i < recipe.instructions().toArray().length; i++) {
            InstructionEntity instructionEntity = new InstructionEntity();
            instructionEntity.setRecipe(recipeEntity);
            instructionEntity.setInstruction(recipe.instructions().get(i));
            instructionEntity.setStepNumber(i);
            this.instructionRepository.save(instructionEntity);
        }

        for (Ingredient ingredient: recipe.ingredients()) {
            IngredientEntity ingredientEntity = new IngredientEntity();
            ingredientEntity.setRecipe(recipeEntity);
            ingredientEntity.setName(ingredient.name());
            ingredientEntity.setIngredientGroup(ingredient.ingredientGroup());
            ingredientEntity.setUnit(ingredient.unit());
            ingredientEntity.setAmount(ingredient.amount());
            this.ingredientRepository.save(ingredientEntity);
        }
    }

    private void writeDietaryRestrictionType(RecipeEntity recipeEntity, List<String> dietaryList, DietaryTypes type) {
        for (String restriction: dietaryList) {
            DietarySpecialityEntity dietarySpecialityEntity = new DietarySpecialityEntity();
            dietarySpecialityEntity.setRecipe(recipeEntity);
            dietarySpecialityEntity.setSpeciality(restriction);
            dietarySpecialityEntity.setType(type);
            this.dietarySpecialityRepository.save(dietarySpecialityEntity);
        }
    }
}
