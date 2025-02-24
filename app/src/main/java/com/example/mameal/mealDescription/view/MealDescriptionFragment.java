package com.example.mameal.mealDescription.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mameal.R;
import com.example.mameal.mealDescription.model.IngredientWithMeasure;
import com.example.mameal.mealDescription.presenter.MealDescriptionPresenter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.google.android.material.chip.Chip;

import java.util.List;

public class MealDescriptionFragment extends Fragment implements MealDescriptionContract.View {
    MealDescriptionPresenter mealDescriptionPresenter;
    private RecyclerView ingredientRecyclerView;
    private ConstraintLayout instructionsLayout;
    private Chip ingredientChip, procedureChip;
    private ScrollView procedureScrollView;

    ImageView addToFav, mealImg;
    TextView mealTitle, mealCategory;

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mealDescriptionPresenter = new MealDescriptionPresenter(
                MaMealRepository.getInstance(new MaMealRemoteDataSource()),
                new IngredientWithMeasure()
        );
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
        showIngredients(mealDescriptionPresenter.getIngredients());
        setupVedioPlayer(view);

        mealTitle.setText("Spicy Arrabiata Penne");
        mealCategory.setText("Vegetarian");
        Glide.with(this)
                .load("https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg")
                .apply(new RequestOptions()
                        .override(700,700))
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(mealImg);



        ingredientChip.setOnClickListener(v -> {
            ingredientChip.setChipBackgroundColorResource(R.color.white);
            ingredientChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            ingredientChip.setChipBackgroundColorResource(R.color.primary_100);

            procedureChip.setChipBackgroundColorResource(R.color.white);
            procedureChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));

            procedureScrollView.setVisibility(View.GONE);
            ingredientRecyclerView.setVisibility(View.VISIBLE);
            instructionsLayout.setVisibility(View.GONE);
        });

        procedureChip.setOnClickListener(v -> {

            procedureScrollView.setVisibility(View.VISIBLE);
            procedureChip.setChipBackgroundColorResource(R.color.primary_100);
            procedureChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));

            ingredientChip.setChipBackgroundColorResource(R.color.white);
            ingredientChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary_80));

            ingredientRecyclerView.setVisibility(View.GONE);
            instructionsLayout.setVisibility(View.VISIBLE);
        });


    }

    private void setupVedioPlayer(@NonNull View view) {
        String youtubeUrl = "https://www.youtube.com/embed/VVnZd8A84z4";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        StringBuilder iframe = new StringBuilder();
        iframe.append("<html><body><iframe width=\"100%\" height=\"100%\" ")
                .append("src=\"").append(youtubeUrl).append("\" ")
                .append("frameborder=\"0\" allowfullscreen></iframe></body></html>");
        webView.loadData(iframe.toString(), "text/html", "utf-8");
    }

    private void setupUiComponent(@NonNull View view) {
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecyclerView);
        instructionsLayout = view.findViewById(R.id.instructionsLayout);
        ingredientChip = view.findViewById(R.id.ingredientChip);
        procedureChip = view.findViewById(R.id.procedureChip);
        procedureScrollView = view.findViewById(R.id.procedureScrollView);
        webView = view.findViewById(R.id.youtubeWebView);
        mealImg = view.findViewById(R.id.mealCardImgView);
        mealTitle = view.findViewById(R.id.mealTitleCardTextViewCard);
        mealCategory = view.findViewById(R.id.mealCategoryCardTextView);
        addToFav = view.findViewById(R.id.mealAddToFavBtn);
        ingredientRecyclerView.setVisibility(View.VISIBLE);
        instructionsLayout.setVisibility(View.GONE);
    }

    @Override
    public void showIngredients(List<IngredientWithMeasure> ingredientList) {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getContext(), ingredientList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(layoutManager);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);
    }


    @Override
    public void showProcedure() {
        // Not implemented yet
    }
}
