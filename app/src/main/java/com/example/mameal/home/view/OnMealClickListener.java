package com.example.mameal.home.view;

import android.view.View;

public interface OnMealClickListener {
    void onClick(View view , String mealId);
    void onAddToFav(String mealId);
}
