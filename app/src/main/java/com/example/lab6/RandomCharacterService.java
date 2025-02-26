/*

Name: Francisco Ozuna Diaz
Assignment: CS 7455 Lab 6
Lab Date: Due July 5, 2020 at 11:59 PM

 */

package com.example.lab6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.util.Random;

public class RandomCharacterService extends Service {
    private boolean isRandomGeneratorOn;

    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final String TAG = "RandomCharacterService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Service started...");
        Log.i(TAG, "In OnStartCommand Thread ID is " + Thread.currentThread().getId());
        isRandomGeneratorOn = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomGenerator();
            }
        }).start();

        return START_STICKY;
    }

    private void startRandomGenerator() {
        while(isRandomGeneratorOn) {
            try {
                Thread.sleep(1000);
                if (isRandomGeneratorOn) {
                    int MIN = 0;
                    int MAX = 25;
                    int randomIdx = new Random().nextInt(MAX) + MIN;
                    char myRandomCharacter = alphabet[randomIdx];
                    Log.i(TAG, "Thread ID is " + Thread.currentThread().getId() + ", Random Character is " + myRandomCharacter);

                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("my.custom.action.tag.lab6");
                    broadcastIntent.putExtra("randomCharacter", myRandomCharacter);
                    sendBroadcast(broadcastIntent);
                }
            } catch (InterruptedException e) {
                Log.i(TAG, "Thread Interrupted.");
            }
        }
    }

    private void stopRandomGenerator() { isRandomGeneratorOn = false; }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomGenerator();
        Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
        Log.i(TAG,"Service Destroyed...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
