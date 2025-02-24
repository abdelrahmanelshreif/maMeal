package com.example.mameal.network;

import androidx.annotation.NonNull;

import com.example.mameal.model.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MaMealRemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    NetworkCallback callback;
    private final MaMealService maMealService;

    public MaMealRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        maMealService = retrofit.create(MaMealService.class);
    }

    public void getAllMeals(NetworkCallback callback) {
        Call<MealResponse> call = maMealService.getAllMeals();
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.body() != null) {              
                    callback.onSuccessResult(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, @NonNull Throwable throwable) {
                callback.onFailureResult(throwable.getMessage());
            }
        });
    }


}
