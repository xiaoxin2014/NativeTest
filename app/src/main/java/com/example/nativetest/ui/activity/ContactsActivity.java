package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.widget.SideBar;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ll_focus)
    LinearLayout mLlFocus;
    @BindView(R.id.rv_contacts)
    RecyclerView mRvContacts;
    @BindView(R.id.tv_group_dialog)
    TextView mTvGroupDialog;
    @BindView(R.id.sv_sidebar)
    SideBar mSvSidebar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts;
    }



    @OnClick(R.id.ll_focus)
    public void onViewClicked() {
    }
}
