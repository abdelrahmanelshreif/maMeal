package com.example.mameal.favourite.view;

import com.example.mameal.model.Meal;

import java.util.List;

public interface FavouriteMealsView {
    void showFavouriteMeals(List<Meal> meals);
    void showErrorRetrievingData(String error);

}
