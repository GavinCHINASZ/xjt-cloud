package com.xjt.cloud.ftp.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/8 18:02
 * @Description:ftp配置
 */
@ConfigurationProperties(prefix = "ftp")
public class FtpConfig {
    private String ip;
    private String user;
    private String pass;
    private String port;
    private String path;
    private String webPath;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }
}
