package com.example.mameal.mealDescription.view;

import androidx.fragment.app.Fragment;

import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.model.RecipeTab;

import java.util.List;

public interface RecipeDetailsContract {
    interface View {
        void showFragment(Fragment fragment);

        void showIngredients(List<IngredientWithMeasure> ingredientList);
    }

    interface Presenter {
        void onChipSelected(RecipeTab selectedTab);
    }
}
