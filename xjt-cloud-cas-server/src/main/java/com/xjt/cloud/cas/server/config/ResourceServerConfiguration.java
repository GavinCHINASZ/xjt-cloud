package com.xjt.cloud.cas.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description: 所有预请求直接通过
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /*@Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable();
        //http.formLogin().successHandler(myAuthenticationSuccessHandler);
        //http.oauth2Login().successHandler(myAuthenticationSuccessHandler);
    }
}