package com.example.mameal.search.view;

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

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {
    private final Context context;
    private List<Meal> values;

    private OnClickMealListener onClickMealListener;

    public MealsAdapter(Context context, List<Meal> values, OnClickMealListener onClickMealListener) {
        this.context = context;
        this.values = values;
        this.onClickMealListener = onClickMealListener;
    }


    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_meal_item_search_fragment, parent, false);
        return new MealsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        Meal meal = values.get(position);
        holder.mealTitle.setText(meal.getMealTitle());
        Glide.with(context)
                .load(meal.getMealThumb())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.mealImg);
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
