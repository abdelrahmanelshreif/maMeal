package com.example.mameal.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.home.presenter.HomePresenter;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.uiModel.MealUiModel;
import com.example.mameal.utils.Utility;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5, recyclerView6, recyclerView7;

    TextView helloText, welcomeText;
    ImageView profileImg;

    HomePresenter homePresenter;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(this
                , MaMealRepository.getInstance(new MaMealRemoteDataSource())
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

    private void setupUiComponent(View view) {
        recyclerView1 = view.findViewById(R.id.homeRecyclerView1);
        recyclerView2 = view.findViewById(R.id.homeRecyclerView2);
        recyclerView4 = view.findViewById(R.id.homeRecyclerView4);
        recyclerView5 = view.findViewById(R.id.homeRecyclerView5);
        recyclerView6 = view.findViewById(R.id.homeRecyclerView6);
        recyclerView7 = view.findViewById(R.id.homeRecyclerView7);
        helloText = view.findViewById(R.id.textView12);
        welcomeText = view.findViewById(R.id.textView13);
        profileImg = view.findViewById(R.id.imageView3);
    }

    @Override
    public void showMeals(List<MealUiModel> dummyMeals) {
        AllMealsAdapter allMealsAdapter = new AllMealsAdapter(getContext(), dummyMeals);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        recyclerView1.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView4.setLayoutManager(layoutManager3);
        recyclerView5.setLayoutManager(layoutManager4);
        recyclerView6.setLayoutManager(layoutManager5);
        recyclerView7.setLayoutManager(layoutManager6);

        recyclerView1.setAdapter(allMealsAdapter);
        recyclerView2.setAdapter(allMealsAdapter);
        recyclerView4.setAdapter(allMealsAdapter);
        recyclerView5.setAdapter(allMealsAdapter);
        recyclerView6.setAdapter(allMealsAdapter);
        recyclerView7.setAdapter(allMealsAdapter);




    }

    @Override
    public void showError(String err) {
        Utility.showToast(getContext(), "Error fetching the data");
    }
}