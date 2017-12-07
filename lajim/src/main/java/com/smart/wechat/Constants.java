package com.smart.wechat;

public class Constants {

    // Access Token
    public static final String URL_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EXPIRE_IN_SEC = "expires_in";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_CREDENTIAL = "client_credential";
    public static final String APP_ID = "appid";
    public static final String SECRET = "secret";

    // 错误码
    public static final String ERROR_CODE = "errcode";

    // 获取用户消息
    public static final String URL_GET_USER = "https://api.weixin.qq.com/cgi-bin/user/info";
    public static final String OPEN_ID = "openid";
    public static final String USER_NICKNAME = "nickname";
    public static final String USER_SEX = "sex";
    public static final String USER_CITY = "city";
    public static final String USER_PROVINCE = "province";
    public static final String USER_COUNTRY = "country";
    public static final String USER_HEAD_IMG_URL = "headimgurl";
    public static final String USER_SUBSCRIBE = "subscribe";
    public static final String USER_SUBSCRIBE_TIME = "subscribe_time";
}
