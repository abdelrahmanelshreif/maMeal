package com.example.mameal.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mameal.model.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance = null;

    public abstract MealDAO getMealDAO();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), com.example.mameal.db.AppDatabase.class, "maMeal").build();
        }
        return instance;
    }
}
