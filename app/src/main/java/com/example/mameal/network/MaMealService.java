package com.example.mameal.network;

import com.example.mameal.model.Category;
import com.example.mameal.model.CategoryDataResponse;
import com.example.mameal.model.CategoryResponse;
import com.example.mameal.model.CountryResponse;
import com.example.mameal.model.IngredientResponse;
import com.example.mameal.model.Meal;
import com.example.mameal.model.MealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MaMealService {
    @GET("search.php?s=")
    Flowable<MealResponse> getAllMeals();

    @GET("categories.php")
    Single<CategoryDataResponse> getAllCategoriesData();

    @GET("list.php?c")
    Single<CategoryResponse> getCategoriesNames();
    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("filter.php")
    Single<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("list.php?a=list")
    Single<CountryResponse> getAreas();

    @GET("list.php?i=list")
    Flowable<IngredientResponse> getIngredientsData();

    @GET("lookup.php")
    Single<MealResponse> getMealById(@Query("i") String mealId);

    @GET("search.php")
    Single<Meal> getMealByName(@Query("s") String mealName);




}
