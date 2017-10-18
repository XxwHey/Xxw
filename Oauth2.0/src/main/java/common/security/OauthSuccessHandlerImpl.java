package common.security;

import common.utils.ResponseUtils;
import model.TbUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by xiexw on 2017/9/8.
 * security验证成功处理
 */
public class OauthSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User securityUser = (User) authentication.getPrincipal();
        TbUser tbUser = new TbUser(securityUser.getUsername(), securityUser.getPassword(), "", true);
        HttpSession session = request.getSession();
        session.setAttribute("user", tbUser);
        ResponseUtils.setWrite(response, "data", 1);
    }

}
