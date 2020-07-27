package com.example.nativetest.ui.activity;

import android.os.Bundle;

import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.FriendsRequestRvAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsRequestListActivity extends BaseActivity {
    @BindView(R.id.rv_request)
    RecyclerView mRvRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends_request;
    }

    @Override
    protected void initView() {
        mRvRequest.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        FriendsRequestRvAdapter friendsRequestRvAdapter = new FriendsRequestRvAdapter(mContext,list);
        mRvRequest.setAdapter(friendsRequestRvAdapter);
    }
}
