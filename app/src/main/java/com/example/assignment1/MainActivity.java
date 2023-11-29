package com.example.assignment1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.health.connect.datatypes.StepsCadenceRecord;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button newTask;
    private ListView listView;
    private ArrayAdapter <Task> adapter;


    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        newTask = findViewById(R.id.newTask);
        if( loadTasks(getApplicationContext()) != null){
            tasks = loadTasks(getApplicationContext());
        }


        adapter=new ListAdapter(MainActivity.this, tasks);
        listView.setAdapter(adapter);

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSecondActivity();
            }
        });

    }

    private static final int REQUEST_CODE = 1; // Define a request code

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String addedTask = data.getStringExtra(MainActivity2.EXTRA_TEXT);
                Task task = new Task(addedTask,false);
                adapter.add(task);
                saveTasks(getApplicationContext(),tasks);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private static final String TASKS_PREFS = "tasks_preferences";
    private static final String TASKS_KEY = "tasks_key";

    public static void saveTasks(Context context, List<Task> tasks) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TASKS_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonTasks = gson.toJson(tasks);

        editor.putString(TASKS_KEY, jsonTasks);
        editor.apply();
    }

    public static ArrayList<Task> loadTasks(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TASKS_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonTasks = sharedPreferences.getString(TASKS_KEY, "");

        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        return gson.fromJson(jsonTasks, type);
    }
    public void openSecondActivity(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

}