package com.example.mameal.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mameal.db.Converters;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "meal_table")
@TypeConverters(Converters.class)
public class Meal {

    @NonNull
    @PrimaryKey
    @SerializedName("idMeal")
    private String mealId;

    @SerializedName("strMeal")
    private String mealTitle;

    @SerializedName("strDrinkAlternate")
    private String drinkAlternate;

    @SerializedName("strCategory")
    private String mealCategory;

    @SerializedName("strMealThumb")
    private String mealThumb;

    @SerializedName("strTags")
    private String mealTags;

    @SerializedName("strYoutube")
    private String mealYoutube;

    @SerializedName("strInstructions")
    private String mealInstructions;

    @SerializedName("strArea")
    private String mealArea;

    @SerializedName("strIngredients")
    private List<String> mealIngredients;

    @SerializedName("strMeasures")
    private List<String> mealMeasures;

    @SerializedName("strSource")
    private String mealSource;

    @SerializedName("strImageSource")
    private String mealImgSource;

    @SerializedName("strCreativeCommonsConfirmed")
    private String mealCreativeCommonsConfirmed;

    @SerializedName("dateModified")
    private String dateModified;

    @Ignore
    public Meal() {
    }

    public Meal(String mealId, String mealTitle, String drinkAlternate, String mealCategory, String mealThumb,
                String mealTags, String mealYoutube, String mealInstructions, List<String> mealIngredients,
                List<String> mealMeasures, String mealSource, String mealImgSource,
                String mealCreativeCommonsConfirmed, String dateModified) {
        this.mealId = mealId;
        this.mealTitle = mealTitle;
        this.drinkAlternate = drinkAlternate;
        this.mealCategory = mealCategory;
        this.mealThumb = mealThumb;
        this.mealTags = mealTags;
        this.mealYoutube = mealYoutube;
        this.mealInstructions = mealInstructions;
        this.mealIngredients = mealIngredients;
        this.mealMeasures = mealMeasures;
        this.mealSource = mealSource;
        this.mealImgSource = mealImgSource;
        this.mealCreativeCommonsConfirmed = mealCreativeCommonsConfirmed;
        this.dateModified = dateModified;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealTitle() {
        return mealTitle;
    }

    public void setMealTitle(String mealTitle) {
        this.mealTitle = mealTitle;
    }

    public String getDrinkAlternate() {
        return drinkAlternate;
    }

    public void setDrinkAlternate(String drinkAlternate) {
        this.drinkAlternate = drinkAlternate;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public void setMealThumb(String mealThumb) {
        this.mealThumb = mealThumb;
    }

    public String getMealTags() {
        return mealTags;
    }

    public void setMealTags(String mealTags) {
        this.mealTags = mealTags;
    }

    public String getMealYoutube() {
        return mealYoutube;
    }

    public void setMealYoutube(String mealYoutube) {
        this.mealYoutube = mealYoutube;
    }

    public String getMealInstructions() {
        return mealInstructions;
    }

    public void setMealInstructions(String mealInstructions) {
        this.mealInstructions = mealInstructions;
    }

    public List<String> getMealIngredients() {
        return mealIngredients;
    }

    public void setMealIngredients(List<String> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    public List<String> getMealMeasures() {
        return mealMeasures;
    }

    public void setMealMeasures(List<String> mealMeasures) {
        this.mealMeasures = mealMeasures;
    }

    public String getMealSource() {
        return mealSource;
    }

    public void setMealSource(String mealSource) {
        this.mealSource = mealSource;
    }

    public String getMealImgSource() {
        return mealImgSource;
    }

    public void setMealImgSource(String mealImgSource) {
        this.mealImgSource = mealImgSource;
    }

    public String getMealCreativeCommonsConfirmed() {
        return mealCreativeCommonsConfirmed;
    }

    public void setMealCreativeCommonsConfirmed(String mealCreativeCommonsConfirmed) {
        this.mealCreativeCommonsConfirmed = mealCreativeCommonsConfirmed;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void setMealArea(String mealArea) {
        this.mealArea = mealArea;
    }

    public String getMealArea() {
        return mealArea;
    }
}
