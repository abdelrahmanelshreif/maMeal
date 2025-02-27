package com.example.mameal.mealDescription.presenter;

import com.example.mameal.mealDescription.model.Ingredient;
import com.example.mameal.mealDescription.view.MealDescriptionContract;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDescriptionPresenter implements MealDescriptionContract.Presenter {

    MaMealRepository maMealRepository;
    Ingredient ingredient;

    MealDescriptionContract.View mealDescriptionView;
    CompositeDisposable compositeDisposable;

    public MealDescriptionPresenter(MaMealRepository maMealRepository, Ingredient ingredient, MealDescriptionContract.View view) {
        this.maMealRepository = maMealRepository;
        this.ingredient = ingredient;
        this.mealDescriptionView = view;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getIngredients(String mealId) {
        Disposable disposable = maMealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(meal -> {
                    List<String> ingredients = meal.getIngredients();
                    List<String> measures = meal.getMeasures();

                    return Single.zip(
                            Observable.fromIterable(ingredients).toList(),
                            Observable.fromIterable(measures).toList(),
                            (ingredientList, measureList) -> {
                                List<Ingredient> ingredientObjects = new ArrayList<>();
                                for (int i = 0; i < ingredientList.size(); i++) {
                                    ingredientObjects.add(new Ingredient(
                                            ingredientList.get(i),
                                            (i < measureList.size()) ? measureList.get(i) : "",
                                            "https://www.themealdb.com/images/ingredients/" + ingredientList.get(i) + "-Small.png"
                                    ));
                                }
                                return ingredientObjects;
                            }
                    );
                })
                .subscribe(
                        ingredients -> mealDescriptionView.showIngredients(ingredients),
                        error -> mealDescriptionView.showError(error.getMessage())
                );

        compositeDisposable.add(disposable);
    }

    @Override
    public String getFormattedInstructions(Meal meal) {
        return meal.getMealInstructions().replace("\r\n", "\n");
    }

    @Override
    public void addToFavourite(String mealId) {
        Disposable disposable = maMealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        meal -> maMealRepository.insert(meal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> mealDescriptionView.successAddingToFav("Successfully added to favourite")
                                        , throwable -> mealDescriptionView.showError("Error at Adding to Favourite")
                                )
                );
        compositeDisposable.add(disposable);
    }


    @Override
    public void getMealData(String mealId) {
        Disposable disposable = maMealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> mealDescriptionView.setMealMainData(meal));
        compositeDisposable.add(disposable);
    }

    @Override
    public String getFormattedYoutubeUrl(String youtubeUrl) {
        if (youtubeUrl.contains("watch?v=")) {
            return youtubeUrl.replace("watch?v=", "embed/");
        }
        return youtubeUrl;
    }


}
