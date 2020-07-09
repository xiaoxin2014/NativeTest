package com.example.nativetest;

import android.view.View;

import com.example.nativetest.widget.SettingItemView;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.siv_info)
    SettingItemView mSivInfo;
    @BindView(R.id.siv_notification)
    SettingItemView mSivNotification;
    @BindView(R.id.siv_hobby)
    SettingItemView mSivHobby;
    @BindView(R.id.siv_contact)
    SettingItemView mSivContact;
    @BindView(R.id.siv_modify_pwd)
    SettingItemView mSivModifyPwd;
    @BindView(R.id.siv_company)
    SettingItemView mSivCompany;
    @BindView(R.id.siv_clear)
    SettingItemView mSivClear;
    @BindView(R.id.siv_logout)
    SettingItemView mSivLogout;

    boolean isFirst = true;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }


    @OnClick({R.id.siv_info, R.id.siv_notification, R.id.siv_hobby, R.id.siv_contact, R.id.siv_modify_pwd, R.id.siv_company, R.id.siv_clear, R.id.siv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.siv_info:
                readyGo(SettingPersonInfoActivity.class);
                break;
            case R.id.siv_notification:
                readyGo(SettingNotificationActivity.class);
                break;
            case R.id.siv_hobby:
                break;
            case R.id.siv_contact:
                break;
            case R.id.siv_modify_pwd:
                if(isFirst) {
                    readyGo(SettingPwdActivity.class);
                    isFirst = false;
                }else{
                    readyGo(ModifyPwdActivity.class);
                }

                break;
            case R.id.siv_company:
                readyGo(ContactCompanyActivity.class);
                break;
            case R.id.siv_clear:
                break;
            case R.id.siv_logout:
                break;
        }
    }
}
