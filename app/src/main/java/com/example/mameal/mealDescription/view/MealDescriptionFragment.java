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
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;

import java.util.List;

public class MealDescriptionFragment extends Fragment implements MealDescriptionContract.View {
    MealDescriptionPresenter mealDescriptionPresenter;
    RecyclerView ingredientRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mealDescriptionPresenter = new MealDescriptionPresenter(
                MaMealRepository.getInstance(new MaMealRemoteDataSource()),
                new IngredientWithMeasure()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_desc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecyclerView);
        showIngredients(mealDescriptionPresenter.getIngredients());
    }

    @Override
    public void showIngredients(List<IngredientWithMeasure> ingredientList) {
        if (ingredientRecyclerView == null) {
            Log.e("MealDescriptionFragment", "ingredientRecyclerView is NULL!");
            return;
        }

        if (ingredientList == null || ingredientList.isEmpty()) {
            Log.e("MealDescriptionFragment", "No ingredients received!");
            return;
        }

        Log.d("MealDescriptionFragment", "showIngredients() called with " + ingredientList.size() + " items.");

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);
    }


    @Override
    public void showProcedure() {
        // Not implemented yet
    }
}
