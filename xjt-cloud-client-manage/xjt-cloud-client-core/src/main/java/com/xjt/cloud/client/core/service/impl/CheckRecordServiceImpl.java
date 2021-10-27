package com.xjt.cloud.client.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.client.core.entity.*;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.client.core.common.ConstantsDevice;
import com.xjt.cloud.client.core.service.service.CheckRecordService;
import com.xjt.cloud.client.core.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 巡检记录
 *
 * @ClassName CheckRecordServiceImpl
 * @Author huanggc
 * @Date 2020-03-25
 * @Version 1.0
 */
@Service
public class CheckRecordServiceImpl extends AbstractService implements CheckRecordService {

    /**
     * 查询巡检记录列表
     *
     * @param json
     * @Author huangGuiChuan
     * @Date 2020-03-25
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findCheckRecordList(String json) {
        CheckRecord checkRecord = JSONObject.parseObject(json, CheckRecord.class);

        String queryDate = checkRecord.getQueryDate();
        Calendar cal = Calendar.getInstance();
        if (StringUtils.isNotEmpty(queryDate)){
            String[] split = queryDate.split("-");
            cal.set(Integer.parseInt(split[0]),Integer.parseInt(split[1]), 1);
            cal.add(Calendar.MONTH, -1);// 月份
        }
        Date time = cal.getTime();

        Date starDate = DateUtils.monthStarDate(time);
        Date endDate = DateUtils.monthEndDate(time);

        checkRecord.setCreateTime(starDate);
        checkRecord.setLastModifyTime(endDate);

        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        /*if (null == totalCount && null != pageSize && 0 != pageSize) {// 判断是否存在总数，如没有，则查询总记录数
            totalCount = checkRecordDao.findCheckRecordTotalCount(checkRecord);
        }*/

        if (null == checkRecord.getOrderCols()){
            String[] orderCols = {"createTime"};
            checkRecord.setOrderCols(orderCols);
            checkRecord.setOrderDesc(true);
        }
        List<CheckRecord> checkRecordList = null;

        return asseData(totalCount, checkRecordList);
    }

}

