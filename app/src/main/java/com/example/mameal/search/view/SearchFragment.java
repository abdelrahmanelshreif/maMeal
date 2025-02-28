package com.example.mameal.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.model.Category;
import com.example.mameal.model.Country;
import com.example.mameal.model.Ingredient;
import com.example.mameal.model.Meal;
import com.example.mameal.search.presenter.SearchPresenter;
import com.example.mameal.shared.Utility;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView, OnClickMealListener, OnIngredientClickListener, OnCountryClickListener, OnCategoryClickListener {

    private RecyclerView recyclerView;
    private ChipGroup chipGroupFilter;
    SearchPresenter searchPresenter;
    TextInputEditText searchTextHolder;
    public static final String CATEGORY = "Category";
    public static final String COUNTRY = "Country";
    public static final String INGREDIENT = "Ingredient";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.searchPresenter = new SearchPresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);
        setupFilterChips();
        searchPresenter.getAllMeals();
        searchTextHolder.addTextChangedListener(getWatcher());


    }

    private void setupFilterChips() {
        for (int i = 0; i < chipGroupFilter.getChildCount(); i++) {
            Chip chip = (Chip) chipGroupFilter.getChildAt(i);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    Log.i("TAG", "Chip clicked: " + buttonView.getText().toString());
                    switchRecyclerView(buttonView.getText().toString());
                }
            });
        }
    }

    private void switchRecyclerView(String selectedFilter) {
        switch (selectedFilter) {
            case CATEGORY:
                searchPresenter.getCategoriesData();
                break;
            case COUNTRY:
                searchPresenter.getAreasData();
                break;
            case INGREDIENT:
                searchPresenter.getIngredientsData();
                break;
        }

    }

    @NonNull
    private TextWatcher getWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPresenter.observeSearch(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void setupUiComponent(@NonNull View view) {
        searchTextHolder = view.findViewById(R.id.searchTextHolder);
        recyclerView = view.findViewById(R.id.searchResultRecyclerView);
        chipGroupFilter = view.findViewById(R.id.chipsGroupFilter);
    }

    @Override
    public void showAllMeals(List<Meal> meals) {
        MealsAdapter mealsAdapter = new MealsAdapter(requireContext(), meals, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(requireContext(), categories, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void showAreas(List<Country> areas) {
        CountryAdapter countryAdapter = new CountryAdapter(requireContext(), areas, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(countryAdapter);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        IngredientAdapter ingredientsAdapter = new IngredientAdapter(requireContext(), ingredients, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(ingredientsAdapter);

    }
    @Override
    public void showError(String message) {
        Utility.showToast(requireContext(), message);
    }

    @Override
    public void showMealsFilteredByIngredient(List<Meal> meals) {

    }

    @Override
    public void showMealsFilteredByCountry(List<Meal> meals) {

    }

    @Override
    public void showMealsFilteredByCategory(List<Meal> meals) {

    }


}