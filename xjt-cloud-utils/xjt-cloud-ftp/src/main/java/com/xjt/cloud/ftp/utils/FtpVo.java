package com.xjt.cloud.ftp.utils;

/**
 * Created with IntelliJ IDEA.
 * User: wangzhiwen
 * Date: 16-12-15
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 */
public class FtpVo {
    private String  host;
    private int     port;
    private String  username;
    private String  password;

    private boolean  binaryTransfer       = true;
    private boolean  passiveMode          = true;
    private String    encoding             = "UTF-8";
    private int       clientTimeout        = 1000 * 30;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBinaryTransfer() {
        return binaryTransfer;
    }

    public void setBinaryTransfer(boolean binaryTransfer) {
        this.binaryTransfer = binaryTransfer;
    }

    public boolean isPassiveMode() {
        return passiveMode;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }
}
