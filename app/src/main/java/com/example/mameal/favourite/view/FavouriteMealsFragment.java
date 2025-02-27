package com.example.mameal.favourite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.favourite.presenter.FavouriteMealsPresenter;
import com.example.mameal.home.view.MealByCategoryAdapter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.shared.Utility;

import java.util.List;


public class FavouriteMealsFragment extends Fragment implements FavouriteMealsView,OnClickFavouriteItem{

    private RecyclerView recyclerView;
    MaMealRepository maMealRepository;
    FavouriteMealsPresenter favouriteMealsPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.favouriteMealsPresenter = new FavouriteMealsPresenter(this,getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponents(view);
        favouriteMealsPresenter.getFavouriteMeals();


    }

    private void setupUiComponents(@NonNull View view) {
        recyclerView = view.findViewById(R.id.favouriteMealsRecyclerView);
    }

    @Override
    public void showFavouriteMeals(List<Meal> meals) {
        FavouriteMealsAdapter adapter = new FavouriteMealsAdapter(getContext(), meals,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showErrorRetrievingData(String error) {
        Utility.showToast(getContext(), error);
    }

    @Override
    public void onClickFavouriteMeal(String mealId) {


    }

    @Override
    public void removeFromFavourite(Meal meal) {
        favouriteMealsPresenter.removeMealFromFavourite(meal);

    }

    @Override
    public void successfulDeletion() {
        Utility.showToast(getContext(),"Successfully Removed");
    }
}