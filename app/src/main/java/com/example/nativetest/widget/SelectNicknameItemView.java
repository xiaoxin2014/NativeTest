package com.example.nativetest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseItemView;
import com.example.nativetest.ui.adapter.NicknameRvAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.OnClick;

public class SelectNicknameItemView extends BaseItemView {

    @BindView(R.id.iv_avatar)
    AppCompatImageView mIvAvatar;
    @BindView(R.id.tv_name)
    AppCompatTextView mTvName;
    @BindView(R.id.tv_color)
    AppCompatTextView mTvColor;
    @BindView(R.id.rl_left)
    RelativeLayout mRlLeft;
    @BindView(R.id.iv_select)
    AppCompatImageView mIvSelect;
    @BindView(R.id.rl_right)
    RelativeLayout mRlRight;
    @BindView(R.id.cv_container)
    CardView mCvContainer;

    public SelectNicknameItemView(Context context) {
        super(context);
    }

    public SelectNicknameItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_select_nickname_color;
    }

    @OnClick(R.id.cv_container)
    public void onViewClicked() {
        mIvSelect.setSelected(!mIvSelect.isSelected());
    }

    public void bindData(NicknameRvAdapter.NickNameBean nickNameBean) {
        mRlLeft.setBackgroundColor(getResources().getColor(nickNameBean.color));
        mRlRight.setBackground(getResources().getDrawable(nickNameBean.src));
        mTvColor.setText(nickNameBean.colorname);
        mTvName.setTextColor(getResources().getColor(nickNameBean.color));
//        mIvAvatar.setBackground(getResources().getDrawable(nickNameBean.avatarSrc));
    }
}
