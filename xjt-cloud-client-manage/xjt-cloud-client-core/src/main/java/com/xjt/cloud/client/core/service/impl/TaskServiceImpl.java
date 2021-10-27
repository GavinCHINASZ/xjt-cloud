package com.xjt.cloud.client.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.client.core.common.ConstantsDeviceMsg;
import com.xjt.cloud.client.core.entity.*;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.client.core.common.ConstantsDevice;
import com.xjt.cloud.client.core.service.service.TaskService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 任务管理
 *
 * @ClassName TaskImplService
 * @Author huanggc
 * @Date 2020-03-25
 * @Version 1.0
 */
@Service
public class TaskServiceImpl extends AbstractService implements TaskService {

    /**
     * 查询 任务
     *
     *@Author huanggc
     *@Date 2020-03-25
     *@param  json
     *@return  com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findTaskList(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        Task task = JSON.parseObject(json, Task.class);

        JSONObject data = new JSONObject();
        if(!("XJT_DITIE_001").equals(task.getAppId())){
            data.put("msg", "appId错误");
            return asseData(data);
        }

        //String[] projectIds = ConstantsDeviceMsg.FIND_SCREEN_PROJECT_IDS.split(",");
        String project = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, task.getAppId());
        jsonObject.put("projectIds", project.split(","));

        JSONObject taskObj = HttpUtils.httpGet(ConstantsDeviceMsg.FIND_SCREEN_TASK_DATA_ANALYZE_URL, jsonObject.toString(), "json");

        data.put("totalCount", taskObj.get("totalCount"));
        data.put("taskJsonArray", taskObj.get("listObj"));
        return asseData(data);
    }

}
