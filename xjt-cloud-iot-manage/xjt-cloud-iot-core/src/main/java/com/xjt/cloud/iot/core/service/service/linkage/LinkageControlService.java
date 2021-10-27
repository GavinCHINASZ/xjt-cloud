package com.xjt.cloud.iot.core.service.service.linkage;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 联动控制 service接口
 * 
 * @author huanggc
 * @date 2019/09/19
 **/
public interface LinkageControlService extends BaseService {
    /**
     * 功能描述:查询 联动控制 列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/19
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findLinkageControlList(String json);

    /**
     * 功能描述: 删除
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data deleteLinkageControl(String json);

    /**
     * 功能描述: 新增
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveLinkageControl(String json);

    /**
     * 功能描述: 启用 或 停止(开或关)
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/26
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data openOrClose(String json);

    /**
     * 功能描述: 进入"水系统联运控制"新增页面
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/10/22
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data addPage(String json);

    /**
     * 功能描述: 水设备发生故障调用这个方法, 查询是否关联动设备,有关联的话联动设备报警
     *
     * @param deviceId 设备ID
     * @param sign 别名
     * @author huanggc
     * @date 2019/10/22
     * @return JSONObject
     */
    JSONObject deviceFault(Long deviceId, String sign);

    /**
     * 功能描述: 水设备发生故障调用这个方法, 查询是否关联动设备,有关联的话联动设备报警
     *
     * @param deviceIds 设备ID数组
     * @param sign 别名
     * @author huanggc
     * @date 2019/10/22
     * @return com.xjt.cloud.commons.utils.Data
     */
    JSONObject deviceFaults(Long[] deviceIds, String sign);
}
