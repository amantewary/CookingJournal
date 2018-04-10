package com.example.amant.cookingjournal;

/**
 * Created by amant on 07-03-2018.
 */

public class Recipes {

    /*
        This is a handler for adding and retrieving recipes,
     */

    private String recipeId;
    private String recipeTitle;
    private String recipeIngredients;
    private String recipeSteps;
    private String recipeCuisine;
    private String recipeUrl;
    private Float recipeRating;

    public Recipes() {

    }

    public Recipes(String recipeId, String recipeTitle, String recipeIngredients, String recipeSteps, String recipeCuisine, String recipeUrl, Float recipeRating) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
        this.recipeCuisine = recipeCuisine;
        this.recipeUrl = recipeUrl;
        this.recipeRating = recipeRating;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
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
