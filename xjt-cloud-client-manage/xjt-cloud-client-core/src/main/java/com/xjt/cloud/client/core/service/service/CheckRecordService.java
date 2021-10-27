package com.xjt.cloud.client.core.service.service;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ClassName CheckRecordService
 * @Author dwt
 * @Date 2019-07-26 11:31
 * @Description 巡检记录
 * @Version 1.0
 */
public interface CheckRecordService {

    /**
     * 查询 巡检记录列表
     *
     * @Author huanggc
     * @Date 2020-03-25
     * @param json
     * @return Data
     */
    Data findCheckRecordList(String json);

}
