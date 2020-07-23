package com.example.nativetest.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.nativetest.BaseFragment;
import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.CommentRvAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


@SuppressLint("ValidFragment")
public class CommentFragment extends BaseFragment {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.rv_comment)
    RecyclerView mRvComment;
    private int position = 0;

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
        mRvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> list = new ArrayList<>();
        for (int i = 0;i<20;i++) {
            list.add("");
        }
        CommentRvAdapter commentRvAdapter = new CommentRvAdapter(getContext(), list);
        mRvComment.setAdapter(commentRvAdapter);
    }
}