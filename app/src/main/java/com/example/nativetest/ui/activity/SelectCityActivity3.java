package com.example.nativetest.ui.activity;

import android.os.Bundle;

import com.example.nativetest.AreaBean;
import com.example.nativetest.R;
import com.example.nativetest.event.CitySelectEvent;
import com.example.nativetest.ui.adapter.BaseRvAdapter;
import com.example.nativetest.ui.adapter.SelectCityRvAdpter;
import com.example.nativetest.ui.adapter.SelectStatusRvAdpter;
import com.example.nativetest.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.rong.eventbus.EventBus;

public class SelectCityActivity3 extends BaseActivity {
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
            String countryCode = bundle.getString("country_code");
            String countryName = bundle.getString("country_name");
            String stateCode = bundle.getString("state_code");
            String stateName = bundle.getString("state_name");
            SelectCityRvAdpter selectCityRvAdpter = new SelectCityRvAdpter(mContext, list);
            mRvCity.setLayoutManager(new LinearLayoutManager(mContext));
            mRvCity.setAdapter(selectCityRvAdpter);
            selectCityRvAdpter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    ToastUtils.showToast("无下级，提交");
                    EventBus.getDefault().post(new CitySelectEvent(
                            countryName+stateName+list.get(position).getCity_name(),
                            countryCode,
                            stateCode,
                            list.get(position).getCity_code()
                            ));
                    finish();

                }
            });
        }
    }
}
