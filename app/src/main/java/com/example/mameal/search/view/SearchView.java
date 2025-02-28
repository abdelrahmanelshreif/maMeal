package com.example.mameal.search.view;

import com.example.mameal.model.Category;
import com.example.mameal.model.Country;
import com.example.mameal.model.Ingredient;
import com.example.mameal.model.Meal;

import java.util.List;

public interface SearchView {
    public static String CATEGORY = "Category";
    public static String COUNTRY = "Country";
    public static String INGREDIENT = "Ingredient";

    void showAllMeals(List<Meal> meals);

    void showCategories(List<Category> categories);

    void showError(String message);

    void showAreas(List<Country> areas);

    void showIngredients(List<Ingredient> ingredients);

    void showLoading();

    void hideLoading();
}
