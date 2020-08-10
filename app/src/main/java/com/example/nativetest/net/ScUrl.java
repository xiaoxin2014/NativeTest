package com.example.nativetest.net;

public class ScUrl {
    public static final String BASE_URL = "https://ttapi.alilusions.com/";


    public static final String USER_GET_SMS = "api/ScSMS/ping";
    public static final String USER_VERIFY_CODE = "api/ScSMS/VerifyCode";
    public static final String PROFILE_GET = "api/ScUser/ProfileGet";
    public static final String PROFILE_UPDATE = "api/ScUser/ProfileUpdate";
    public static final String LOG_OUT = "/api/ScUser/Logout";
    public static final String COMMENTS_LIST = "/api/ScMoment/CommentsCenter";
    public static final String FOLLOWING_LIST = "/api/ScUser/FollowingsList";
    public static final String FOLLOWERS_LIST = "/api/ScUser/FollowersList";//通讯录
    public static final String FOLLOWERS_REQUEST_LIST = "/api/ScUser/FollowersListDetail";//好友请求
    public static final String FOLLOWINGS_REMOVE = "/api/ScUser/FollowingsRemove";//取消好友




    public static final String HAS_SET_PASSWORD = "/api/ScUser/HasSetPassword";

    public static final String USER_INFO_UPDATE = "api/ScUser/UserInfoUpdate";


    public static final String GET_IM_TOKEN = "/api/ScIM/GetImUserToken";




    //获取token相关
    public static final String TOKEN_BASE_URL = "https://ttid.alilusions.com/";
    public static final String CONNECT_TOKEN = "connect/token";
    public static final String CHANGE_PW_BY_CODE = "api/MemberApi/ChangePWbyCode";
    public static final String CHANGE_PW_BY_OLD_PW = "api/MemberApi/ChangePWbyOldPW";



    //上传图片
    public static final String UPLOAD_BASE_URL = "https://ttmedia.alilusions.com/";
    public static final String UPLOAD_AVATAR = "api/jjUpload/UUupload";

}
