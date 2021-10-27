package com.xjt.cloud.ftp.utils;

import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.ConstantsMsg;
import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * Created with IntelliJ IDEA.
 * User: wangzhiwen
 * Date: 16-12-15
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class FtpConnect {
    private ThreadLocal<FTPClient> ftpClientThreadLocal = new ThreadLocal<>();
    private FtpVo ftpVo;

    public FtpConnect(){}

    public FtpConnect(FtpVo ftpVo){
        this.ftpVo=ftpVo;
    }

    public FTPClient getFTPClient() throws BaseServiceException {
        if (ftpClientThreadLocal.get() != null && ftpClientThreadLocal.get().isConnected()) {
            return ftpClientThreadLocal.get();
        } else {
            FTPClient ftpClient = new FTPClient(); //构造一个FtpClient实例
            ftpClient.setControlEncoding(ftpVo.getEncoding()); //设置字符集
            connect(ftpClient); //连接到ftp服务器
            //设置为passive模式
            if (ftpVo.isPassiveMode()) {
                ftpClient.enterLocalPassiveMode();
            }
            setFileType(ftpClient); //设置文件传输类型

            try {
                ftpClient.setSoTimeout(ftpVo.getClientTimeout());
            } catch (SocketException e) {
                SysLog.error(e);
                throw BaseServiceException.initException(ConstantsMsg.CONNECTION_TIMEOUT, ServiceErrCode.FTP_ERR.getCode());
            }
            ftpClientThreadLocal.set(ftpClient);
            return ftpClient;
        }
    }


    /**
     * 设置文件传输类型
     *
     * @throws BaseServiceException
     * @throws IOException
     */
    private void setFileType(FTPClient ftpClient) throws BaseServiceException {
        try {
            if (ftpVo.isBinaryTransfer()) {
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            } else {
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
            }
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.FILE_TYPE_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
    }

    /**
     * 连接到ftp服务器
     *
     * @param ftpClient
     * @return 连接成功返回true，否则返回false
     * @throws BaseServiceException
     */
    private boolean connect(FTPClient ftpClient) throws BaseServiceException {
        try {
            ftpClient.connect(ftpVo.getHost(), ftpVo.getPort());

            // 连接后检测返回码来校验连接是否成功
            int reply = ftpClient.getReplyCode();

            if (FTPReply.isPositiveCompletion(reply)) {
                //登陆到ftp服务器
                if (ftpClient.login(ftpVo.getUsername(), ftpVo.getPassword())) {
                    setFileType(ftpClient);
                    return true;
                }
            } else {
                ftpClient.disconnect();
                throw BaseServiceException.initException(ConstantsMsg.CONNECTION_FAIL, ServiceErrCode.FTP_ERR.getCode());
            }
        } catch (IOException e) {
            SysLog.error(e);
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect(); //断开连接
                } catch (IOException e1) {
                    SysLog.error(e);
                    throw BaseServiceException.initException(ConstantsMsg.CONNECTION_FAIL, ServiceErrCode.FTP_ERR.getCode());
                }

            }
            throw BaseServiceException.initException(ConstantsMsg.CONNECTION_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
        return false;
    }

    public void disconnect() throws BaseServiceException {
        try {
            FTPClient ftpClient = getFTPClient();
            ftpClient.logout();
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
                ftpClient = null;
            }
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.CLOSE_SERVICE_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
    }
    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws BaseServiceException
     */
    public boolean put(String remoteAbsoluteFile, String localAbsoluteFile) throws BaseServiceException {
        return put(remoteAbsoluteFile, localAbsoluteFile, true);
    }
    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws BaseServiceException
     */
    public boolean put(String remoteAbsoluteFile, InputStream localAbsoluteFile) throws BaseServiceException {
        return put(remoteAbsoluteFile, localAbsoluteFile, true);
    }
    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @param autoClose 是否自动关闭当前连接
     * @return 成功时，返回true，失败返回false
     * @throws BaseServiceException
     */
    public boolean put(String remoteAbsoluteFile, String localAbsoluteFile, boolean autoClose) throws BaseServiceException {
        InputStream input = null;
        try {
            boolean filemk;
            //截取远程路径最后一个/
            int fnum = remoteAbsoluteFile.lastIndexOf("/");
            String folderPath = remoteAbsoluteFile.substring(0,fnum);
            //根据截取的字段判断路径是否存在
            boolean falg = getFTPClient().changeWorkingDirectory(folderPath);
            //false为路径不存在调用CeateDirecroty创建文件夹
            if(falg == false){
                //创建文件夹
                String path = PropertyUtils.getProperty("ftp.path");
                createDirecroty(folderPath.replace(path, ""),path);
            }
            //检查文件是否正确
            filemk = getFTPClient().changeWorkingDirectory("/" + folderPath);
            if(filemk == true){
                // 处理传输
                input = new FileInputStream(localAbsoluteFile);
                //上传  如果返回false 注意上传模式 二进制还是文本
                boolean  b=getFTPClient().storeFile(remoteAbsoluteFile, input);
                //删除本地文件
                File file=new File(localAbsoluteFile);
                //如果判断是文件夹不操作
                if(!file.isDirectory()){
                    if(file.isFile()){
                        //关闭文件流避免删除失败
                        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
                        dos.close();
                        boolean f = file.delete();
                        if(f==false){
                            throw BaseServiceException.initException(ConstantsMsg.DEL_FAIL  + localAbsoluteFile, ServiceErrCode.FTP_ERR.getCode());
                        }
                    }
                }
            }else{
                return false;
            }
            return true;
        } catch (FileNotFoundException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.FILE_NOT_EXIST, ServiceErrCode.FTP_ERR.getCode());
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                SysLog.error(e);
                throw BaseServiceException.initException(ConstantsMsg.CLOSE_FAIL, ServiceErrCode.FTP_ERR.getCode());
            }
            if (autoClose) {
                disconnect(); //断开连接
            }
        }
    }
    /**
     * 上传一个本地文件到远程指定文件
     *
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @param autoClose 是否自动关闭当前连接
     * @return 成功时，返回true，失败返回false
     * @throws BaseServiceException
     */
    public boolean put(String remoteAbsoluteFile, InputStream localAbsoluteFile, boolean autoClose) throws BaseServiceException {
        InputStream input = null;
        try {
            boolean filemk;
            //截取远程路径最后一个/
            int fnum = remoteAbsoluteFile.lastIndexOf("/");
            String folderPath = remoteAbsoluteFile.substring(0,fnum);

            //根据截取的字段判断路径是否存在
            boolean falg = getFTPClient().changeWorkingDirectory(folderPath);
            //false为路径不存在调用CeateDirecroty创建文件夹
            if(falg == false){
                //创建文件夹
                String path = PropertyUtils.getProperty("ftp.path");
                createDirecroty(folderPath.replace(path, ""),path);
            }
            //检查文件是否正确
            filemk = getFTPClient().changeWorkingDirectory("/" + folderPath);
            if(filemk==true){
                // 处理传输
                if(localAbsoluteFile!=null){
                    input = localAbsoluteFile;
                    //上传  如果返回false 注意上传模式 二进制还是文本
                    boolean  b=getFTPClient().storeFile(remoteAbsoluteFile, input);
                    if(b == false){
                        throw BaseServiceException.initException(ConstantsMsg.UPDATE_FILE_FAIL + remoteAbsoluteFile, ServiceErrCode.FTP_ERR.getCode());
                    }
                }else{
                    throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FILE_NULL + localAbsoluteFile, ServiceErrCode.FTP_ERR.getCode());
                }
            }else{
                return false;
            }
            return true;
        } catch (FileNotFoundException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.FILE_NOT_EXIST, ServiceErrCode.FTP_ERR.getCode());
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e) {
                SysLog.error(e);
                throw BaseServiceException.initException(ConstantsMsg.CLOSE_FAIL, ServiceErrCode.FTP_ERR.getCode());
            }
            if (autoClose) {
                disconnect(); //断开连接
            }
        }
    }
    /**
     * 创建文件夹
     * @param remotePath //需要创建文件夹的路径
     * @param pwdstr //创建后的路径 主要用于进入路径
     * @return
     */
    public boolean createDirecroty(String remotePath, String pwdstr) throws BaseServiceException{

        try{
            if(remotePath == null||"".equals(remotePath)){
                return true;
            }
            int startnum = remotePath.indexOf("/");
            String crpath;
            String endpath = "";
            if(startnum>0){
                crpath=remotePath.substring(0,startnum);
                endpath=remotePath.substring(startnum);
            }else if(startnum==0){
                remotePath=remotePath.substring(1);
                startnum=remotePath.indexOf("/");
                crpath=remotePath.substring(0,startnum);
                endpath=remotePath.substring(startnum);
            }else{
                crpath= remotePath;
            }
            //检查文件夹是否存在
            pwdstr = pwdstr + "/" + crpath;
            boolean flag = getFTPClient().changeWorkingDirectory(pwdstr);
            //不存在创建文件夹进入  存在直接进入
            if(flag == false){
                flag = getFTPClient().makeDirectory(crpath);
                flag = getFTPClient().changeWorkingDirectory(pwdstr);
            }else{
                flag = getFTPClient().changeWorkingDirectory(pwdstr);
            }
            //endpath的长度=0表示需要创建的文件夹没有了
            if(endpath.length() == 0){
                return true;
            }else {
                //减1去掉/线
                endpath = endpath.substring(1);
                //递归
                createDirecroty(endpath,pwdstr);
            }
        }catch (Exception e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.CREATE_FILE_FAIL,ServiceErrCode.FTP_ERR.getCode());
        } finally {
            try{
                if (true) {
                    //创建完后关闭连接
                    disconnect(); //断开连接
                }
            }catch (Exception e){
                SysLog.error(e);
            }
        }
        return true;
    }


    /**
     * 下载一个远程文件到本地的指定文件
     *
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @return 成功时，返回true，失败返回false
     * @throws BaseServiceException
     */
    public boolean get(String remoteAbsoluteFile, String localAbsoluteFile) throws BaseServiceException {
        return get(remoteAbsoluteFile, localAbsoluteFile, true);
    }

    /**
     * 下载一个远程文件到本地的指定文件
     *
     * @param remoteAbsoluteFile 远程文件名(包括完整路径)
     * @param localAbsoluteFile 本地文件名(包括完整路径)
     * @param autoClose 是否自动关闭当前连接
     *
     * @return 成功时，返回true，失败返回false
     * @throws BaseServiceException
     */
    public boolean get(String remoteAbsoluteFile, String localAbsoluteFile, boolean autoClose) throws BaseServiceException {
        OutputStream output = null;
        try {
            output = new FileOutputStream(localAbsoluteFile);
            return get(remoteAbsoluteFile, output, autoClose);
        } catch (FileNotFoundException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.FILE_NOT_EXIST, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                throw BaseServiceException.initException(ConstantsMsg.CLOSE_FAIL, ServiceErrCode.FTP_ERR.getCode());
            }
        }
    }

    /**
     * 下载一个远程文件到指定的流 处理完后记得关闭流
     *
     * @param remoteAbsoluteFile
     * @param output
     * @return
     * @throws BaseServiceException
     */
    public boolean get(String remoteAbsoluteFile, OutputStream output) throws BaseServiceException {
        return get(remoteAbsoluteFile, output, true);
    }

    /**
     * 下载一个远程文件到指定的流 处理完后记得关闭流
     *
     * @param remoteAbsoluteFile
     * @param output
     * @return
     * @throws BaseServiceException
     */
    public boolean get(String remoteAbsoluteFile, OutputStream output, boolean autoClose) throws BaseServiceException {
        try {
            FTPClient ftpClient = getFTPClient();
            // 处理传输
            return ftpClient.retrieveFile(remoteAbsoluteFile, output);
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.GET_SERVER_FILE_FAIL, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }

    /**
     * 从ftp服务器上删除一个文件
     *
     * @param delFile
     * @param autoClose 是否自动关闭当前连接
     *
     * @return
     * @throws BaseServiceException
     */
    public boolean delete(String delFile, boolean autoClose) throws BaseServiceException {
        try {
            getFTPClient().deleteFile(delFile);
            return true;
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.DEL_FAIL, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }

    /**
     * 批量删除
     * 该方法将自动关闭当前连接
     *
     * @param delFiles
     * @return
     * @throws BaseServiceException
     */
    public boolean delete(String[] delFiles) throws BaseServiceException {
        return delete(delFiles, true);
    }

    /**
     * 批量删除
     *
     * @param delFiles
     * @param autoClose 是否自动关闭当前连接
     *
     * @return
     * @throws BaseServiceException
     */
    public boolean delete(String[] delFiles, boolean autoClose) throws BaseServiceException {
        try {
            FTPClient ftpClient = getFTPClient();
            for (String s : delFiles) {
                ftpClient.deleteFile(s);
            }
            return true;
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.DEL_FAIL, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }

    /**
     * 列出远程默认目录下所有的文件
     *
     * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
     * @throws BaseServiceException
     */
    public String[] listNames() throws BaseServiceException {
        return listNames(null, true);
    }

    public String[] listNames(boolean autoClose) throws BaseServiceException {
        return listNames(null, autoClose);
    }

    /**
     * 列出远程目录下所有的文件
     *
     * @param remotePath 远程目录名
     * @param autoClose 是否自动关闭当前连接
     *
     * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组
     * @throws BaseServiceException
     */
    public String[] listNames(String remotePath, boolean autoClose) throws BaseServiceException {
        try {
            String[] listNames = getFTPClient().listNames(remotePath);
            return listNames;
        } catch (IOException e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.LIST_FILE_NAMES_FAIL, ServiceErrCode.FTP_ERR.getCode());
        } finally {
            if (autoClose) {
                disconnect(); //关闭链接
            }
        }
    }
}
