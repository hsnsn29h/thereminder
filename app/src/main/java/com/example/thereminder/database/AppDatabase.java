package com.example.thereminder.database;  // CHANGE ME

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.thereminder.models.CategoryItem;


@Database(entities = {CategoryItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "appDb")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
