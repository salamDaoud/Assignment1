package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {
    public static final String EXTRA_TEXT = "task";
    private EditText inputt;
    private Button addTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        inputt = findViewById(R.id.input);
        addTask = findViewById(R.id.addTask);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addedTask = inputt.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(EXTRA_TEXT, addedTask);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}