package com.example.nativetest.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.nativetest.AreaBean;
import com.example.nativetest.ui.item.ItemSelectCity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SelectCityRvAdpter extends BaseRvAdapter<AreaBean.ProvinceBean.CityBean>{
    public SelectCityRvAdpter(Context context, List<AreaBean.ProvinceBean.CityBean> datas) {
        super(context, datas);
    }

    @Override
    protected View getItemView(int viewType) {
        return new ItemSelectCity(mContext);
    }

    @Override
    protected void bindData(RecyclerView.ViewHolder holder, int position) {
        ((ItemSelectCity) holder.itemView).bindData(mDatas.get(position));
    }
}
