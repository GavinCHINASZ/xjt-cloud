package com.xjt.cloud.video.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.video.core.VideoClient.HCNetSDK;
import com.xjt.cloud.video.core.dao.VideoDao;
import com.xjt.cloud.video.core.entity.Video;
import com.xjt.cloud.video.core.service.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName VideoServiceImpl
 * @Author dwt
 * @Date 2019-09-09 15:41
 * @Description video 实现类
 * @Version 1.0
 */
@Service
public class VideoServiceImpl extends AbstractService implements VideoService {

    @Autowired
    private VideoDao videoDao;

    private static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息
    private HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;// IP参数
    private NativeLong lUserID;// 用户句柄
    private static boolean bRet;
    static{
        bRet = hCNetSDK.NET_DVR_Init();// 初始化
    }

    /**
     *@Author: dwt
     *@Date: 2019-09-09 17:11
     *@Param: java.Lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 保存视频设备信息
     */
    @Override
    public Data saveVideo(String json) {
        Video video = JSONObject.parseObject(json,Video.class);
        video.setDeviceStatus(1);
        Integer a = videoDao.saveVideo(video);
        if(a > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:28
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 修改视频设备信息
     */
    @Override
    public Data modifyVideoDeviceById(String json) {
        Video video = JSONObject.parseObject(json,Video.class);
        Integer a = videoDao.updateVideoDeviceInfoById(video);
        if(a > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }
    /**
     *@Author: dwt
     *@Date: 2019-09-10 10:38
     *@Param: java.lang.String
     *@Return: com.xjt.cloud.commons.utils.Data
     *@Description 根据条件查询视频设备列表
     */
    @Override
    public Data getVideoDeviceList(String json) {
        Video video = JSONObject.parseObject(json,Video.class);
        List<Video> list = videoDao.findVideoDeviceList(video);
        return  asseData(list);
    }
    /**
     *@Author: dwt
     *@Date: 2019-09-11 15:23
     *@Param:
     *@Return: void
     *@Description 视频设备定时任务处理接口
     */
    @Override
    public void  NVRInfoHandle() {
        // =====================2. 注册服务开始====================================
        List<Video> videoList = videoDao.findParentIdIsNullList();
        if(videoList == null || videoList.size() <= 0){
            return;
        }
        Video v;
        List<Video> list = new ArrayList<>();
        for(Video vi : videoList){
            m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
            lUserID = hCNetSDK.NET_DVR_Login_V30(vi.getDeviceIp(), (short)(int)vi.getDevicePort(), vi.getUserName(), vi.getPassword(),m_strDeviceInfo);
            System.out.println(lUserID.intValue());
            long userID = lUserID.longValue();
            if (userID == -1) {
                vi.setDeviceStatus(1);
                continue;
            }
            vi.setDeviceStatus(0);
            IntByReference ibrBytesReturned = new IntByReference(0);//获取IP接入配置参数

            m_strIpparaCfg = new HCNetSDK.NET_DVR_IPPARACFG();
            m_strIpparaCfg.write();
            Pointer lpIpParaConfig = m_strIpparaCfg.getPointer();
            bRet = hCNetSDK.NET_DVR_GetDVRConfig(lUserID, HCNetSDK.NET_DVR_GET_IPPARACFG, new NativeLong(0), lpIpParaConfig, m_strIpparaCfg.size(), ibrBytesReturned);
            m_strIpparaCfg.read();
            if(bRet){
                //设备支持IP通道
                /*for (int iChannum = 0; iChannum < m_strDeviceInfo.byChanNum; iChannum++){
                    if(m_strIpparaCfg.byAnalogChanEnable[iChannum] == 1)
                    {
                        System.out.println("Camera"+(iChannum+1));
                    }
                }*/
                for(int iChannum =0; iChannum < HCNetSDK.MAX_IP_CHANNEL; iChannum++){
                    v = new Video();
                    v.setParentId(vi.getId());
                    v.setChannel(iChannum+1);
                    if (m_strIpparaCfg.struIPChanInfo[iChannum].byEnable == 1){
                        v.setDeviceStatus(0);
                    }else{
                        v.setDeviceStatus(1);
                    }
                    list.add(v);
                }
            }
            //如果已经注册,注销
            if (lUserID.longValue() > -1){
                hCNetSDK.NET_DVR_Logout_V30(lUserID);
            }
        }
        videoList.addAll(list);
        for(Video video : videoList){
            videoDao.updateVideoDeviceInfoById(video);
        }
    }

}
