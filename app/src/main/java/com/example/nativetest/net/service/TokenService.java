package com.example.nativetest.net.service;

import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.net.ScUrl;

import java.util.Map;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface  TokenService {
    @Multipart
    @POST(ScUrl.CONNECT_TOKEN)
//    LiveData<TokenBean> connectToken(@Header("Content-Type") String contentType,@PartMap Map<String, RequestBody> requestBodyMap);
    LiveData<TokenBean> connectToken(@PartMap Map<String, RequestBody> requestBodyMap);
}
