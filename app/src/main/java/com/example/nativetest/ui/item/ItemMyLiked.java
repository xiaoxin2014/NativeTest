package com.example.nativetest.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseItemView;

public class ItemMyLiked extends BaseItemView {
    public ItemMyLiked(Context context) {
        super(context);
    }

    public ItemMyLiked(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_my_liked;
    }
}
