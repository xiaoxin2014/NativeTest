package com.example.nativetest.net.service;

import com.example.nativetest.common.NetConstant;
import com.example.nativetest.model.Result;
import com.example.nativetest.net.ScUrl;

import java.util.List;

import androidx.lifecycle.LiveData;
import okhttp3.MultipartBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadService {
    @Multipart
    @POST(ScUrl.UPLOAD_AVATAR)
    LiveData<Result<String>> uploadAvatar(@Part List<MultipartBody.Part> partList);
}
