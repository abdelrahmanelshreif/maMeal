package com.example.mameal.mealDescription.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mameal.R;
import com.example.mameal.mealDescription.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {


    private final Context context;

    List<Ingredient> values;

    public IngredientsAdapter(Context context, List<Ingredient> values) {
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredients_recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = values.get(position);

        holder.ingredientTitleTextView.setText(ingredient.getIngredientTitle());
        holder.ingredientMeasureTextView.setText(ingredient.getIngredientMeasure());
        Glide.with(context)
                .load(ingredient.getIngredientThumbnail())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.ingredientImgView);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Ingredient> ingredientList) {
        Log.d("IngredientsAdapter", "Adapter updating with " + ingredientList.size() + " items.");
        this.values = ingredientList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView ingredientTitleTextView;
        TextView ingredientMeasureTextView;
        ImageView ingredientImgView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            this.ingredientTitleTextView = itemView.findViewById(R.id.ingredientTitleTextView);
            this.ingredientMeasureTextView = itemView.findViewById(R.id.ingredientMeasureTextView);
            this.ingredientImgView = itemView.findViewById(R.id.ingredientImgView);
        }
    }
}
