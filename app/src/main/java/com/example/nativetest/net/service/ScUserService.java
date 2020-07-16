package com.example.nativetest.net.service;

import com.example.nativetest.common.NetConstant;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.sc.NetResponse;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.UserInfo;
import com.example.nativetest.net.ScUrl;

import java.util.List;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ScUserService {
    @POST("user/login")
    LiveData<Result<String>> login(@Body RequestBody body);


    @POST(ScUrl.USER_INFO_GET)
    @Headers(NetConstant.JSON)
    LiveData<NetResponse<UserInfo>> getUserInfo(@Body RequestBody body);


    @POST(ScUrl.USER_GET_SMS)
    @Headers(NetConstant.JSON)
    LiveData<List<Result>> getSms(@Body RequestBody body);

    @POST(ScUrl.USER_VERIFY_CODE)
    @Headers(NetConstant.JSON)
    LiveData<List<Result>> verifyCode(@Body RequestBody body);


    @POST(ScUrl.PROFILE_GET)
    @Headers(NetConstant.JSON)
    LiveData<List<Result<ProfileInfo>>> getProfileInfo();

    @POST(ScUrl.PROFILE_UPDATE)
    @Headers(NetConstant.JSON)
    LiveData<List<Result<Boolean>>> updateProfileInfo(@Body RequestBody body);

}
