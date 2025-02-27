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
import com.example.mameal.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final Context context;

    private List<Category> values;

    OnClickCategoryItem onClickCategoryItem;

    public CategoryAdapter(Context context, List<Category> values) {
        this.context = context;
        this.values = values;
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_category_item_search_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = values.get(position);
        holder.categoryTitle.setText(category.getStrCategory());
        Glide.with(context)
                .load(category.getStrCategoryThumb())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return values.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View layout;
        TextView categoryTitle;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            categoryTitle = itemView.findViewById(R.id.categoryTitleTextView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
        }
    }
}
