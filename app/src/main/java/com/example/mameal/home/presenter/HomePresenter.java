package com.example.mameal.home.presenter;

import com.example.mameal.home.view.HomeView;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.uiModel.MealUiModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePresenter {

    MaMealRepository maMealRepository;
    MealUiModel uiModel;
    HomeView homeView;


    public HomePresenter(HomeView homeView, MaMealRepository maMealRepository,MealUiModel uiModel) {
        this.homeView = homeView;
        this.maMealRepository = maMealRepository;
        this.uiModel = uiModel;
    }

    public void loadMeals() {
        homeView.showMeals(DummyMealData.getDummyMeals());
    }


    public static class DummyMealData {

        public static List<MealUiModel> getDummyMeals() {
            List<MealUiModel> meals = new ArrayList<>();

            meals.add(new MealUiModel(
                    "1",
                    "Spaghetti Carbonara",
                    "Pasta",
                    "Italian, Classic",
                    Arrays.asList("Spaghetti", "Eggs", "Parmesan Cheese", "Bacon"),
                    Arrays.asList("200g", "2", "50g", "100g"),
                    "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg"
            ));

            meals.add(new MealUiModel(
                    "2",
                    "Chicken Biryani",
                    "Rice Dish",
                    "Indian, Spicy",
                    Arrays.asList("Chicken", "Basmati Rice", "Yogurt", "Spices"),
                    Arrays.asList("300g", "250g", "100g", "Varied"),
                    "https://www.themealdb.com/images/media/meals/xrttsx1487339558.jpg"
            ));

            meals.add(new MealUiModel(
                    "3",
                    "Cheeseburger",
                    "Fast Food",
                    "American, Fast",
                    Arrays.asList("Beef Patty", "Cheese", "Burger Bun", "Lettuce"),
                    Arrays.asList("150g", "1 slice", "1", "30g"),
                    "https://www.themealdb.com/images/media/meals/urzj1d1587670726.jpg"
            ));

            meals.add(new MealUiModel(
                    "4",
                    "Sushi",
                    "Japanese",
                    "Seafood, Raw",
                    Arrays.asList("Rice", "Nori", "Salmon", "Avocado"),
                    Arrays.asList("200g", "1 sheet", "100g", "50g"),
                    "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"
            ));

            return meals;
        }
    }

}
