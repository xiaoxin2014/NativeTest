package com.example.nativetest.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.nativetest.ProfileUtils;
import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.event.RefreshProfileEvent;
import com.example.nativetest.event.SelectAtEvent;
import com.example.nativetest.event.SelectCompleteEvent;
import com.example.nativetest.event.ShowMoreEvent;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.ui.adapter.FollowRvAdapter;
import com.example.nativetest.viewmodel.UserInfoViewModel;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

public class SelectAtPersonActivity extends BaseActivity {
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_complete)
    TextView mTvComplete;
    @BindView(R.id.fl)
    TagFlowLayout mFl;
    @BindView(R.id.rv_follow)
    RecyclerView mRvFollow;
    private TagAdapter<FollowBean> mTagAdapter;
    private List<FollowBean> mList;
    private UserInfoViewModel mUserInfoViewModel;
    private FollowRvAdapter mFollowRvAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_at_person;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mList = new ArrayList<>();
        mTagAdapter = new TagAdapter<FollowBean>(mList) {
            @Override
            public View getView(FlowLayout parent, int position, FollowBean bean) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flowlayout_tv,
                        mFl, false);
                tv.setText("@"+bean.getName());
                return tv;
            }
        };
        mFl.setAdapter(mTagAdapter);


        mRvFollow.setLayoutManager(new LinearLayoutManager(mContext));
        mFollowRvAdapter = new FollowRvAdapter(mContext, new ArrayList<>());
        mRvFollow.setAdapter(mFollowRvAdapter);

        initViewModel();

    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getFollowListResult().observe(this, result -> {
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                List<FollowBean> rsData = result.getRsData();
                mFollowRvAdapter.setDatas(rsData);
            }
        });

        mUserInfoViewModel.getFollowList(NetConstant.SKIP,NetConstant.TAKE);
    }


    @OnClick({R.id.tv_cancel, R.id.tv_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_complete:
                complete();
                break;
        }
    }

    private void complete() {
        String result = "";
        for (FollowBean bean : mList){
            result = result.concat("@").concat(bean.getName()).concat(" ");
        }
        EventBus.getDefault().post(new SelectCompleteEvent(result));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(SelectAtEvent event) {
        if(!mList.contains(event.bean)) {
            mList.add(event.bean);
            mTagAdapter.notifyDataChanged();
            mTvComplete.setText("完成" + mList.size());
        }
    }




}
