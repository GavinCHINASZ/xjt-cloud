package com.xjt.cloud.client.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.client.core.common.ConstantsDeviceMsg;
import com.xjt.cloud.client.core.entity.FaultRepairRecord;
import com.xjt.cloud.client.core.entity.Task;
import com.xjt.cloud.client.core.service.service.FaultRepairRecordService;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 故障报修
 *
 * @Author huanggc
 * @Date 2020-03-25
 **/
@Service
public class FaultRepairRecordServiceImpl extends AbstractService implements FaultRepairRecordService {

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json
     * @auther huanggc
     * @date 2019/09/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findFaultRepairRecordList(String json) {
        FaultRepairRecord faultRepairRecord = JSON.parseObject(json, FaultRepairRecord.class);
        JSONObject data = new JSONObject();
        if(!("XJT_DITIE_001").equals(faultRepairRecord.getAppId())){
            data.put("msg", "appId错误");
            return asseData(data);
        }

        JSONObject jsonObject = JSONObject.parseObject(json);

        //String[] projectIds = ConstantsDeviceMsg.FIND_SCREEN_PROJECT_IDS.split(",");
        String project = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, faultRepairRecord.getAppId());
        jsonObject.put("projectIds", project.split(","));

        JSONObject faultObj = HttpUtils.httpGet(ConstantsDeviceMsg.FIND_SCREEN_FAULT_DATA_ANALYZE_URL, jsonObject.toString(), "json");

        data.put("totalCount", faultObj.get("totalCount"));
        data.put("faultRepairJsonArray", faultObj.get("listObj"));
        return asseData(data);
    }

}
