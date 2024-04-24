package com.scouts.kitchenplanerbackend.services;

import com.scouts.kitchenplanerbackend.entities.recipe.DietarySpecialityEntity;
import com.scouts.kitchenplanerbackend.entities.recipe.DietaryTypes;
import com.scouts.kitchenplanerbackend.entities.recipe.RecipeEntity;
import com.scouts.kitchenplanerbackend.repositories.recipes.DietarySpecialityRepository;
import com.scouts.kitchenplanerbackend.repositories.recipes.RecipeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public class RecipeService {
    RecipeRepository recipeRepository;
    DietarySpecialityRepository dietarySpecialityRepository;

    @Transactional
    public void saveNewRecipe(Recipe recipe) {
        saveNewToRepositories(recipe);
    }

    private void saveNewToRepositories(Recipe recipe) {
        saveToRepositories(recipe, true);
    }

    private void saveToRepositories(Recipe recipe) {
        saveToRepositories(recipe, false);
    }

    private void saveToRepositories(Recipe recipe, boolean saveNew) {
        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setName(recipe.name());
        //TODO image uri
        long version;
        if (saveNew) {
            version = 0L;
        } else {
            long oldId = this.recipeRepository.getRecipeEntityBy(recipe.id()).getId();
            version = ++oldId;
            assert version > 0L;
        }
        recipeEntity.setVersion(version);
        recipeEntity.setDescription(recipe.description());
        recipeEntity.setNumberOfPeople(recipe.number_of_people());
        this.recipeRepository.save(recipeEntity);

        Collection<DietarySpecialityEntity> old = this.dietarySpecialityRepository.getDietarySpecialityEntitiesByRecipeId(recipe.id());
        this.dietarySpecialityRepository.deleteAll(old);

        writeDietaryRestrictionType(recipeEntity, recipe.traces(), DietaryTypes.TRACE);
        writeDietaryRestrictionType(recipeEntity, recipe.allergens(), DietaryTypes.ALLERGEN);
        writeDietaryRestrictionType(recipeEntity, recipe.freeOfAllergen(), DietaryTypes.FREE_OF);
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
