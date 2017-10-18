package common.utils;

import org.json.JSONObject;

/**
 * Created by xiexw on 2017/8/31.
 * QQ第三方授权登录
 */
public class QQUtils {

    private static String QQ_APPID = "101422669";

    private static String QQ_APPKEY = "31cdc061ee67f0710f3e0e464d7e98dd";

    private static String REDIRECT_URL = "http://heyhey.xicp.io/oauth/getQQ.do";

    /**
     * 获取code请求URL（GET）
     */
    public static String CODE_URL = "https://graph.qq.com/oauth2.0/authorize?" +
            "client_id=" + QQ_APPID + "&" +
            "response_type=code&" +
            "redirect_uri=" + REDIRECT_URL + "&" +
            "state=1";

    /**
     * 获取access_token请求URL（GET）
     */
    public static String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";

    /**
     * 获取access_token请求PARAM（GET）
     */
    public static String ACCESS_TOKEN_PARAM = "grant_type=authorization_code&" +
            "client_id=" + QQ_APPID + "&" +
            "client_secret=" + QQ_APPKEY + "&" +
            "code=CODE&" +
            "redirect_uri=" + REDIRECT_URL;

    /**
     * 获取用户OpenID请求URL（GET）
     */
    public static String USER_OPENID_URL = "https://graph.qq.com/oauth2.0/me";

    /**
     * 获取用户OpenID请求PARAM（GET）
     */
    public static String USER_OPENID_PARAM = "access_token=ACCESS_TOKEN";

    /**
     * 获取用户信息URL（GET）
     */
    public static String USER_INFO_URL = "https://graph.qq.com/user/get_user_info";

    /**
     * 获取用户信息PARAM（GET）
     */
    public static String USER_INFO_PARAM = "access_token=ACCESS_TOKEN&" +
            "oauth_consumer_key=APPID&" +
            "openid=OPENID&" +
            "format=json";

    /**
     * 获取access_token
     *
     * @param code GET请求中获取的code
     * @return access_token
     */
    public static JSONObject getAccessToken(String code) {
        String url = ACCESS_TOKEN_URL;
        String param = ACCESS_TOKEN_PARAM.replace("CODE", code);
        String result = CommonUtils.sendGet(url, param);
        return CommonUtils.parseParam(result);
    }

    /**
     * 获取用户OpenID
     *
     * @param code GET请求中获取的code
     * @return OpenID
     */
    public static JSONObject getUserOpenID(String code) {
        //取出access_token
        String access_token = getAccessToken(code).get("access_token").toString();
        String url = USER_OPENID_URL;
        String param = USER_OPENID_PARAM.replace("ACCESS_TOKEN", access_token);
        String result = CommonUtils.sendGet(url, param);
        JSONObject json = new JSONObject(result.replace("callback(", "").replace(");", "").trim());
        //存入access_token供其他接口使用，避免重复请求
        json.put("access_token", access_token);
        return json;
    }

    /**
     * 获取用户信息
     *
     * @param code GET请求中获取的code
     * @return userInfo
     * */
    public static JSONObject getUserInfo(String code) {
        JSONObject OpenID = getUserOpenID(code);
        String url = USER_INFO_URL;
        String param = USER_INFO_PARAM
                .replace("ACCESS_TOKEN", OpenID.get("access_token").toString())
                .replace("APPID", OpenID.get("client_id").toString())
                .replace("OPENID", OpenID.get("openid").toString());
        String result = CommonUtils.sendGet(url, param);
        return new JSONObject(result);
    }

}
