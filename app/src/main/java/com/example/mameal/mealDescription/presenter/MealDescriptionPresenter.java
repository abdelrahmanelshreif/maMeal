package com.example.mameal.mealDescription.presenter;

import androidx.fragment.app.Fragment;

import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.model.RecipeTab;
import com.example.mameal.mealDescription.view.IngredientFragment;
import com.example.mameal.mealDescription.view.ProcedureFragment;
import com.example.mameal.mealDescription.view.RecipeDetailsContract;
import com.example.mameal.model.MaMealRepository;

import java.util.ArrayList;
import java.util.List;

public class MealDescriptionPresenter implements RecipeDetailsContract.Presenter {

    MaMealRepository maMealRepository;
    IngredientWithMeasure ingredientWithMeasure;
    RecipeDetailsContract.View mealDescriptionView;

    public MealDescriptionPresenter(MaMealRepository maMealRepository, IngredientWithMeasure ingredientWithMeasure, RecipeDetailsContract.View mealDescriptionView) {
        this.maMealRepository = maMealRepository;
        this.ingredientWithMeasure = ingredientWithMeasure;
        this.mealDescriptionView = mealDescriptionView;
    }

    public void loadIngredients() {
        mealDescriptionView.showIngredients(DummyData.getDummyIngredients());
    }

    @Override
    public void onChipSelected(RecipeTab selectedTab) {
        Fragment fragment;
        if (selectedTab == RecipeTab.INGREDIENTS) {
            IngredientFragment ingredientFragment = IngredientFragment.newInstance(this);
            fragment = ingredientFragment;
        } else {
            fragment = new ProcedureFragment();
        }
        mealDescriptionView.showFragment(fragment);
    }

    public static class DummyData {
        public static List<IngredientWithMeasure> getDummyIngredients() {
            List<IngredientWithMeasure> ingredients = new ArrayList<>();

            ingredients.add(new IngredientWithMeasure("Flour", "2 cups", "https://example.com/flour.png"));
            ingredients.add(new IngredientWithMeasure("Sugar", "1 cup", "https://example.com/sugar.png"));
            ingredients.add(new IngredientWithMeasure("Butter", "100g", "https://example.com/butter.png"));
            ingredients.add(new IngredientWithMeasure("Eggs", "2 pieces", "https://example.com/eggs.png"));
            ingredients.add(new IngredientWithMeasure("Milk", "1/2 cup", "https://example.com/milk.png"));
            ingredients.add(new IngredientWithMeasure("Vanilla Extract", "1 tsp", "https://example.com/vanilla.png"));

            return ingredients;
        }

    }
}
