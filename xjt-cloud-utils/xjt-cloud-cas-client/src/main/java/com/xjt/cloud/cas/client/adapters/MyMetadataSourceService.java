package com.xjt.cloud.cas.client.adapters;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/5/9 0009 14:10
 * @Description: 权限信息数据
 */
@Service
public class MyMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private AuthorizationParam authorizationParam;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 对请求路径进行权限判断
     *
     * @param object 请求信息对象
     * @return Collection<ConfigAttribute> 成功时返回null
     * @throws IllegalArgumentException 各权限处理的错误信息
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws BaseServiceException {
        String[] paths = authorizationParam.getPermitPermissionsUrls();
        AntPathRequestMatcher matcher;
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        //判断请求路径是否存在于，不需要权限的配置中，该配置在资源文件中配置
        if (paths != null && paths.length >= 1) {
            for (String path : paths){
                if(StringUtils.isEmpty(path)){
                    continue;
                }
                matcher = new AntPathRequestMatcher(path);
                if (matcher.matches(request)) {
                    ArrayList array = new ArrayList<>();
                    array.add(new SecurityConfig(path));
                    return null;//成功时返回null
                }
            }
        }

        //用户url鉴权
        String url = request.getServletPath();
        String json = getPermissionUrls(url);
        if (StringUtils.isNotEmpty(json) && json.indexOf("," + url + ",") != -1){
            return null;
        }

        //判断，该请求是否存在
        AntPathRequestMatcher matcherAll;
        List<String> list = loadResourceDefine();
        for (String path:list) {
            if(StringUtils.isEmpty(path)){
                continue;
            }
            matcher = new AntPathRequestMatcher(path);
            matcherAll = new AntPathRequestMatcher(path + "/**");
            if (matcher.matches(request) || matcherAll.matches(request)) {
                //throw BaseServiceException.initException(ConstantsMsg.USER_NOT_PERMISSION + url + "?" + request.getQueryString(), ServiceErrCode.REQ_PERMISSION_ERR.getCode());
                throw BaseServiceException.initException(ConstantsMsg.USER_NOT_PERMISSION, ServiceErrCode.REQ_PERMISSION_ERR.getCode());
            }
        }

        //throw BaseServiceException.initException(ConstantsMsg.NOT_URL + url + "?" + request.getQueryString(), ServiceErrCode.REQ_URL_ERR.getCode());
        throw BaseServiceException.initException(ConstantsMsg.NOT_URL, ServiceErrCode.REQ_URL_ERR.getCode());
    }

    /**
     *
     * 功能描述: 得到用户权限url列表
     *
     * @param url
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/12/16 10:10
     */
    private String getPermissionUrls(String url){
        String projectId = url.substring(url.lastIndexOf("/") + 1);
        if (!StringUtils.isInteger(projectId)){
            projectId = "0";
        }

        String loginName = SecurityUserHolder.getUserName();
        String key = "userPermissionList_" + authorizationParam.getProjectType() + "_" + loginName + "_" + projectId;
        String json = redisUtils.getString(key);
        if (StringUtils.isEmpty(json)){
            try {
                HttpUtils.httpGet(authorizationParam.getUserPermissionsUrl() + StringUtils.urlEncode("{\"loginName\":\"" + loginName + "\"}"));
                json = redisUtils.getString(key);
            }catch (Exception ex){
                SysLog.error(ex);
            }
        }
        return json;
    }

    /**
     *
     * 功能描述:加载权限表中所有权限，不论用户是否有该权限
     *
     * @param
     * @return: java.util.Map
     * @auther: wangzhiwen
     * @date: 2019/5/17 0017 16:09
     */
    private List<String> loadResourceDefine() {
        String key = "permissionList_" + authorizationParam.getProjectType();
        Object cacheObj = redisUtils.get(key);//从redis中得到权限信息列表
        if (null == cacheObj){
            HttpUtils.httpGet(authorizationParam.getPermissionsUrl());//得到所有的权限列表
            cacheObj = redisUtils.get(key);//从redis中得到权限信息列表
        }
        if (null == cacheObj){
            SysLog.error("url路径列表请求失败，url＝"  + authorizationParam.getPermissionsUrl());
            throw BaseServiceException.initException(ConstantsMsg.NOT_URL, ServiceErrCode.REQ_URL_ERR.getCode());
        }
        List<String> list = JSONObject.parseArray(cacheObj.toString(),String.class);//把从缓存中取得的权限列表转成list
        return  list;
    }

    @Bean(name = "serializationStrategy")
    public RedisTokenStoreSerializationStrategy initRedisTokenStoreSerializationStrategy(){
        return new JdkSerializationStrategy();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
