package com.example.mameal.model;

import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.network.NetworkCallback;

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

    public void getAllMealsData(NetworkCallback callback) {
        maMealRemoteDataSource.getAllMeals(callback);
    }

}
