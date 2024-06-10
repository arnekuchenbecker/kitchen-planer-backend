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
        recipeEntity.setVersion(0L);
        recipeEntity.setImageVersion(-1L);
        recipeEntity.setName(recipe.name());
        recipeEntity.setDescription(recipe.description());
        recipeEntity.setNumberOfPeople(recipe.number_of_people());
        this.recipeRepository.save(recipeEntity);
        saveToRepositories(recipe, recipeEntity);
    }

    @Transactional
    public void updateRecipe(Recipe recipe) {
        RecipeEntity oldRecipeEntity = this.recipeRepository.findById(recipe.id()).orElseThrow();

        //Todo update Metadata in services

        saveToRepositories(recipe, oldRecipeEntity);
    }

    private void saveToRepositories(Recipe recipe,  RecipeEntity recipeEntity) {

        Collection<DietarySpecialityEntity> oldDietarySpecialities = this.dietarySpecialityRepository.getDietarySpecialityEntitiesByRecipeId(recipe.id());
        this.dietarySpecialityRepository.deleteAll(oldDietarySpecialities);

        writeDietaryRestrictionType(recipeEntity, recipe.traces(), DietaryTypes.TRACE);
        writeDietaryRestrictionType(recipeEntity, recipe.allergens(), DietaryTypes.ALLERGEN);
        writeDietaryRestrictionType(recipeEntity, recipe.freeOfAllergen(), DietaryTypes.FREE_OF);

        Collection<InstructionEntity> oldInstructions = this.instructionRepository.getInstructionEntitiesByRecipeIdOrderByStepNumber(recipe.id());
        this.instructionRepository.deleteAll(oldInstructions);

        for (int i = 0; i < recipe.instructions().toArray().length; i++) {
            InstructionEntity instructionEntity = new InstructionEntity();
            instructionEntity.setRecipe(recipeEntity);
            instructionEntity.setInstruction(recipe.instructions().get(i));
            instructionEntity.setStepNumber(i);
            this.instructionRepository.save(instructionEntity);
        }

        Collection<IngredientEntity> oldIngredients = this.ingredientRepository.getIngredientEntitiesByRecipeId(recipe.id());
        this.ingredientRepository.deleteAll(oldIngredients);

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
