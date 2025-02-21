package com.example.mameal.uiModel;

import java.util.List;

// DTO for transferring the meal form model layer to the presenter layer to the view layer
public class MealUiModel {
    private String mealId;
    private String mealTitle;
    private String mealCategory;
    private String mealTags;
    private List<String> mealIngredients;
    private List<String> mealMeasures;
    private String mealImgSource;

    public MealUiModel(){}
    public MealUiModel(String mealId, String mealTitle, String mealCategory, String mealTags, List<String> mealIngredients, List<String> mealMeasures, String mealImgSource) {
        this.mealId = mealId;
        this.mealTitle = mealTitle;
        this.mealCategory = mealCategory;
        this.mealTags = mealTags;
        this.mealIngredients = mealIngredients;
        this.mealMeasures = mealMeasures;
        this.mealImgSource = mealImgSource;
    }

    public String getMealId() {
        return mealId;
    }

    public String getMealTitle() {
        return mealTitle;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public String getMealTags() {
        return mealTags;
    }

    public List<String> getMealIngredients() {
        return mealIngredients;
    }

    public List<String> getMealMeasures() {
        return mealMeasures;
    }

    public String getMealImgSource() {
        return mealImgSource;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public void setMealTitle(String mealTitle) {
        this.mealTitle = mealTitle;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public void setMealTags(String mealTags) {
        this.mealTags = mealTags;
    }

    public void setMealIngredients(List<String> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    public void setMealMeasures(List<String> mealMeasures) {
        this.mealMeasures = mealMeasures;
    }

    public void setMealImgSource(String mealImgSource) {
        this.mealImgSource = mealImgSource;
    }
}
