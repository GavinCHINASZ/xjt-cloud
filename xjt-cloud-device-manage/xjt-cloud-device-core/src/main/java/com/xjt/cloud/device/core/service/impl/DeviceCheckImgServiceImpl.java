package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.img.ImgComputeUtils;
import com.xjt.cloud.device.core.dao.device.DeviceCheckImgDao;
import com.xjt.cloud.device.core.entity.DeviceCheckImg;
import com.xjt.cloud.device.core.service.service.DeviceCheckImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName DeviceCheckImgServiceImpl
 * @Description  设备图片检测管理
 * @Author wangzhiwen
 * @Date 2021/3/8 9:53
 **/
@Service
public class DeviceCheckImgServiceImpl extends AbstractService implements DeviceCheckImgService {
    @Autowired
    private DeviceCheckImgDao deviceCheckImgDao;

    /**
     * @Description 查询设备检测图片的key
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/11 17:06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDeviceCheckImgKey(String json){
        DeviceCheckImg deviceCheckImg = JSONObject.parseObject(json, DeviceCheckImg.class);
        return asseData(deviceCheckImgDao.findDeviceCheckImg(deviceCheckImg));
    }

    /**
     * @Description 上传设备检测图片的key
     *
     * @author wangzhiwen
     * @date 2021/3/8 9:58
     * @return com.xjt.cloud.commons.utils.Data
     */
    public Data uploadDeviceCheckImgKey(String json){
        DeviceCheckImg deviceCheckImg = JSONObject.parseObject(json, DeviceCheckImg.class);
        int num ;
        if (deviceCheckImg.getId() != null){
            num = deviceCheckImgDao.modifyDeviceCheckImg(deviceCheckImg);
        }else {
            num = deviceCheckImgDao.saveDeviceCheckImg(deviceCheckImg);
        }
        return asseData(num);
    }

    /**
     * @Description 上传检测图片并计算
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/8 10:02
     * @return com.xjt.cloud.commons.utils.Data
     */
    public Data uploadDeviceCheckImgAndCompute(String json){
        DeviceCheckImg deviceCheckImg = JSONObject.parseObject(json, DeviceCheckImg.class);
        DeviceCheckImg keyDeviceCheckImg = deviceCheckImgDao.findDeviceCheckImg(deviceCheckImg);
        int differ = keyDeviceCheckImg.getDiffer();
        String checkImg = StringUtils.urlDecode(deviceCheckImg.getCheckImg());
        boolean isOk = checkImg(checkImg,keyDeviceCheckImg.getImgKey1(),differ);
        if (isOk){
            return asseData(200);
        }
        isOk = checkImg(checkImg,keyDeviceCheckImg.getImgKey2(),differ);
        if (isOk){
            return asseData(200);
        }
        isOk = checkImg(checkImg,keyDeviceCheckImg.getImgKey3(),differ);
        if (isOk){
            return asseData(200);
        }
        return Data.isFail();
    }

    /**
     * @Description 检测图片是否布在
     *
     * @param img
     * @param imgKey
     * @param differ
     * @author wangzhiwen
     * @date 2021/3/15 15:22
     * @return boolean
     */
    private boolean checkImg(String img,String imgKey,int differ){
        if (StringUtils.isNotEmpty(imgKey)){
            return ImgComputeUtils.imgComparisonByBase64(img,StringUtils.urlDecode(imgKey),differ);
        }
        return false;
    }
}
