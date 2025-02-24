package com.example.mameal.mealDescription.presenter;

import static android.provider.Settings.System.getString;

import com.example.mameal.R;
import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.view.MealDescriptionContract;
import com.example.mameal.model.MaMealRepository;
import java.util.ArrayList;
import java.util.List;

public class MealDescriptionPresenter implements MealDescriptionContract.Presenter {

    MaMealRepository maMealRepository;
    IngredientWithMeasure ingredientWithMeasure;

    public MealDescriptionPresenter(MaMealRepository maMealRepository, IngredientWithMeasure ingredientWithMeasure) {
        this.maMealRepository = maMealRepository;
        this.ingredientWithMeasure = ingredientWithMeasure;
    }

    @Override
    public List<IngredientWithMeasure> getIngredients() {
        return DummyData.getDummyIngredients();
    }

    @Override
    public void getProcedure() {
        // Not implemented yet
    }

    public static class DummyData {
        public static List<IngredientWithMeasure> getDummyIngredients() {
            List<IngredientWithMeasure> ingredients = new ArrayList<>();
            ingredients.add(new IngredientWithMeasure("Flour", "2 cups", "https://www.hhs1.com/hubfs/carrots%20on%20wood-1.jpg"));
            ingredients.add(new IngredientWithMeasure("Sugar", "1 cup", "https://www.hhs1.com/hubfs/carrots%20on%20wood-1.jpg"));
            ingredients.add(new IngredientWithMeasure("Butter", "100g", "https://www.hhs1.com/hubfs/carrots%20on%20wood-1.jpg"));
            ingredients.add(new IngredientWithMeasure("Eggs", "2 pieces", "https://www.hhs1.com/hubfs/carrots%20on%20wood-1.jpg"));
            ingredients.add(new IngredientWithMeasure("Milk", "1/2 cup", "https://www.hhs1.com/hubfs/carrots%20on%20wood-1.jpg"));
            ingredients.add(new IngredientWithMeasure("Vanilla Extract", "1 tsp", "https://www.hhs1.com/hubfs/carrots%20on%20wood-1.jpg"));

            return ingredients;
        }
    }
}
