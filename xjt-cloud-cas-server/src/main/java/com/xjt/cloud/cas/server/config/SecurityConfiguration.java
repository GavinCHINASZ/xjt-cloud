package com.xjt.cloud.cas.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /*@Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;*/
    @Autowired
    private AuthorizationParam authorizationParam;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
               // .successHandler(myAuthenticationSuccessHandler)
                .usernameParameter("email")
                .failureUrl("/login?error")
                .permitAll();
        //http.oauth2Login().successHandler(myAuthenticationSuccessHandler);
        http.cors().and().csrf().disable();
    }

    /***设置不拦截规则*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        List<String> permitUrls = Arrays.asList("/js/**", "/css/**", "/images/**", "/druid/**");
        List<String> permitUrlList = new ArrayList<>();
        permitUrlList.addAll(Arrays.asList(authorizationParam.getPermitUrls()));
        permitUrlList.addAll(permitUrls);
        String[] urls = new String[permitUrlList.size()];
        web.ignoring().antMatchers(permitUrlList.toArray(urls));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
