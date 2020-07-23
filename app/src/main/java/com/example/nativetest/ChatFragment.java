package com.example.nativetest;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.example.nativetest.event.ShowMoreEvent;
import com.example.nativetest.ui.activity.ContactsActivity;
import com.example.nativetest.ui.fragment.CommentFragment;
import com.flyco.tablayout.SlidingScaleTabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import io.rong.eventbus.EventBus;

public class ChatFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    SlidingScaleTabLayout tabLayout;
    @BindView(R.id.vp_main_container)
    ViewPager vpFragmentContainer;
    @BindView(R.id.fl_order_layout)
    FrameLayout mFlOrderLayout;
    @BindView(R.id.btn_more)
    AppCompatImageButton mBtnMore;


    private PopupWindow mPopupWindow;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tab;
    }


    @Override
    protected void onInitView(Bundle savedInstanceState, Intent intent) {
        if (mFlOrderLayout.getForeground() != null) {
            mFlOrderLayout.getForeground().setAlpha(0);
        }

        // ViewPager 的 Adpater
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return position == 0 ? "消息" : "评论";
            }

            @Override
            public int getItemPosition(@NonNull Object object) {
                View view = (View) object;
                return (int) view.getTag();
            }

            @Override
            public Fragment getItem(int i) {
                return CommentFragment.getInstance(i);
            }
        };

        vpFragmentContainer.setAdapter(fragmentPagerAdapter);
        vpFragmentContainer.setOffscreenPageLimit(2);
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

        tabLayout.setViewPager(vpFragmentContainer);
    }

    /**
     * 各个 Fragment 界面
     */
//    private List<Fragment> fragments = new ArrayList<>();


    @OnClick({R.id.btn_contact, R.id.btn_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_contact:
                readyGo(ContactsActivity.class);
                break;
            case R.id.btn_more:
                showMorePop();
                break;
        }
    }

    private void showMorePop() {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_more, null);
            view.findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
            mPopupWindow = new PopupWindow(view, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (mFlOrderLayout.getForeground() != null) {
                        mFlOrderLayout.getForeground().setAlpha(0);
                        EventBus.getDefault().post(new ShowMoreEvent(false));

                    }
                }
            });
        }

        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            if (mFlOrderLayout.getForeground() != null) {
                mFlOrderLayout.getForeground().setAlpha(153);
                EventBus.getDefault().post(new ShowMoreEvent(true));
            }
            mPopupWindow.showAsDropDown(mBtnMore);
        }


//    private void showSpinner() {
//        if (mPopupWindow == null) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_spinner, null);
//            mTvTopic = (TextView) view.findViewById(R.id.tv_my_topic);
//            mTvQA = (TextView) view.findViewById(R.id.tv_my_qa);
//            mTvFocusTopic = (TextView) view.findViewById(R.id.tv_focus_topics);
//            mPopupWindow = new PopupWindow(view, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
//            mPopupWindow.setFocusable(true);
//            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//            mPopupWindow.setOutsideTouchable(true);
//            mTvTopic.setOnClickListener(new DialogSpinner());
//            mTvQA.setOnClickListener(new DialogSpinner());
//            mTvFocusTopic.setOnClickListener(new DialogSpinner());
//            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    ExpandAnimator.arrowAnimator(mImgSocial, -180, 0);
////                    WindowUtils.setWindowAlpha(PersonalSocialCenter.this, 1);
//                    if(mFlOrderLayout.getForeground()!=null){
//                        mFlOrderLayout.getForeground().setAlpha(0);
//                    }
//                }
//            });
//        }
//        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
////            WindowUtils.setWindowAlpha(PersonalSocialCenter.this, 0.6f);
//            if(mFlOrderLayout.getForeground()!=null){
//                mFlOrderLayout.getForeground().setAlpha(153);
//            }
//
//            mPopupWindow.showAsDropDown(mLlTitle);
//            ExpandAnimator.arrowAnimator(mImgSocial, 0, -180);
//        }
//
//    }
    }
}
