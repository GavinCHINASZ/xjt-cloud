package com.xjt.cloud.cas.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description:登录用户验证类
 */
@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    /**
     *
     * 功能描述: 验证用户密码是否正确
     *
     * @param auth
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/10 15:37
     */
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
                @Override
                public String encode(CharSequence charSequence){
                    return charSequence.toString();
                }

                @Override
                public boolean matches(CharSequence charSequence, String s) {
                   PasswordEncoder encoder = new StandardPasswordEncoder();
                    return charSequence.equals(s) || encoder.matches(charSequence,s)  ;//判断密码是否正确，原始密码与编码值是否一样，或两个编码后的密码是否想等
                }
            });
    }
}
