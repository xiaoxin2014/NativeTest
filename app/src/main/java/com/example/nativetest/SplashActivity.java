package com.example.nativetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.logging.Handler;

import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

//    @Override
//    protected void initView() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//        if (intentUri != null) {
//            goWithUri();
//        } else {
//            finish();
//        }
//    }
}
