package com.example.mameal.model;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.network.MaMealRemoteDataSource;

import io.reactivex.rxjava3.core.Flowable;

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

}
