package com.xjt.cloud.client.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * 故障报修 Service接口
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
public interface FaultRepairRecordService extends BaseService {

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findFaultRepairRecordList(String json);

}
