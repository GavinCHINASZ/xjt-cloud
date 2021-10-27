package com.xjt.cloud.device.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:31
 * @Description:设备管理业务接口
 */
public interface DeviceService {

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data findDeviceList(String json);

    /**
     *
     * 功能描述:查询设备列表 树结构
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/24
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceListTree(String json);

    /**
     *
     * 功能描述:查询设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data findDeviceListByAppId(String json);

    /**
     *
     * 功能描述:查询未关联水压设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data findDeviceListNotBoundIot(String json);

    /**
     *
     * 功能描述:查询关联水压设备的设备列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data findDeviceListBoundIot(String json);

    /**
     *
     * 功能描述:查询设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data findDevice(String json);

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data saveDevice(String json);

    /**
     *
     * 功能描述:新增设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data saveDevices(String json);

    /**
     *
     * 功能描述:修改设备信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Data modifyDevice(String json);

    /**
     *
     * 功能描述:查询设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    JSONObject findDeviceReport(String json);

    /**
     *
     * 功能描述:巡检点批量绑定设备
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    Data pointBindDevices(String json);

    /**
     *
     * 功能描述:巡检点批量清除设备绑定
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/27 15:40
     */
    Data pointClearBindDevices(String json);

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    void downloadDeviceRecord(HttpServletResponse response, String json);

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    void downloadDeviceRecordPicList(HttpServletResponse response, String json);

    /**
     *
     * 功能描述:下载设备档案
     *
     * @param response
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/9/10 11:50
     */
    void downloadDeviceRecordByList(HttpServletResponse response, String json);

    /**
     *
     * 功能描述:查询设备档案列表信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    Data findDeviceRecordList(String json);

    /**
     *
     * 功能描述:查询设备档案列表信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    Data findDeviceRecordCount(String json);

    /**
     *
     * 功能描述:查询设备档案基本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    Data findDeviceRecordBaseInfo(String json);

    /**
     *
     * 功能描述:以巡检点id查询设备档案基本信息总数
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/11 16:35
     */
    Data findDeviceRecordCountByPoint(String json);

    /**
     * 功能描述:查询 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceRemind(String json);

    /**
     * 功能描述:更新 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data updateDeviceRemind(String json);

    /**
     * 功能描述:保存 设备过期提醒
     *
     * @param json
     * @auther: huanggc
     * @date: 2020-03-27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveDeviceRemind(String json);

    /**
     * 功能描述: 设备过期提醒 每天定时任务
     *
     * @auther: huanggc
     * @date: 2020-03-30
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data deviceRemindTask();

    /**
     * 功能描述: 送修提醒 每天定时任务
     *
     * @auther: huanggc
     * @date: 2020-03-30
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data repairRemindTask();

    /**
     * @Description 查询设备操作记录饼图
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/2/22 14:05
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceReportPie(String json);
}
