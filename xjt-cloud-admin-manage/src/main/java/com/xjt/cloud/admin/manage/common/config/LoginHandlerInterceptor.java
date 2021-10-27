package com.xjt.cloud.admin.manage.common.config;

import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/26 18:14
 * @Description: 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("userName");

        /**
         * 已经成功登录，则放行；否则重定向到登录页面
         */
        SysLog.debug("----" + user + " ::: " + request.getRequestURL());
        if (user == null) {
            response.sendRedirect("/index");
            return false;
        }
        return true;
    }
}
