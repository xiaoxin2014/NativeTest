package com.example.nativetest.net.upload;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nativetest.common.NetConstant;
import com.example.nativetest.net.ScUrl;

import static android.content.Context.MODE_PRIVATE;


public class UploadHttpClientManager {
    private static final String TAG = "HttpClientManager";
    private static UploadHttpClientManager instance;
    private Context context;
    private UploadRetrofitClient client;

    private UploadHttpClientManager(Context context) {
        this.context = context;
        client = new UploadRetrofitClient(context, ScUrl.UPLOAD_BASE_URL);
    }

    public static UploadHttpClientManager getInstance(Context context) {
        if (instance == null) {
            synchronized (UploadHttpClientManager.class) {
                if (instance == null) {
                    instance = new UploadHttpClientManager(context);
                }
            }
        }

        return instance;
    }

    public UploadRetrofitClient getClient() {
        return client;
    }

    /**
     * 设置用户登录认证
     *
     * @param auth
     */
    public void setAuthHeader(String auth) {
        SharedPreferences.Editor config = context.getSharedPreferences(NetConstant.API_SP_NAME_NET, MODE_PRIVATE)
                .edit();
        config.putString(NetConstant.API_SP_KEY_NET_HEADER_AUTH, auth);
        config.commit();
    }

    /**
     * 获取用户登录认证
     *
     * @return
     */
    public String getCurrentAuth() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NetConstant.API_SP_NAME_NET, MODE_PRIVATE);
        return sharedPreferences.getString(NetConstant.API_SP_KEY_NET_HEADER_AUTH, null);
    }

    /**
     * 清除包括cookie和登录认证
     */
    public void clearRequestCache() {
        SharedPreferences.Editor config = context.getSharedPreferences(NetConstant.API_SP_NAME_NET, MODE_PRIVATE)
                .edit();
        config.remove(NetConstant.API_SP_KEY_NET_HEADER_AUTH);
        config.remove(NetConstant.API_SP_KEY_NET_COOKIE_SET);
        config.commit();
    }
}
