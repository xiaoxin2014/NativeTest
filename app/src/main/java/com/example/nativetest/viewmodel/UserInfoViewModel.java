package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceLiveData;
import com.example.nativetest.utils.SingleSourceMapLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserInfoViewModel extends AndroidViewModel {

    private UserTask userTask;

    private SingleSourceLiveData<Resource<ProfileInfo>> profileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> updateProfileResult =  new SingleSourceLiveData<>();


    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userTask = new UserTask(application);
//        requestUserInfo(imManager.getCurrentId());
    }

    public SingleSourceLiveData<Resource<ProfileInfo>> getProfileResult() {
        return profileResult;
    }

    public void getProfile() {
        profileResult.setSource(userTask.getProfile());
    }


    public SingleSourceLiveData<Result<Boolean>> getUpdateProfile() {
        return updateProfileResult;
    }

    public void updateProfile(int type, String key, String value) {
        updateProfileResult.setSource(userTask.updateProfile(type, key, value));
    }
}
