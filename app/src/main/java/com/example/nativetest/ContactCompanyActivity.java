package com.example.nativetest;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.example.nativetest.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactCompanyActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_company;
    }


    @OnClick(R.id.tv_copy)
    public void onViewClicked() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(getString(R.string.company_email));
        ToastUtils.showToast(R.string.copy_success);
    }
}
