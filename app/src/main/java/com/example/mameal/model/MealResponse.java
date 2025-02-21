package com.example.mameal.model;

import android.database.Observable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    private Observable<List<Meal>> meals;


    public MealResponse(Observable<List<Meal>> meals) {
        this.meals = meals;
    }

    public Observable<List<Meal>> getMeals() {
        return meals;
    }

    public void setMeals(Observable<List<Meal>>meals) {
        this.meals = meals;
    }
}
