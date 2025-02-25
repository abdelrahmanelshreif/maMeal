package com.example.mameal.favourite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;


public class FavouriteMealsFragment extends Fragment {

    private RecyclerView favouriteMeals;
    MaMealRepository maMealRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maMealRepository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance() , new MealsLocalDataSource(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourite_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favouriteMeals = view.findViewById(R.id.favouriteMealsRecyclerView);


    }
}