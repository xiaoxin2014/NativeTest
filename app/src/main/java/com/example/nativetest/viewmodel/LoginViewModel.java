package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceLiveData;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginViewModel extends AndroidViewModel {
    private UserTask userTask;
    private SingleSourceLiveData<TokenBean> getTokenResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result> getSmsResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result> verifyResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<TokenBean> getUserTokenResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<String>> getImTokenResult = new SingleSourceLiveData<>();

    private SingleSourceLiveData<Resource<Boolean>> changePwResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<Boolean>> setPwResult = new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<String>> uploadResult = new SingleSourceLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);

        userTask = new UserTask(application);
    }

    public void getToken() {
        getTokenResult.setSource(userTask.getAccessToken());
    }

    public SingleSourceLiveData<TokenBean> getGetTokenResult() {
        return getTokenResult;
    }

    public SingleSourceLiveData<Result> getGetSmsResult() {
        return getSmsResult;
    }

    public void getSms() {
        getSmsResult.setSource(userTask.getSms());
    }

    public void verifySms() {
        verifyResult.setSource(userTask.smsVerify());
    }

    public SingleSourceLiveData<Result> getVerifyResult() {
        return verifyResult;
    }

    public void getUserToken() {
        getUserTokenResult.setSource(userTask.getUserToken());
    }

    public SingleSourceLiveData<TokenBean> getGetUserTokenResult() {
        return getUserTokenResult;
    }


    public void getImToken() {
        getImTokenResult.setSource(userTask.getImToken());
    }

    public SingleSourceLiveData<Resource<String>> getGetImTokenResult() {
        return getImTokenResult;
    }

    public void changePw(String oldPw,String newPw){
        changePwResult.setSource(userTask.changePw(oldPw,newPw));
    }

    public SingleSourceLiveData<Resource<Boolean>> getChangePwResult() {
        return changePwResult;
    }

    public void setPw(String newPw){
        setPwResult.setSource(userTask.setPw(newPw));
    }

    public SingleSourceLiveData<Resource<Boolean>> getSetPwResult() {
        return setPwResult;
    }


}
