package com.xjt.cloud.device.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:31
 * @Description:巡检点管理业务接口
 */
public interface CheckPointService {
    /**
     *
     * 功能描述:查询巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Data findCheckPointList(String json);

    /**
     *
     * 功能描述:查询巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Data findCheckPoint(String json);

    /**
     *
     * 功能描述:添加巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Data saveCheckPoint(String json);

    /**
     *
     * 功能描述:修改巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Data modifyCheckPoint(String json);

    /**
     *
     * 功能描述:删除巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Data delCheckPoint(String json);

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    Data findCheckPointAndDeviceReport(String json);

    /**
     * @Description 查询app首页设备信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectDeviceData(String json);

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    Data findCheckPointAndDeviceReportGroupBuilding(String json);

    /**
     *
     * 功能描述: 上传设备excel表格
     *
     * @param file
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/5 9:29
     */
    Data uploadPointDeviceExcel(String json, MultipartFile file);

    /**
     *
     * 功能描述:下载巡检点设备列表
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    void downloadPointDeviceExcel(HttpServletResponse resp, String json);

    /**
     *
     * 功能描述:查询未关联水压设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    Data findCheckPointNotBoundIot(String json);

    /**
     *
     * 功能描述:查询已关联物联设备的建物物楼层列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    Data findBuildingFloorBoundIot(String json);

    /**
     *
     * 功能描述:查询关联水压设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    Data findCheckPointBoundIot(String json);

    /***@MethodName: findProjectCheckPointCount
     * @Description: 查询当前项目巡检点总数
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/19 9:46
     **/
    Data findProjectCheckPointCount(String json);
}
