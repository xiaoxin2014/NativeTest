package com.example.nativetest.ui.activity;

import android.os.Bundle;

import com.example.nativetest.AreaBean;
import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseRvAdapter;
import com.example.nativetest.ui.adapter.SelectCityRvAdpter;
import com.example.nativetest.ui.adapter.SelectStatusRvAdpter;
import com.example.nativetest.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SelectActivity3 extends BaseActivity {
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            final List<AreaBean.ProvinceBean.CityBean> list = (List<AreaBean.ProvinceBean.CityBean>) bundle.getSerializable("bean");
            String state = bundle.getString("state");
            SelectCityRvAdpter selectCityRvAdpter = new SelectCityRvAdpter(mContext, list);
            mRvCity.setLayoutManager(new LinearLayoutManager(mContext));
            mRvCity.setAdapter(selectCityRvAdpter);
            selectCityRvAdpter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ToastUtils.showToast("无下级，提交");

                }
            });
        }
    }
}
