package com.xjt.cloud.commons.filter;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/3/19 10:03
 * @Description:
 */

import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/12 10:00
 * @Description: 拦截器
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "CloudFilter")
public class CloudFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     *
     * 功能描述:
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @return: void
     * @auther: wangzhiwen
     * @date: 2020/6/11 16:52
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        String accessControlAllowOrigin = PropertyUtils.getProperty("accessControlAllowOrigin");
        if (StringUtils.isNotEmpty(accessControlAllowOrigin)){
            String originHeader = req.getHeader("Origin");
            if (StringUtils.isNotEmpty(originHeader)) {
                URL url = null;
                String host = "";
                try {
                    url = new URL(originHeader);
                    host = url.getHost();
                }catch (Exception ex){
                }

                boolean isSuccess = false;
                if (null == url || "*".equals(accessControlAllowOrigin)) {
                    isSuccess = true;
                }else{
                    //判断请求的域是否存存在，或是子域名
                    String[] domains = accessControlAllowOrigin.split(",");
                    for (String domain : domains){
                        if (host.indexOf(domain) != -1){
                            isSuccess = true;
                            break;
                        }
                    }
                }

                if (isSuccess){
                    response.setHeader("Access-Control-Allow-Origin", originHeader);
                    response.setHeader("Access-Control-Allow-Credentials", "false");//若要返回cookie、携带seesion等信息则将此项设置我true
                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");//允许跨域的请求方式
                    response.setHeader("Access-Control-Max-Age", "3600");//预检请求的间隔时间
                    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");//允许跨域请求携带的请求头

                    //response.setHeader("strict-transport-security","max-age=16070400; includeSubDomains");//简称为HSTS。它允许一个HTTPS网站，要求浏览器总是通过HTTPS来访问它
                    response.setHeader("X-XSS-Protection", "1; mode=block");//1; mode=block：启用XSS保护，并在检查到XSS攻击时，停止渲染页面
                    response.setHeader("X-Frame-Options", "SAMEORIGIN");//SAMEORIGIN：不允许被本域以外的页面嵌入；
                    //response.setHeader("Content-Security-Policy", "default-src 'self';");//这个响应头主要是用来定义页面可以加载哪些资源，减少XSS的发生
                }else{
                    SysLog.logger.error("########Origin" + originHeader);//打印不存在的域名
                }
            }
        }

        Map parametersMap = servletRequest.getParameterMap();
        Iterator it = parametersMap.entrySet().iterator();
        //要过漏的注入参数
        String regx = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|" +
                "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String[] value = (String[]) entry.getValue();
            for (int i = 0; i < value.length; i++) {
                if (null != value[i] && value[i].matches(regx)) {//判断是否有注入的参数
                    SysLog.error("您输入的参数有非法字符，请输入正确的参数！");
                    servletRequest.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
                    servletRequest.setAttribute("pageUrl",req.getRequestURI());
                    servletRequest.getRequestDispatcher(servletRequest.getServletContext().getContextPath() + "/error").forward(servletRequest, servletResponse);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}

