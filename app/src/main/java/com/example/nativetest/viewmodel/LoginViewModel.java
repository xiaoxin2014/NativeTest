package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.model.Resource;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceLiveData;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {
    private UserTask userTask;
    private SingleSourceLiveData<Resource<String>> loginResult = new SingleSourceLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);

        userTask = new UserTask(application);
    }

    public void login(String region, String phone, String pwd){
        loginResult.setSource(userTask.login(region, phone, pwd));
        //TODO 示例代码，当需要转换类型时参考
        //loginResultNoResource.setSource(userTask.login(region, phone, pwd));
    }

    public LiveData<Resource<String>> getLoginResult(){
        return loginResult;
    }
}
