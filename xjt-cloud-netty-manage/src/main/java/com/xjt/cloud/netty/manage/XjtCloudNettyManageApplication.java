package com.xjt.cloud.netty.manage;

import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.netty.manage.common.utils.SpringBootInitUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 以jar包启动，初使化入口类
 *
 * @author wangzhiwen
 * @date 2019/4/24 17:50
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不启用数据库配置
@ComponentScan(basePackages = {"com.xjt.cloud"})
public class XjtCloudNettyManageApplication {
    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudNettyManageApplication.class, args));
        // 启动完成后，调用的方法，可以做一些被使化设置
        SpringBootInitUtils.SpringBootServletInitializer();
    }
}
