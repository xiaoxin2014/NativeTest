package com.example.nativetest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.R;
import com.example.nativetest.db.model.ProfileInfo;
import com.example.nativetest.event.CitySelectEvent;
import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.Status;
import com.example.nativetest.utils.ToastUtils;
import com.example.nativetest.viewmodel.UserInfoViewModel;
import com.example.nativetest.widget.SettingItemView;
import com.example.nativetest.widget.dialog.SelectGenderBottomDialog;
import com.example.nativetest.widget.dialog.SelectPictureBottomDialog;
import com.example.nativetest.widget.wheel.date.DatePickerDialogFragment;

import java.text.SimpleDateFormat;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

public class SettingPersonInfoActivity extends BaseActivity {

    public static final int TYPE_NICKNAME = 0;
    public static final int TYPE_SCHOOL = 1;
    public static final int TYPE_EMAIL = 2;

    @BindView(R.id.siv_img)
    SettingItemView mSivImg;
    @BindView(R.id.siv_nickname)
    SettingItemView mSivNickname;
    @BindView(R.id.siv_gender)
    SettingItemView mSivGender;
    @BindView(R.id.siv_city)
    SettingItemView mSivCity;
    @BindView(R.id.siv_own)
    SettingItemView mSivOwn;
    @BindView(R.id.siv_school)
    SettingItemView mSivSchool;
    @BindView(R.id.siv_age)
    SettingItemView mSivAge;
    private UserInfoViewModel mUserInfoViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_person_info;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initViewModel();
    }


    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getProfileResult().observe(this, new Observer<Resource<ProfileInfo>>() {
            @Override
            public void onChanged(Resource<ProfileInfo> resource) {
                if (resource.status == Status.SUCCESS) {
                    dismissLoadingDialog(new Runnable() {
                        @Override
                        public void run() {
                            ProfileUtils.sProfileInfo = resource.data;
                            refreshUI();
                            showToast("获取用户信息成功");
                        }
                    });

                } else if (resource.status == Status.LOADING) {
                    showLoadingDialog("");
                } else {
                    dismissLoadingDialog(new Runnable() {
                        @Override
                        public void run() {
                            showToast(resource.message);
                        }
                    });
                }
            }
        });
//        {
//            if (profileInfoResult.RsCode == 3){
//                ProfileUtils.sProfileInfo = profileInfoResult.RsData;
//                //刷新界面
//                refreshUI();
//            }
//        });

        mUserInfoViewModel.getUpdateProfile().observe(this,profileInfoResult -> {
            if (profileInfoResult.RsCode == 3){
                //刷新界面
                ToastUtils.showToast("刷新界面");
                refreshUI();

            }
        });

        mUserInfoViewModel.getProfile();

    }

    private void refreshUI() {
        ProfileInfo profileInfo = ProfileUtils.sProfileInfo;
        if(profileInfo==null){return;}
        mSivNickname.setValue(profileInfo.getHead().getName());
        mSivGender.setValue(profileInfo.getHead().isGender()?R.string.man:R.string.women);
        mSivCity.setValue(profileInfo.getLocation());
        mSivOwn.setValue(profileInfo.getBio());
        mSivSchool.setValue(profileInfo.getSchool());
        mSivAge.setValue(profileInfo.getDOB());
    }

    @OnClick({R.id.siv_img, R.id.siv_nickname, R.id.siv_gender, R.id.siv_city, R.id.siv_own, R.id.siv_school, R.id.siv_age})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_img:
                showSelectPictureDialog();
                break;
            case R.id.siv_nickname:
                Bundle bundleNickname = new Bundle();
                bundleNickname.putString("nickname",mSivNickname.getValue());
                bundleNickname.putInt("type",TYPE_NICKNAME);
                readyGo(ModifyNicknameActivity.class,bundleNickname);
                break;
            case R.id.siv_gender:
                showSelectGenderDialog();
                break;
            case R.id.siv_city:
                readyGo(SelectCityActivity1.class);
                break;
            case R.id.siv_own:
                Bundle bundlePersonal = new Bundle();
                bundlePersonal.putString("content",mSivOwn.getValue());
                readyGo(PersonalProfileActivity.class,bundlePersonal);
                break;
            case R.id.siv_school:
                Bundle bundleSchool = new Bundle();
                bundleSchool.putString("school",mSivSchool.getValue());
                bundleSchool.putInt("type",TYPE_SCHOOL);
                readyGo(ModifyNicknameActivity.class,bundleSchool);
                break;
            case R.id.siv_age:
                showDateDialog();
                break;
        }
    }

    private void showDateDialog() {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
            @Override
            public void onDateChoose(int year, int month, int day) {
                ToastUtils.showToast(year + "-" + month +"-" + day);
            }
        });
        datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
    }

    private void showSelectGenderDialog() {
        SelectGenderBottomDialog.Builder builder = new SelectGenderBottomDialog.Builder();
        builder.setOnSelectPictureListener(isMan -> {
            mUserInfoViewModel.updateProfile(3,"Gender",isMan);
        });
        SelectGenderBottomDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager(), "select_picture_dialog");
    }


    /**
     * 选择图片的 dialog
     */
    private void showSelectPictureDialog() {
        SelectPictureBottomDialog.Builder builder = new SelectPictureBottomDialog.Builder();
        builder.setOnSelectPictureListener(uri -> {
            //上传图片
            uploadPortrait(uri);
        });
        SelectPictureBottomDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager(), "select_picture_dialog");
    }


    /**
     * 上传头像
     *
     * @param uri
     */
    private void uploadPortrait(Uri uri) {
//        if (userInfoViewModel != null) {
//            userInfoViewModel.uploadPortrait(uri);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(CitySelectEvent event) {
        mSivCity.setValue(event.getCity());
        mUserInfoViewModel.updateProfile(2,"Location",event.getCityCode());
    }
}
