package com.example.nativetest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    private NotificationManager mManager;
    private static final String CHANNEL_ID = "pushChannel";
    private static final String CHANNEL_NAME = "push";

    public NotificationHelper(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setLightColor(Color.GRAY);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(channel);
    }

    private Notification getNotification(String title, String body) {
        Notification notification;
//        Intent mainIntent = new Intent(this, MainActivity.class);
//        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setWhen(System.currentTimeMillis())
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),getApplicationContext().getApplicationInfo().icon))
                    .setAutoCancel(true)
//                    .setContentIntent(mainPendingIntent)
                    .build();
        }else {
            notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(body)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),getApplicationContext().getApplicationInfo().icon))
                    .setAutoCancel(true)
//                    .setContentIntent(mainPendingIntent)
                    .build();
        }
        return notification;
    }

    public void notify(String title, String body) {
        getManager().notify(1, getNotification(title, body));
    }

    public void notify(String value) {
        String title;
        String content;
        try {
            JSONObject valueJsonObject = new JSONObject(value);
            title = valueJsonObject.getString("title");
            content = valueJsonObject.getString("content");
            getManager().notify(1, getNotification(title, content));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}
