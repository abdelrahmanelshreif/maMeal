package com.example.mameal.home.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mameal.R;
import com.example.mameal.uiModel.MealUiModel;

import java.util.List;

public class AllMealsAdapter extends RecyclerView.Adapter<AllMealsAdapter.ViewHolder> {


    private final Context context;
    private List<MealUiModel> values;


    public AllMealsAdapter(Context context, List<MealUiModel> values) {
        this.context = context;
        this.values = values;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_meal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealUiModel meal = values.get(position);
        holder.mealTitle.setText(meal.getMealTitle());
        holder.mealCategory.setText(meal.getMealCategory());
        Glide.with(context)
                .load(meal.getMealImgSource())
                .apply(new RequestOptions().override(600, 100))
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.mealImage);
        holder.mealAddToFav.setImageResource(R.drawable.add_to_favorites_icon);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public TextView mealTitle, mealCategory;
        public ImageView mealImage, mealAddToFav;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            mealTitle = itemView.findViewById(R.id.mealTitleCardTextViewCard);
            mealCategory = itemView.findViewById(R.id.mealCategoryCardTextView);
            mealImage = itemView.findViewById(R.id.mealCardImgView);
            mealAddToFav = itemView.findViewById(R.id.mealAddToFavBtn);

        }
    }
}
