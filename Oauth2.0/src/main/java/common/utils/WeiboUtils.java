package common.utils;

import org.json.JSONObject;

/**
 * Created by xiexw on 2017/8/31.
 * 微博第三方授权登录
 */
public class WeiboUtils {

    private static String WEIBO_APPID = "2636031874";

    private static String WEIBO_SECRET = "57911971eebfd639e55b5ea44dc0bb1b";

    private static String REDIRECT_URL = "http://heyhey.xicp.io/oauth/getWeibo.do";

    /**
     * 获取code请求URL（GET）
     */
    public static String CODE_URL = "https://api.weibo.com/oauth2/authorize?" +
            "client_id=" + WEIBO_APPID + "&" +
            "response_type=code&" +
            "redirect_uri=" + REDIRECT_URL;

    /**
     * 获取access_token请求URL（POST）
     */
    public static String ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";

    /**
     * 获取access_token请求PARAM（POST）
     */
    public static String ACCESS_TOKEN_PARAM = "client_id=" + WEIBO_APPID + "&" +
            "client_secret=" + WEIBO_SECRET + "&" +
            "grant_type=authorization_code&" +
            "redirect_uri=" + REDIRECT_URL + "&" +
            "code=CODE";

    /**
     * 获取用户信息URL（GET）
     */
    public static String USER_SHOW_URL = "https://api.weibo.com/2/users/show.json";

    /**
     * 获取用户信息PARAM（GET）
     */
    public static String USER_SHOW_PARAM = "access_token=ACCESS_TOKEN&" +
            "uid=UID";

    /**
     * 获取access_token
     *
     * @param code GET请求中获取的code
     * @return access_token
     */
    public static JSONObject getAccessToken(String code) {
        String url = ACCESS_TOKEN_URL;
        String param = ACCESS_TOKEN_PARAM.replace("CODE", code);
        String result = CommonUtils.sendPost(url, param);
        return new JSONObject(result);
    }

    /**
     * 获取用户信息
     *
     * @param code GET请求中获取的code
     * @return userInfo
     * */
    public static JSONObject getUserInfo(String code) {
        JSONObject access_token = getAccessToken(code);
        //微博用户id
        String uid = access_token.getString("uid");
        String url = USER_SHOW_URL;
        String param = USER_SHOW_PARAM
                .replace("ACCESS_TOKEN", access_token.getString("access_token"))
                .replace("UID", uid);
        String result = CommonUtils.sendGet(url, param);
        JSONObject userInfo = new JSONObject(result);
        userInfo.put("uid", uid);
        return userInfo;
    }
}
