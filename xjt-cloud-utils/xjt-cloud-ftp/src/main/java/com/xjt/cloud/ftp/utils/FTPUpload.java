package com.xjt.cloud.ftp.utils;

import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wangzhiwen
 * Date: 16-12-2
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FTPUpload {
    @Autowired
    private FtpConfig ftpConfig;
    @Autowired
    private UploadServer uploadServer;
    public static void main(String[] args) {
        FtpVo ftp = new FtpVo();
        ftp.setHost("192.168.0.100");
        ftp.setPort(21);
        ftp.setUsername("xjtftpuser");
        ftp.setPassword("123456");
        ftp.setBinaryTransfer(true);
        ftp.setPassiveMode(true);
        ftp.setEncoding("utf-8");
        FtpConnect ftpConnect=new FtpConnect(ftp);
        ftpConnect.put("D:\\文档\\123456789.xlsx".replace("\\","/"),"excel");
        //ftp.deleteFile("http://192.168.1.11:8060/app_store/2016/12/14/16/47/01/1481705221029.jpg","app_store");
    }
    /**
     * 从ftp服务器上删除一个文件
     * 该方法将自动关闭当前连接
     *
     * @param delFile
     * @return
     * @throws BaseServiceException
     */
    public boolean deleteFile(String delFile,String projectName) throws BaseServiceException {
        boolean flag = false;
        String isLocalUpload = PropertyUtils.getProperty("isLocalUpload");
        if (StringUtils.isNotEmpty(isLocalUpload) && "true".equals(isLocalUpload)) {//是否需要使用ftp上传 true保存本地  false ftp上传
            flag = deleteLocalFile(delFile, projectName);
        }else {
            flag = deleteRemoteFile(delFile, projectName);
        }
        return flag;
    }

    /**
     * 从ftp服务器上删除一个文件
     * 该方法将自动关闭当前连接
     *
     * @param delFile
     * @return
     * @throws BaseServiceException
     */
    public boolean deleteRemoteFile(String delFile,String projectName) throws BaseServiceException {
        boolean falg = false;
        try{
            String rmPath = ftpConfig.getPath();
            int indexNum = delFile.indexOf(projectName);
            String locapath;
            if(indexNum >= 0){
                locapath = rmPath+delFile.substring(indexNum);
            }else{
                throw BaseServiceException.initException(ConstantsMsg.FILE_NOT_EXIST + projectName, ServiceErrCode.FTP_ERR.getCode());
            }
            locapath = locapath.replace("\\","/");
            List<FtpVo> list= getFtpUpload();
            for(int i = 0;i < list.size();i++){
                FtpConnect ftpConnect = new FtpConnect(list.get(i));
                falg = ftpConnect.delete(locapath,true);
                if(falg == false){
                    throw BaseServiceException.initException(ConstantsMsg.DEL_FAIL + " 文件地址 "+ locapath, ServiceErrCode.FTP_ERR.getCode());
                }
            }

        }catch (Exception e) {
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.DEL_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
        return falg;
    }

    /**
     *
     * 功能描述: 删除本地文件
     *
     * @param delFile
     * @param projectName
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2020/6/15 9:55
     */
    private boolean deleteLocalFile(String delFile, String projectName) throws BaseServiceException {
        try {
            String rmPath = this.ftpConfig.getPath();
            int indexNum = delFile.indexOf(projectName);
            if (indexNum >= 0) {
                String locapath = rmPath + delFile.substring(indexNum);
                locapath = locapath.replace("\\", "/");
                File dest = new File(locapath);
                SysLog.error("删除文件地址＝＝＝＝＝＝＝" + dest.getPath());
                if (!dest.delete()) {
                    throw BaseServiceException.initException("提示：文件删除失败，请重新删除 文件地址 " + locapath, ServiceErrCode.FTP_ERR.getCode());
                }
                return true;
            } else {
                throw BaseServiceException.initException("提示：文件不存在，请重新选择" + projectName, ServiceErrCode.FTP_ERR.getCode());
            }
        } catch (Exception var10) {
            SysLog.error(var10);
            throw BaseServiceException.initException("提示：文件删除失败，请重新删除", ServiceErrCode.FTP_ERR.getCode());
        }
    }

    /**
     *
     * 功能描述: 以本地路径上传文件
     *
     * @param local
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/8/23 10:53
     */
    public boolean ftpUpload(String local)throws BaseServiceException{
        boolean ret=true;
        try{
            String rmPath = getRemotePath(local);
            InputStream inputStream = new FileInputStream(local);
            ftpUpload(inputStream, rmPath, rmPath, DateUtils.getFilePath(), local);
        }catch (Exception e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
        return ret;
    }

    /**
     *
     * 功能描述: 以文件与项目名称上传文件
     *
     * @param local
     * @param filename
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/8/23 10:53
     */
    public boolean ftpUpload(InputStream local,String filename)throws BaseServiceException{

        boolean ret=true;
        try{
            String rmPath = getRemotePath(filename);
            ftpUpload(local, rmPath, rmPath, DateUtils.getFilePath(), filename);
        }catch (BaseServiceException e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
        return ret;
    }


    /**
     *
     * 功能描述:以本地路径与项目名称上传文件
     *
     * @param local
     * @param projectName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/23 10:54
     */
    public String ftpUpload(String local,String projectName)throws BaseServiceException{
        String rpath;
        try{
            InputStream inputStream = new FileInputStream(local);
            String  rmPath = getRemotePath(projectName);
            rpath = ftpUpload(inputStream, rmPath, projectName, DateUtils.getFilePath(), local);
        }catch (Exception e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
        return rpath;
    }

    /**
     *
     * 功能描述:以文件与项目名称上传文件
     *
     * @param file
     * @param projectName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/23 10:54
     */
    public String ftpUploadFile(File file,String projectName)throws BaseServiceException{
        String rpath;
        try{
            InputStream inputStream = new FileInputStream(file);
            String  rmPath = getRemotePath(projectName);
            rpath = ftpUpload(inputStream, rmPath, projectName, DateUtils.getFilePath(), System.currentTimeMillis() + "." + uploadServer.getFileExtName(file.getName()));
        }catch (BaseServiceException e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }catch (FileNotFoundException ex){
            SysLog.error(ex);
            throw BaseServiceException.initException(ConstantsMsg.FILE_NOT_EXIST, ServiceErrCode.FTP_ERR.getCode());
        }
        return rpath;
    }

    /**
     * 读取FTP配置文件信息
     * @return
     * @throws BaseServiceException
     */
    public List<FtpVo> getFtpUpload() throws BaseServiceException{
        List<FtpVo> list=new ArrayList<FtpVo>();
        try{
            //判断ip是否有多个
            String[][][] ftplog;
            String[] ip = ftpConfig.getIp().split(",");
            //多个FTP连接
            if(ip.length > 1){
                String[] userNames = ftpConfig.getUser().split(",");
                int userNameNum = userNames.length;
                String[] passwords = ftpConfig.getPass().split(",");
                int passwordNum = passwords.length;
                String[] ports = ftpConfig.getPort().split(",");
                int portNum = ports.length;
                for(int i=0;i<ip.length;i++){
                    FtpVo ftp = new FtpVo();
                    ftp.setHost(ip[i]);
                    ftp.setPort(Integer.parseInt(portNum > 1? ports[i] : ports[0]));
                    ftp.setUsername(userNameNum > 1 ? userNames[i] : userNames[0]);
                    ftp.setPassword(passwordNum > 1 ? passwords[i] : passwords[0]);
                    ftp.setBinaryTransfer(true);
                    ftp.setPassiveMode(true);
                    ftp.setEncoding("utf-8");
                    list.add(ftp);
                }
            }else{
                //一个FTP连接
                FtpVo ftp = new FtpVo();
                ftp.setHost(ftpConfig.getIp());
                ftp.setPort(Integer.parseInt(ftpConfig.getPort()));
                ftp.setUsername(ftpConfig.getUser());
                ftp.setPassword(ftpConfig.getPass());
                //文件上传模式
                ftp.setBinaryTransfer(true);
                ftp.setPassiveMode(true);
                ftp.setEncoding("utf-8");
                list.add(ftp);
            }
        }catch (Exception e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.READ_CONFIG_FILE_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
        return  list;
    }

    /**
     *
     * 功能描述: 上传文件，可以同时上传多个ftp服务器
     *
     * @param inputStream
     * @param remotePath
     * @param projectName
     * @param webPath
     * @param fileName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/23 10:59
     */
    private String ftpUpload(InputStream inputStream, String remotePath, String projectName, String webPath,String fileName){
        List<FtpVo> list= getFtpUpload();
        String httpPath = "";
        for(int i = 0;i < list.size();i++){
            FtpConnect ftpConnect=new FtpConnect(list.get(i));

            boolean ret = ftpConnect.put(remotePath,inputStream);
            if(ret == false){
                httpPath = ftpConfig.getWebPath() + "/error.jpg";
            }else{
                httpPath = ftpConfig.getWebPath() + "/" + projectName + "/" + webPath + "/" + fileName;
            }
        }
        return httpPath;
    }

    /**
     *
     * 功能描述: 得到远程路径
     *
     * @param localPath
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/8/22 18:13
     */
    private String getRemotePath(String localPath){
        try{
            String remotePath = ftpConfig.getPath();
            String substr = System.getProperty("user.dir").replace("\\","/")+"/";
            localPath = localPath.replace("\\","/").replaceAll("//","/");
            int numc = localPath.indexOf(substr);
            if(numc >= 0){
                remotePath = remotePath + localPath.substring(numc + substr.length());
            }else{
                throw BaseServiceException.initException(ConstantsMsg.PATH_NULL, ServiceErrCode.FTP_ERR.getCode());
            }
            return remotePath;
        }catch (BaseServiceException e){
            SysLog.error(e);
            throw BaseServiceException.initException(ConstantsMsg.UPLOAD_FAIL, ServiceErrCode.FTP_ERR.getCode());
        }
    }
}
