/*

Name: Francisco Ozuna Diaz
Assignment: CS 7455 Lab 6
Lab Date: Due July 5, 2020 at 11:59 PM

 */

package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText randomCharacterEditText;

    private BroadcastReceiver broadcastReceiver;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomCharacterEditText = findViewById(R.id.editText_randomCharacter);

        broadcastReceiver = new MyBroadcastReceiver();

        serviceIntent = new Intent(getApplicationContext(), RandomCharacterService.class);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start:
                startService(serviceIntent);
                break;
            case R.id.button_end:
                stopService(serviceIntent);
                randomCharacterEditText.setText("");
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("my.custom.action.tag.lab6");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                char data = intent.getCharExtra("randomCharacter", '?');
                randomCharacterEditText.setText(String.valueOf(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}