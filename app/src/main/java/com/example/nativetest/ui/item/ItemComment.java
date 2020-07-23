package com.example.nativetest.ui.item;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;

import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseItemView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;

public class ItemComment extends BaseItemView {

    @BindView(R.id.iv_left)
    AppCompatImageView mIvLeft;
    @BindView(R.id.tv_content)
    AppCompatTextView mTvContent;
    @BindView(R.id.iv_right)
    AppCompatImageView mIvRight;

    public ItemComment(Context context) {
        super(context);
    }

    public ItemComment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_comment;
    }

    public void bindData() {

        //文案效果1  多色效果  可以一个标签标签使用
        Spanned strA = Html.fromHtml("<font color=#65666C>" + "Frank Oliver评论了你的动态：" + "</font>"+"<font color=#0A0A0B>" + "你的照片风格很有个性"+"<font color=#65666C>" + "4-10");
        mTvContent.setText(strA);
    }
}
