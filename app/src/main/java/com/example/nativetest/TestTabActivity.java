package com.example.nativetest;

import android.view.View;
import android.view.ViewGroup;

import com.example.nativetest.ui.activity.BaseActivity;
import com.example.nativetest.ui.fragment.SimpleCardFragment;
import com.flyco.tablayout.SlidingScaleTabLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class TestTabActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tab;
    }


    /**
     * 各个 Fragment 界面
     */
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void initView() {
        fragments.add(new TabTestFragment());
        fragments.add(new TabTestFragment());



        ViewPager vpFragmentContainer = (ViewPager)findViewById(R.id.vp_main_container);
        // ViewPager 的 Adpater
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return position==0?"消息":"评论";
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                View view = (View) object;
                return (int) view.getTag();
            }
            @Override
            public Fragment getItem(int i) {
                return SimpleCardFragment.getInstance("这是第" + i + "个Fragment", i);
            }
        };

        vpFragmentContainer.setAdapter(fragmentPagerAdapter);
        vpFragmentContainer.setOffscreenPageLimit(fragments.size());
        // 设置页面切换监听
        vpFragmentContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 当页面切换完成之后， 同时也要把 tab 设置到正确的位置
//                tabGroupView.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

        SlidingScaleTabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setViewPager(vpFragmentContainer);
        TabLayout layout = (TabLayout) findViewById(R.id.tl);
        layout.setupWithViewPager(vpFragmentContainer);
    }
}
