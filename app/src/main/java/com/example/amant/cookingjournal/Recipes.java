package com.example.amant.cookingjournal;

/**
 * Created by amant on 07-03-2018.
 */

public class Recipes {

    private String recipeId;
    private String recipeName;
    private String recipeIngredients;
    private String recipeSteps;
    private String recipeCuisine;
    private String recipeUrl;
    private Float recipeRating;

    public Recipes(String recipeId, String recipeName, String recipeIngredients, String recipeSteps, String recipeCuisine, String recipeUrl, Float recipeRating) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.recipeCuisine = recipeCuisine;
        this.recipeUrl = recipeUrl;
        this.recipeRating = recipeRating;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public String getRecipeSteps() {
        return recipeSteps;
    }

    public String getRecipeCuisine() {
        return recipeCuisine;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public Float getRecipeRating() {
        return recipeRating;
    }
}
