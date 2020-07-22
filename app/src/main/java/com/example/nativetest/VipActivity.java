package com.example.nativetest;

import android.os.Bundle;
import android.view.View;

import com.example.nativetest.ui.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VipActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }



    @OnClick({R.id.tv_use, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use:
                readyGo(SelectNickNameColorActivity.class);
                break;
            case R.id.tv_share:
                break;
        }
    }
}
