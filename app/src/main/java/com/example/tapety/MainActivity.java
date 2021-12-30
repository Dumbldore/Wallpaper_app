package com.example.tapety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button mona;
    Button obraz;
    Button scheduler;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"Co 12h", "Co 24h", "Co 1min"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        // handle the set wallpaper button to set the desired wallpaper for home screen
        mona = findViewById(R.id.button);
        scheduler = findViewById(R.id.scheduler);
        scheduler.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                Data.Builder data = new Data.Builder();
                String[] images = new String[]{String.valueOf(R.drawable.rubens1), String.valueOf(R.drawable.rubens2), String.valueOf(R.drawable.rubens3), String.valueOf(R.drawable.rubens4), String.valueOf(R.drawable.rubens5)};
                data.putStringArray("images", images);
                data.putInt("iterator", 0);
                OneTimeWorkRequest mywork =
                        new OneTimeWorkRequest.Builder(Scheduler.class)
                                .setInitialDelay(2, TimeUnit.SECONDS)
                                .setInputData(data.build())
                                .addTag("WALLPAPER")
                                .build();
                // Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                WorkManager.getInstance().enqueue(mywork);

            }
        });
        mona.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().cancelAllWorkByTag("WALLPAPER");

                try {

                    wallpaperManager.setResource(R.drawable.mona);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        obraz = findViewById(R.id.button2);
        obraz.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                try {
                    wallpaperManager.setResource(R.drawable.obraz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}