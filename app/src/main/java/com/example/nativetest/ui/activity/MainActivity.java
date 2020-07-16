package com.example.nativetest.ui.activity;

import android.view.Gravity;
import android.view.View;

import com.example.nativetest.R;
import com.example.nativetest.TestTabActivity;
import com.example.nativetest.db.DbManager;
import com.example.nativetest.ui.fragment.MainConversationListFragment;
import com.example.nativetest.ui.fragment.MainFragment;
import com.example.nativetest.widget.MainBottomTabGroupView;
import com.example.nativetest.widget.MainBottomTabItem;
import com.example.nativetest.widget.TabGroupView;
import com.example.nativetest.widget.TabItem;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import io.rong.imkit.fragment.ConversationListFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tg_bottom_tabs)
    MainBottomTabGroupView mTgBottomTabs;



    /**
     * tab 项枚举
     */
    public enum Tab {
        /**
         * 我的
         */
        MY(0),
        /**
         * 三俩
         */
        TWO_THREE(1),
        /**
         * 联系
         */
        CHAT(2),
        /**
         * 个人
         */
        ME(3);

        private int value;

        Tab(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Tab getType(int value) {
            for (Tab type : Tab.values()) {
                if (value == type.getValue()) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * tabs 的图片资源
     */
    private int[] tabImageRes = new int[]{
            R.drawable.seal_ic_chat,
            R.drawable.seal_ic_chat,
            R.drawable.seal_ic_chat,
            R.drawable.seal_ic_chat
    };

    /**
     * 各个 Fragment 界面
     */
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initTabs();
    }

    /**
     * 初始化 Tabs
     */
    private void initTabs() {
        // 初始化 tab
        List<TabItem> items = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.tab_names);

        for (Tab tab : Tab.values()) {
            TabItem tabItem = new TabItem();
            tabItem.id = tab.getValue();
            tabItem.text = stringArray[tab.getValue()];
            tabItem.drawable = tabImageRes[tab.getValue()];
            items.add(tabItem);
        }

        mTgBottomTabs.initView(items, new TabGroupView.OnTabSelectedListener() {
            @Override
            public void onSelected(View view, TabItem item) {
                // 当点击 tab 的后， 也要切换到正确的 fragment 页面
//                int currentItem = vpFragmentContainer.getCurrentItem();
//                if (currentItem != item.id) {
//                     切换布局
//                    vpFragmentContainer.setCurrentItem(item.id);
//                     如果是我的页面， 则隐藏红点
//                    if (item.id == Tab.ME.getValue()) {
//                        ((MainBottomTabItem) tabGroupView.getView(Tab.ME.getValue())).setRedVisibility(View.GONE);
//                    }
//                }
                if(item.id == Tab.MY.getValue()){
                }
            }
        });

        mTgBottomTabs.setOnTabDoubleClickListener(new MainBottomTabGroupView.OnTabDoubleClickListener() {
            @Override
            public void onDoubleClick(TabItem item, View view) {
                // 双击定位到某一个未读消息位置
//                if (item.id == Tab.CHAT.getValue()) {
//                    MainConversationListFragment fragment = (MainConversationListFragment) fragments.get(Tab.CHAT.getValue());
//                    fragment.focusUnreadItem();
//                }
                if(item.id == Tab.ME.getValue()){
                    readyGo(SettingActivity.class);
                }else if(item.id == Tab.MY.getValue()){
                    readyGo(TestTabActivity.class);
                }else {

                }
            }
        });

        ((MainBottomTabItem) mTgBottomTabs.getView(Tab.CHAT.getValue())).setNum("11");
        ((MainBottomTabItem) mTgBottomTabs.getView(Tab.CHAT.getValue())).setNumVisibility(View.VISIBLE);

//        // 未读数拖拽
//        ((MainBottomTabItem) tabGroupView.getView(Tab.CHAT.getValue())).setTabUnReadNumDragListener(new DragPointView.OnDragListencer() {
//
//            @Override
//            public void onDragOut() {
//                ((MainBottomTabItem) tabGroupView.getView(Tab.CHAT.getValue())).setNumVisibility(View.GONE);
//                showToast(getString(R.string.seal_main_toast_unread_clear_success));
//                clearUnreadStatus();
//            }
//        });
//        ((MainBottomTabItem) tabGroupView.getView(Tab.CHAT.getValue())).setNumVisibility(View.VISIBLE);


//        addFragment(new MainConversationListFragment());
        MainFragment mainFragment = new MainFragment();
        addFragment(mainFragment);
        changeFragment(mainFragment);
    }

    private void addFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_container, fragment);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    private void changeFragment(Fragment lastFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.hide(mSelectFragment);
        fragmentTransaction.show(lastFragment);
        fragmentTransaction.commit();
//        mSelectFragment = lastFragment;
    }
}
