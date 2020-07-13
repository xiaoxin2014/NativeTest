package com.example.nativetest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.nativetest.R;
import com.example.nativetest.event.CitySelectEvent;
import com.example.nativetest.utils.ToastUtils;
import com.example.nativetest.widget.SettingItemView;
import com.example.nativetest.widget.dialog.SelectGenderBottomDialog;
import com.example.nativetest.widget.dialog.SelectPictureBottomDialog;
import com.example.nativetest.widget.wheel.date.DatePickerDialogFragment;

import java.text.SimpleDateFormat;

import androidx.annotation.Nullable;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_person_info;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
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
            ToastUtils.showToast(""+isMan);
        });
        SelectGenderBottomDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager(), "select_picture_dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
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
    }
}
