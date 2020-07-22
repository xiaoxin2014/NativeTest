package com.example.nativetest;

import android.os.Bundle;

import com.example.nativetest.ui.activity.BaseActivity;
import com.example.nativetest.ui.adapter.NicknameRvAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectNickNameColorActivity extends BaseActivity {
    @BindView(R.id.rv_nickname)
    RecyclerView mRvNickname;

    private static final int[] colors = {
            R.color.nickname1,
            R.color.nickname2,
            R.color.nickname3,
            R.color.nickname4,
            R.color.nickname5,
            R.color.nickname6,
            R.color.nickname7
    };

    private static final int[] rlRes = {
            R.drawable.img_nickname_rl_1,
            R.drawable.img_nickname_rl_2,
            R.drawable.img_nickname_rl_3,
            R.drawable.img_nickname_rl_4,
            R.drawable.img_nickname_rl_5,
            R.drawable.img_nickname_rl_6,
            R.drawable.img_nickname_rl_7
    };

    private static final int[] avatatRes = {
            R.drawable.img_nickname_rl_1,
            R.drawable.img_nickname_rl_2,
            R.drawable.img_nickname_rl_3,
            R.drawable.img_nickname_rl_4,
            R.drawable.img_nickname_rl_5,
            R.drawable.img_nickname_rl_6,
            R.drawable.img_nickname_rl_7
    };

    private static final int[] strings = {
            R.string.nickname1,
            R.string.nickname2,
            R.string.nickname3,
            R.string.nickname4,
            R.string.nickname5,
            R.string.nickname6,
            R.string.nickname7
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_nickname_color;
    }

    @Override
    protected void initView() {
        mRvNickname.setLayoutManager(new LinearLayoutManager(mContext));
        List<NicknameRvAdapter.NickNameBean> list = new ArrayList<>();
        for(int i = 0 ; i < colors.length;i++){
            NicknameRvAdapter.NickNameBean nickNameBean = new NicknameRvAdapter.NickNameBean();
            nickNameBean.color = colors[i];
            nickNameBean.colorname = strings[i];
            nickNameBean.src = rlRes[i];
            list.add(nickNameBean);
        }
        NicknameRvAdapter nicknameRvAdapter = new NicknameRvAdapter(mContext, list);
        mRvNickname.setAdapter(nicknameRvAdapter);

    }
}
