package com.example.nativetest.ui.item;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.nativetest.R;
import com.example.nativetest.event.ItemCommentEvent;
import com.example.nativetest.ui.activity.SelectAtPersonActivity;
import com.example.nativetest.ui.adapter.BaseItemView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

import static android.content.Context.INPUT_METHOD_SERVICE;

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

    @Override
    protected void initView() {
//        mEtInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(String.valueOf(s).equals("@")){
//                    readyGo(SelectAtPersonActivity.class);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
    }

    public void bindData() {
        //文案效果1  多色效果  可以一个标签标签使用
        Spanned strA = Html.fromHtml("<font color=#65666C>" + "Frank Oliver评论了你的动态：" + "</font>" + "<font color=#0A0A0B>" + "你的照片风格很有个性" + "<font color=#65666C>" + "4-10");
        mTvContent.setText(strA);
    }

    @OnClick(R.id.ll_container)
    public void onViewClicked() {
        EventBus.getDefault().post(new ItemCommentEvent());
    }


}
