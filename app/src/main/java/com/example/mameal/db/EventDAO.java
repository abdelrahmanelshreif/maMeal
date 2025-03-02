package com.example.mameal.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mameal.model.Event;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface EventDAO {
    @Query("SELECT * FROM events")
    Single<List<Event>> getAllEvents();

    @Query("SELECT * FROM events WHERE eventDate = :date")
    Single<List<Event>> getEventsByDate(String date);

    @Query("SELECT meal FROM events WHERE eventDate = :date")
    Single<List<String>> getMealsOfDay(String date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertEvent(Event event);

    @Query("DELETE FROM events WHERE meal = :mealId AND eventDate = :mealDate")
    Completable deleteEvent(String mealId, String mealDate);
}
