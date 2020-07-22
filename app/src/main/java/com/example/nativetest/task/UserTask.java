package com.example.nativetest.task;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.db.dao.UserDao;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.IMTokenBean;
import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.NetResponse;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.model.sc.UserInfo;
import com.example.nativetest.net.HttpClientManager;
import com.example.nativetest.net.NetworkBoundResource;
import com.example.nativetest.net.NetworkOnlyResource;
import com.example.nativetest.net.RetrofitUtil;
import com.example.nativetest.net.ScInterceptor;
import com.example.nativetest.net.service.ScIMService;
import com.example.nativetest.net.service.ScUserService;
import com.example.nativetest.net.service.TokenService;
import com.example.nativetest.net.token.TokenHttpClientManager;
import com.example.nativetest.utils.log.SLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import okhttp3.RequestBody;

public class UserTask {
//    private FileManager fileManager;
    private ScUserService scUserService;
    private TokenService tokenService;
    private ScIMService scIMService;
    private Context context;

    public UserTask(Context context) {
        this.context = context.getApplicationContext();
        scUserService = HttpClientManager.getInstance(context).getClient().createService(ScUserService.class);
        tokenService = TokenHttpClientManager.getInstance(context).getClient().createService(TokenService.class);
        scIMService = HttpClientManager.getInstance(context).getClient().createService(ScIMService.class);
    }





    /**
     * 用户登录
     *
     * @param region   国家区号
     * @param phone    手机号码
     * @param password 密码
     */
    public LiveData<Resource<String>> login(String region, String phone, String password) {
//        MediatorLiveData<Resource<String>> result = new MediatorLiveData<>();
//        result.setValue(Resource.loading(null));

        LiveData<Resource<String>> login = new NetworkOnlyResource<String, Result<String>>() {
            @NonNull
            @Override
            protected LiveData<Result<String>> createCall() {
                HashMap<String, Object> paramsMap = new HashMap<>();
                RequestBody body = RetrofitUtil.createJsonRequest(paramsMap);
                return scUserService.login(body);
            }
        }.asLiveData();
        return login;

//        result.addSource(login, loginResultResource -> {
//            if (loginResultResource.status == Status.SUCCESS) {
//                result.removeSource(login);
//
//                LoginResult loginResult = loginResultResource.data;
//                if (loginResult != null) {
//                    imManager.connectIM(loginResult.token, true, new ResultCallback<String>() {
//                        @Override
//                        public void onSuccess(String s) {
//                            result.postValue(Resource.success(s));
//                            // 存储当前登录成功的用户信息
//                            UserCacheInfo info = new UserCacheInfo(s, loginResult.token, phone, password, region, countryCache.getCountryInfoByRegion(region));
//                            userCache.saveUserCache(info);
//                        }
//
//                        @Override
//                        public void onFail(int errorCode) {
//                            result.postValue(Resource.error(errorCode, null));
//                        }
//                    });
//                } else {
//                    result.setValue(Resource.error(ErrorCode.API_ERR_OTHER.getCode(), null));
//                }
//            } else if (loginResultResource.status == Status.ERROR) {
//                result.setValue(Resource.error(loginResultResource.code, null));
//            } else {
//                // do nothing
//            }
//        });
//        return result;
    }


    public LiveData<TokenBean> getAccessToken(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "client_credentials");
        paramsMap.put("scope", "jjApiScope");
        Map<String, RequestBody> stringRequestBodyMap = RetrofitUtil.generateRequestBody(paramsMap);
        return tokenService.connectToken(stringRequestBodyMap);
    }

    public LiveData<Result> getSms(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("PhoneNumber", "13305938755");
        paramsMap.put("PhoneCountry", "86");
        RequestBody body = RetrofitUtil.getRequestBody(paramsMap);
        return scUserService.getSms(body);
    }

    public LiveData<Result> smsVerify(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("PhoneNumber", "13305938755");
        paramsMap.put("PhoneCountry", "86");
        paramsMap.put("VCode", "9999");
        RequestBody body = RetrofitUtil.getRequestBody(paramsMap);
        return scUserService.verifyCode(body);
    }

    public LiveData<TokenBean> getUserToken(){
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", "password");
        paramsMap.put("scope", "jjApiScope");

        paramsMap.put("UserName", "13305938755");
        paramsMap.put("Password", ScInterceptor.getDV()+"9999");
        paramsMap.put("VCode", "9999");

        Map<String, RequestBody> stringRequestBodyMap = RetrofitUtil.generateRequestBody(paramsMap);
        return tokenService.connectToken(stringRequestBodyMap);
    }


    public LiveData<Resource<ProfileInfo>> getProfile(){
//        MediatorLiveData<Resource<Result<ProfileInfo>>> result = new MediatorLiveData<>();
//        result.setValue(Resource.loading(null));
        LiveData<Resource<ProfileInfo>> profile = new NetworkOnlyResource<ProfileInfo, Result<ProfileInfo>>() {

            @NonNull
            @Override
            protected LiveData<Result<ProfileInfo>> createCall() {
                return scUserService.getProfileInfo();
            }
        }.asLiveData();
        return profile;
    }
 /*   *//**
     * 获取用户信息
     *
     * @return
     *//*
    public LiveData<Resource<ProfileInfo>> getProfile() {
        return new NetworkBoundResource<ProfileInfo, Result<ProfileInfo>>() {
            @Override
            protected void saveCallResult(@NonNull Result<ProfileInfo> item) {
                ProfileUtils.sProfileInfo = item.getRsData();
            }

            @NonNull
            @Override
            protected LiveData<ProfileInfo> loadFromDb() {
                MediatorLiveData<ProfileInfo> profileInfoMediatorLiveData = new MediatorLiveData<>();
                profileInfoMediatorLiveData.setValue(ProfileUtils.sProfileInfo);
                return profileInfoMediatorLiveData;
//                UserDao userDao = dbManager.getUserDao();
//                if (userDao != null) {
//                    return userDao.getUserById(userId);
//                } else {
//                    return new MediatorLiveData<>();
//                }
            }

            @NonNull
            @Override
            protected LiveData<Result<ProfileInfo>> createCall() {
                return scUserService.getProfileInfo();
            }
        }.asLiveData();
    }
*/


    public LiveData<Result<Boolean>> updateProfile(int type,String key,Object value){
        return scUserService.updateProfileInfo(RetrofitUtil.createJsonRequest(ProfileUtils.getUpdateInfo(type,key,value)));
    }

    public LiveData<Result<Boolean>> hasSetPassword(){
        return scUserService.hasSetPassword();
    }

    public LiveData<Resource<Boolean>> logout(){

        return new NetworkOnlyResource<Boolean, Result<Boolean>>() {

            @NonNull
            @Override
            protected LiveData<Result<Boolean>> createCall() {
                return scUserService.logout();
            }
        }.asLiveData();
    }


    public LiveData<Resource<String>> getImToken(){

        return new NetworkOnlyResource<String, Result<String>>() {

            @NonNull
            @Override
            protected LiveData<Result<String>> createCall() {
                return scIMService.getIMToken();
            }
        }.asLiveData();
    }




}
