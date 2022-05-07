package com.example.billiard_pub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new NotificationHelper(context).send("Itt az idő foglalni! *.-");
    }
}
