package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.example.nativetest.R;
import com.example.nativetest.widget.TitleBar;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyNicknameActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_nickname)
    AppCompatEditText mEtNickname;
    @BindView(R.id.tv_tips)
    AppCompatTextView mTvTips;
    @BindView(R.id.tv_error)
    AppCompatTextView mTvError;
    @BindView(R.id.tv_length)
    AppCompatTextView mTvLength;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            initEt();
            int type = bundle.getInt("type",0);
            switch (type){
                case 0:
                    String nickname = bundle.getString("nickname");
                    mEtNickname.setText(nickname);
                    mEtNickname.setHint(R.string.setting_your_nickname);
                    break;
                case 1:
                    String school = bundle.getString("school");
                    mEtNickname.setText(school);
                    mEtNickname.setHint(R.string.setting_your_school);
                    break;
                default:
                    String email = bundle.getString("email");
                    mEtNickname.setText(email);
                    mEtNickname.setHint(R.string.setting_your_email);
                    break;
            }
        }
    }

    private void initEt() {
        mEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                mTvLength.setText(String.valueOf(10-content.length()));
                mTitleBar.getTitleBarTvRight().setEnabled(!TextUtils.isEmpty(content));
            }
        });
    }

}
