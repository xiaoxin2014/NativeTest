package com.example.nativetest.net.service;

import com.example.nativetest.common.NetConstant;
import com.example.nativetest.model.IMTokenBean;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.net.ScUrl;

import java.util.Map;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ScIMService {
    @POST(ScUrl.GET_IM_TOKEN)
    @Headers(NetConstant.JSON)
    LiveData<Result<String>> getIMToken();
}
