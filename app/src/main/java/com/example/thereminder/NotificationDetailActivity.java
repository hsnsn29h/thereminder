package com.example.thereminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thereminder.models.NotificationItem;

public class NotificationDetailActivity extends AppCompatActivity {

    TextView text1, text2;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotificationDetailActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationdetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Notification");
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);

        if (getIntent().getExtras().containsKey("notification_title")) {
            text1.setText(getIntent().getExtras().getString("notification_title") + "");
        }
        if (getIntent().getExtras().containsKey("notification_message")) {
            text2.setText(getIntent().getExtras().getString("notification_message") + "");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(NotificationDetailActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                break;
        }
        return true;
    }
}
