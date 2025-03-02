package com.example.mameal.model;

import android.util.Log;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MaMealRepository {

    MaMealRemoteDataSource maMealRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;
    private FirebaseFirestore firebaseFirestore;
    private static MaMealRepository repo = null;
    CollectionReference mealCollection;
    CollectionReference eventsCollection;

    private MaMealRepository(MaMealRemoteDataSource remoteSrc, MealsLocalDataSource localSrc) {
        this.maMealRemoteDataSource = remoteSrc;
        this.mealsLocalDataSource = localSrc;
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.mealCollection = firebaseFirestore.collection("favourite_meals_collection");
        this.eventsCollection = firebaseFirestore.collection("meal_plan");
    }

    public static MaMealRepository getInstance(MaMealRemoteDataSource maMealRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        if (repo == null) {
            repo = new MaMealRepository(maMealRemoteDataSource, mealsLocalDataSource);
        }
        return repo;
    }

    public Flowable<MealResponse> getAllMealsData() {
        return maMealRemoteDataSource.getAllMeals();
    }

    public Single<CategoryResponse> getCategoriesNamesOnly() {
        return maMealRemoteDataSource.getCategoriesNamesOnly();
    }

    public Single<CategoryDataResponse> getCategories() {
        return maMealRemoteDataSource.getCategoriesWithDetails();
    }

    public Single<CountryResponse> getAreas() {
        return maMealRemoteDataSource.getAreas();
    }

    public Flowable<IngredientResponse> getIngredients() {
        return maMealRemoteDataSource.getIngredients();
    }


    public Single<MealResponse> getHomeScreenMealsDataByCategory(String category) {
        return maMealRemoteDataSource.getMealsByCategory(category);
    }

    public Observable<List<CategoryWithMeals>> getCategoryWithMeals() {
        return maMealRemoteDataSource.getCategoryWithMeals();
    }

    public Single<MealResponse> getDailyMeal() {
        return maMealRemoteDataSource.getDailyMeal();
    }

    public Single<Meal> getMealById(String id) {
        return maMealRemoteDataSource.getMealbyId(id);
    }

    public Completable insert(Meal meal) {
        return mealsLocalDataSource.insert(meal);
    }

    public Completable delete(Meal meal) {
        return mealsLocalDataSource.delete(meal);
    }

    public Observable<List<Meal>> getFavouriteMeals() {
        return mealsLocalDataSource.getStoredData();
    }

    public Completable insertEvent(Event event) {
        return mealsLocalDataSource.insertEvent(event);
    }

    public Completable deleteEvent(String mealId, String mealDate) {
        return mealsLocalDataSource.deleteEvent(mealId, mealDate);
    }

    public Single<List<String>> getPlannedMealsAt(String date) {
        return mealsLocalDataSource.getPlannedMealsAtDay(date);
    }

    public void uploadMealsToFirebase() {
        Disposable disposable = mealsLocalDataSource.getStoredData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        meals -> {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                for (Meal meal : meals) {
                                    mealCollection.document(meal.getMealId()).set(meal);
                                }
                            });
                        }, throwable -> {
                            Log.e("FirebaseSync", "Error uploading meals", throwable);

                        }
                );
    }

    public void uploadEventsToFirebase() {
        Disposable disposable = mealsLocalDataSource.getAllEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(events -> {
                    Log.d("DatabaseDebug", "Fetched events: " + events.size());
                    for (Event event : events) {
                        Log.d("DatabaseDebug", "Event ID: " + event.getId() + ", Name: " + event.getMeal());
                        eventsCollection.document(String.valueOf(event.getId())).set(event);
                    }
                }, throwable -> {
                    Log.e("DatabaseDebug", "Error fetching events", throwable);
                });
    }
}