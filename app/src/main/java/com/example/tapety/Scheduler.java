package com.example.tapety;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;

import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * Created on : Mar 26, 2019
 * Author     : AndroidWave
 */
public class Scheduler extends Worker {
    public Scheduler(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @Override
    public Result doWork() {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            int iterator = getInputData().getInt("iterator", 0);
            String[] images = getInputData().getStringArray("images");

            // set the wallpaper by calling the setResource function and
            // passing the drawable file
            wallpaperManager.setResource(Integer.parseInt(images[iterator]));
            iterator++;
            if (iterator==images.length) iterator=0;
            Data.Builder data = new Data.Builder();
            data.putStringArray("images", images);
            data.putInt("iterator", iterator);
            OneTimeWorkRequest mywork =
                    new OneTimeWorkRequest.Builder(Scheduler.class)
                            .setInitialDelay(10, TimeUnit.SECONDS)
                            .setInputData(data.build())
                            .addTag("WALLPAPER")
                            .build();
            // Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
            WorkManager.getInstance().enqueue(mywork);
        } catch (IOException e) {
            // here the errors can be logged instead of printStackTrace
            e.printStackTrace();
        }

        return ListenableWorker.Result.success();
    }

}