package com.example.mameal.calender.view;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Pair;
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
import com.example.mameal.shared.Utility;

import java.util.List;

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.ViewHolder> {


    private final Context context;
    private List<Meal> values;
    OnPlannedMealClickListener onPlannedMealClickListener;


    public PlannedMealsAdapter(Context context,List<Meal> values, OnPlannedMealClickListener onPlannedMealClickListener) {
        this.context = context;
        this.values = values;
        this.onPlannedMealClickListener = onPlannedMealClickListener;


    }

    @NonNull
    @Override
    public PlannedMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favourite_meals_recycler_view_item, parent, false);
        return new PlannedMealsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlannedMealsAdapter.ViewHolder holder, int position) {
        Meal meal = values.get(position);
        holder.mealTitle.setText(meal.getMealTitle());
        holder.mealCategory.setText("");
        holder.removeFromCal.setImageResource(R.drawable.remove_from_calender);
        Glide.with(context)
                .load(meal.getMealThumb())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.mealImage);

        holder.removeFromCal.setOnClickListener(v -> {
            Utility.showConfirmationAlert(holder.itemView.getContext(),
                    "Warning",
                    "Are you sure that you want to remove it from your plans?",
                    (dialog, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            onPlannedMealClickListener.removeFromCalender(meal);
                        }
                    }
            );
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public TextView mealTitle, mealCategory;
        public ImageView mealImage, removeFromCal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            mealTitle = itemView.findViewById(R.id.mealTitleCardTextViewCard);
            mealCategory = itemView.findViewById(R.id.mealCategoryCardTextView);
            mealImage = itemView.findViewById(R.id.mealCardImgView);
            removeFromCal = itemView.findViewById(R.id.mealAddToFavBtn);


        }
    }
}