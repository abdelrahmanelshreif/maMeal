package com.example.mameal.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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

public class SearchFragment extends Fragment implements SearchView, OnFilteredClickListener, OnClickMealListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ChipGroup chipGroupFilter;
    private String selectedFilter = "";
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
        searchPresenter.getAllMeals();
        /* WARNING */
        // The Setup of Filter Chips Should be here After getAllMeals not before..
        setupFilterChips();
        searchTextHolder.addTextChangedListener(getWatcher());
    }
    private void setupFilterChips() {
        for (int i = 0; i < chipGroupFilter.getChildCount(); i++) {
            Chip chip = (Chip) chipGroupFilter.getChildAt(i);
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    chip.setChipBackgroundColorResource(R.color.primary_100);
                    chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                    switchRecyclerView(buttonView.getText().toString());
                } else {
                    chip.setChipBackgroundColorResource(R.color.white);
                    chip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));


                }
            });
        }
    }

    private void switchRecyclerView(String selectedFilter) {
        this.selectedFilter = selectedFilter;
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
                searchPresenter.observeSearch(s.toString(), selectedFilter);

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
        progressBar = view.findViewById(R.id.progressBarRecyclerViewSearchFragment);
    }

    @Override
    public void showAllMeals(List<Meal> meals) {
        this.selectedFilter = "";
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
    public void showLoading() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Utility.showToast(requireContext(), message);
    }


    @Override
    public void navigateToFilteredMealsFragment(View view, String type, String title) {
        SearchFragmentDirections.ActionSearchFragment3ToFilteredMealsFragment action = SearchFragmentDirections.actionSearchFragment3ToFilteredMealsFragment(selectedFilter, title);
        Navigation.findNavController(view).navigate(action);

    }

    @Override
    public void navigateToMealDescription(View view, String mealId) {
        SearchFragmentDirections.ActionSearchFragmentToMealDescFragment action = SearchFragmentDirections.actionSearchFragmentToMealDescFragment(mealId);
        Navigation.findNavController(view).navigate(action);
    }
}