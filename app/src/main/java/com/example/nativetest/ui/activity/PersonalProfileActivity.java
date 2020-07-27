package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.R;
import com.example.nativetest.event.RefreshProfileEvent;
import com.example.nativetest.viewmodel.UserInfoViewModel;
import com.example.nativetest.widget.TitleBar;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;

public class PersonalProfileActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.tv_length)
    AppCompatTextView mTvLength;
    @BindView(R.id.et_content)
    AppCompatEditText mEtContent;
    private TextView mTvSubmit;
    private UserInfoViewModel mUserInfoViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void initView() {
        initViewModel();
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setOnClickListener(v -> {
            mUserInfoViewModel.updateProfile(2,"Bio",mEtContent.getText().toString().trim());
        });
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                mTvLength.setText(String.valueOf(30 - content.length()));
                mTvSubmit.setEnabled(!TextUtils.isEmpty(content));
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String content = bundle.getString("content");
            mEtContent.setText(content);

        }
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getUpdateProfile().observe(this, profileInfoResult -> {
            if (profileInfoResult.RsCode == 3) {
                ProfileUtils.sProfileInfo.setBio(mEtContent.getText().toString().trim());
                mUserInfoViewModel.getProfileCache().saveUserCache(ProfileUtils.sProfileInfo);
                EventBus.getDefault().post(new RefreshProfileEvent());
                finish();
            }
        });
    }
}
