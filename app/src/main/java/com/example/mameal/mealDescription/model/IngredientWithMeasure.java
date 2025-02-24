package com.example.mameal.mealDescription.model;

public class IngredientWithMeasure {
    String ingredientTitle;
    String ingredientMeasure;
    String ingredientThumbnail;

    public IngredientWithMeasure(){}
    public IngredientWithMeasure(String ingredientTitle, String ingredientMeasure, String ingredientThumbnail) {
        this.ingredientTitle = ingredientTitle;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientThumbnail = ingredientThumbnail;
    }

    public String getIngredientTitle() {
        return ingredientTitle;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public String getIngredientThumbnail() {
        return ingredientThumbnail;
    }

    public void setIngredientTitle(String ingredientTitle) {
        this.ingredientTitle = ingredientTitle;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }

    public void setIngredientThumbnail(String ingredientThumbnail) {
        this.ingredientThumbnail = ingredientThumbnail;
    }
}
