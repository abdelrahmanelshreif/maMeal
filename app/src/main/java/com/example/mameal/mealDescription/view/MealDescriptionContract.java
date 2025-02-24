package com.example.mameal.mealDescription.view;

import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import java.util.List;

public interface MealDescriptionContract {
    public interface View {
        void showIngredients(List<IngredientWithMeasure> ingredientList); // ✅ Now takes a parameter
        void showProcedure();
    }

    public interface Presenter {
        List<IngredientWithMeasure> getIngredients();
        void getProcedure();
    }
}
