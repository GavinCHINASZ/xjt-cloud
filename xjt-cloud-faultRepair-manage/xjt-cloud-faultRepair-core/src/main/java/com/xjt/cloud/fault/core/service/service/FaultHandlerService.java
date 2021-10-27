package com.xjt.cloud.fault.core.service.service;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 故障处理人/维修人 Service接口
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
public interface FaultHandlerService extends BaseService {

    /**
     * 功能描述:查询 故障处理人 列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findFaultHandlerList(String json);
}
