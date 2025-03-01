package com.example.mameal.calender.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mameal.R;
import com.example.mameal.calender.CalenderContract;
import com.example.mameal.model.Event;

import java.util.List;


public class CalenderFragment extends Fragment implements CalenderContract.View {

    CalendarView calenderAtFragmentCalender;
    RecyclerView plannedMealsRecyclerView;
    CalenderContract.Presenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);

    }

    private void setupUiComponent(@NonNull View view) {
        calenderAtFragmentCalender = view.findViewById(R.id.calenderAtFragmentCalender);
        plannedMealsRecyclerView = view.findViewById(R.id.planned_meals);
    }

    @Override
    public void showEvents(List<Event> events) {

    }

    @Override
    public void showError(String message) {

    }
}