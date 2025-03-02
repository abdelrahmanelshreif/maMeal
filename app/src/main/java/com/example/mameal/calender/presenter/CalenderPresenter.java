package com.example.mameal.calender.presenter;

import android.content.Context;

import com.example.mameal.calender.CalenderContract;
import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenter implements CalenderContract.Presenter {

    MaMealRepository maMealRepository;

    CalenderContract.View view;
    CompositeDisposable compositeDisposable;

    public CalenderPresenter(CalenderContract.View view, Context context) {
        this.maMealRepository = MaMealRepository.getInstance(
                MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        this.view = view;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void getMealsForDate(String selectedDate) {
        Disposable disposable = maMealRepository.getPlannedMealsAt(selectedDate)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(Observable::fromIterable)
                .flatMapSingle(id -> maMealRepository.getMealById(id)
                        .subscribeOn(Schedulers.io())
                )
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> view.showMealOfEvent(meals),
                        throwable -> view.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    @Override
    public void removeEventFromPlan(String mealId, String mealDate) {
        Disposable disposable = maMealRepository.deleteEvent(mealId, mealDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    view.showSuccessDeletion("Meal Deleted Successfully from Plan");
                    getMealsForDate(mealDate);
                });

        compositeDisposable.add(disposable);

    }


}
