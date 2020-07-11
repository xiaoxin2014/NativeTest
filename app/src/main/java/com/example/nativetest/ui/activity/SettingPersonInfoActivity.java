package com.example.nativetest.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.nativetest.R;
import com.example.nativetest.event.CitySelectEvent;
import com.example.nativetest.widget.SettingItemView;
import com.example.nativetest.widget.dialog.SelectPictureBottomDialog;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

public class SettingPersonInfoActivity extends BaseActivity {

    public static final int TYPE_NICKNAME = 0;
    public static final int TYPE_SCHOOL = 1;

    @BindView(R.id.siv_img)
    SettingItemView mSivImg;
    @BindView(R.id.siv_nickname)
    SettingItemView mSivNickname;
    @BindView(R.id.siv_sex)
    SettingItemView mSivSex;
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

    @OnClick({R.id.siv_img, R.id.siv_nickname, R.id.siv_sex, R.id.siv_city, R.id.siv_own, R.id.siv_school, R.id.siv_age})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_img:
                showSelectPictureDialog();
                break;
            case R.id.siv_nickname:
                Bundle bundleNickname = new Bundle();
                bundleNickname.putString("nickname",mSivNickname.getValue());
                bundleNickname.putInt("type",TYPE_NICKNAME);
                readyGoForResult(ModifyNicknameActivity.class,bundleNickname,TYPE_NICKNAME);
                break;
            case R.id.siv_sex:
                break;
            case R.id.siv_city:
                readyGo(SelectCityActivity1.class);
                break;
            case R.id.siv_own:
                break;
            case R.id.siv_school:
                Bundle bundleSchool = new Bundle();
                bundleSchool.putString("school",mSivSchool.getValue());
                bundleSchool.putInt("type",TYPE_SCHOOL);
                readyGoForResult(ModifyNicknameActivity.class,bundleSchool,TYPE_SCHOOL);
                break;
            case R.id.siv_age:
                break;
        }
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
        builder.setOnSelectPictureListener(new SelectPictureBottomDialog.OnSelectPictureListener() {
            @Override
            public void onSelectPicture(Uri uri) {
                //上传图片
                uploadPortrait(uri);
            }
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
