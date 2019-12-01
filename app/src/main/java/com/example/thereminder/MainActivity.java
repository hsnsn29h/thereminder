package com.example.thereminder;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.thereminder.database.AppDatabase;
import com.example.thereminder.models.CategoryItem;
import com.google.firebase.FirebaseApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    ArrayList<String> arrayList;
    ArrayList<CategoryItem> arrayList_search;



    private PendingIntent pendingIntent;
    private AlarmManager manager;
    List<CategoryItem> categoryItemArrayList = new ArrayList<>();
    AppDatabase db;
    EditText inputSearchE;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getInstance(MainActivity.this);
        FirebaseApp.initializeApp(this);
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);


        listView = findViewById(R.id.list);
arrayList=new ArrayList<>();
arrayList_search=new ArrayList<>();

        inputSearchE = findViewById(R.id.inputSearch);
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


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        boolean firstStart = prefs.getBoolean("firstStart", true);


        if (firstStart) {
            firstStartMethod();
        }

        try {

            JSONArray obj = new JSONArray(loadJSONFromAsset());
            db.categoryDao().delete();
            for (int i = 0; i < obj.length(); i++) {
                JSONObject obj1 = obj.getJSONObject(i);
                CategoryItem item = new CategoryItem();
                item.text_ID = obj1.getString("text_ID");
                item.message = obj1.getString("message");
                arrayList.add(item.text_ID);
                db.categoryDao().insert(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        categoryItemArrayList = db.categoryDao().getAll();
        CategoryAdapter adapter = new CategoryAdapter(MainActivity.this);
        listView.setAdapter(adapter);
    }

    private void firstStartMethod() {
        final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
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
//                Toast.makeText(MainActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();

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
    }

    public void search() {


       arrayList_search.clear();
        String searchWord = inputSearchE.getText().toString().trim();

        for (int i = 0; i < categoryItemArrayList.size(); i++) {

            String item = categoryItemArrayList.get(i).text_ID;
            if (item.contains(searchWord)) {

               arrayList_search.add(categoryItemArrayList.get(i));

           }
       }

        CategoryAdapter2 adapter2 = new CategoryAdapter2(MainActivity.this);
        listView.setAdapter(adapter2);



    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("csvjson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public class CategoryAdapter extends BaseAdapter {
        ViewHolder viewHolder;
        Context _c;
        LayoutInflater inflater;

        public CategoryAdapter(Context context) {
            _c = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return categoryItemArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return categoryItemArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.athkar_list_view, null);
                viewHolder = new ViewHolder();
                viewHolder.txtmessage = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.txtmessage.setText(categoryItemArrayList.get(i).text_ID);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, CategoryDetailActivity.class).putExtra("category", categoryItemArrayList.get(i)));
                }
            });
            return convertView;
        }

        public class ViewHolder {
            TextView txtmessage;
        }
    }
    public class CategoryAdapter2 extends BaseAdapter {
        ViewHolder viewHolder;
        Context _c;
        LayoutInflater inflater;

        public CategoryAdapter2(Context context) {
            _c = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList_search.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList_search.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.athkar_list_view, null);
                viewHolder = new ViewHolder();
                viewHolder.txtmessage = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.txtmessage.setText(arrayList_search.get(i).text_ID);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, CategoryDetailActivity.class).putExtra("category", arrayList_search.get(i)));
                }
            });
            return convertView;
        }

        public class ViewHolder {
            TextView txtmessage;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
