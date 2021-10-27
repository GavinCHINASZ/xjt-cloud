package com.xjt.cloud.admin.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.ftp.utils.FTPUpload;
import com.xjt.cloud.ftp.utils.UploadServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/5 09:42
 * @Description:
 */
@RestController
public class UploadController extends AbstractController {
    @Autowired
    private UploadServer uploadServer;
    @Autowired
    private FTPUpload fTPUpload;
    /**
     *
     * 功能描述:
     *
     * @param file
     * @param packageName
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/5 9:52
     */
    @RequestMapping(value = "/uploadFtpFile")
    public Data uploadFtpFile(MultipartFile file,String packageName){
        Data data = new Data();
        try {
            String path = uploadServer.getUploadPathInputSteam(file, packageName);
            if (StringUtils.isNotEmpty(path)){
                data.setStatus(Constants.SUCCESS_CODE);
                data.setMsg("上传文件成功");
                data.setObject(path);
            }else {
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("上传文件失败");
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        return data;
    }

    /**
     *
     * 功能描述:
     *
     * @param imgFile
     * @param packageName
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/5 9:52
     */
    @RequestMapping(value = "/kindeditorUploadFtpFile")
    public JSONObject kindeditorUploadFtpFile(MultipartFile imgFile, String packageName){
        JSONObject jsonObject = new JSONObject();
        try {
            String path = uploadServer.getUploadPathInputSteam(imgFile, packageName);
            if (StringUtils.isNotEmpty(path)){
                jsonObject.put("error",0);
                jsonObject.put("url",path);
                return jsonObject;
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        jsonObject.put("error",500);
        return jsonObject;
    }

    /**
     *
     * 功能描述:
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/16 9:13
     */
    @RequestMapping(value = "/delFtpFile")
    public Data delFtpFile(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (fTPUpload.deleteFile(jsonObject.getString("url"), jsonObject.getString("packageName"))){
            return Data.isSuccess();
        }else {
            return Data.isFail();
        }
    }
}
