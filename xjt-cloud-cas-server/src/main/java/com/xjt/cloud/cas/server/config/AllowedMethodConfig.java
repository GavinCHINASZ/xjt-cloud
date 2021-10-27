package com.xjt.cloud.cas.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description: 支持get与post配置
 */
@Configuration
public class AllowedMethodConfig {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostConstruct
    public void reconfigure() {
        Set<HttpMethod> allowedMethods = new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
        tokenEndpoint.setAllowedRequestMethods(allowedMethods);
    }
}
