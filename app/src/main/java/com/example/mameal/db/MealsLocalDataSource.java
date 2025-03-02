package com.example.mameal.db;

import android.content.Context;
import android.util.Log;

import com.example.mameal.model.Event;
import com.example.mameal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsLocalDataSource {


    private Context context;
    private MealDAO mealDAO;
    private EventDAO eventDAO;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

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

    public Completable deleteEvent(String mealId, String mealDate) {
        return eventDAO.deleteEvent(mealId, mealDate);
    }

    public Single<List<String>> getPlannedMealsAtDay(String date) {
        return eventDAO.getMealsOfDay(date);
    }

    public Single<List<Event>> getAllEvents() {
        return eventDAO.getAllEvents();
    }


    public void clearAllTables() {
        compositeDisposable.add(
                mealDAO.clearMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> Log.i("ROOM", "clearMealsTable: Success"),
                                throwable -> Log.e("ROOM", "Error clearing meals", throwable))
        );

        compositeDisposable.add(
                eventDAO.clearEvents()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> Log.i("ROOM", "clearEventsTable: Success"),
                                throwable -> Log.e("ROOM", "Error clearing events", throwable))
        );
    }

    public Completable insertMeals(List<Meal> meals) {
        return mealDAO.insertMeals(meals);
    }

    public Completable insertEvents(List<Event> events) {
        return eventDAO.insertEvents(events);
    }


}

