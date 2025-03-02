package com.example.mameal.calender.view;

import com.example.mameal.model.Event;
import com.example.mameal.model.Meal;

public interface OnPlannedMealClickListener {
    void removeFromCalender(Meal meal);
    void navigateToMeal(String mealId);
}
