package com.example.mameal.db;

import android.content.Context;
import android.util.Log;

import com.example.mameal.model.Event;
import com.example.mameal.model.Meal;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSource {


    private Context context;
    private MealDAO mealDAO;
    private EventDAO eventDAO;

    public MealsLocalDataSource(Context _context) {
        this.context = _context;
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        mealDAO = db.getMealDAO();
        eventDAO = db.getEventDAO();
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

    public Completable insertEvent(Event event) {
        return eventDAO.insertEvent(event);
    }

    public Completable deleteEvent(String mealId , String mealDate) {
        return eventDAO.deleteEvent(mealId,mealDate);
    }

    public Single<List<String>> getPlannedMealsAtDay(String date) {
        return eventDAO.getMealsOfDay(date);
    }

    public Single<List<Event>> getAllEvents() {
        return eventDAO.getAllEvents();
    }


}

