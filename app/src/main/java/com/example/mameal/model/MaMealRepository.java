package com.example.mameal.model;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.model.CategoryWithMeals;
import com.example.mameal.network.MaMealRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MaMealRepository {

    MaMealRemoteDataSource maMealRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;


    private static MaMealRepository repo = null;

    private MaMealRepository(MaMealRemoteDataSource remoteSrc, MealsLocalDataSource localSrc) {
        this.maMealRemoteDataSource = remoteSrc;
        this.mealsLocalDataSource = localSrc;
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

    public Single<MealResponse> getHomeScreenMealsDataByCategory(String category) {
        return maMealRemoteDataSource.getMealsByCategory(category);
    }

    public Observable<List<CategoryWithMeals>> getCategoryWithMeals(){
        return maMealRemoteDataSource.getCategoryWithMeals();
    }

    public Single<MealResponse> getDailyMeal()
    {
        return maMealRemoteDataSource.getDailyMeal();
    }


}
