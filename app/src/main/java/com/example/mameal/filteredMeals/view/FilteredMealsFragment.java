package com.example.mameal.filteredMeals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.filteredMeals.presenter.FilteredMealsPresenter;
import com.example.mameal.filteredMeals.presenter.FilteredMealsView;
import com.example.mameal.model.Meal;
import com.example.mameal.shared.Utility;

import java.util.List;

public class FilteredMealsFragment extends Fragment implements FilteredMealsView, OnFilteredMealClickListenerAtFilterFragment {


    FilteredMealsPresenter filteredMealsPresenter;
    RecyclerView recyclerView;
    TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.filteredMealsPresenter = new FilteredMealsPresenter(this, requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filtered_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponents(view);
        String filterType = FilteredMealsFragmentArgs.fromBundle(getArguments()).getFilterType();
        String filter = FilteredMealsFragmentArgs.fromBundle(getArguments()).getFilter();
        filteredMealsPresenter.filterDelegator(filterType, filter);
    }

    private void setupUiComponents(@NonNull View view) {
        title = view.findViewById(R.id.filterTitleSearch);
        recyclerView = view.findViewById(R.id.filteredMealsRecyclerView);
    }

    @Override
    public void showMealsFilteredByCategory(List<Meal> meals) {
        FilteredMealsAdapter adapter = new FilteredMealsAdapter(getContext(), meals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMealsFilteredByIngredient(List<Meal> meals) {
        FilteredMealsAdapter adapter = new FilteredMealsAdapter(getContext(), meals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showMealsFilteredByCountry(List<Meal> meals) {
        FilteredMealsAdapter adapter = new FilteredMealsAdapter(getContext(), meals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String message) {
        Utility.showToast(requireContext(), message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void navigateToMealDescription(View v, String mealId) {
        FilteredMealsFragmentDirections.ActionFilteredMealsFragmentToMealDescFragment action = FilteredMealsFragmentDirections.actionFilteredMealsFragmentToMealDescFragment(mealId);
        Navigation.findNavController(v).navigate(action);


    }
}