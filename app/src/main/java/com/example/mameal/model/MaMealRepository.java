package com.example.mameal.model;

import android.util.Log;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MaMealRepository {
    private static final String TAG = "MaMealRepository";

    private MaMealRemoteDataSource maMealRemoteDataSource;
    private MealsLocalDataSource mealsLocalDataSource;
    private FirebaseFirestore firebaseFirestore;
    private static MaMealRepository repo = null;
    private CollectionReference usersCollection;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MaMealRepository(MaMealRemoteDataSource remoteSrc, MealsLocalDataSource localSrc) {
        this.maMealRemoteDataSource = remoteSrc;
        this.mealsLocalDataSource = localSrc;
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.usersCollection = firebaseFirestore.collection("users");
    }

    public static MaMealRepository getInstance(MaMealRemoteDataSource maMealRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        if (repo == null) {
            repo = new MaMealRepository(maMealRemoteDataSource, mealsLocalDataSource);
        }
        return repo;
    }

    // API remote data source methods
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
    public Single<MealResponse> getMealsFilteredByIngredient(String ingredient){
        return maMealRemoteDataSource.getMealsFilteredByIngredient(ingredient);
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

    public void userOutClear() {
        mealsLocalDataSource.clearAllTables();
    }

    public void syncFirestoreDataWithRoom(String userId) {
        if (userId == null || userId.isEmpty()) {
            Log.e(TAG, "Invalid user ID for syncing");
            return;
        }

        Log.d(TAG, "Starting sync for user: " + userId);
        checkFirestoreData(userId);
    }

    private void checkFirestoreData(String userId) {
        usersCollection.document(userId).collection("meals")
                .limit(1)
                .get()
                .addOnSuccessListener(mealSnapshots -> {
                    boolean hasMealsInFirestore = !mealSnapshots.isEmpty();

                    usersCollection.document(userId).collection("events")
                            .limit(1)
                            .get()
                            .addOnSuccessListener(eventSnapshots -> {
                                boolean hasEventsInFirestore = !eventSnapshots.isEmpty();

                                if (hasMealsInFirestore || hasEventsInFirestore) {
                                    Log.d(TAG, "Found data in Firestore, downloading to Room");
                                    if (hasMealsInFirestore) syncMeals(userId);
                                    if (hasEventsInFirestore) syncEvents(userId);
                                } else {
                                    Log.d(TAG, "No data found in Firestore, checking local data");
                                    checkRoomDataAndUpload();
                                }
                            })
                            .addOnFailureListener(e ->
                                    Log.e(TAG, "Error checking events in Firestore", e)
                            );
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error checking meals in Firestore", e)
                );
    }

    private void checkRoomDataAndUpload() {
        Disposable disposable = mealsLocalDataSource.getStoredData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                            if (meals != null && !meals.isEmpty()) {
                                Log.d(TAG, "Found local meals, uploading to Firestore");
                                uploadMealsToFirebase();
                            } else {
                                Log.d(TAG, "No local meals found");
                            }

                            checkAndUploadEvents();
                        }, throwable ->
                                Log.e(TAG, "Error checking local meals", throwable)
                );

        compositeDisposable.add(disposable);
    }

    private void checkAndUploadEvents() {
        Disposable disposable = mealsLocalDataSource.getAllEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {
                            if (events != null && !events.isEmpty()) {
                                Log.d(TAG, "Found local events, uploading to Firestore");
                                uploadEventsToFirebase();
                            } else {
                                Log.d(TAG, "No local events found");
                            }
                        }, throwable ->
                                Log.e(TAG, "Error checking local events", throwable)
                );

        compositeDisposable.add(disposable);
    }

    private void syncMeals(String userId) {
        usersCollection.document(userId).collection("meals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Meal> meals = new ArrayList<>();

                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Meal meal = document.toObject(Meal.class);
                        if (meal != null) {
                            meals.add(meal);
                            Log.d(TAG, "Retrieved meal: " + meal.getMealTitle());
                        }
                    }

                    if (!meals.isEmpty()) {
                        Disposable disposable = mealsLocalDataSource.insertMeals(meals)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> Log.d(TAG, "Successfully synced " + meals.size() + " meals to Room"),
                                        throwable -> Log.e(TAG, "Error syncing meals to Room", throwable)
                                );
                        compositeDisposable.add(disposable);
                    } else {
                        Log.d(TAG, "No meals found to sync");
                    }
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error fetching meals from Firestore", e)
                );
    }

    private void syncEvents(String userId) {
        usersCollection.document(userId).collection("events")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Event> events = new ArrayList<>();

                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Event event = document.toObject(Event.class);
                        if (event != null) {
                            events.add(event);
                            Log.d(TAG, "Retrieved event: " + event.getId() + " for date: " + event.getEventDate());
                        }
                    }

                    if (!events.isEmpty()) {
                        Disposable disposable = mealsLocalDataSource.insertEvents(events)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> Log.d(TAG, "Successfully synced " + events.size() + " events to Room"),
                                        throwable -> Log.e(TAG, "Error syncing events to Room", throwable)
                                );
                        compositeDisposable.add(disposable);
                    } else {
                        Log.d(TAG, "No events found to sync");
                    }
                })
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error fetching events from Firestore", e)
                );
    }

    public void uploadMealsToFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.e(TAG, "User not authenticated! Cannot upload meals.");
            return;
        }

        String userId = user.getUid();
        Disposable disposable = mealsLocalDataSource.getStoredData()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(meals -> {
                    CollectionReference mealCollection = usersCollection.document(userId).collection("meals");
                    Executors.newSingleThreadExecutor().execute(() -> {
                        for (Meal meal : meals) {
                            mealCollection.document(meal.getMealId()).set(meal);
                        }
                        Log.d(TAG, "Successfully uploaded " + meals.size() + " meals to Firestore");
                    });
                }, throwable -> {
                    Log.e(TAG, "Error uploading meals", throwable);
                });

        compositeDisposable.add(disposable);
    }

    public void uploadEventsToFirebase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Log.e(TAG, "User not authenticated! Cannot upload events.");
            return;
        }

        String userId = user.getUid();
        Disposable disposable = mealsLocalDataSource.getAllEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(events -> {
                    Log.d(TAG, "Fetched events: " + events.size());
                    CollectionReference eventsCollection = usersCollection.document(userId).collection("events");

                    for (Event event : events) {
                        Log.d(TAG, "Event ID: " + event.getId() + ", Name: " + event.getMeal());
                        eventsCollection.document(String.valueOf(event.getId())).set(event);
                    }
                    Log.d(TAG, "Successfully uploaded " + events.size() + " events to Firestore");
                }, throwable -> {
                    Log.e(TAG, "Error fetching events", throwable);
                });

        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}