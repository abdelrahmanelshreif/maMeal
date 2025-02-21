package com.example.mameal.network;

import android.database.Observable;

import com.example.mameal.model.Meal;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessResult(Observable<List<Meal>> meals);

    public void onFailureResult(String errMsg);
}
