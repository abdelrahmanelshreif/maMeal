package com.example.mameal.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mameal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

interface MealDAO {

    @Query("SELECT * FROM MEAL_TABLE")
    Observable<List<Meal>> getAllMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

}
