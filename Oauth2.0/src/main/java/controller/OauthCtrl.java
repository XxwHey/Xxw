package controller;

import common.utils.*;
import model.TbUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiexw on 2017/8/29.
 */
@Controller
public class OauthCtrl {

    @RequestMapping(value = "admin/list.do")
    public void adminList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/static/html/admin/admin.html").forward(request, response);
    }

    @RequestMapping(value = "logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response, String error) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        request.getRequestDispatcher("/static/html/login/logout.html").forward(request, response);
    }

    @RequestMapping(value = "oauth/getType.do")
    public void getType(HttpServletRequest request, HttpServletResponse response, String type) {
        String TPurl = null;
        switch (type) {
            case "WeChat":
                TPurl = WeChatUtils.CODE_URL;
                break;
            case "QQ":
                TPurl = QQUtils.CODE_URL;
                break;
            case "Weibo":
                TPurl = WeiboUtils.CODE_URL;
                break;
        }
        try {
            response.sendRedirect(TPurl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "oauth/getWeChat.do")
    public void getWeChat(HttpServletRequest request, HttpServletResponse response, String code) throws Exception {
        System.out.println("来自微信的请求...");
        if (code != null) {
            JSONObject result = WeChatUtils.getUserInfo(code);
            System.out.println(result.toString());
            //session存储用户信息
            HttpSession session = request.getSession();
            session.setAttribute("user", result);
            //重定向POST至登录验证URL
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", result.getString("unionid") + "/TP");
            params.put("password", "admin");
            CommonUtils.redirectPost(response, "/j_spring_security_check", params);
        }
    }

    @RequestMapping(value = "oauth/getWeibo.do")
    public void getWeibo(HttpServletRequest request, HttpServletResponse response, String code) {
        System.out.println("来自微博的请求...");
        if (code != null) {
            JSONObject result = WeiboUtils.getUserInfo(code);
            System.out.println(result.toString());
            //初始化第三方用户(userService.saveTPUser)(if userService.getByUid == null)
            TbUser user = new TbUser();
            //微博登录以微博用户ID作为初始用户名，用于security验证
            String TP_GENERATED_USERNAME = result.getString("id");
            //第三方登录时生成的初始用户密码，用于security验证
            String TP_GENERATED_PASSWORD = "admin";
            //数据库存储初始用户
            user.setUsername(TP_GENERATED_USERNAME);
            user.setPassword(TP_GENERATED_PASSWORD);    //userService.save(user)
            //session存储用户信息
            HttpSession session = request.getSession();
            session.setAttribute("user", result);
            //重定向POST至登录验证URL
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", TP_GENERATED_USERNAME + "/TP");
            params.put("password", TP_GENERATED_PASSWORD);
            CommonUtils.redirectPost(response, "/j_spring_security_check", params);
        }
    }

    @RequestMapping(value = "oauth/getQQ.do")
    public void getQQ(HttpServletRequest request, HttpServletResponse response, String code) {
        System.out.println("来自QQ的请求...");
        if (code != null) {
            JSONObject result = QQUtils.getUserInfo(code);
            System.out.println(result.toString());
            ResponseUtils.setWrite(response, "data", result);
        }
    }

    @RequestMapping(value = "login/userInfo.do")
    public void userInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object o = session.getAttribute("user");
        if (o != null) {
            TbUser user = (TbUser) o;
            ResponseUtils.setWrite(response, "data", new JSONObject(user));
        } else {
            //未登录或登录已过期
            try {
                request.getRequestDispatcher("/static/html/login/login.html").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
