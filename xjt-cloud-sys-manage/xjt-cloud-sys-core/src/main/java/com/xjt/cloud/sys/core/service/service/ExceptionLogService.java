package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName ExceptionLogService
 * @Description
 * @Author wangzhiwen
 * @Date 2020/9/14 9:36
 **/
public interface ExceptionLogService {
    /**
     * @param json
     * @return Data
     * @Description 保存错误信息
     * @author wangzhiwen
     * @date 2020/9/14 9:48
     */
   Data saveExceptionLog(String json);
}
