package com.example.mameal.calender;

import com.example.mameal.model.Event;

import java.util.List;

public interface CalenderContract {
    interface View {
        void showEvents(List<Event> events);
        void showError(String message);
    }

    interface Presenter {
        void onDateSelected(String date);
    }
}
