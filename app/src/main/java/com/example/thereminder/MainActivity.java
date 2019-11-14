package com.example.thereminder;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    ArrayAdapter arrayAdapter2;
    ArrayList<String> arrayList;
    ArrayList<String> arrayList_search;
    EditText inputSearchE;
    ListView listView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       arrayList=new ArrayList<>();
       arrayList_search=new ArrayList<>();


        arrayList.add("سبحان الله");
        arrayList.add("الله اكبر");
        arrayList.add("الحمد لله");
        arrayList.add("لا اله الا الله");
        arrayList.add("محمد رسول الله");
        arrayList.add("استغفر الله");
        arrayList.add("لاحول ولا قوة الا بالله");



        arrayAdapter=new ArrayAdapter(this,R.layout.athkar_list_view,arrayList);
        listView=findViewById(R.id.list);
        listView.setAdapter(arrayAdapter);




         inputSearchE=findViewById(R.id.inputSearch);
        inputSearchE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
search();




            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        SharedPreferences prefs=getSharedPreferences("prefs",MODE_PRIVATE);

        boolean firstStart=prefs.getBoolean("firstStart",true);


        if(!firstStart){ firstStartMethod();}

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
    public void search(){


        arrayList_search.clear();
        String searchWord=inputSearchE.getText().toString().trim();

        for(int i=0;i<arrayList.size();i++){

            String item=arrayList.get(i).toString();
            if(item.contains(searchWord)){

                arrayList_search.add(item);

            }
        }

        arrayAdapter2=new ArrayAdapter(this,R.layout.athkar_list_view,arrayList_search);
        listView.setAdapter(arrayAdapter2);

    }


}
