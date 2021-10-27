package com.xjt.cloud.cas.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description: 所有预请求直接通过
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private MyMetadataSourceService mySecurityMetadataSource;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().headers().frameOptions().disable()
                .and().csrf().disable();
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(mySecurityMetadataSource);//判断，该请求是否存在该请求路径
        // 配置登陆过滤器
        http.addFilterAt(filterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
        AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new Auth2ResponseExceptionTranslator());
        resource.authenticationEntryPoint(authenticationEntryPoint);
    }
}