package com.example.mameal.search.view;

import com.example.mameal.model.Category;
import com.example.mameal.model.Country;
import com.example.mameal.model.Ingredient;
import com.example.mameal.model.Meal;

import java.util.List;

public interface SearchView {
    void showAllMeals(List<Meal> meals);

    void showCategories(List<Category> categories);

    void showError(String message);

    void showMealsFilteredByIngredient(List<Meal> meals);

    void showMealsFilteredByCountry(List<Meal> meals);

    void showMealsFilteredByCategory(List<Meal> meals);

    void showAreas(List<Country> areas);

    void showIngredients(List<Ingredient> ingredients);
}
