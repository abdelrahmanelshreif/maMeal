package com.example.mameal.mealDescription.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.presenter.MealDescriptionPresenter;

import java.util.ArrayList;
import java.util.List;

public class IngredientFragment extends Fragment implements RecipeDetailsContract.View {

    RecyclerView ingredientsRecyclerView;
    IngredientsAdapter ingredientsAdapter;
    MealDescriptionPresenter mealDescriptionPresenter;

    public static IngredientFragment newInstance(MealDescriptionPresenter presenter) {
        IngredientFragment fragment = new IngredientFragment();
        fragment.mealDescriptionPresenter = presenter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);

        ingredientsAdapter = new IngredientsAdapter(getContext(), new ArrayList<>());
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        if (mealDescriptionPresenter == null) {
            Log.d("IngredientFragment", "mealDescriptionPresenter is NULL!");
        } else {
            Log.d("IngredientFragment", "mealDescriptionPresenter is NOT null, calling loadIngredients()");
            mealDescriptionPresenter.loadIngredients();
        }

    }

    @Override
    public void showIngredients(List<IngredientWithMeasure> ingredientList) {
        Log.d("IngredientFragment", "showIngredients() called with " + ingredientList.size() + " items.");

        if (ingredientList.isEmpty()) {
            Log.e("IngredientFragment", "ingredientList is EMPTY! Nothing to display.");
        }

        ingredientsAdapter.updateData(ingredientList);
    }

    @Override
    public void showFragment(Fragment fragment) {
        // Not needed in this fragment
    }
}
