package com.example.mameal.favourite.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mameal.R;
import com.example.mameal.model.Meal;

import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.ViewHolder> {


    private final Context context;
    private List<Meal> values;


    public FavouriteMealsAdapter(Context context, List<Meal> values) {
        this.context = context;
        this.values = values;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favourite_meals_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = values.get(position);
        holder.mealTitle.setText(meal.getMealTitle());
        holder.mealCategory.setText(meal.getMealCategory());
        Glide.with(context)
                .load(meal.getMealImgSource())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.mealImage);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public TextView mealTitle, mealCategory;
        public ImageView mealImage, removeFromFav;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            mealTitle = itemView.findViewById(R.id.mealTitleCardTextViewCard);
            mealCategory = itemView.findViewById(R.id.mealCategoryCardTextView);
            mealImage = itemView.findViewById(R.id.mealCardImgView);
            removeFromFav = itemView.findViewById(R.id.mealAddToFavBtn);

        }
    }
}
