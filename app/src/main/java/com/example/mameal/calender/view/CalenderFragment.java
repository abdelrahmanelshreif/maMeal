package com.example.mameal.calender.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;

import com.example.mameal.R;
import com.example.mameal.calender.CalenderContract;
import com.example.mameal.calender.presenter.CalenderPresenter;
import com.example.mameal.model.Event;
import com.example.mameal.model.Meal;
import com.example.mameal.shared.Utility;

import java.util.List;
import java.util.Locale;


public class CalenderFragment extends Fragment implements CalenderContract.View , OnPlannedMealClickListener{

    CalendarView calenderAtFragmentCalender;
    Calendar calender;
    RecyclerView plannedMealsRecyclerView;
    CalenderContract.Presenter presenter;
    private String selectedDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CalenderPresenter(this,requireContext());
        calender = Calendar.getInstance();
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
        Calendar calendar = Calendar.getInstance();
        initializingPlans(calendar);
        calenderAtFragmentCalender.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            presenter.getMealsForDate(selectedDate);
        });

    }

    private void initializingPlans(Calendar calendar) {
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int dayOfMonth1 = calendar.get(Calendar.DAY_OF_MONTH);
        selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, month1 + 1, dayOfMonth1);
        presenter.getMealsForDate(selectedDate);
    }

    private void setupUiComponent(@NonNull View view) {
        calenderAtFragmentCalender = view.findViewById(R.id.calenderAtFragmentCalender);
        plannedMealsRecyclerView = view.findViewById(R.id.planned_meals);
    }


    @Override
    public void showError(String message) {
        Utility.showToast(requireContext(),message);

    }

    @Override
    public void showMealOfEvent(List<Meal> meals) {
        Log.d("CalenderFragment", "Meals received: " + meals.size());
        PlannedMealsAdapter adapter = new PlannedMealsAdapter(getContext(), meals, this);
        plannedMealsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL, false));
        plannedMealsRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showSuccessDeletion(String message) {
        Utility.showToast(requireContext(),message);
    }


    @Override
    public void removeFromCalender(Meal meal) {
        if (selectedDate != null) {
            presenter.removeEventFromPlan(meal.getMealId(), selectedDate);
        } else {
            Log.e("RemoveEvent", "No date selected");
        }
    }

    @Override
    public void navigateToMeal(String mealId) {
        com.example.mameal.calender.view.CalenderFragmentDirections.ActionCalenderFragmentToMealDescFragment action = CalenderFragmentDirections.actionCalenderFragmentToMealDescFragment(mealId);
        Navigation.findNavController(requireView()).navigate(action);
    }
}