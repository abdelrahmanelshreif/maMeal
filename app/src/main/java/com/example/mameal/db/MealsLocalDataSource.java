package com.example.mameal.db;

import android.content.Context;

import com.example.mameal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealsLocalDataSource {


    private Context context;
    private MealDAO mealDAO;


    public MealsLocalDataSource(Context _context) {
        this.context = _context;
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        mealDAO = db.getMealDAO();
    }

    public Observable<List<Meal>> getStoredData() {
        return mealDAO.getAllMeals();
    }

    public Completable insert(Meal meal) {
        return mealDAO.insertMeal(meal);
    }

    public Completable delete(Meal meal) {
        return mealDAO.deleteMeal(meal);
    }
}

