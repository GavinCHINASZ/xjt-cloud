package com.xjt.cloud.iot.core.service.service.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 联动设备 service接口
 *
 * @author huanggc
 * @date  2019/09/19
 **/
public interface LinkageDeviceService extends BaseService {

    /**
     * 功能描述查询 联动设备 列表
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageDeviceList(String json);

    /**
     * 功能描述查询 联动设备 汇总
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageDeviceSummary(String json);

    /**
     * @Description 查询app首页联动设备信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject findUserProjectLinkageData(String json);

    /**
     * 功能描述导出 联动设备 列表
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date  2019/09/19
     */
    void downLinkageDeviceList(String json, HttpServletResponse response);

    /**
     * 功能描述删除 联动设备
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data deleteLinkageDevice(String json);

    /**
     * 功能描述绑定声光联动 设备
     *
     * @param json 参数
     * @author huanggc
     * @date  2020/08/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveLinkageDevice(String json);

    /**
     * 功能描述联动设备--更新
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data updateLinkageDevice(String json);

    /**
     * 功能描述联动设备--点击"新增"按钮跳转"联动设备新增"页面
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data jumpNewPage(String json);

    /**
     * 功能描述联动设备--根据"设备名称"查询"设备ID"
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findQrNo(String json);

    /**
     * 功能描述查询 联动设备
     *
     * @param json 参数
     * @author huanggc
     * @date  2019/10/22
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageDevice(String json);

    /** 
     * 校验联动设备是否在线离线
     * 
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/3/31 1029
     **/
    Data linkageDeviceOffLineTask();

    /**
     *  查询传感器ID是否存在
     *  
     * @param sensorId 传感器ID
     * @return JSONObject
     * @author zhangZaiFa
     * @date 2020/3/31 1130
     **/
    JSONObject isSensorPresence(String sensorId);

    /**
     *  联动设备数据解析
     *  
     * @param jsonObject JSONObject
     * @return java.util.List<JSONObject>
     * @author zhangZaiFa
     * @date 2020/3/31 1140
     **/
    List<JSONObject> linkageDeviceAnalyze(JSONObject jsonObject);

    /**
     * 功能描述查询 声光报警设备所在的建筑物
     *
     * @param json 参数
     * @author huanggc
     * @date  2020/08/21
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findLinkageDeviceBuilding(String json);

    /**
     * 功能描述查询 声光报警设备所有的巡查点
     *
     * @param json 参数
     * @author huanggc
     * @date  2020/08/21
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findLinkageDeviceCheckPoint(String json);

    /**
     * 功能描述查询单个 声光报警设备
     *
     * @param json 参数
     * @author huanggc
     * @date  2020/08/26
     * @return com.xjt.cloud.commons.utils.Data
     **/
    Data findLinkageDeviceView(String json);
}
