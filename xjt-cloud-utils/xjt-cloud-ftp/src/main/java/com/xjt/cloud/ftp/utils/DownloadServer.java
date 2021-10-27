package com.xjt.cloud.ftp.utils;

import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/22 11:31
 * @Description: ftp下载文件
 */
@Service
public class DownloadServer {
    @Autowired
    private FTPUpload ftpUpload;
    @Autowired
    private FtpConfig ftpConfig;

    /**
     *
     * 功能描述: ftp下载文件
     *
     * @param url 文件请求和url
     * @param projectName 文件pack包名
     * @param outputStream 输出流对象
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2019/8/22 17:09
     */
    public boolean downLoadServerByUrl(String url, String projectName, OutputStream outputStream){
        try {
            String isLocalUpload = PropertyUtils.getProperty("isLocalUpload");
            if (StringUtils.isNotEmpty(isLocalUpload) && "true".equals(isLocalUpload)) {//是否需要使用ftp上传 true保存本地  false ftp上传
                String rmPath = ftpConfig.getPath();
                int indexNum = url.indexOf(projectName);
                if (indexNum >= 0) {
                    String locapath = rmPath + url.substring(indexNum);
                    locapath = locapath.replace("\\", "/");
                    File dest = new File(locapath);
                    FileInputStream in = new FileInputStream(dest);
                    int c;
                    while((c = in.read()) != -1){
                        outputStream.write(c);
                    }
                    return true;
                }
            }else{
                List<FtpVo> list = ftpUpload.getFtpUpload();
                for (int i = 0; i < list.size(); i++) {
                    FtpConnect ftpConnect = new FtpConnect(list.get(i));
                    String path = url.substring(url.indexOf(projectName));
                    path = ftpConfig.getPath() + path;
                    boolean ret = ftpConnect.get(path, outputStream);
                    return ret;
                }
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return false;
    }

    public static void main(String[] args) {
        DownloadServer d = new DownloadServer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        d.downLoadServerByUrl("http://ico.xiaojiantong.com:6020/qrcode/qr-logo.png","qrcode",outputStream);
        ByteArrayInputStream swapStream = new ByteArrayInputStream(outputStream.toByteArray());
        try {
            MultipartFile logoFile = new MockMultipartFile("file", "logoFile.png", "image/png", IOUtils.toByteArray(swapStream));
            System.out.println("文件大小" + logoFile.getSize());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
