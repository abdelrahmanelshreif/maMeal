package com.example.mameal.home.view;

import com.example.mameal.uiModel.MealUiModel;

import java.util.List;

public interface HomeView {

    void showMeals(List<MealUiModel> mealsList);

    void showError(String err);
}
