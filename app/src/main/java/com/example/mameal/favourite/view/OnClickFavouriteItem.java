package com.example.mameal.favourite.view;

import com.example.mameal.model.Meal;

public interface OnClickFavouriteItem {
    void onClickFavouriteMeal(String mealId);
    void removeFromFavourite(Meal meal);


}
