package com.example.mameal.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.authentication.view.OnMealClickListener;
import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.home.presenter.HomePresenter;
import com.example.mameal.model.Meal;
import com.example.mameal.utils.Utility;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView, OnMealClickListener {

    private LinearLayout linearLayoutSections;
    private TextView dailyMealTitle, dailyMealArea;
    private ImageView dailyMealImage;
    private HomePresenter homePresenter;
    private CardView dailyMealCard;
    String dailyMealId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponents(view);
        homePresenter.getDailyMeal();
        homePresenter.loadMealsSections();
        dailyMealCard.setOnClickListener(v -> homePresenter.handleMealsNavigation(view, dailyMealId));

    }

    private void setupUiComponents(View view) {
        dailyMealTitle = view.findViewById(R.id.dailyMealTitle);
        dailyMealArea = view.findViewById(R.id.daiylMealArea);
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        dailyMealCard = view.findViewById(R.id.dailyMealCard);
        linearLayoutSections = view.findViewById(R.id.sectionsLinearLayout);
    }

    @Override
    public void setAdaptersOfCategories(List<CategoryWithMeals> categories) {
        linearLayoutSections.removeAllViews();
        for (CategoryWithMeals category : categories) {
            addCategorySection(category);
        }
    }

    @Override
    public void setDailyMealData(Meal meal) {
        dailyMealId = meal.getMealId();
        dailyMealTitle.setText(meal.getMealTitle());
        dailyMealArea.setText(meal.getMealArea());
        Utility.loadImage(this, meal.getMealThumb(), dailyMealImage);
    }

    private void addCategorySection(CategoryWithMeals category) {
        View sectionView = LayoutInflater.from(getContext()).inflate(R.layout.home_section, linearLayoutSections, false);
        TextView sectionTitle = sectionView.findViewById(R.id.sectionTitle);
        RecyclerView recyclerView = sectionView.findViewById(R.id.homeRecyclerView);
        sectionTitle.setText(category.getCategoryTitle());
        setupRecyclerView(recyclerView, category.getMeals());
        linearLayoutSections.addView(sectionView);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Meal> meals) {
        MealByCategoryAdapter adapter = new MealByCategoryAdapter(getContext(), meals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String err) {
        Utility.showToast(getContext(), err);
    }

    @Override
    public void navigateToMealDescription(View view, String mealId) {
        com.example.mameal.home.view.HomeFragmentDirections.ActionHomeFragmentToMealDescFragment action = HomeFragmentDirections.actionHomeFragmentToMealDescFragment(mealId);
        Navigation.findNavController(view).navigate(action);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.detachView();
    }

    @Override
    public void onClick(View view, String mealId) {
        homePresenter.handleMealsNavigation(view, mealId);
    }
}
