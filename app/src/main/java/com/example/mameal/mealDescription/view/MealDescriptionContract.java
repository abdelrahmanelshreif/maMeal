package com.example.mameal.mealDescription.view;

import androidx.fragment.app.FragmentManager;

import com.example.mameal.mealDescription.model.Ingredient;
import com.example.mameal.model.Meal;

import java.util.List;

public interface MealDescriptionContract {
    public interface View {
        void successAddingToFav(String message);

        void setMealMainData(Meal meal);

        void showIngredients(List<Ingredient> ingredientList);

        void showError(String message);

        String getCurrentMealId();

        void showSucessAddingToPlan(String s);
    }

    public interface Presenter {

        void getMealData(String mealId);

        String getFormattedYoutubeUrl(String youtubeUrl);

        void getIngredients(String mealId);

        String getFormattedInstructions(Meal meal);

        void addToFavourite(String mealId);

        void showDatePicker(FragmentManager fragmentManager);
    }
}
