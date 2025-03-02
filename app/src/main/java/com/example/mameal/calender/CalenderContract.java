package com.example.mameal.calender;

import com.example.mameal.model.Event;
import com.example.mameal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public interface CalenderContract {
    interface View {

        void showError(String message);

        void showMealOfEvent(@NonNull List<Meal> meals);

        void showSuccessDeletion(String message);
    }

    interface Presenter {

        void getMealsForDate(String selectedDate);


        void removeEventFromPlan(String mealId, String mealDate);
    }
}
