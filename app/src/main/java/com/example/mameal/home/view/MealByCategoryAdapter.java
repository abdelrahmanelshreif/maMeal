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
import com.example.mameal.R;
import com.example.mameal.model.Meal;

import java.util.List;

public class MealByCategoryAdapter extends RecyclerView.Adapter<MealByCategoryAdapter.ViewHolder> {
    private final Context context;
    private List<Meal> values;

    private OnMealClickListener onMealClickListener;

    public MealByCategoryAdapter(Context context, List<Meal> values, OnMealClickListener onMealClickListener) {
        this.context = context;
        this.values = values;
        this.onMealClickListener = onMealClickListener;
    }

    @NonNull
    @Override
    public MealByCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.meal_item_from_category_recycler_row_at_home_fragment, parent, false);
        return new MealByCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealByCategoryAdapter.ViewHolder holder, int position) {

        Meal meal = values.get(position);
        holder.mealTitle.setText(meal.getMealTitle());
        Glide.with(context)
                .load(meal.getMealThumb())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.mealImage);
        holder.mealImage.setOnClickListener(v -> onMealClickListener.onClick(v, meal.getMealId()));
        holder.mealAddToFav.setOnClickListener(v -> {
            onMealClickListener.onAddToFav(meal.getMealId(), true);

        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void updateFavoriteStatus(String mealId, boolean isFavorite) {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).getMealId().equals(mealId)) {
                notifyItemChanged(i, isFavorite);
                break;
            }
        }
    }

    public void updateFavoriteIcon(ImageView imageView, boolean isFavorite) {
        if (isFavorite) {
            imageView.setImageResource(R.drawable.filled_fav);
        } else {
            imageView.setImageResource(R.drawable.add_to_fav);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public TextView mealTitle;
        public ImageView mealImage, mealAddToFav;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            mealTitle = itemView.findViewById(R.id.mealTitleCardTextViewCard);
            mealImage = itemView.findViewById(R.id.mealCardImgView);
            mealAddToFav = itemView.findViewById(R.id.mealAddToFavBtn);

        }
    }
}
