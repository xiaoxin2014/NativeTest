package com.example.nativetest.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.nativetest.BaseFragment;
import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.event.ItemCommentEvent;
import com.example.nativetest.event.SelectCompleteEvent;
import com.example.nativetest.model.CommentBean;
import com.example.nativetest.ui.activity.SelectAtPersonActivity;
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

import static android.content.Context.INPUT_METHOD_SERVICE;


@SuppressLint("ValidFragment")
public class CommentFragment extends BaseFragment {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_comment)
    RecyclerView mRvComment;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.et_input)
    EditText mEtInput;
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

        List<CommentBean> commentBeans = new ArrayList<>();
        commentBeans.add(new CommentBean());
        commentBeans.add(new CommentBean());
        commentBeans.add(new CommentBean());
        commentBeans.add(new CommentBean());
        mCommentRvAdapter = new CommentRvAdapter(getContext(),commentBeans);
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

        mEtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SLog.e("input",s+"start = " +start+"before = " +before+"count = " +count);
                if(s.toString().equals("@")&&before==0){
                    readyGo(SelectAtPersonActivity.class);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onInitViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getCommentListResult().observe(this, result -> {
//            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
//                List<CommentBean> rsData = result.RsData;
//                mCommentRvAdapter.setDatas(rsData);
//            }
        });
        mUserInfoViewModel.getCommentList(NetConstant.SKIP, NetConstant.TAKE);
    }

    public void onEventMainThread(SelectCompleteEvent event) {
        SLog.e("niko", event.name);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void onEventMainThread(ItemCommentEvent event) {
        mEtInput.setVisibility(View.VISIBLE);
        showInput();
    }

    public void showInput() {
        mEtInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEtInput, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}