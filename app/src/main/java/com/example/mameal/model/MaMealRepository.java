package com.example.mameal.model;

import com.example.mameal.network.MaMealRemoteDataSource;

import io.reactivex.rxjava3.core.Flowable;

public class MaMealRepository {

    MaMealRemoteDataSource maMealRemoteDataSource;

    private static MaMealRepository repo = null;

    private MaMealRepository(MaMealRemoteDataSource maMealRemoteDataSource) {
        this.maMealRemoteDataSource = maMealRemoteDataSource;
    }

    public static MaMealRepository getInstance(MaMealRemoteDataSource maMealRemoteDataSource) {
        if (repo == null) {
            repo = new MaMealRepository(maMealRemoteDataSource);
        }
        return repo;
    }

    public Flowable<MealResponse> getAllMealsData() {
        maMealRemoteDataSource.getAllMeals();
    }

}
