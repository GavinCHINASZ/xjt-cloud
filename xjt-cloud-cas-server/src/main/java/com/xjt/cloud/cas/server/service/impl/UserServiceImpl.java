package com.xjt.cloud.cas.server.service.impl;

import com.xjt.cloud.cas.server.commons.utils.ConstantsCasServer;
import com.xjt.cloud.cas.server.config.AuthorizationParam;
import com.xjt.cloud.cas.server.dao.sys.UserDao;
import com.xjt.cloud.cas.server.entity.SysUser;
import com.xjt.cloud.cas.server.service.service.UserService;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Primary
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorizationParam authorizationParam;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userDao.selectOne(username);
        if (StringUtils.isNotEmpty(ConstantsCasServer.REMOVE_ACCESS_TOKEN) && "true".equals(ConstantsCasServer.REMOVE_ACCESS_TOKEN)) {
            TokenStore tokenStore = SpringBeanUtil.getBean(TokenStore.class);
            //清除原有登录信息
            Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(authorizationParam.getClientId(), username);
            if (CollectionUtils.isNotEmpty(tokens)) {
                for (OAuth2AccessToken oauth2AccessToken : tokens) {
                    tokenStore.removeAccessToken(oauth2AccessToken);
                    tokenStore.removeRefreshToken(oauth2AccessToken.getRefreshToken());
                }
            }
        }

        if(sysUser==null){
            throw BaseServiceException.initException(ConstantsMsg.USER_NOT_EXIST, ServiceErrCode.REQ_PARAM_ERR.getCode());
        }else{
            username = sysUser.getUsername();

            //取得用户信息
            try {
                HttpUtils.httpGet(authorizationParam.getUserUrl() + StringUtils.urlEncode("{\"loginName\":\"" + username + "\"}"));
            }catch (Exception ex){
                SysLog.error(ex);
                throw BaseServiceException.initException(ConstantsMsg.GET_USER_FAIL, ServiceErrCode.REQ_PARAM_ERR.getCode());
            }
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();//用户权限信息对象列表 不使用自带的鉴权
            User user = new User(username, sysUser.getPassword(), true, true, true, true, grantedAuthorities);
            return user;
        }
    }
}
