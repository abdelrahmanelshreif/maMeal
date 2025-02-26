package com.example.mameal.network;

import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.model.Category;
import com.example.mameal.model.CategoryResponse;
import com.example.mameal.model.Meal;
import com.example.mameal.model.MealResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MaMealRemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private static MaMealRemoteDataSource instance;
    private final MaMealService maMealService;

    private MaMealRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        maMealService = retrofit.create(MaMealService.class);
    }

    public static synchronized MaMealRemoteDataSource getInstance() {
        if (instance == null) {
            instance = new MaMealRemoteDataSource();
        }
        return instance;
    }

    public Flowable<MealResponse> getAllMeals() {
        return maMealService.getAllMeals();
    }

    public Single<MealResponse> getDailyMeal() {
        return maMealService.getRandomMeal();
    }

    public Single<Meal> getMealbyId(String id) {
        return maMealService.getMealById(id);
    }

    public Single<CategoryResponse> getCategoriesNamesOnly() {
        return maMealService.getCategoriesNames();
    }

    public Single<MealResponse> getMealsByCategory(String category) {
        return maMealService.getMealsByCategory(category);
    }

    public Observable<List<CategoryWithMeals>> getCategoryWithMeals() {
        return maMealService.getCategoriesNames()
                .map(CategoryResponse::getCategories)
                .flattenAsObservable(categories -> categories)
                .flatMap(category -> {
                    Single<MealResponse> mealsSingle = maMealService.getMealsByCategory(category.getStrCategory());
                    return Single.zip(
                            Single.just(category.getStrCategory()),
                            mealsSingle.map(MealResponse::getMeals),
                            (categoryName, meals) -> new CategoryWithMeals(categoryName, meals)
                    ).toObservable();
                })
                .toList()
                .toObservable();
    }





}
