package com.example.mameal.calender.presenter;

import android.content.Context;

import com.example.mameal.calender.CalenderContract;
import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;

public class CalenderPresenter implements CalenderContract.Presenter {

    MaMealRepository maMealRepository;

    CalenderContract.View view;

    public CalenderPresenter(CalenderContract.View view, Context context) {
        this.maMealRepository = MaMealRepository.getInstance(
                MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        this.view = view;
    }

    @Override
    public void onDateSelected(String date) {

    }
}
