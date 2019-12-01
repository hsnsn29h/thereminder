package com.example.thereminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView text1, text2, text3;
    private PendingIntent pendingIntent;
    private AlarmManager manager;


    private String aboutUs =getResources().getString(R.string.aboutUs);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Settings");
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);


        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            text3.setText("App version : " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text1:
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();
                final Dialog firstD = new Dialog(this);
                firstD.setContentView(R.layout.first_start_dialog);
                firstD.show();
                Button finishButton = firstD.findViewById(R.id.finishB);

                final CheckBox mercyCB = firstD.findViewById(R.id.mercy);
                final CheckBox respectCB = firstD.findViewById(R.id.respect);
                final CheckBox ropCB = firstD.findViewById(R.id.ROP);
                final CheckBox honestyCB = firstD.findViewById(R.id.honesty);
                final CheckBox trustCB = firstD.findViewById(R.id.trust);
                final CheckBox justiceCB = firstD.findViewById(R.id.justice);

                boolean isMercySelected = prefs.getBoolean("mercy", false);
                boolean isrespectSelected = prefs.getBoolean("respect", false);
                boolean isropSelected = prefs.getBoolean("rop", false);
                boolean ishonestySelected = prefs.getBoolean("honesty", false);
                boolean istrustSelected = prefs.getBoolean("trust", false);
                boolean isjusticeSelected = prefs.getBoolean("justice", false);
                if (isMercySelected) {
                    mercyCB.setChecked(true);
                }
                if (isrespectSelected) {
                    respectCB.setChecked(true);
                }
                if (isropSelected) {
                    ropCB.setChecked(true);
                }
                if (ishonestySelected) {
                    honestyCB.setChecked(true);
                }
                if (istrustSelected) {
                    trustCB.setChecked(true);
                }
                if (isjusticeSelected) {
                    justiceCB.setChecked(true);
                }


                finishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                Intent myIntent = new Intent(getApplicationContext(), BroadcastReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                        getApplicationContext(), 0, myIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//                alarmManager.cancel(pendingIntent);

                        ///////////
                        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AppConstants.pushNotificationInterval, pendingIntent);

                        editor.putBoolean("mercy", mercyCB.isChecked());
                        editor.putBoolean("respect", respectCB.isChecked());
                        editor.putBoolean("rop", ropCB.isChecked());
                        editor.putBoolean("honesty", honestyCB.isChecked());
                        editor.putBoolean("trust", trustCB.isChecked());
                        editor.putBoolean("justice", justiceCB.isChecked());
                        editor.commit();
                        firstD.dismiss();

                    }
                });


                editor.putBoolean("firstStart", false);
                editor.apply();
                break;
            case R.id.text2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(aboutUs)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
