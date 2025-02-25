package com.example.mameal.network;

import com.example.mameal.model.Meal;
import com.example.mameal.model.MealResponse;

import io.reactivex.rxjava3.core.Flowable;
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

    public Single<Meal> getDailyMeal() {
        return maMealService.getRandomMeal();
    }

    public Single<Meal> getMealbyId(String id) {
        return maMealService.getMealById(id);
    }

}
