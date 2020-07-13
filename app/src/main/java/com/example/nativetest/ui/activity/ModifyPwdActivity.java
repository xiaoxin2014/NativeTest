package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyPwdActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_old_pwd)
    EditText mEtOldPwd;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText mEtConfirmPwd;
    @BindView(R.id.tv_forget)
    TextView mTvForget;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    private TextView mTvSubmit;
    private boolean oldPass,newPass,confirmPass;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }


    @Override
    protected void initView() {
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setOnClickListener(v -> {
            if(mEtConfirmPwd.getText().toString().trim().equals(mEtPwd.getText().toString().trim())) {
                finish();
            }else {
                mTvTips.setVisibility(View.VISIBLE);
            }
        });
        initEt();
    }

    private void initEt() {
        mEtOldPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = s.toString().trim();
                oldPass = !TextUtils.isEmpty(pwd);
                mTvSubmit.setEnabled(isPass());
            }
        });

        mEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = s.toString().trim();
                newPass = !TextUtils.isEmpty(pwd);
                mTvSubmit.setEnabled(isPass());
            }
        });

        mEtConfirmPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = s.toString().trim();
                confirmPass = !TextUtils.isEmpty(pwd);
                mTvSubmit.setEnabled(isPass());
            }
        });
    }

    private boolean isPass(){
        return oldPass&&newPass&&confirmPass;
    }
}
