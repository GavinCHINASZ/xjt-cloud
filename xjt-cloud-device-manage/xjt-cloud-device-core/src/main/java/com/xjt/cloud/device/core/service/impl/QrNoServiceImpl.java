package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.device.core.common.ConstantsDevice;
import com.xjt.cloud.device.core.dao.device.CheckPointDao;
import com.xjt.cloud.device.core.dao.device.DeviceDao;
import com.xjt.cloud.device.core.dao.device.QrNoDao;
import com.xjt.cloud.device.core.entity.CheckPoint;
import com.xjt.cloud.device.core.entity.Device;
import com.xjt.cloud.device.core.entity.QrNo;
import com.xjt.cloud.device.core.entity.QrNoConfig;
import com.xjt.cloud.device.core.service.service.QrNoService;
import com.xjt.cloud.ftp.utils.DownloadServer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:32
 * @Description:二维码管理
 */
@Service
public class QrNoServiceImpl extends AbstractService implements QrNoService {
    @Autowired
    private QrNoDao qrNoDao;
    @Autowired
    private DownloadServer downloadServer;
    @Autowired
    private CheckPointDao checkPointDao;
    @Autowired
    private DeviceDao deviceDao;

    /**
     * 功能描述:批量生成二维码
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    @Override
    public Data generationQrNo(String json) {
        QrNo qrNo = JSONObject.parseObject(json, QrNo.class);
        int type = qrNo.getType();
        List<QrNo> list = generationQrNo(qrNo);
        if (1 == type) {//判断打印的是否是巡检点的码
            qrNoDao.saveCheckPointList(list);
        } else {
            qrNoDao.saveDeviceList(list);
        }
        return asseData(list);
    }

    /**
     * 功能描述:查询二维码列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:01
     */
    @Override
    public Data findQrNoList(String json) {
        QrNo qrNo = JSONObject.parseObject(json, QrNo.class);
        Integer totalCount = qrNo.getTotalCount();
        Integer pageSize = qrNo.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = qrNoDao.findQrNoListCount(qrNo);
        }
        List<QrNo> list = qrNoDao.findQrNoList(qrNo);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:下载二维码列表
     *
     * @param json
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    @Override
    public void downloadQrNoExcelByList(HttpServletResponse resp, String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        QrNo qrNo = JSONObject.parseObject(json, QrNo.class);
        List<QrNo> list = qrNoDao.findDownloadQrNoList(qrNo,ConstantsDevice.QR_NO_URL);
        if (CollectionUtils.isEmpty(list)){
            return ;
        }
        Long projectId = jsonObject.getLong("projectId");
        //从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "-二维码列表");
        String[] keys = {"rowNum","qrNo", "content", "qrNo"};
        /*String[] heads = {"序号","巡查码","内容","巡查码"};*/
        ExcelUtils.createAndDownloadExcelNotStyle(resp, list, keys, ConstantsDevice.QR_NO_MODEL_FILE_PATH,
                3, null, jsonObject, "1:0");
    }

    /**
     * 功能描述:查询二维码模版信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:37
     */
    @Override
    public Data findQrNoConfigList(String json) {
        QrNoConfig qrNoConfig = JSONObject.parseObject(json, QrNoConfig.class);
        List<QrNoConfig> list = qrNoDao.findQrNoConfigList(qrNoConfig);
        return asseData(list);
    }

    /**
     * 功能描述:生成二维码图片
     *
     * @param backFile 二维码背景图片
     * @param logoFile 二维码logo
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:40
     */
    @Override
    public Data generationQrNoImg(MultipartFile backFile, MultipartFile logoFile, String json) {
        QrNoConfig qrNoConfig = JSONObject.parseObject(json, QrNoConfig.class);
        int num = qrNoConfig.getNum();
        int type = qrNoConfig.getType();
        String[] qrNos = qrNoConfig.getQrNos();
        Long projectId = qrNoConfig.getProjectId();
        List<QrNo> list = new ArrayList<>();

        try {
            //初使化二维码参数
            if (null != qrNoConfig.getId()) {
                qrNoConfig = qrNoDao.findQrNoConfigById(qrNoConfig);
                if (StringUtils.isNotEmpty(qrNoConfig.getLogoUrl())) {
                    logoFile = downFileToMultipartFile(qrNoConfig.getLogoUrl());
                }
            } else {
                //把上传的MultipartFile类型文件转化成MockMultipartFile类型文件
                if (logoFile != null) {
                    ByteArrayInputStream logoByteArrayIn = new ByteArrayInputStream(logoFile.getBytes());
                    logoFile = new MockMultipartFile("file", "logoFile.png", "image/png", IOUtils.toByteArray(logoByteArrayIn));
                }else if(StringUtils.isNotEmpty(qrNoConfig.getLogoUrl())){
                    logoFile = downFileToMultipartFile(qrNoConfig.getLogoUrl());
                }
            }
        } catch (Exception ex) {
            SysLog.error(ex);
            return Data.isFail();
        }

        int width = qrNoConfig.getWidth();
        int height = qrNoConfig.getHeight();
        int logoWidth = qrNoConfig.getLogoWidth();
        int logoHeight = qrNoConfig.getLogoHeight();
        int margin = qrNoConfig.getMargin();
        int colorInt = qrNoConfig.getColorInt();
        int colorBackInt = qrNoConfig.getColorBackInt();
        int logoClipRadius = qrNoConfig.getLogoClipRadius();

        //组装要生成图片的二维码值
        List<QrNo> qrNoList;
        if (null == qrNos || 0 == qrNos.length) {//生成新的二维码
            QrNo qrNo = new QrNo();
            qrNo.setNum(num);
            qrNo.setProjectId(projectId);
            qrNo.setType(type);
            qrNoList = generationQrNo(qrNo);
            if (1 == type) {//判断打印的是否是巡检点的码
                qrNoDao.saveCheckPointList(qrNoList);
            } else {
                qrNoDao.saveDeviceList(qrNoList);
            }

        } else {//以已存在的码生成二维码
            qrNoList = new ArrayList<>();
            QrNo qrNo;
            for (String qrNoStr : qrNos) {
                qrNo = new QrNo();
                qrNo.setQrNo(qrNoStr);
                qrNoList.add(qrNo);
            }
        }

        for (QrNo qn : qrNoList) {//生成二维码图片
            qn.setImgStr(QrCodeUtils.generateQrCodeByFile(logoFile, ConstantsDevice.QR_NO_URL + qn.getQrNo(), width, height,
                     logoWidth, logoHeight, margin, colorInt, colorBackInt, logoClipRadius));
            list.add(qn);
        }
        return asseData(list);
    }

    /**
     * @Description 下载图片，并转成MultipartFile格式
     *
     * @param url 下载文件路径
     * @author wangzhiwen
     * @date 2020/11/9 16:47
     * @return org.springframework.web.multipart.MultipartFile
     */
    private MultipartFile downFileToMultipartFile(String url) throws Exception{
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //下载logo图片
            boolean downFile = downloadServer.downLoadServerByUrl(url, ConstantsDevice.QR_NO_FILE_PROJECT_NAME, outputStream);
            if (downFile) {
                //把下载的文件转化成MockMultipartFile类型文件
                ByteArrayInputStream swapStream = new ByteArrayInputStream(outputStream.toByteArray());
                return  new MockMultipartFile("file", "logoFile.png", "image/png", IOUtils.toByteArray(swapStream));
            }
        } catch (Exception ex) {
            throw ex;
        }
        return null;
    }

    /**
     * 功能描述:批量生成二维码
     *
     * @param qrNo
     * @return: List<String>
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    @Override
    public List<QrNo> generationQrNo(QrNo qrNo) {
        qrNo.setOldStatus(1);
        QrNo temQrNo = qrNoDao.findMaxQrNo(qrNo);

        List<QrNo> list = new ArrayList<>();
        Long qrNum = 0L;
        int num = qrNo.getNum();
        int type = qrNo.getType();
        String letters = (1 == type ? "AA" : "S");
        Long projectId = qrNo.getProjectId();

        if (null != temQrNo) {//判断是否存在二维码
            String maxQrNo = temQrNo.getQrNo();
            if (1 == type) {
                letters = maxQrNo.substring(0, 2);
                qrNum = Long.parseLong(maxQrNo.substring(2));
            } else {
                letters = maxQrNo.substring(0, 1);
                qrNum = Long.parseLong(maxQrNo.substring(1));
            }
        }
        String loginName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(loginName);
        String userName = getOrgUserName(userId, projectId);

        for (int i = 0; i < num; i++) {
            qrNo = new QrNo();
            qrNum++;
            if (qrNum.equals(ConstantsDevice.CHECK_POINT_MAX_QR_NO) || qrNum > ConstantsDevice.CHECK_POINT_MAX_QR_NO && 1 == type) {//判断巡检点二维码数量是否用完
                char c = (char) (letters.charAt(1) + 1);//给第二个字母加一个
                if (c >= 'Z') {//判断第二个字符是否是Z
                    letters = ((char) (letters.charAt(0) + 1)) + "" + "A";
                } else {
                    letters = letters.charAt(0) + "" + c;
                }
                qrNum = 1L;
            }

            qrNo.setQrNo(letters + asseQrNo(qrNum, type));
            qrNo.setProjectId(projectId);
            qrNo.setType(type);
            qrNo.setCreateUserName(userName);
            qrNo.setCreateUserId(userId);
            list.add(qrNo);
        }

        num = qrNoDao.saveQrNoList(list);
        if (0 < num) {
            return list;
        }
        return null;
    }

    /**
     * 功能描述: 生成一个二维码
     *
     * @param projectId
     * @param type      类型 1 巡检点码 2 设备码
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/7 16:01
     */
    @Override
    public String getOneQrNo(Long projectId, int type) {
        QrNo qrNo = new QrNo();
        qrNo.setNum(1);
        qrNo.setType(type);
        qrNo.setProjectId(projectId);
        List<QrNo> list = generationQrNo(qrNo);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0).getQrNo();
        }
        return null;
    }

    /**
     * 功能描述:保存二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    @Override
    public Data saveQrNoConfig(String json) {
        QrNoConfig qrNoConfig = JSONObject.parseObject(json, QrNoConfig.class);
        int num = qrNoDao.saveQrNoConfig(qrNoConfig);
        return asseData(num);
    }

    /**
     * 功能描述:修改二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    @Override
    public Data modifyQrNoConfig(String json) {
        QrNoConfig qrNoConfig = JSONObject.parseObject(json, QrNoConfig.class);
        int num = qrNoDao.modifyQrNoConfig(qrNoConfig);
        return asseData(num);
    }

    /**
     * 功能描述:删除二维码模版
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    @Override
    public Data delQrNoConfig(String json) {
        QrNoConfig qrNoConfig = JSONObject.parseObject(json, QrNoConfig.class);
        int num = qrNoDao.modifyQrNoConfig(qrNoConfig);
        return asseData(num);
    }

    /**
     * 功能描述:组装二维码字符串 0000001234
     *
     * @param qrNum
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/31 11:27
     */
    private String asseQrNo(Long qrNum, int type) {
        int len = qrNum.toString().length();
        String str = "00000000000000000000000000";
        int numLen;
        if (1 == type) {
            numLen = ConstantsDevice.CHECK_POINT_MAX_QR_NO.toString().length() - len;
        } else {
            numLen = ConstantsDevice.DEVICE_MAX_QR_NO.toString().length() - len;
        }
        str = str.substring(0, numLen) + qrNum;
        return str;
    }

    /**
     * @MethodName: findQrNoInformation
     * @Description: 查询二维码相关信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/4 10:26
     **/
    @Override
    public Data findQrNoInformation(String json, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        try {
            int sourceType = 0;//0 消检通APP扫码  1、其他信息扫码
            int type = 1;  //1、是巡查点  2、设备
            CheckPoint cp = JSONObject.parseObject(json, CheckPoint.class);
            if (!"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {// not
                sourceType = 1;
            }
            CheckPoint checkPoint = checkPointDao.findCheckPoint(cp);
            Device device = new Device();
            List<Device> devices;
            if (checkPoint != null) {//设备巡查点信息
                device.setPointQrNo(cp.getQrNo());
                devices = deviceDao.findDeviceList(device);
            } else {//设备信息
                type = 2;
                device.setQrNo(cp.getQrNo());
                devices = deviceDao.findDeviceList(device);
                if (devices.size() == 0) {
                    return asseData(600, "二维码未绑定设备信息");
                }
                cp.setId(devices.get(0).getCheckPointId());
                cp.setQrNo(null);
                checkPoint = checkPointDao.findCheckPoint(cp);
            }
            if (sourceType == 1) {
                JSONObject jsonObject = HttpUtils.httpGet(ConstantsDevice.PROJECT_DEVICE_QR_NO_URL, "{\"projectId\":" + checkPoint.getProjectId() + "}", "json");
                JSONObject obj = jsonObject.getJSONObject("obj");
                jsonObject = dataAnalysis(obj,devices,checkPoint);
                data.put("projectDeviceOrNo",jsonObject);
            } else {
                data.put("checkPoint", checkPoint);
                data.put("devices", devices);
                data.put("type", type);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return asseData(600, "不能识别二维码信息");
        }
        return asseData(data);
    }

    /**@MethodName: dataAnalysis
     * @Description: 数据分析
     * @Param: [jsonObject, devices, checkPoint]
     * @Return: com.alibaba.fastjson.JSONObject
     * @Author: zhangZaiFa
     * @Date:2019/12/5 10:30
     **/
    private JSONObject dataAnalysis(JSONObject jsonObject, List<Device> devices, CheckPoint checkPoint) {
        Boolean isProjectName = jsonObject.getBoolean("isProjectName");
        if(isProjectName){//不显示项目名称
            jsonObject.put("projectName",CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, checkPoint.getProjectId(), "projectName"));
        }
        Boolean isConstructionUnit =jsonObject.getBoolean("isConstructionUnit");
        if(!isConstructionUnit){//不显示建设单位
            jsonObject.remove("constructionUnit");
        }
        Boolean isProjectAddress =jsonObject.getBoolean("isProjectAddress");
        if(!isProjectAddress){//不显示项目地址
            jsonObject.remove("projectAddress");
        }
        Boolean isProjectIntroduce = jsonObject.getBoolean("isProjectIntroduce");
        if(!isProjectIntroduce){//不显示项目介绍
            jsonObject.remove("projectIntroduce");
        }
        Boolean isOnDutyPhone =  jsonObject.getBoolean("isOnDutyPhone");
        if(!isOnDutyPhone){//不显示值班电话
            jsonObject.remove("onDutyPhone");
        }
        Boolean isDeviceTaskDes = jsonObject.getBoolean("isDeviceTaskDes");
        //是否显示水设备物联网信息
        Boolean isWaterDevice = jsonObject.getBoolean("isWaterDevice");

        jsonObject.put("checkPointName",checkPoint.getPointName());
        jsonObject.put("checkPointQrNo",checkPoint.getQrNo());
        jsonObject.put("checkPointId",checkPoint.getId());
        jsonObject.put("buildingName",CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, checkPoint.getBuildingId(), "buildingName"));
        jsonObject.put("floorName",CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, checkPoint.getBuildingFloorId(), "floorName"));
        jsonObject.put("pointLocation",checkPoint.getPointLocation());
        jsonObject.put("buildingId",checkPoint.getBuildingId());
        jsonObject.put("buildingFloorId",checkPoint.getBuildingFloorId());
        Boolean isDeviceMethodDes = jsonObject.getBoolean("isDeviceMethodDes");
        List<JSONObject> deviceList = new ArrayList<>();
        for (Device dev: devices) {
            JSONObject obj = new JSONObject();
            if(isDeviceMethodDes){//不显示设备方法详情
                obj.put("deviceMethodDes",dev.getUseMethod());
            }
            String imgUrl = CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, dev.getDeviceTypeId(), "imgUrl");
            obj.put("imgUrl",imgUrl);
            obj.put("deviceCount",dev.getNum());
            SysLog.debug("-------设备物联网信息详情----------->"+isWaterDevice+"------------>"+dev.getDeviceType());
            if(isWaterDevice) {//不显示设备物联网信息详情
                obj.put("deviceStatus",dev.getDeviceStatus());
                if(dev.getDeviceType() == 2 || dev.getDeviceType() == 13 || dev.getDeviceType() == 14){
                    try {
                        JSONObject waterDevice = HttpUtils.httpGet(ConstantsDevice.WATER_DEVICE_REAL_TIME_VALUE_URL, "{\"deviceId\":" + dev.getId() + "}", "json");
                        SysLog.info(waterDevice.toJSONString());
                        obj.put("iotDevice",waterDevice);
                    }catch (Exception e){
                        SysLog.info(e.fillInStackTrace());
                    }
                }
            }
            obj.put("deviceId",dev.getId());
            obj.put("deviceQrNo",dev.getQrNo());
            obj.put("deviceName",dev.getDeviceName());
            deviceList.add(obj);
            jsonObject.put("deviceList",deviceList);
        }
        if(isDeviceTaskDes){//不显示设备任务详情
            jsonObject.put("checkPointStatus",(checkPoint.getStatus()-1));
            JSONObject taskObj = HttpUtils.httpGet(ConstantsDevice.TASK_CHECK_POINT_DEVICE_RECORD_URL+checkPoint.getProjectId(), "{\"checkPointId\":" + checkPoint.getId() + "}", "json");

            if(taskObj.getJSONObject("object")!=null ){
                JSONArray checkRecords = taskObj.getJSONObject("object").getJSONArray("checkRecords");
                if(checkRecords!=null && checkRecords.size()!=0){
                    String checkerName = checkRecords.getJSONObject(0).getString("checkerName");
                    String checkTime = checkRecords.getJSONObject(0).getString("createTime");
                    jsonObject.put("checkName",checkerName);
                    jsonObject.put("checkTime",checkTime);

                }

            }
        }
       /* Boolean isFaultRepairFun =  jsonObject.getBoolean("isFaultRepairFun");
        if(!isFaultRepairFun){//不显示故障故障报修功能

        }*/
        return jsonObject;
    }

    /**
     *
     * 功能描述: 二维码扫描
     *
     * @param qrNo
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/12/17 14:12
     */

    @Override
    public Data scanQrNo(String qrNo){
        QrNo qr = qrNoDao.findQrNo(qrNo);
        Data data = new Data();
        data.setObj(qr);
        if (qr != null){
            if (1 == qr.getType()){//判断是否是巡检点二维码
                CheckPoint checkPoint = new CheckPoint();
                checkPoint.setQrNo(qrNo);
                checkPoint = checkPointDao.findCheckPoint(checkPoint);
                data.setObject(checkPoint);
            }else {
                Device device = new Device();
                device.setQrNo(qrNo);
                device = deviceDao.findDevice(device);
                data.setObject(device);
            }
        }else{
            return asseData(qr);
        }
        return data;
    }
}
