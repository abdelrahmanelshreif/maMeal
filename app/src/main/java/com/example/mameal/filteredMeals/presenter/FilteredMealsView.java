package com.example.mameal.filteredMeals.presenter;

import com.example.mameal.model.Meal;

import java.util.List;

public interface FilteredMealsView {
    void showMealsFilteredByCategory(List<Meal> meals);

    void showMealsFilteredByIngredient(List<Meal> meals);

    void showMealsFilteredByCountry(List<Meal> meals);

    void showError(String message);

    void showLoading();

    void hideLoading();
}
