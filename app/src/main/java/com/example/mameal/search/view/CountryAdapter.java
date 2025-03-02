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
import com.example.mameal.model.Country;
import com.example.mameal.model.Meal;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private final Context context;
    private List<Country> values;

    private OnFilteredClickListener onClickListener;

    public CountryAdapter(Context context, List<Country> values, OnFilteredClickListener onClickListener) {
        this.context = context;
        this.values = values;
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_country_item_search_fragment, parent, false);
        return new CountryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        Country country = values.get(position);
        holder.countryTitle.setText(country.getStrArea());
        Glide.with(context)
                .load(country.getCountryThumbnail())
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(holder.countryImage);

        holder.countryImage.setOnClickListener(v -> onClickListener.navigateToFilteredMealsFragment(v,SearchView.COUNTRY,country.getStrArea()));

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public TextView countryTitle;
        public ImageView countryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            this.layout = itemView;
            countryTitle = itemView.findViewById(R.id.countryNameTextView);
            countryImage = itemView.findViewById(R.id.countryImg);
        }
    }
}
