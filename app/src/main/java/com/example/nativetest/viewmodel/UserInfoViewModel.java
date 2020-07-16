package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.Result;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceMapLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserInfoViewModel extends AndroidViewModel {

    private UserTask userTask;


    private SingleSourceMapLiveData<List<Result<ProfileInfo>>,Result<ProfileInfo>> profileResult =  new SingleSourceMapLiveData<>(input -> input.get(0));
    private SingleSourceMapLiveData<List<Result<Boolean>>,Result<Boolean>> updateProfileResult =  new SingleSourceMapLiveData<>(input -> input.get(0));


    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userTask = new UserTask(application);
//        requestUserInfo(imManager.getCurrentId());
    }

    public SingleSourceMapLiveData<List<Result<ProfileInfo>>, Result<ProfileInfo>> getProfileResult() {
//    public SingleSourceMapLiveData<Resource<List<Result<ProfileInfo>>>, Resource<Result<ProfileInfo>>> getProfileResult() {
        return profileResult;
    }

    public void getProfile() {
        profileResult.setSource(userTask.getProfile());
    }


    public SingleSourceMapLiveData<List<Result<Boolean>>, Result<Boolean>> getUpdateProfile() {
//    public SingleSourceMapLiveData<Resource<List<Result<ProfileInfo>>>, Resource<Result<ProfileInfo>>> getProfileResult() {
        return updateProfileResult;
    }

    public void updateProfile(int type, String key, String value) {
        updateProfileResult.setSource(userTask.updateProfile(type, key, value));
    }
}
