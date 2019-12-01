package com.example.thereminder.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.thereminder.models.CategoryItem;

import java.util.List;

@Dao
public interface CategoryDao {


    @Query("SELECT * FROM categoryitem")
    List<CategoryItem> getAll();


//    @Insert()
//    void insertAll(CategoryItem[] books);

    @Insert()
    void insert(CategoryItem categoryItem);

    @Delete
    void deleteAll(CategoryItem categoryItem);

    @Query("DELETE FROM categoryitem")
    public void delete();

}
