package com.xjt.cloud.cas.client.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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
//@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyMetadataSourceService mySecurityMetadataSource;
    @Autowired
    private AuthorizationParam authorizationParam;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/logout").permitAll() // 根据配置文件放行无需验证的url，只是不验证token
                .anyRequest().authenticated()
                .and().headers().frameOptions().disable();
        //判断，该请求是否存在
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(mySecurityMetadataSource);//判断，该请求是否存在该请求路径
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        // 配置登陆过滤器
        http.addFilterAt(filterSecurityInterceptor, FilterSecurityInterceptor.class);

        http.cors().and().csrf().disable();
    }

    /***设置不拦截规则*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/favicon.ico", "/oauth/**", "/login**", "/userLogout", "/error**");
        List<String> permitUrls = Arrays.asList("/favicon.ico", "/oauth/**", "/userLogout", "/error**");
        List<String> permitUrlList = new ArrayList<>();
        permitUrlList.addAll(Arrays.asList(authorizationParam.getPermitUrls()));
        permitUrlList.addAll(permitUrls);
        String[] urls = new String[permitUrlList.size()];
        web.ignoring().antMatchers(permitUrlList.toArray(urls));
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");//必须添加配置，如不配置，跨域时，会报跨域错误
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

    /**
     *
     * 功能描述:过滤器配置
     *
     * @param
     * @return: org.springframework.security.access.AccessDecisionManager
     * @auther: wangzhiwen
     * @date: 2019/5/13 0013 10:57
     */
    @Bean(name = "accessDecisionManager")
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new AuthenticatedVoter());
        MyAccessDecisionManager accessDecisionManager = new MyAccessDecisionManager(decisionVoters);
        return accessDecisionManager;
    }
}
