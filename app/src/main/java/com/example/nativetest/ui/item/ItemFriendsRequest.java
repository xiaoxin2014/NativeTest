package com.example.nativetest.ui.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseItemView;

public class ItemFriendsRequest extends BaseItemView {

    public ItemFriendsRequest(Context context) {
        super(context);
    }

    public ItemFriendsRequest(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_friends_request;
    }
}
