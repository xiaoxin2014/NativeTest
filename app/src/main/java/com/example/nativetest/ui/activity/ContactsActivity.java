package com.example.nativetest.ui.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.nativetest.R;
import com.example.nativetest.common.NetConstant;
import com.example.nativetest.db.model.ProfileHeadInfo;
import com.example.nativetest.model.FollowBean;
import com.example.nativetest.model.sc.UserInfo;
import com.example.nativetest.viewmodel.UserInfoViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.mention.SideBar;
import io.rong.imkit.tools.CharacterParser;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imlib.model.Conversation;

public class ContactsActivity extends BaseActivity {

    private UserInfoViewModel mUserInfoViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initView() {
        EditText searchBar = (EditText) findViewById(R.id.rc_edit_text);
        mListView = (ListView) findViewById(R.id.rc_list);
        SideBar mSideBar = (SideBar) findViewById(R.id.rc_sidebar);
        TextView letterPopup = (TextView) findViewById(R.id.rc_popup_bg);
        mSideBar.setTextView(letterPopup);


        mAdapter = new MembersAdapter();
        mListView.setAdapter(mAdapter);
        mAllMemberList = new ArrayList<>();

        mTargetId = getIntent().getStringExtra("targetId");
        mConversationType = (Conversation.ConversationType) getIntent().getSerializableExtra("conversationType");


        initViewModel();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("contact", mAdapter.getItem(position).userInfo);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
            }
        });

        //设置右侧触摸监听
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                List<MemberInfo> filterDataList = new ArrayList<>();

//                if (TextUtils.isEmpty(s.toString())) {
//                    filterDataList = mAllMemberList;
//                } else {
//                    filterDataList.clear();
//                    for (MemberInfo member : mAllMemberList) {
//                        String name = member.userInfo.getName();
//                        if (name != null) {
//                            if (name.contains(s) || CharacterParser.getInstance().getSelling(name).startsWith(s.toString())) {
//                                filterDataList.add(member);
//                            }
//                        }
//                    }
//                }
                // 根据a-z进行排序
                Collections.sort(filterDataList, PinyinComparator.getInstance());
                mAdapter.setData(filterDataList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initViewModel() {
        mUserInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        mUserInfoViewModel.getFollowerListResult().observe(this, result -> {
            if (result.RsCode == NetConstant.REQUEST_SUCCESS_CODE) {
                List<ProfileHeadInfo> rsData = result.getRsData();
//                mFollowRvAdapter.setDatas(rsData);

                for (int i = 0; i < rsData.size(); i++) {
                    ProfileHeadInfo profileHeadInfo = rsData.get(i);
                    MemberInfo memberInfo = new MemberInfo(profileHeadInfo);
                    String sortString = "#";
                    //汉字转换成拼音
                    String pinyin = CharacterParser.getInstance().getSelling(profileHeadInfo.getName());

                    if (pinyin != null) {
                        if (pinyin.length() > 0) {
                            sortString = pinyin.substring(0, 1).toUpperCase();
                        }
                    }
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        memberInfo.setLetter(sortString.toUpperCase());
                    } else {
                        memberInfo.setLetter("#");
                    }
                    mAllMemberList.add(memberInfo);
                }
                Collections.sort(mAllMemberList, PinyinComparator.getInstance());
                mAdapter.setData(mAllMemberList);
                mAdapter.notifyDataSetChanged();
            }
        });

        mUserInfoViewModel.getFollowerList(NetConstant.SKIP,NetConstant.TAKE);
    }

    private ListView mListView;
    private List<MemberInfo> mAllMemberList;
    private MembersAdapter mAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());

    private Conversation.ConversationType mConversationType;
    private String mTargetId;


    @OnClick(R.id.ll_focus)
    public void onViewClicked() {
        readyGo(FriendsRequestListActivity.class);
    }


    private static class MembersAdapter extends BaseAdapter implements SectionIndexer {
        private List<MemberInfo> mList = new ArrayList<>();

        public void setData(List<MemberInfo> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public MemberInfo getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_list_item_contact_card, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.rc_user_name);
                viewHolder.portrait = (AsyncImageView) convertView.findViewById(R.id.rc_user_portrait);
                viewHolder.letter = (TextView) convertView.findViewById(R.id.letter);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ProfileHeadInfo userInfo = mList.get(position).userInfo;
            if (userInfo != null) {
                viewHolder.name.setText(userInfo.getName());
//                if (!TextUtils.isEmpty(userInfo.getExtra())) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(userInfo.getExtra());
//                        if (jsonObject.has("displayName")) {
//                            viewHolder.name.setText(jsonObject.getString("displayName"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        viewHolder.name.setText(userInfo.getName());
//                    }
//                } else {
//                    viewHolder.name.setText(userInfo.getName());
//                }
//                viewHolder.portrait.setAvatar(userInfo.getPortraitUri());
            }

            //根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(position);
            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                viewHolder.letter.setVisibility(View.VISIBLE);
                viewHolder.letter.setText(mList.get(position).getLetter());
            } else {
                viewHolder.letter.setVisibility(View.GONE);
            }

            return convertView;
        }

        @Override
        public Object[] getSections() {
            return new Object[0];
        }

        @Override
        public int getPositionForSection(int sectionIndex) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = mList.get(i).getLetter();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }

            return -1;
        }

        @Override
        public int getSectionForPosition(int position) {
            return mList.get(position).getLetter().charAt(0);
        }
    }

    private static class ViewHolder {
        AsyncImageView portrait;
        TextView name;
        TextView letter;
    }

    private static class MemberInfo {
        ProfileHeadInfo userInfo;
        String letter;

        MemberInfo(ProfileHeadInfo userInfo) {
            this.userInfo = userInfo;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getLetter() {
            return letter;
        }
    }

    public static class PinyinComparator implements Comparator<MemberInfo> {


        public static PinyinComparator instance = null;

        public static PinyinComparator getInstance() {
            if (instance == null) {
                instance = new PinyinComparator();
            }
            return instance;
        }

        public int compare(MemberInfo o1, MemberInfo o2) {
            if (o1.getLetter().equals("@") || o2.getLetter().equals("#")) {
                return -1;
            } else if (o1.getLetter().equals("#") || o2.getLetter().equals("@")) {
                return 1;
            } else {
                return o1.getLetter().compareTo(o2.getLetter());
            }
        }

    }
}
