package com.xjt.cloud.netty.manage;

import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.netty.manage.common.utils.SpringBootInitUtils;
import com.xjt.cloud.netty.manage.netty.NettyInit;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:以war包启动，初使化入口类
 */
@SpringBootApplication
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(XjtCloudNettyManageApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        SpringBeanUtil.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext));
        SpringBootInitUtils.SpringBootServletInitializer();//启动完成后，调用的方法，可以做一些被使化设置
    }
}
