package com.xjt.cloud.iot.core.service.service.linkage;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 联动设备表 service接口
 *
 * @author huanggc
 * @date 2020/08/25
 **/
public interface LinkageDeviceTableService extends BaseService {

    /**
     * 功能描述:查询 联动设备 列表
     *
     * @param json
     * @auther huanggc
     * @date 2020/08/25
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findDeviceTableList(String json);

}
