package common.utils;

import org.json.JSONObject;

/**
 * Created by xiexw on 2017/8/31.
 * 微信第三方授权登录
 */
public class WeChatUtils {

    //测试号ID
//    private static String WECHAT_APPID = "wx1684c9c7e9a960bb";
    //职教桥ID
    private static String WECHAT_APPID = "wxc08d041eeb13bba4";

    //测试号密钥
//    private static String WECHAT_APPSECRET = "b0316603fb5adab1158ad71c2b20819c";
    //职教桥密钥
    private static String WECHAT_APPSECRET = "700f4fc6b497237f946a1c52293f8436";

    private static String REDIRECT_URL = "http://heyhey.xicp.io/oauth/getWeChat.do";

    public static String CODE_URL = "https://open.weixin.qq.com/connect/qrconnect?" +
            "appid=" + WECHAT_APPID + "&" +
            "redirect_uri=" + REDIRECT_URL + "&" +
            "response_type=code&" +
            "scope=snsapi_login&" +
            "state=1" +
            "#wechat_redirect";

    /**
     * 获取access_token请求URL（GET）
     */
    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取access_token请求PARAM（GET）
     */
    public static String ACCESS_TOKEN_PARAM = "appid=" + WECHAT_APPID + "&" +
            "secret=" + WECHAT_APPSECRET + "&" +
            "code=CODE&" +
            "grant_type=authorization_code";

    /**
     * 获取用户信息URL（GET）
     */
    public static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 获取用户信息PARAM（GET）
     */
    public static String USER_INFO_PARAM = "access_token=ACCESS_TOKEN&" +
            "openid=OPENID";

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
        String url = USER_INFO_URL;
        String param = USER_INFO_PARAM
                .replace("ACCESS_TOKEN", access_token.getString("access_token"))
                .replace("OPENID", access_token.getString("openid"));
        String result = CommonUtils.sendGet(url, param);
        return new JSONObject(result);
    }
}
