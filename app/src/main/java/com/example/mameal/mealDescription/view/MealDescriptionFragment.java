package com.example.mameal.mealDescription.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mameal.R;
import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.mealDescription.model.Ingredient;
import com.example.mameal.mealDescription.presenter.MealDescriptionPresenter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.shared.Utility;
import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.List;

public class MealDescriptionFragment extends Fragment implements MealDescriptionContract.View {
    MealDescriptionContract.Presenter mealDescriptionPresenter;
    private RecyclerView ingredientRecyclerView;
    private ConstraintLayout instructionsLayout;
    private Chip ingredientChip, procedureChip;
    ImageView addToFav, mealImg , addToPlan;
    TextView mealTitle, mealCategory;
    TextView instructions;
    String mealId;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mealDescriptionPresenter = new MealDescriptionPresenter(
                MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(getContext())),
                new Ingredient(),
                this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_description, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);
        mealId = MealDescriptionFragmentArgs.fromBundle(getArguments()).getMealId();
        mealDescriptionPresenter.getMealData(mealId);
        mealDescriptionPresenter.getIngredients(mealId);
        addToFav.setOnClickListener(v -> mealDescriptionPresenter.addToFavourite(mealId));
        addToPlan.setOnClickListener(v -> mealDescriptionPresenter.showDatePicker(requireActivity().getSupportFragmentManager()));
    }


    private void setupUiComponent(@NonNull View view) {
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecyclerView);
        instructionsLayout = view.findViewById(R.id.instructionsLayout);
        ingredientChip = view.findViewById(R.id.ingredientChip);
        procedureChip = view.findViewById(R.id.procedureChip);
        webView = view.findViewById(R.id.youtubeWebView);
        mealImg = view.findViewById(R.id.mealCardImgView);
        mealTitle = view.findViewById(R.id.mealTitleCardTextViewCard);
        mealCategory = view.findViewById(R.id.mealCategoryCardTextView);
        addToFav = view.findViewById(R.id.mealAddToFavBtn);
        addToPlan = view.findViewById(R.id.addToCalender);
        instructions = view.findViewById(R.id.textView20);
        ingredientRecyclerView.setVisibility(View.VISIBLE);
        instructionsLayout.setVisibility(View.GONE);
        ingredientChip.setOnClickListener(v -> {
            ingredientChip.setChipBackgroundColorResource(R.color.white);
            ingredientChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            ingredientChip.setChipBackgroundColorResource(R.color.primary_100);
            procedureChip.setChipBackgroundColorResource(R.color.white);
            procedureChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));
            ingredientRecyclerView.setVisibility(View.VISIBLE);
            instructionsLayout.setVisibility(View.GONE);
        });
        procedureChip.setOnClickListener(v -> {
            procedureChip.setChipBackgroundColorResource(R.color.primary_100);
            procedureChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            ingredientChip.setChipBackgroundColorResource(R.color.white);
            ingredientChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));
            ingredientRecyclerView.setVisibility(View.GONE);
            instructionsLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void showIngredients(List<Ingredient> ingredientList) {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);
    }

    @Override
    public void showError(String message) {
        Utility.showToast(getContext(), message);
    }

    @Override
    public String getCurrentMealId() {
        return mealId;
    }

    @Override
    public void showSucessAddingToPlan(String message) {
        Utility.showToast(requireContext(),message);
        addToPlan.setImageResource(R.drawable.add_to_calender_filled);
    }

    @Override
    public void successAddingToFav(String message) {
        Utility.showToast(getContext(), message);
        addToFav.setImageResource(R.drawable.filled_fav);
    }

    @Override
    public void setMealMainData(Meal meal) {
        setMealTextData(meal);
        Utility.loadImage(this, meal.getMealThumb(), mealImg);
        loadYoutubeVideo(mealDescriptionPresenter.getFormattedYoutubeUrl(meal.getMealYoutube()));
    }


    private void setMealTextData(Meal meal) {
        mealTitle.setText(meal.getMealTitle());
        mealCategory.setText(meal.getMealCategory());
        Glide.with(this)
                .load(Utility.buildFlagUrl(meal.getMealArea()))
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mealCategory.setCompoundDrawablesWithIntrinsicBounds(null, null, null, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        mealCategory.setCompoundDrawablesWithIntrinsicBounds(null, null, null, placeholder);
                    }
                });

        instructions.setText(mealDescriptionPresenter.getFormattedInstructions(meal));
    }


    private void loadYoutubeVideo(String youtubeUrl) {
        setupWebView();
        String iframe = "<html><body><iframe width=\"100%\" height=\"100%\" " +
                "src=\"" + youtubeUrl + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        webView.loadData(iframe, "text/html", "utf-8");
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

}
