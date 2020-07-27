package com.example.nativetest.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.nativetest.BaseFragment;
import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.event.SelectCompleteEvent;
import com.example.nativetest.model.CommentBean;
import com.example.nativetest.ui.adapter.CommentRvAdapter;
import com.example.nativetest.utils.log.SLog;
import com.example.nativetest.viewmodel.UserInfoViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.rong.eventbus.EventBus;


@SuppressLint("ValidFragment")
public class CommentFragment extends BaseFragment {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_comment)
    RecyclerView mRvComment;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int position = 0;
    private UserInfoViewModel mUserInfoViewModel;
    private CommentRvAdapter mCommentRvAdapter;

    public static CommentFragment getInstance(int position) {
        CommentFragment sf = new CommentFragment();
        sf.position = position;
        return sf;
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_comment;
    }

    @Override
    public void onCreateView(View view) {
        super.onCreateView(view);
        view.setTag(position);
    }

    @Override
    protected void onInitView(Bundle savedInstanceState, Intent intent) {
        EventBus.getDefault().register(this);
        mRvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        mCommentRvAdapter = new CommentRvAdapter(getContext(), new ArrayList<>());
        mRvComment.setAdapter(mCommentRvAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void onInitViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getCommentListResult().observe(this, result -> {
            if(result.RsCode == NetConstant.REQUEST_SUCCESS_CODE){
                List<CommentBean> rsData = result.RsData;
                mCommentRvAdapter.setDatas(rsData);
            }
        } );
        mUserInfoViewModel.getCommentList(NetConstant.SKIP,NetConstant.TAKE);
    }

    public void onEventMainThread(SelectCompleteEvent event) {
        SLog.e("niko",event.name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}