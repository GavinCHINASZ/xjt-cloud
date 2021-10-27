package com.xjt.cloud.fault.core.service.service;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * 报修进度 Service接口
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
public interface RepairProgressService extends BaseService {

    /**
     * 功能描述:查询 报修进度 列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/02
     * @return: com.xjt.cloud.commons.utils.Data
     */
    Data findRepairProgressList(String json);
}
