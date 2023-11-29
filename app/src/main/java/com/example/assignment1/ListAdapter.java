package com.example.assignment1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

public class ListAdapter extends ArrayAdapter<Task> {
    private ArrayList<Task> dataArrayList;
    public ListAdapter(@NonNull Context context, ArrayList<Task> dataArrayList) {

        super(context, R.layout.list_item, dataArrayList);
        this.dataArrayList = dataArrayList;
    }
    @NonNull
    //@Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView listImage = view.findViewById(R.id.listImage);
        TextView listName = view.findViewById(R.id.listName);
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        String listData = getItem(position).getName().toString();
        listName.setText(listData);
        checkBox.setChecked(getItem(position).getCheck());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox.setChecked(checkBox.isChecked());
                getItem(position).check =checkBox.isChecked();
                saveTasks(getContext(),dataArrayList);
            }
        });

        return view;
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

}
