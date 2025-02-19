package com.example.mameal.network;

import com.example.mameal.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

interface MaMealService {
    @GET("search.php?s=")
    Call<MealResponse> getAllMeals();

}
