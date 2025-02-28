package com.example.mameal.filteredMeals.view;

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



public class FilteredMealsAdapter extends RecyclerView.Adapter<FilteredMealsAdapter.ViewHolder> {
    private final Context context;
    private List<Meal> values;

    private OnFilteredMealClickListenerAtFilterFragment onMealClickListener;

    public FilteredMealsAdapter(Context context, List<Meal> values, OnFilteredMealClickListenerAtFilterFragment onMealClickListener) {
        this.context = context;
        this.values = values;
        this.onMealClickListener = onMealClickListener;
    }


    @NonNull
    @Override
    public FilteredMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_meal_item_search_fragment, parent, false);
        return new FilteredMealsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilteredMealsAdapter.ViewHolder holder, int position) {
        Meal meal = values.get(position);
        holder.mealTitle.setText(meal.getMealTitle());
        Glide.with(context)
                .load(meal.getMealThumb())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.mealImg);
        holder.mealImg.setOnClickListener(v -> onMealClickListener.navigateToMealDescription(v, meal.getMealId()));
    }

    public void updateList(List<Meal> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public ImageView mealImg;
        public TextView mealTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            mealImg = itemView.findViewById(R.id.mealItemImgSearchFragment);
            mealTitle = itemView.findViewById(R.id.mealItemTitleSearchFragment);

        }
    }

}
