package com.xjt.cloud.client.core.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.client.core.entity.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务Service
 *
 * @ClassName TaskService
 * @Author huanggc
 * @Date 2020-03-25
 * @Version 1.0
 */
public interface TaskService {
    /**
     * 查询 任务
     *
     *@Author huanggc
     *@Date 2020-03-25
     *@param  json
     *@return  com.xjt.cloud.commons.utils.Data
     */
    Data findTaskList(String json);
}
