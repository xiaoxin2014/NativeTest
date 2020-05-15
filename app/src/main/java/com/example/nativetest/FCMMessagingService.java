package com.example.nativetest;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMMessagingService extends FirebaseMessagingService {

    NotificationHelper notificationHelper ;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(notificationHelper==null){
            notificationHelper = new NotificationHelper(this);
        }
        Log.v("FCM----", JSON.toJSONString(remoteMessage));
        if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
            sendNotification(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else {
            sendNotification(getApplicationContext(), remoteMessage.getData().get("title"),remoteMessage.getData().get("body"));
        }

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    private void sendNotification(Context iContext, String messageTitle, String messageBody) {
        notificationHelper.notify(messageTitle,messageBody);
    }
}
