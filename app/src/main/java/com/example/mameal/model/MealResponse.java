package com.example.mameal.model;

import android.database.Observable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealResponse {

    @SerializedName("meals")
    private Flowable<List<Meal>> meals;


    public MealResponse(Flowable<List<Meal>> meals) {
        this.meals = meals;
    }

    public Flowable<List<Meal>> getMeals() {
        return meals;
    }

}
