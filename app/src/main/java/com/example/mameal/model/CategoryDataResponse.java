package com.example.mameal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDataResponse {
    @SerializedName("categories")
    private List<Category> categories;

    public List<Category> getCategoriesData() {
        return categories;
    }
}
