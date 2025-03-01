package com.example.mameal.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mameal.model.Event;
import com.example.mameal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM events WHERE eventDate = :date")
    Observable<List<Event>> getEventsByDate(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertEvent(Event event);

    @Delete
    Completable deleteEvent(Event event);

}
