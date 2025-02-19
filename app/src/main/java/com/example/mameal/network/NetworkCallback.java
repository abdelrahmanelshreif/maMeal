package com.example.mameal.network;

import com.example.mameal.model.Meal;

import java.util.List;

public interface NetworkCallback {
    public void onSuccessResult(List<Meal> meals);

    public void onFailureResult(String errMsg);
}
