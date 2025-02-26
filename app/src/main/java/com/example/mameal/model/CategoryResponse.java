    package com.example.mameal.model;

    import com.google.gson.annotations.SerializedName;

    import java.util.List;

    public class CategoryResponse {
        @SerializedName("meals")
        private List<Category> categories;

        public List<Category> getCategories() {
            return categories;
        }
    }