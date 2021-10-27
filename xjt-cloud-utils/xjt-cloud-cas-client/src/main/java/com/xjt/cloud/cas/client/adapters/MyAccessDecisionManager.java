package com.xjt.cloud.cas.client.adapters;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/9 0009 14:11
 * @Description: 权限管理类
 */
public class MyAccessDecisionManager extends AbstractAccessDecisionManager {

    public MyAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    /**
     * @param authentication   UserService 权限信息集合
     * @param object           equset message
     * @param configAttributes MyMetadataSourceService
     * @throws AccessDeniedException,InsufficientAuthenticationException,BadCredentialsException 加入权限表后，则返回给 decide 方法 其它情况 不做控制
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException, BadCredentialsException, DisabledException {
        return;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
