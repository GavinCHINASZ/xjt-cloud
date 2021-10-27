package com.xjt.cloud.iot.core.service.service.linkage;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 联动设备表 service接口
 *
 * @author huanggc
 * @date 2020/08/25
 **/
public interface LinkageDeviceRelationService extends BaseService {

    /**
     * 功能描述:删除 联动设备
     *
     * @param deviceIds
     * @auther huanggc
     * @date 2020/09/01
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data deleteDeviceTableByDeviceId(Long[] deviceIds);
}
