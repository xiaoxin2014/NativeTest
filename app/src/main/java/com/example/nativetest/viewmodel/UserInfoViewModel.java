package com.example.nativetest.viewmodel;

import android.app.Application;

import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.model.CommentBean;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.sp.ProfileCache;
import com.example.nativetest.task.UserTask;
import com.example.nativetest.utils.SingleSourceLiveData;
import com.example.nativetest.utils.SingleSourceMapLiveData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserInfoViewModel extends AndroidViewModel {

    private UserTask userTask;
    private ProfileCache profileCache;

    private SingleSourceLiveData<Resource<ProfileInfo>> profileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> updateProfileResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<Boolean>> hasSetPasswordResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<Boolean>> logoutResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Resource<String>> uploadResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<CommentBean>>> commentResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<FollowBean>>> followResult =  new SingleSourceLiveData<>();
    private SingleSourceLiveData<Result<List<ProfileHeadInfo>>> followerListResult =  new SingleSourceLiveData<>();

    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userTask = new UserTask(application);
        profileCache = new ProfileCache(application);
    }

    public ProfileCache getProfileCache(){
        return profileCache;
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

    public void updateProfile(int type, String key, Object value) {
        updateProfileResult.setSource(userTask.updateProfile(type, key, value));
    }

    public SingleSourceLiveData<Result<Boolean>> getHasSetPasswordResult() {
        return hasSetPasswordResult;
    }

    public void hasSetPassword() {
        hasSetPasswordResult.setSource(userTask.hasSetPassword());
    }

    public SingleSourceLiveData<Resource<Boolean>> getLogoutResult() {
        return logoutResult;
    }

    public void logout() {
        logoutResult.setSource(userTask.logout());
    }



    public void uploadAvatar(String imgPath ){
        uploadResult.setSource(userTask.upload(imgPath));
    }

    public SingleSourceLiveData<Resource<String>> getUploadResult(){
        return uploadResult;
    }

    public void getCommentList(int skip,int take){
        commentResult.setSource(userTask.getCommentList(skip,take));
    }

    public SingleSourceLiveData<Result<List<CommentBean>>> getCommentListResult() {
        return commentResult;
    }

    public void getFollowList(int skip,int take){
        followResult.setSource(userTask.getFollowList(skip,take));
    }

    public SingleSourceLiveData<Result<List<FollowBean>>> getFollowListResult() {
        return followResult;
    }


    public void getFollowerList(int skip,int take){
        followerListResult.setSource(userTask.getFollowerList(skip,take));
    }

    public SingleSourceLiveData<Result<List<ProfileHeadInfo>>> getFollowerListResult() {
        return followerListResult;
    }





}
