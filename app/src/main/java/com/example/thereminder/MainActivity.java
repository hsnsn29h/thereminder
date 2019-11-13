package com.example.thereminder;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);

        boolean firstStart=prefs.getBoolean("firstStart",true);


        if(firstStart){ firstStartMethod();}

    }

    private void firstStartMethod() {
        final SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);
        final SharedPreferences.Editor editor=prefs.edit();
        final Dialog firstD=new Dialog(this);
        firstD.setContentView(R.layout.first_start_dialog);
firstD.show();
        Button finishButton=firstD.findViewById(R.id.finishB);

        final CheckBox mercyCB=firstD.findViewById(R.id.mercy);
        final CheckBox respectCB=firstD.findViewById(R.id.respect);
        final CheckBox ropCB=firstD.findViewById(R.id.ROP);
        final CheckBox honestyCB=firstD.findViewById(R.id.honesty);
        final CheckBox trustCB=firstD.findViewById(R.id.trust);
        final CheckBox justiceCB=firstD.findViewById(R.id.justice);


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("قيمة الرحمة",mercyCB.isChecked());
                editor.putBoolean("قيمة الاحترام",respectCB.isChecked());
                editor.putBoolean("قيمة بر الوالدين",ropCB.isChecked());
                editor.putBoolean("قيمة الصدق",honestyCB.isChecked());
                editor.putBoolean("قيمة الأمانة",trustCB.isChecked());
                editor.putBoolean("قيمة العدل",justiceCB.isChecked());
                firstD.dismiss();

            }
        });





        editor.putBoolean("firstStart",false);
        editor.apply();
    }


}
