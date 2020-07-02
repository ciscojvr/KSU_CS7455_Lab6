package com.example.lab6;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class RandomCharacterService extends Service {
    private char myRandomCharacter;
    private boolean isRandomGeneratorOn;

    private final int MIN = 0;
    private final int MAX = 0;

    char [] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private final String TAG = "RandomCharacterService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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
                    int randomIdx = new Random().nextInt(MAX) + MIN;
                    myRandomCharacter = alphabet[randomIdx];
                    Log.i(TAG, "Thread ID is " + Thread.currentThread().getId() + ", Random Character is " + myRandomCharacter);
                }
            } catch (InterruptedException e) {
                Log.i(TAG, "Thread Interrupted.");
            }
        }
    }

    private void stopRandomGenerator() { isRandomGeneratorOn = false; }

    public char getRandomCharacter() { return myRandomCharacter; }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomGenerator();
        Log.i(TAG, "Service Destroyed...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
