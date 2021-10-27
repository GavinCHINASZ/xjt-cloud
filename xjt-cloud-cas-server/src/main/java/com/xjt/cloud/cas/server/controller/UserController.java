package com.xjt.cloud.cas.server.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Base64;
import java.util.Collections;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/9 0009 14:06
 * @Description: cas用户操作类
 */
@RestController
public class UserController {

    /**
     *
     * 功能描述:获取登录用户名
     *
     * @param principal
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 14:13
     */
    @RequestMapping(value = "/cas/getUserName", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @RequestMapping(value = "cas/getAuthentication")
    @ResponseBody
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication;
        }
        return null;
    }

    /**
     * 通过密码授权方式向授权服务器获取令牌
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<OAuth2AccessToken> login(String loginName, String password)  throws Exception{
        //Http Basic 验证
        String clientAndSecret = "123456:123456";
        //这里需要注意为 Basic 而非 Bearer
        clientAndSecret = "Basic "+ Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",clientAndSecret);
        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginName));
        map.put("password", Collections.singletonList(password));
        map.put("grant_type", Collections.singletonList("password"));
        map.put("scope", Collections.singletonList("write"));
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
        //获取 Token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OAuth2AccessToken> token = restTemplate.exchange("http://localhost:7080/oauth/token", HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
        return token;
    }

    @RequestMapping(value = "/accessDenied")
    @ResponseBody
    public String accessDenied(){
        return "该用户没有此权限";
    }

    @RequestMapping(value = "/oauthAuthorize")
    @ResponseBody
    public String oauthAuthorize(){
        return "授权成功！";
    }
}
