package com.example.nativetest.net.service;

import com.example.nativetest.model.LoginBean;
import com.example.nativetest.model.Result;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NikoTest {
    @POST("user/login")
    LiveData<Result<String>> login(@Body RequestBody body);
}
