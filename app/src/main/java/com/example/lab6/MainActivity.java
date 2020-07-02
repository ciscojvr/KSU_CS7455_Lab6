package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private Button startServiceButton;
    private Button stopServiceButton;
    private EditText randomCharacterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceButton = (Button) findViewById(R.id.button_start);
        stopServiceButton = (Button) findViewById(R.id.button_end);

        randomCharacterEditText = (EditText) findViewById(R.id.editText_randomCharacter);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start:
                break;
            case R.id.button_end:
                break;
        }
    }


}