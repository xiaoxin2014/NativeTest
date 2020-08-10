package com.example.nativetest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import com.example.nativetest.common.ErrorCode;
import com.example.nativetest.common.LogTag;
import com.example.nativetest.sp.UserConfigCache;
import com.example.nativetest.ui.activity.MainActivity;
import com.example.nativetest.utils.ToastUtils;
import com.example.nativetest.utils.log.SLog;

import java.util.List;

import androidx.multidex.MultiDexApplication;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongConfigurationManager;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.DefaultLocationPlugin;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class SealApp extends MultiDexApplication {
    private static SealApp appInstance;
    /**
     * 应用是否在后台
     */
    private boolean isAppInForeground;
    private String lastVisibleActivityName;
    private Intent nextOnForegroundIntent;
    private boolean isMainActivityIsCreated;

    @Override
    protected void attachBaseContext(Context base) {
        Context context = RongConfigurationManager.getInstance().getConfigurationContext(base);
        super.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;

        // 初始化 bugly BUG 统计
        //CrashReport.initCrashReport(getApplicationContext());

        ErrorCode.init(this);

        /*
         * 以上部分在所有进程中会执行
         */
        if (!getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            return;
        }

        // 检查是否争取配置了 SealTalk 参数
        checkSealConfig();

//        /*
//         * 以下部分仅在主进程中进行执行
//         */
//        // 初始化融云IM SDK，初始化 SDK 仅需要在主进程中初始化一次
//        IMManager.getInstance().init(this);
//        Stetho.initializeWithDefaults(this);
//
//        SearchUtils.init(this);
//
//        Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));
//
//        // 微信分享初始化
//        WXManager.getInstance().init(this);
//
//        PhoneContactManager.getInstance().init(this);

        // 监听 App 前后台变化
        observeAppInBackground();


//        String appKey = "x18ywvqfx5h3c";//后台appkey
        String appKey = "k51hidwqkv77b";//自己appkey
        RongIM.init(appInstance, appKey);
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            /**
             * 连接状态返回回调
             * @param connectionStatus 状态值 状态值详细可查看
             * https://www.rongcloud.cn/docs/api/android/imlib/io/rong/imlib/RongIMClient.ConnectionStatusListener.ConnectionStatus.html
             */
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                SLog.d(LogTag.IM, "ConnectionStatus onChanged = " + connectionStatus.getMessage());
                if (connectionStatus.equals(ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT)) {
                    //被其他提出时，需要返回登录界面
//                    kickedOffline.postValue(true);
                } else if (connectionStatus == ConnectionStatus.TOKEN_INCORRECT) {
                    //TODO token 错误时，重新登录
                }


                else if(connectionStatus == ConnectionStatus.CONNECTED){
                    connectIM();
                }

            }
        });

    }

    /**
     * 初始化扩展模块
     *
     * @param context
     */
    private void initExtensionModules(Context context) {
        /**
         * 因为 SealExtensionModule 继承与融云默认 DefaultExtensionModule，
         * 需要先移除掉默认的扩展后再进行注册
         * 继承并覆盖默认的扩展模块可在自己需要的时机控制各默认模块的展示与隐藏
         */
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
            }
        }


    }

    public static SealApp getApplication() {
        return appInstance;
    }

    /**
     * 监听应用是否转为后台
     */
    private void observeAppInBackground() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if(activity instanceof MainActivity){
                    isMainActivityIsCreated = true;
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                // 当切换为前台时启动预设的优先显示界面
                if (isMainActivityIsCreated && !isAppInForeground && nextOnForegroundIntent != null) {
                    activity.startActivity(nextOnForegroundIntent);
                    nextOnForegroundIntent = null;
                }

                lastVisibleActivityName = activity.getClass().getSimpleName();
                isAppInForeground = true;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                String pauseActivityName = activity.getClass().getSimpleName();
                /*
                 * 介于 Activity 生命周期在切换画面时现进行要跳转画面的 onResume，
                 * 再进行当前画面 onPause，所以当用户且到后台时肯定会为当前画面直接进行 onPause，
                 * 同过此来判断是否应用在前台
                 */
                if (pauseActivityName.equals(lastVisibleActivityName)) {
                    isAppInForeground = false;
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if(activity instanceof MainActivity){
                    isMainActivityIsCreated = false;
                }
            }
        });
    }

    /**
     * 当前 App 是否在前台
     *
     * @return
     */
    public boolean isAppInForeground() {
        return isAppInForeground;
    }

    /**
     * 获取最后在前台的 Activity 名称
     *
     * @return
     */
    public String getLastVisibleActivityName() {
        return lastVisibleActivityName;
    }

    /**
     * 设置当 App 切换为前台时启动的 intent，该 intent 在启动后情况
     *
     * @param intent
     */
    public void setOnAppForegroundStartIntent(Intent intent) {
        nextOnForegroundIntent = intent;
    }

    /**
     * 获取最近设置的未触发的启动 intent
     *
     * @return
     */
    public Intent getLastOnAppForegroundStartIntent() {
        return nextOnForegroundIntent;
    }

    /**
     * 判断是否进入到了主界面
     * @return
     */
    public boolean isMainActivityCreated(){
        return isMainActivityIsCreated;
    }

    /**
     * 检查是否正确的配置 SealTalk 中的一些必要环境。
     */
    private void checkSealConfig(){
//        if(!BuildConfig.SEALTALK_SERVER.startsWith("http")){
//            Log.e("SealTalk 集成错误", "\n" +
//                    "您需要确认是否将 sealtalk 目录下 gradle.properties " +
//                    "文件中的 SEALTALK_SERVER 参数修改为了您所部署的 SealTalk 服务器。\n" +
//                    "同时，建议您阅读下 README.MD 中的关于【运行 SealTalk-Android】部分，以便您能正常运行。");
//            throw new IllegalArgumentException("需要运行 SealTalk 您先要正确的指定您的 SealTalk 服务器。");
//        }
//
//        if(BuildConfig.SEALTALK_APP_KEY.contains("AppKey")){
//            Log.e("SealTalk 集成错误", "\n" +
//                    "您需要确认是否将 sealtalk 目录下 gradle.properties " +
//                    "文件中的 SEALTALK_APP_KEY 参数修改为了您在融云所申请的 AppKey。\n" +
//                    "同时，建议您阅读下 README.MD 中的关于【运行 SealTalk-Android】部分，以便您能正常运行。");
//            throw new IllegalArgumentException("需要运行 SealTalk 您需要指定您所申请融云的 Appkey。");
//        }
    }


    private String mToken = "GEpsiFHSeu9WHjEyUGfZJ7rbXNyChVbiuqG1LeOB0KU=@u7r5.cn.rongnav.com;u7r5.cn.rongcfg.com\"";

    private void connectIM() {
        RongIM.connect(mToken, 10, new RongIMClient.ConnectCallback() {
            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
//                if (callback != null) {
//                    callback.onSuccess(RongIMClient.getInstance().getCurrentUserId());
//                }
                SLog.e(LogTag.IM, "connect databaseOpenStatus ");

            }

            @Override
            public void onSuccess(String s) {
                // 连接 IM 成功后，初始化数据库
//                DbManager.getInstance(context).openDb(s);
                SLog.e(LogTag.IM, "connect success - code:" + s);
                ToastUtils.showToast("连接成功");
            }

            public void onError(RongIMClient.ConnectionErrorCode errorCode) {
                SLog.e(LogTag.IM, "connect error - code:" + errorCode.getValue());
//                if (errorCode == RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT) {
//                    getToken(new ResultCallback<LoginResult>() {
//                        @Override
//                        public void onSuccess(LoginResult loginResult) {
//                            connectIM(loginResult.token, timeOut, callback);
//                        }
//34001 30007
//                        @Override
//                        public void onFail(int errorCode) {
//                            callback.onFail(errorCode);
//                        }
//                    });;
//                } else {
//                    if (callback != null) {
//                        callback.onFail(ErrorCode.IM_ERROR.getCode());
//                    } else {
//                        // do nothing
//                    }
//                }
            }
        });
    }

}
