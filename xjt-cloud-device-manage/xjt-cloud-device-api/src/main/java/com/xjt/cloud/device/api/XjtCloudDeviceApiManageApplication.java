package com.xjt.cloud.device.api;

import com.xjt.cloud.cas.client.adapters.AuthorizationParam;
import com.xjt.cloud.commons.utils.SpringBeanUtil;
import com.xjt.cloud.ftp.utils.FtpConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/27 09:41
 * @Description:
 */
@SpringBootApplication(scanBasePackages = "com.xjt.cloud")//第三方包扫描路径
@MapperScan("com.xjt.cloud.*.*.dao")
@ComponentScan({"com.xjt.cloud"})
@EnableCaching
@EnableConfigurationProperties({AuthorizationParam.class, FtpConfig.class})
@ServletComponentScan(basePackages ="com.xjt.cloud.commons.filter")
public class XjtCloudDeviceApiManageApplication {
    public static void main(String[] args) {
        SpringBeanUtil.setApplicationContext(SpringApplication.run(XjtCloudDeviceApiManageApplication.class, args));
    }
}
