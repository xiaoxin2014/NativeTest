package com.example.nativetest.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.event.SelectAtEvent;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.ui.adapter.BaseItemView;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;
import io.rong.imkit.widget.AsyncImageView;

public class ItemFollow extends BaseItemView {
    @BindView(R.id.rc_left)
    AsyncImageView mRcLeft;
    @BindView(R.id.tv_name)
    TextView mTvName;
    private FollowBean bean;

    public ItemFollow(Context context) {
        super(context);
    }

    public ItemFollow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_follow;
    }


    public void bindData(FollowBean bean) {
        this.bean = bean;
        mTvName.setText(bean.getName());
    }

    @OnClick(R.id.ll_container)
    public void onViewClicked() {
        EventBus.getDefault().post(new SelectAtEvent(bean));
    }
}
