package com.xjt.cloud.cas.client.adapters;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description:cas服务器配置
 */
@ConfigurationProperties(prefix = "authorizationparam")
public class AuthorizationParam {
    private String clientId; //客户端id
    private String secret; //(可信客户端需要)客户端密钥
    private String[] scopes; //客户受限范围
    private String authorizedGrantTypes; // 授权客户端使用的授权类型
 //  private String authorities;//授予客户端的权限
    private int tokenExpire;//token过期时间
    private int tokenRefresh;//token 刷新时间
    private String userPermissionsUrl;//
    private String permissionsUrl;
    private String userUrl;//获取用户信息url
    private String[] permitPermissionsUrls;
    //cas服务器请求路径
    private String casServerHostUrl;
    private String[] permitUrls;//不用token路径
    private Long projectType;//项目类型1　客户pc　2app　3管理后台

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public int getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(int tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public int getTokenRefresh() {
        return tokenRefresh;
    }

    public void setTokenRefresh(int tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

    public String getUserPermissionsUrl() {
        return userPermissionsUrl;
    }

    public void setUserPermissionsUrl(String userPermissionsUrl) {
        this.userPermissionsUrl = userPermissionsUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getPermissionsUrl() {
        return permissionsUrl;
    }

    public void setPermissionsUrl(String permissionsUrl) {
        this.permissionsUrl = permissionsUrl;
    }

    public String[] getPermitPermissionsUrls() {
        return permitPermissionsUrls;
    }

    public void setPermitPermissionsUrls(String[] permitPermissionsUrls) {
        this.permitPermissionsUrls = permitPermissionsUrls;
    }

    public String getCasServerHostUrl() {
        return casServerHostUrl;
    }

    public void setCasServerHostUrl(String casServerHostUrl) {
        this.casServerHostUrl = casServerHostUrl;
    }

    public String[] getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(String[] permitUrls) {
        this.permitUrls = permitUrls;
    }

    public Long getProjectType() {
        return projectType;
    }

    public void setProjectType(Long projectType) {
        this.projectType = projectType;
    }
}
