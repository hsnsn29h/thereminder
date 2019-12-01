package com.example.thereminder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thereminder.models.CategoryItem;

public class CategoryDetailActivity extends AppCompatActivity {
    private CategoryItem categoryItem;
    private TextView text1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorydetail);
        categoryItem = (CategoryItem) getIntent().getExtras().getSerializable("category");
        text1 = findViewById(R.id.text1);
        text1.setText(categoryItem.message + "");
    }
}
