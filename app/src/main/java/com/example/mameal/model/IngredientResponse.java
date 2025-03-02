package com.example.mameal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    List<Ingredient> ingredientList;

    public IngredientResponse() {
    }

    public IngredientResponse(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }


    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
