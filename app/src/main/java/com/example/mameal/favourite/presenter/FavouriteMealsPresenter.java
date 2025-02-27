package com.example.mameal.favourite.presenter;

import android.content.Context;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.favourite.view.FavouriteMealsView;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.network.MaMealRemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouriteMealsPresenter {

    FavouriteMealsView favouriteMealsView;
    MaMealRepository repository;

    CompositeDisposable compositeDisposable;

    public FavouriteMealsPresenter(FavouriteMealsView favouriteMealsView, Context context) {
        this.favouriteMealsView = favouriteMealsView;
        this.repository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        compositeDisposable = new CompositeDisposable();
    }


    public void getFavouriteMeals() {
        Disposable disposable = repository.getFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> favouriteMealsView.showFavouriteMeals(meals));
        compositeDisposable.add(disposable);
    }

    public void removeMealFromFavourite(Meal meal) {
        repository.delete(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> favouriteMealsView.successfulDeletion()
                );
    }
}
