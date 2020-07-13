package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.widget.TitleBar;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalProfileActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.tv_length)
    AppCompatTextView mTvLength;
    @BindView(R.id.et_content)
    AppCompatEditText mEtContent;
    private TextView mTvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void initView() {
        mTvSubmit = mTitleBar.getTitleBarTvRight();
        mTvSubmit.setOnClickListener(v -> {
            finish();
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
}
