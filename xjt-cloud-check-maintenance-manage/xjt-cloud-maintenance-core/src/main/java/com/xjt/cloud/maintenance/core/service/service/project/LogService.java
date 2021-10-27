package com.xjt.cloud.maintenance.core.service.service.project;

import com.xjt.cloud.commons.utils.Data;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-08-20 16:37
 **/
public interface LogService {
    /**@MethodName: findByProjectLogList 按条件搜索项目日志
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/8 14:17
     **/
    Data findByProjectLogList(String json);

    /**@MethodName: findByProjectLogTypeList 查询项目日志类型
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/8 14:16 
     **/
    Data findByProjectLogTypeList(String json);

    /**@MethodName: downloadProjectLog
     * @Description: 导出项目日志
     * @Param: [response, json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/3/25 9:59
     **/
    void downloadProjectLog(HttpServletResponse response, String json);
}
