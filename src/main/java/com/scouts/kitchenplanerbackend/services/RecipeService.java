package com.scouts.kitchenplanerbackend.services;

import com.scouts.kitchenplanerbackend.entities.recipe.RecipeEntity;
import com.scouts.kitchenplanerbackend.repositories.recipes.RecipeRepository;
import org.springframework.transaction.annotation.Transactional;

public class RecipeService {
    RecipeRepository recipeRepository;

    @Transactional
    public void saveNewRecipe(Recipe recipe) {
        saveNewInRecipeRepository(recipe);
    }

    private void saveNewInRecipeRepository(Recipe recipe) {
        saveToRecipeRepository(recipe, 0L);
    }

    private void saveToRecipeRepository(Recipe recipe) {
        long oldId = this.recipeRepository.getRecipeEntityBy(recipe.id()).getId();
        saveToRecipeRepository(recipe, ++oldId);
    }

    private void saveToRecipeRepository(Recipe recipe, long version) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(recipe.name());
        //TODO image uri
        recipeEntity.setVersion(version);
        recipeEntity.setDescription(recipe.description());
        recipeEntity.setNumberOfPeople(recipe.number_of_people());
        this.recipeRepository.save(recipeEntity);
    }
}
