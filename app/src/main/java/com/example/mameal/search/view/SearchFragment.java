package com.example.mameal.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.mealDescription.view.IngredientsAdapter;
import com.example.mameal.model.Category;
import com.example.mameal.model.Country;
import com.example.mameal.model.Ingredient;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.search.presenter.SearchPresenter;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView,OnClickMealListener, OnIngredientClickListener, OnCountryClickListener, OnCategoryClickListener {

    private RecyclerView recyclerView;
    private ChipGroup chipGroupFilter;
    SearchPresenter searchPresenter;

    TextInputEditText searchTextHolder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.searchPresenter = new SearchPresenter(this,getContext());
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
        searchTextHolder.addTextChangedListener(new TextWatcher() {
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
        });
        searchPresenter.getAllMeals();
        

    }

    private void setupUiComponent(@NonNull View view) {
        searchTextHolder = view.findViewById(R.id.searchTextHolder);
        recyclerView = view.findViewById(R.id.searchResultRecyclerView);
    }

    @Override
    public void showAllMeals(List<Meal> meals) {
        MealsAdapter mealsAdapter = new MealsAdapter(requireContext(), meals,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showCategories(List<Category> categories) {

    }

    @Override
    public void showError(String message) {

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

    @Override
    public void showAreas(List<Country> areas) {

    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {

    }
}