package com.xjt.cloud.cas.client.adapters;

import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.ConstantsMsg;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/17 15:41
 * @Description: 当得登录用户信息管理
 */
public class SecurityUserHolder {
    /**
     *
     * 功能描述: 得到当前登录的登录用户名
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/17 15:52
     */
    public static String getUserName(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                User user = (User)authentication.getPrincipal();
                return user.getUsername();
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
        HttpServletRequest request = SpringBeanUtil.getRequest();
        SysLog.error(ConstantsMsg.USER_LOGIN_FAIL + request.getRequestURL() + "?" + request.getQueryString());
        /*throw BaseServiceException.initException(ConstantsMsg.USER_LOGIN_FAIL + request.getRequestURI() + "?" + request.getQueryString(),
                ServiceErrCode.NOT_URL_ERR.getCode());*/
        throw BaseServiceException.initException(ConstantsMsg.USER_LOGIN_FAIL, ServiceErrCode.NOT_URL_ERR.getCode());
    }

    /**
     *
     * 功能描述:从请求参数中获取token参数
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/4/9 17:21
     */
    public static String getToken() {
        HttpServletRequest request = SpringBeanUtil.getRequest();
        String token = extractHeaderToken(request);
        if (token == null) {
            token = request.getParameter("access_token");
        }
        return token;
    }

    /**
     *
     * 功能描述:从请求头中获取token值
     *
     * @param request
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/4/9 17:21
     */
    public static String extractHeaderToken(HttpServletRequest request) {
        Enumeration headers = request.getHeaders("Authorization");
        String value;
        do {
            if (!headers.hasMoreElements()) {
                return null;
            }
            value = (String)headers.nextElement();
        } while(!value.toLowerCase().startsWith("Bearer".toLowerCase()));

        String authHeaderValue = value.substring("Bearer".length()).trim();
        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE, value.substring(0, "Bearer".length()).trim());
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }
        return authHeaderValue;
    }
}
