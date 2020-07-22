package com.example.nativetest.net.service;

import com.example.nativetest.common.NetConstant;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.net.ScUrl;

import java.util.Map;

import androidx.lifecycle.LiveData;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface  TokenService {
    @Multipart
    @POST(ScUrl.CONNECT_TOKEN)
    LiveData<TokenBean> connectToken(@PartMap Map<String, RequestBody> requestBodyMap);

    @POST(ScUrl.CHANGE_PW_BY_CODE)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> changePwByCode(@Body RequestBody body);

    @POST(ScUrl.CHANGE_PW_BY_OLD_PW)
    @Headers(NetConstant.JSON)
    LiveData<Result<Boolean>> changePwByOldPw(@Body RequestBody body);


}
