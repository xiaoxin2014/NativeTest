package com.example.nativetest;

import android.view.View;

import com.example.nativetest.ui.activity.BaseActivity;

import butterknife.OnClick;

public class VipActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }



    @OnClick({R.id.tv_use})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use:
                readyGo(SelectNickNameColorActivity.class);
                break;
        }
    }
}
