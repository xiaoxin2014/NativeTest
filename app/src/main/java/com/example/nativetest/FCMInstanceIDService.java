package com.example.nativetest;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FCMInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v("FCM----！！！", refreshedToken);
//        sendRefreshToken(refreshedToken);
//        PremiumApp.fcmToken = refreshedToken;
    }

    public void sendRefreshTokenToService(String refreshedToken){
        //这里是往自己app应用的后台发送刷新refreshedToken的api
    }
}
