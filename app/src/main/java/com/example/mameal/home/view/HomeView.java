package com.example.mameal.home.view;

import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.model.Meal;


import java.util.List;

public interface HomeView {
    void setAdaptersOfCategories(List<CategoryWithMeals> categories);

    void setDailyMealData(Meal meal);
    void showError(String err);
}
