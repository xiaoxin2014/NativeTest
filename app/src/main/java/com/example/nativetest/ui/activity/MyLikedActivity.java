package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.MyLikedRvAdapter;
import com.example.nativetest.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLikedActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_my_liked)
    RecyclerView mRvMyLiked;
    private TextView mTitleBarTvRight;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_liked;
    }

    @Override
    protected void initView() {
        mTitleBarTvRight = mTitleBar.getTitleBarTvRight();
        mTitleBarTvRight.setText("发送");


        mRvMyLiked.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        mRvMyLiked.setAdapter(new MyLikedRvAdapter(mContext,list));
    }
}
