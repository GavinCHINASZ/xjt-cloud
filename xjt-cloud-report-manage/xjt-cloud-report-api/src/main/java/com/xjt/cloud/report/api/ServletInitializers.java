package com.xjt.cloud.report.api;

import com.xjt.cloud.commons.utils.SpringBeanUtil;
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
public class ServletInitializers extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(XjtCloudReportApiManageApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        SpringBeanUtil.setApplicationContext(WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext));
    }
}
