package com.example.mameal.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mameal.R;
import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.home.presenter.HomePresenter;
import com.example.mameal.model.Meal;
import com.example.mameal.utils.Utility;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private LinearLayout linearLayoutSections;
    private TextView dailyMealTitle, dailyMealArea;
    private ImageView dailyMealImage;
    private HomePresenter homePresenter;


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
        homePresenter.loadMeals();
    }

    private void setupUiComponents(View view) {
        dailyMealTitle = view.findViewById(R.id.dailyMealTitle);
        dailyMealArea = view.findViewById(R.id.daiylMealArea);
        dailyMealImage = view.findViewById(R.id.dailyMealImage);
        linearLayoutSections = view.findViewById(R.id.sectionsLinearLayout);
        if (linearLayoutSections == null) {
            Log.e("HomeFragment", "LinearLayout sectionsLinearLayout is null");
        }
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
        dailyMealTitle.setText(meal.getMealTitle());
        dailyMealArea.setText(meal.getMealArea());
        Glide.with(getContext())
                .load(meal.getMealThumb())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(dailyMealImage);
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
        MealByCategoryAdapter adapter = new MealByCategoryAdapter(getContext(), meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String err) {
        Utility.showToast(getContext(), err);
    }
}
