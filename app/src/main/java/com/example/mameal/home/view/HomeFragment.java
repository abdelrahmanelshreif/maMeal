package com.example.mameal.home.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.presenter.HomePresenter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.uiModel.MealUiModel;
import com.example.mameal.utils.Utility;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {


    private LinearLayout linearLayoutSections;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;

    private TextView sectionTitle1;
    private TextView sectionTitle2;
    private TextView sectionTitle3;
    private TextView sectionTitle4;
    private TextView helloText, welcomeText;
    private ImageView profileImg;

    HomePresenter homePresenter;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(this
                , MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(),new MealsLocalDataSource(getContext()))
                , new MealUiModel()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);
        homePresenter.loadMeals();
    }

    @SuppressLint("CutPasteId")
    private void setupUiComponent(View view) {
        helloText = view.findViewById(R.id.textView12);
        welcomeText = view.findViewById(R.id.textView13);
        linearLayoutSections = view.findViewById(R.id.sectionsLinearLayout);

        initializeSection(R.id.home_section1, 0);
        initializeSection(R.id.home_section2, 1);
        initializeSection(R.id.home_section3, 2);
        initializeSection(R.id.home_section4, 3);
    }

    private void initializeSection(int sectionId, int index) {
        View section = linearLayoutSections.findViewById(sectionId);

        RecyclerView recyclerView = section.findViewById(R.id.homeRecyclerView);
        TextView sectionTitle = section.findViewById(R.id.sectionTitle);
        switch (index) {
            case 0:
                recyclerView1 = recyclerView;
                sectionTitle1 = sectionTitle;
                break;
            case 1:
                recyclerView2 = recyclerView;
                sectionTitle2 = sectionTitle;
                break;
            case 2:
                recyclerView3 = recyclerView;
                sectionTitle3 = sectionTitle;
                break;
            case 3:
                recyclerView4 = recyclerView;
                sectionTitle4 = sectionTitle;
        }
    }

    @Override
    public void showMeals(List<MealUiModel> dummyMeals) {
        setupRecyclerView(recyclerView1, dummyMeals);
        setupRecyclerView(recyclerView2, dummyMeals);
        setupRecyclerView(recyclerView3, dummyMeals);
        setupRecyclerView(recyclerView4, dummyMeals);

    }

    private void setupRecyclerView(RecyclerView recyclerView, List<MealUiModel> dummyMeals) {
        if (recyclerView != null) {
            MealByCategoryAdapter mealByCategoryAdapter = new MealByCategoryAdapter(getContext(), dummyMeals);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(mealByCategoryAdapter);
        }

    }

    @Override
    public void showError(String err) {
        Utility.showToast(getContext(), "Error fetching the data");
    }
}