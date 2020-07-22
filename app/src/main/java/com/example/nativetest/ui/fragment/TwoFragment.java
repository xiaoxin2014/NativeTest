package com.example.nativetest.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nativetest.BaseFragment;
import com.example.nativetest.R;
import com.example.nativetest.common.LogTag;
import com.example.nativetest.model.Status;
import com.example.nativetest.utils.log.SLog;
import com.example.nativetest.viewmodel.LoginViewModel;

import androidx.lifecycle.ViewModelProviders;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class TwoFragment extends BaseFragment {
    private LoginViewModel mLoginViewModel;
    private String mToken;
    private String mUserId;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void onInitView(Bundle savedInstanceState, Intent intent) {
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mLoginViewModel.getGetImTokenResult().observe(this, resource -> {
            if (resource.status == Status.SUCCESS) {
                dismissLoadingDialog(new Runnable() {
                    @Override
                    public void run() {
                        showToast("获取成功");
                        mToken = resource.data;
//                        mUserId = resource.data.getUserId();
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
        });
    }

    @OnClick({R.id.btn_get_token, R.id.btn_init_token})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_token:
                mLoginViewModel.getImToken();
                break;
            case R.id.btn_init_token:
                connextIM();
                break;
        }
    }

    private void connextIM() {
        RongIM.connect(mToken, 10, new RongIMClient.ConnectCallback() {
            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
//                if (callback != null) {
//                    callback.onSuccess(RongIMClient.getInstance().getCurrentUserId());
//                }
                SLog.e(LogTag.IM, "connect databaseOpenStatus ");

            }

            @Override
            public void onSuccess(String s) {
                // 连接 IM 成功后，初始化数据库
//                DbManager.getInstance(context).openDb(s);
                SLog.e(LogTag.IM, "connect success - code:" + s);

            }

            public void onError(RongIMClient.ConnectionErrorCode errorCode) {
                SLog.e(LogTag.IM, "connect error - code:" + errorCode.getValue());
//                if (errorCode == RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT) {
//                    getToken(new ResultCallback<LoginResult>() {
//                        @Override
//                        public void onSuccess(LoginResult loginResult) {
//                            connectIM(loginResult.token, timeOut, callback);
//                        }
//34001 30007
//                        @Override
//                        public void onFail(int errorCode) {
//                            callback.onFail(errorCode);
//                        }
//                    });;
//                } else {
//                    if (callback != null) {
//                        callback.onFail(ErrorCode.IM_ERROR.getCode());
//                    } else {
//                        // do nothing
//                    }
//                }
            }
        });
    }
}
