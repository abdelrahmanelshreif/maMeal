package com.example.mameal.mealDescription.view;

import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import java.util.List;

public interface MealDescriptionContract {
    public interface View {
        void showIngredients(List<IngredientWithMeasure> ingredientList);

    }
    public interface Presenter {
        List<IngredientWithMeasure> getIngredients();

    }
}
