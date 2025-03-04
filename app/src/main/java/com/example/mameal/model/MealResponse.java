package com.example.mameal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealResponse {

    @SerializedName("meals")
    private List<Meal> meals;


    public MealResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {

        return meals;
    }

}
