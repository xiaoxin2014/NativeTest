package com.example.nativetest.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nativetest.BaseFragment;
import com.example.nativetest.R;
import com.example.nativetest.SealApp;
import com.example.nativetest.model.Result;
import com.example.nativetest.model.sc.TokenBean;
import com.example.nativetest.net.ScInterceptor;
import com.example.nativetest.sp.UserConfigCache;
import com.example.nativetest.viewmodel.LoginViewModel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment {
    @BindView(R.id.btn_access_token)
    Button mBtnAccessToken;
    @BindView(R.id.btn_send_sms)
    Button mBtnSendSms;
    @BindView(R.id.btn_vertify_sms)
    Button mBtnVertifySms;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_access_token)
    TextView mTvAccessToken;
    @BindView(R.id.tv_send_sms)
    TextView mTvSendSms;
    @BindView(R.id.tv_vertify_sms)
    TextView mTvVertifySms;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.btn_user_token)
    Button mBtnUserToken;
    @BindView(R.id.tv_user_token)
    TextView mTvUserToken;
    @BindView(R.id.tv_is_login)
    TextView mTvIsLogin;


    private LoginViewModel mLoginViewModel;
    private UserConfigCache mUserConfigCache;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState, Intent intent) {
        mUserConfigCache = new UserConfigCache(SealApp.getApplication());
//        if(!TextUtils.isEmpty(mUserConfigCache.getSpUserToken())){
//            mTvIsLogin.setText("已登陆");
//            mBtnAccessToken.setEnabled(false);
//            mBtnLogin.setEnabled(false);
//            mBtnSendSms.setEnabled(false);
//            mBtnUserToken.setEnabled(false);
//            mBtnVertifySms.setEnabled(false);
//            ScInterceptor.Authorization = "Bearer "+mUserConfigCache.getSpUserToken();
//        }
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        mLoginViewModel.getGetTokenResult().observe(this, new Observer<TokenBean>() {
            @Override
            public void onChanged(TokenBean tokenBean) {
                if (tokenBean != null && !TextUtils.isEmpty(tokenBean.getAccess_token())) {
                    showToast("成功");
                    mTvAccessToken.setText("成功");
                    ScInterceptor.Authorization = "Bearer "+tokenBean.getAccess_token();
                } else {
                    showToast("失败");
                    mTvAccessToken.setText("失败");

                }
            }
        });


        mLoginViewModel.getGetSmsResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.RsCode == 3) {
                    showToast("成功");
                    mTvSendSms.setText("成功");
                } else {
                    showToast("失败");
                    mTvSendSms.setText("失败");

                }
            }
        });

        mLoginViewModel.getVerifyResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.RsCode == 3) {
                    showToast("成功");
                    mTvVertifySms.setText("成功");

                } else {
                    showToast("失败");
                    mTvVertifySms.setText("失败");
                }
            }
        });

        mLoginViewModel.getGetUserTokenResult().observe(this, new Observer<TokenBean>() {
            @Override
            public void onChanged(TokenBean tokenBean) {
                if (tokenBean != null && !TextUtils.isEmpty(tokenBean.getAccess_token())) {
                    showToast("成功");
                    mTvUserToken.setText("成功");
                    ScInterceptor.Authorization = "Bearer "+tokenBean.getAccess_token();
                    mUserConfigCache.setSpUserToken("Bearer "+ScInterceptor.Authorization);
                } else {
                    showToast("失败");
                    mTvUserToken.setText("失败");

                }
            }
        });

//        mLoginViewModel.getProfileResult().observe(this,profileInfoResult -> {
//            if (profileInfoResult.RsCode == 3){
//                ProfileUtils.sProfileInfo = profileInfoResult.RsData;
//                mTvIsLogin.setText("登陆成功");
//            }
//        });


//        mLoginViewModel.getProfileResult().observe(this, new Observer<Resource<Result<ProfileInfo>>>() {
//            @Override
//            public void onChanged(Resource<Result<ProfileInfo>> resource) {
//                if (resource.status == Status.SUCCESS) {
//                    SLog.d("niko","SUCCESS");
//                    mTvIsLogin.setText(JSON.toJSONString(resource.data));
//                } else if (resource.status == Status.LOADING) {
//                    SLog.d("niko","LOADING");
//                } else {
//                    SLog.d("niko","FINISH");
//                }
//            }
//        });

//        mLoginViewModel.getUpdateProfile().observe(this,profileInfoResult -> {
//            if (profileInfoResult.RsCode == 3){
//                mTvIsLogin.setText("更新");
//            }
//        });

    }

    @OnClick({R.id.btn_access_token, R.id.btn_send_sms, R.id.btn_vertify_sms, R.id.btn_user_token, R.id.btn_login,R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_access_token:
                mLoginViewModel.getToken();
//                UserTask userTask = new UserTask(getActivity());
//                userTask.getAccessToken();
                break;
            case R.id.btn_send_sms:
                mLoginViewModel.getSms();
                break;
            case R.id.btn_vertify_sms:
                mLoginViewModel.verifySms();
                break;
            case R.id.btn_user_token:
                ScInterceptor.Authorization = "Basic ampBcHBBcGlDbGllbnQ6Q2lyY2xlMjAyMEBXb3JsZA==";
                mLoginViewModel.getUserToken();
                break;
            case R.id.btn_login:
//                mLoginViewModel.getUserInfo();
//                mLoginViewModel.getProfile();
                break;
            case R.id.btn_update:
//                mLoginViewModel.updateProfile(3,"Name","Nikooooooo");
                break;
        }
    }
}
