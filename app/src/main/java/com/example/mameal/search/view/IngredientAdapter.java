package com.example.mameal.search.view;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mameal.R;
import com.example.mameal.model.Ingredient;
import com.example.mameal.shared.Utility;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private final Context context;
    private List<Ingredient> values;

    private OnFilteredClickListener onClickListener;

    public IngredientAdapter(Context context, List<Ingredient> values, OnFilteredClickListener onClickListener) {
        this.context = context;
        this.values = values;
        this.onClickListener = onClickListener;
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
        Log.i("TAG", "onBindViewHolder: " + ingredient.getStrIngredient());
        holder.ingredientTitle.setText(ingredient.getStrIngredient());
        Glide.with(context)
                .load(Utility.ingredientThumbGenerator(ingredient.getStrIngredient()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.ingredientImage);
        holder.ingredientImage.setOnClickListener(v -> onClickListener.navigateToFilteredMealsFragment(v, SearchView.INGREDIENT, ingredient.getStrIngredient()));
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
            ingredientTitle = itemView.findViewById(R.id.ingredientTitleSearchFragment);
            ingredientImage = itemView.findViewById(R.id.ingredientImgSearchFragment);
        }
    }
}
