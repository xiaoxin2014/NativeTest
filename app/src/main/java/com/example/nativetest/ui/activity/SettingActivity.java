package com.example.nativetest.ui.activity;

import android.view.View;

import com.example.nativetest.model.Resource;
import com.example.nativetest.model.Status;
import com.example.nativetest.R;
import com.example.nativetest.viewmodel.LoginViewModel;
import com.example.nativetest.widget.ClearCacheDialog;
import com.example.nativetest.widget.CommonDialog;
import com.example.nativetest.widget.SettingItemView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
    private LoginViewModel mLoginViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.getLoginResult().observe(this, new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> resource) {
                if (resource.status == Status.SUCCESS) {
                    dismissLoadingDialog(new Runnable() {
                        @Override
                        public void run() {
                            showToast("Success");
//                            toMain(resource.data);
                        }
                    });

                } else if (resource.status == Status.LOADING) {
                    showLoadingDialog("loading");
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
                readyGo(SelectCityActivity1.class);
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
                showClearDialog();
                break;
            case R.id.siv_logout:
                mLoginViewModel.login("","13305938755","qq123456");
                break;
        }
    }

//    private void cityTest() {
//        String json = FileUtils.getJson(this, "data.json");
//        List<AreaBean> areaBeans = JSONObject.parseArray(json, AreaBean.class);
//        Log.e("areaBeans","areaBeans.size()="+areaBeans.size());
//    }

    private void showClearDialog() {
        ClearCacheDialog.Builder builder = new ClearCacheDialog.Builder();
        builder.setContentMessage(getString(R.string.seal_set_account_dialog_clear_cache_message));
        CommonDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager(), "clear_cache");
    }
}
