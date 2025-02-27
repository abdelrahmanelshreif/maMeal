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
import com.example.mameal.model.Ingredient;
import com.example.mameal.model.Meal;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private final Context context;
    private List<Ingredient> values;

    private OnIngredientClickListener onIngredientClickListener;

    public IngredientAdapter(Context context, List<Ingredient> values, OnIngredientClickListener onIngredientClickListener) {
        this.context = context;
        this.values = values;
        this.onIngredientClickListener = onIngredientClickListener;
    }


    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_ingredient_item_search_fragment, parent, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = values.get(position);
        holder.ingredientTitle.setText(ingredient.getStrIngredient());
        Glide.with(context)
                .load(ingredient.getIngThumbnail())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.ingredientImage);

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public TextView ingredientTitle;
        public ImageView ingredientImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.layout = itemView;
            ingredientTitle = itemView.findViewById(R.id.ingredientTitleTextView);
            ingredientImage = itemView.findViewById(R.id.ingredientImgView);
        }
    }
}
