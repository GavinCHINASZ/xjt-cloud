package com.xjt.cloud.iot.core.service.service.smoke;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * SmokeDeviceService 烟感设备service接口
 *
 * @author huanggc
 * @date 2020/07/15
 **/
public interface SmokeDeviceService extends BaseService {
    /**
     * 功能描述 查询烟感设备列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeDeviceList(String json);

    /**
     * 功能描述 查询烟感设备列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeDevice(String json);

    /**
     * 功能描述 导出烟感设备列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/20
     * @return: com.xjt.cloud.commons.utils.Data
     */
    void downSmokeDeviceList(String json, HttpServletResponse response);

    /**
     * 功能描述: 增加 烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data saveSmokeDevice(String json);

    /**
     * 功能描述: 更新 烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data updateSmokeDevice(String json);

    /**
     * 功能描述: 删除 烟感设备
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/15
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data deletedSmokeDevice(String json);

    /**
     * 功能描述: 烟感设备汇总报表 饼图
     *
     * @param json
     * @auther huanggc
     * @date 2020/07/27
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findSomkeDeviceSummaryReport(String json);

    /**
     * @Description 查询app首页智能烟感信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectSmokeData(String json);

    /**
     * 功能描述: 烟感设备离线任务
     *
     * @auther huanggc
     * @date 2020/08/04
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data smokeOffLineTask();

    /**
     * 功能描述: 查询支持的平台
     *
     * @auther huanggc
     * @date 2020/08/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findSmokeCloud();

    /**
     *
     * 功能描述: 上传 excel表格 批量绑定烟感
     *
     * @param file
     * @return com.xjt.cloud.commons.utils.Data
     * @auther huanggc
     * @date 2020/08/18
     */
    Data uploadPointDeviceExcel(String json, MultipartFile file);

    /**
     * 功能描述: 智能无线烟感 批量导入表格模板下载
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @auther huanggc
     * @date 2020/08/21
     */
    Data downSmokeModelExcel(String json, HttpServletResponse response);
}
