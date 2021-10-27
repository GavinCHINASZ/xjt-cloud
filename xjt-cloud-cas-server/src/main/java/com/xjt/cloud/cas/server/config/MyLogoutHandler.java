package com.xjt.cloud.cas.server.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/11 09:45
 * @Description:
 */

@Component
public class MyLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            String aa = request.getParameter("returnUrl");//aa即为前端传来自定义跳转url地址
            response.sendRedirect(aa);//实现自定义重定向
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
