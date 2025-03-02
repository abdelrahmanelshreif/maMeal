package com.example.mameal.home.view;

import android.view.View;

import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.model.Meal;


import java.util.List;

public interface HomeView {
    void setAdaptersOfCategories(List<CategoryWithMeals> categories);

    void setDailyMealData(Meal meal);
    void showError(String err);

    void navigateToMealDescription(View view, String mealId);

    void successAddingToFav(String successfullyAddedToFavourite);
}
