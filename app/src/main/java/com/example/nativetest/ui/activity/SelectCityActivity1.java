package com.example.nativetest.ui.activity;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.example.nativetest.AreaBean;
import com.example.nativetest.R;
import com.example.nativetest.ui.adapter.BaseRvAdapter;
import com.example.nativetest.ui.adapter.SelectCountryRvAdpter;
import com.example.nativetest.utils.FileUtils;
import com.example.nativetest.utils.ToastUtils;

import java.io.Serializable;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SelectCityActivity1 extends BaseActivity {
    @BindView(R.id.rv_city)
    RecyclerView mRvCity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {


        String json = FileUtils.getJson(this, "data.json");
        List<AreaBean> areaBeans = JSONObject.parseArray(json, AreaBean.class);
        SelectCountryRvAdpter selectCountryRvAdpter = new SelectCountryRvAdpter(mContext, areaBeans);
        mRvCity.setLayoutManager(new LinearLayoutManager(mContext));
        mRvCity.setAdapter(selectCountryRvAdpter);
        selectCountryRvAdpter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AreaBean areaBean = areaBeans.get(position);
                if(areaBean.getProvince().size()!=0){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", (Serializable) areaBean.getProvince());
                    bundle.putString("country",areaBean.getState_code());
                    readyGo(SelectActivity2.class,bundle);
                }else {
                    ToastUtils.showToast("无下级，提交");
                }
            }
        });
    }

}
