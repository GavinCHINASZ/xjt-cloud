package com.xjt.cloud.task.core.service.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName TaskSummaryService
 * @Author huanggc
 * @Date 2019-11-25
 * @Description: 任务汇总
 * @Version 1.0
 */
public interface TaskSummaryIoService {
    /**
     *@Author: huanggc
     *@Date: 2019-11-26
     *@Param: json
     *@Return: Data
     *@Description PC月任务汇总--任务概览表 导出详情
     */
    void downTaskTableDetails(String json, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
