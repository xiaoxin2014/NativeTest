package com.example.nativetest.net.service;

import com.example.nativetest.model.sc.NetResponse;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.UserBean;
import com.example.nativetest.net.ScUrl;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ScUserService {
    @POST("user/login")
    LiveData<Result<String>> login(@Body RequestBody body);


    @POST(ScUrl.USER_INFO_GET)
    LiveData<NetResponse<UserBean>> getUserInfo(@Header("www-authenticate") String token, @Body RequestBody body);
}
