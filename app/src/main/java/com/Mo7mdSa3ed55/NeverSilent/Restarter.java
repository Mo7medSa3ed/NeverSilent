package com.Mo7mdSa3ed55.NeverSilent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, Reoncreate.class));
        } else {
            context.startService(new Intent(context, Reoncreate.class));
        }
    }
}
