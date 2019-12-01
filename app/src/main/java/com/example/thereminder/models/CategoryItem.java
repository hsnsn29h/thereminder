package com.example.thereminder.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CategoryItem implements Serializable {

    @PrimaryKey(autoGenerate = true     )
    public int unique_id;
    @ColumnInfo(name = "text_ID")
    public String text_ID;
    @ColumnInfo(name = "message")
    public String message;
}
