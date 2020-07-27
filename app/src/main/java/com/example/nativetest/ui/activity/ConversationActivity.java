package com.example.nativetest.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import com.example.nativetest.R;
import com.example.nativetest.ui.fragment.TwoFragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

public class ConversationActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName())
                .appendQueryParameter("targetId", "niko2").build();

        fragement.setUri(uri);
    }

}
