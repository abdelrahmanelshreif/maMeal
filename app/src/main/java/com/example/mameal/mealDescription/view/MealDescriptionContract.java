package com.example.mameal.mealDescription.view;

import com.example.mameal.mealDescription.model.Ingredient;
import com.example.mameal.model.Meal;

import java.util.List;

public interface MealDescriptionContract {
    public interface View {
        void setMealMainData(Meal meal);
        void showIngredients(List<Ingredient> ingredientList);

        void showError(String message);
    }
    public interface Presenter {
        void getMealData(String mealId);
        String getFormattedYoutubeUrl(String youtubeUrl);
        void getIngredients(String mealId);
    }
}
