package com.example.mameal.home.model;

import com.example.mameal.model.Meal;

import java.util.List;

public class CategoryWithMeals {
    private String categoryTitle;
    private List<Meal> meals;

    public CategoryWithMeals(String categoryTitle, List<Meal> meals) {
        this.categoryTitle = categoryTitle;
        this.meals = meals;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
