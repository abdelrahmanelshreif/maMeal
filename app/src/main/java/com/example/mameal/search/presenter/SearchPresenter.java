package com.example.mameal.search.presenter;

import android.content.Context;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.Category;
import com.example.mameal.model.CategoryDataResponse;
import com.example.mameal.model.Country;
import com.example.mameal.model.CountryResponse;
import com.example.mameal.model.Ingredient;
import com.example.mameal.model.IngredientResponse;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.model.MealResponse;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.search.view.SearchView;
import com.example.mameal.shared.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {

    private SearchView searchView;
    private MaMealRepository repository;
    CompositeDisposable compositeDisposable;
    List<Meal> mealList = new ArrayList<>();
    List<Category> categoryList;
    List<Ingredient> ingredientList;
    List<Country> countryList;


    public SearchPresenter(SearchView searchView, Context context) {
        this.repository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        this.searchView = searchView;
        compositeDisposable = new CompositeDisposable();
    }

    public void getAllMeals() {
        searchView.showLoading();
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .subscribe(
                        meals -> {
                            searchView.showAllMeals(meals);
                            searchView.hideLoading();
                            mealList = meals;
                        },
                        throwable -> searchView.showError(throwable.getMessage())

                );
        compositeDisposable.add(disposable);
    }

    public void getIngredientsData() {
        searchView.showLoading();
        Disposable disposable = repository.getIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(IngredientResponse::getIngredientList)
                .subscribe(
                        ingredients -> {
                            this.ingredientList = ingredients;
                            searchView.showIngredients(ingredients);
                            searchView.hideLoading();
                        },
                        throwable -> searchView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void getAreasData() {
        searchView.showLoading();
        Disposable disposable = repository.getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(CountryResponse::getCountryList)
                .map(countryList -> {
                    for (Country country : countryList) {
                        country.setCountryThumbnail(Utility.buildFlagUrl(country.getStrArea()));
                    }
                    return countryList;
                })
                .subscribe(
                        countryList -> {
                            this.countryList = countryList;
                            searchView.showAreas(countryList);
                            searchView.hideLoading();
                        },
                        throwable -> searchView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void getCategoriesData() {
        searchView.showLoading();
        Disposable disposable = repository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(CategoryDataResponse::getCategoriesData)
                .subscribe(
                        categories -> {
                            this.categoryList = categories;
                            searchView.showCategories(categories);
                            searchView.hideLoading();
                        },
                        throwable -> searchView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void getMealsFilteredByCategory(String category) {
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .filter(meal -> meal.getMealCategory().equals(category)
                ).toList()
                .subscribe(
                        meals -> searchView.showMealsFilteredByCategory(meals),
                        throwable -> searchView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void getMealsFilteredByIngredients(String ingredient) {
        // getting all meals
        // then filter it if the ingredients any match with strIngredient
        // show meals that matches the required ingredient
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .filter(meal -> {

                    for (String tempIngredient : meal.getIngredients()) {
                        if (tempIngredient.equals(ingredient))
                            return true;
                    }
                    return false;
                }).toList()
                .subscribe(
                        meals -> searchView.showMealsFilteredByIngredient(meals),
                        throwable -> searchView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void getMealsFilteredByCountry(String country) {
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .filter(meal -> meal.getMealArea().equals(country)
                ).toList()
                .subscribe(
                        meals -> searchView.showMealsFilteredByCountry(meals),
                        throwable -> searchView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void observeSearch(String query, String selectedFilter) {
        searchView.showLoading();
        if (query.isEmpty()) {
            searchView.showAllMeals(mealList);
            return;
        }

        switch (selectedFilter) {
            case SearchView.CATEGORY:
                List<Category> filteredCategories = categoryList.stream()
                        .filter(category -> category.getStrCategory().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                searchView.showCategories(filteredCategories);
                break;

            case SearchView.COUNTRY:
                List<Country> filteredCountries = countryList.stream()
                        .filter(country -> country.getStrArea().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                searchView.showAreas(filteredCountries);
                break;

            case SearchView.INGREDIENT:
                List<Ingredient> filteredIngredients = ingredientList.stream()
                        .filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                searchView.showIngredients(filteredIngredients);
                break;

            default:
                List<Meal> filteredMeals = mealList.stream()
                        .filter(meal -> meal.getMealTitle().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                searchView.showAllMeals(filteredMeals);
                break;
        }
        searchView.hideLoading();
    }
}




