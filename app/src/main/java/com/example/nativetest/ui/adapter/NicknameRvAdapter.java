package com.example.nativetest.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.nativetest.R;
import com.example.nativetest.widget.SelectNicknameItemView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NicknameRvAdapter extends BaseRvAdapter<NicknameRvAdapter.NickNameBean> {


    public NicknameRvAdapter(Context context, List<NickNameBean> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new SelectNicknameItemView(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((SelectNicknameItemView) holder.itemView).bindData(mDatas.get(position));
    }

    public static class NickNameBean{
        public int color;
        public int src;
        public int colorname;
        public int avatarSrc;
    }
}
