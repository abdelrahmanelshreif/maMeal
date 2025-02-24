package com.example.mameal.mealDescription.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.presenter.MealDescriptionPresenter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.google.android.material.chip.Chip;

import java.util.List;

public class MealDescriptionFragment extends Fragment implements MealDescriptionContract.View {
    MealDescriptionPresenter mealDescriptionPresenter;
    private RecyclerView ingredientRecyclerView;
    private ConstraintLayout instructionsLayout;
    private Chip ingredientChip, procedureChip;
    private ScrollView procedureScrollView;

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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);
        ingredientRecyclerView.setVisibility(View.VISIBLE);
        instructionsLayout.setVisibility(View.GONE);
        showIngredients(mealDescriptionPresenter.getIngredients());

        ingredientChip.setOnClickListener(v -> {
            ingredientChip.setChipBackgroundColorResource(R.color.white);
            ingredientChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            ingredientChip.setChipBackgroundColorResource(R.color.primary_100);

            procedureChip.setChipBackgroundColorResource(R.color.white);
            procedureChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));

            procedureScrollView.setVisibility(View.GONE);
            ingredientRecyclerView.setVisibility(View.VISIBLE);
            instructionsLayout.setVisibility(View.GONE);
        });

        procedureChip.setOnClickListener(v -> {

            procedureScrollView.setVisibility(View.VISIBLE);
            procedureChip.setChipBackgroundColorResource(R.color.primary_100);
            procedureChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

            ingredientChip.setChipBackgroundColorResource(R.color.white);
            ingredientChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));

            ingredientRecyclerView.setVisibility(View.GONE);
            instructionsLayout.setVisibility(View.VISIBLE);
        });


    }

    private void setupUiComponent(@NonNull View view) {
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecyclerView);
        instructionsLayout = view.findViewById(R.id.instructionsLayout);
        ingredientChip = view.findViewById(R.id.ingredientChip);
        procedureChip = view.findViewById(R.id.procedureChip);
        procedureScrollView = view.findViewById(R.id.procedureScrollView);
    }

    @Override
    public void showIngredients(List<IngredientWithMeasure> ingredientList) {
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
