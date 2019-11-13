package com.example.thereminder;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart=prefs.getBoolean("firstStart",true);

        if(true){ firstStartMethod();}

    }

    private void firstStartMethod() {
        Dialog firstD=new Dialog(this);
        firstD.setContentView(R.layout.first_start_dialog);
firstD.show();




SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }


}
