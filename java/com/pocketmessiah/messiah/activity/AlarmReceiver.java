package com.pocketmessiah.messiah.activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.pocketmessiah.messiah.R;

public class AlarmReceiver extends BroadcastReceiver {

    String quote = "";

    public void onReceive(Context context, Intent intent) {
        PendingIntent resultIntent = PendingIntent.getActivity(context, 0, new Intent(context, Messiah.class), 0);

        Messiah messiah = new Messiah();
        quote = Messiah.qouteForNotification;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(resultIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Messiah")
                .setContentText(quote)
                .setPriority(Notification.PRIORITY_MAX);


        mBuilder.setAutoCancel(true);
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        mBuilder.setVibrate(new long[] {0, 100, 200, 300});

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}