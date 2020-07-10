package com.example.nativetest.ui.activity;

import android.os.Bundle;

import com.example.nativetest.AreaBean;
import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseRvAdapter;
import com.example.nativetest.ui.adapter.SelectCountryRvAdpter;
import com.example.nativetest.ui.adapter.SelectStatusRvAdpter;
import com.example.nativetest.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SelectActivity2 extends BaseActivity{
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            final List<AreaBean.ProvinceBean> list = (List<AreaBean.ProvinceBean>) bundle.getSerializable("bean");
            String country = bundle.getString("country");
            SelectStatusRvAdpter selectStatusRvAdpter = new SelectStatusRvAdpter(mContext, list);
            mRvCity.setLayoutManager(new LinearLayoutManager(mContext));
            mRvCity.setAdapter(selectStatusRvAdpter);
            selectStatusRvAdpter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    AreaBean.ProvinceBean provinceBean = list.get(position);
                    if(provinceBean.getCity().size()!=0){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("bean", (Serializable) provinceBean.getCity());
                        bundle.putString("state",provinceBean.getProvince_code());
                        readyGo(SelectActivity3.class,bundle);
                    }else {
                        ToastUtils.showToast("无下级，提交");
                    }
                }
            });
        }
    }
}
