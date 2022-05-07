package com.example.billiard_pub;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {
    private NotificationHelper mNotificationHelper;

    @Override
    public boolean onStartJob(JobParameters params) {
        mNotificationHelper = new NotificationHelper(getApplicationContext());
        mNotificationHelper.send("Foglald le helyed, hogy meglegyen a kedved, nem lesz este móka, ha pub után a róka, jön.s");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
