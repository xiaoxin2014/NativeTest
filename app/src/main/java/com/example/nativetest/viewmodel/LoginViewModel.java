package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.model.sc.UserInfo;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceLiveData;
import com.example.nativetest.utils.SingleSourceMapLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {
    private UserTask userTask;
    private SingleSourceLiveData<Resource<String>> loginResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<UserInfo>> getUserResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<TokenBean> getTokenResult = new SingleSourceLiveData<>();
    private SingleSourceMapLiveData<List<Result>, Result> getSmsResult = new SingleSourceMapLiveData<>(input -> input.get(0));
    private SingleSourceMapLiveData<List<Result>, Result> verifyResult = new SingleSourceMapLiveData<>(input -> input.get(0));
    private SingleSourceLiveData<TokenBean> getUserTokenResult = new SingleSourceLiveData<>();
//    private SingleSourceMapLiveData<Resource<List<Result<ProfileInfo>>>, Resource<Result<ProfileInfo>>> profileResult;


    public LoginViewModel(@NonNull Application application) {
        super(application);

        userTask = new UserTask(application);

//        profileResult = new SingleSourceMapLiveData<>(input -> {
//
//            SLog.d("niko SingleSourceMapLiveData", "1");
//            if (input.status == Status.LOADING) {
//                return Resource.loading(null);
//            }
//            SLog.d("niko SingleSourceMapLiveData", "2");
//
//            if (input.status == Status.ERROR) {
//                return Resource.error(input.code, null);
//            }
//            SLog.d("niko SingleSourceMapLiveData", "3");
//
//            if (input.data == null || input.data.size() == 0) {
//                return new Resource<>(input.status, null, input.code);
//            }
//            SLog.d("niko SingleSourceMapLiveData", "4");
//
//            List<Result<ProfileInfo>> data = input.data;
//            return new Resource<>(Status.SUCCESS, data.get(0), input.code);
//        });
    }

    public void login(String region, String phone, String pwd) {
        loginResult.setSource(userTask.login(region, phone, pwd));
        //TODO 示例代码，当需要转换类型时参考
        //loginResultNoResource.setSource(userTask.login(region, phone, pwd));
    }

    public LiveData<Resource<String>> getLoginResult() {
        return loginResult;
    }

    public void getUserInfo() {
        getUserResult.setSource(userTask.getUserInfo());
    }

    public SingleSourceLiveData<Resource<UserInfo>> getGetUserResult() {
        return getUserResult;
    }

    public void getToken() {
        getTokenResult.setSource(userTask.getAccessToken());
    }

    public SingleSourceLiveData<TokenBean> getGetTokenResult() {
        return getTokenResult;
    }

    public SingleSourceMapLiveData<List<Result>, Result> getGetSmsResult() {
        return getSmsResult;
    }

    public void getSms() {
        getSmsResult.setSource(userTask.getSms());
    }

    public void verifySms() {
        verifyResult.setSource(userTask.smsVerify());
    }

    public SingleSourceMapLiveData<List<Result>, Result> getVerifyResult() {
        return verifyResult;
    }

    public void getUserToken() {
        getUserTokenResult.setSource(userTask.getUserToken());
    }

    public SingleSourceLiveData<TokenBean> getGetUserTokenResult() {
        return getUserTokenResult;
    }


}
