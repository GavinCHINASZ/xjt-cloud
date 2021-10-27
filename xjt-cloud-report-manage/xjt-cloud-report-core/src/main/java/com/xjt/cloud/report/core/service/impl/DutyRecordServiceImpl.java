package com.xjt.cloud.report.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.report.core.common.utils.ConstantsReport;
import com.xjt.cloud.report.core.dao.report.DutyRecordDao;
import com.xjt.cloud.report.core.entity.report.DutyRecord;
import com.xjt.cloud.report.core.entity.report.FaultLevel;
import com.xjt.cloud.report.core.service.service.DutyRecordService;
import com.xjt.cloud.report.core.service.service.FaultLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/12
 * @Description: 值班记录
 */
@Service
public class DutyRecordServiceImpl extends AbstractService implements DutyRecordService {

    @Autowired
    private DutyRecordDao dutyRecordDao;
    @Autowired
    private FaultLevelService faultLevelService;

    @Override
    public Data save(String json) {
        DutyRecord dr = JSONObject.parseObject(json, DutyRecord.class);
        FaultLevel faultLevel = faultLevelService.findByProjectNewVersionFaultLevel(dr.getProjectId());
        // 生成默认故障等级设置参数
        if (faultLevel == null) {
            faultLevel = new FaultLevel(70, 50, 30, 10, 1, dr.getProjectId());
            Data data = faultLevelService.save(JSONObject.toJSONString(faultLevel));
            faultLevel = (FaultLevel) data.getObj();
        }
        SysLog.debug(SecurityUserHolder.getUserName()+"----->");
        setEntityUserIdName( SecurityUserHolder.getUserName(),dr.getProjectId(),dr);
        dr.setFillOrgUserName(dr.getCreateUserName());
        DutyRecord oldDr = new DutyRecord();
        oldDr.setProjectId(dr.getProjectId());
        oldDr.setCreateTime(new Date());
        oldDr = dutyRecordDao.findByDutyRecord(oldDr);
        if (oldDr != null) {
            //记录已存在
            dutyRecordDao.deleteImageUrl(dr.getId());
            dutyRecordDao.deleteRecord(oldDr);
        }
        //新增记录
        dr.setFaultCount(dr.getNoResponseCount() + dr.getOtherCount() + dr.getErrorResponseCount());
        dr.setFaultLevelId(faultLevel.getId());
        if(dr.getFaultCount()<faultLevel.getLevel5()){
            dr.setFaultLevel(5);
        }else if(dr.getFaultCount()<faultLevel.getLevel4()){
            dr.setFaultLevel(4);
        }else if(dr.getFaultCount()<faultLevel.getLevel3()){
            dr.setFaultLevel(3);
        }else if(dr.getFaultCount()<faultLevel.getLevel2()){
            dr.setFaultLevel(2);
        }else{
            dr.setFaultLevel(1);
        }
        dutyRecordDao.save(dr);
        if(dr.getImageUrls()!=null && dr.getImageUrls().size()!=0){
            dutyRecordDao.saveImageUrl(dr);
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: findByDutyRecord 查询值班信息列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:33
     **/
    @Override
    public Data findByDutyRecordList(String json) {
        DutyRecord dr = JSONObject.parseObject(json, DutyRecord.class);
        dr = initFindTime(dr);
        List<DutyRecord> list = dutyRecordDao.findByDutyRecordList(dr);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", dr.getProjectId());
        JSONObject object = HttpUtils.httpGetToken(ConstantsReport.USER_PROJECT_PERMISSION_URL , jsonObject.toJSONString(), "json",SecurityUserHolder.getToken());
        JSONArray array = object.getJSONArray("object");
        jsonObject.clear();
        jsonObject.put("list", list);
        jsonObject.put("permission", array.contains("report_manage_edit"));
        return asseData(jsonObject);
    }

    /**
     * @Description 查询app首页值班信息信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject findUserProjectDutyRecordData(String json){
        DutyRecord dr = JSONObject.parseObject(json, DutyRecord.class);
        dr = initFindTime(dr);
        List<DutyRecord> list = dutyRecordDao.findByDutyRecordList(dr);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("modelIndex",16);
        if (CollectionUtils.isNotEmpty(list)) {
            DutyRecord dutyRecord = list.get(0);
            jsonObject.put("totalCount", dutyRecord.getFaultCount() + dutyRecord.getIsolateCount());
            jsonObject.put("faultCount", dutyRecord.getFaultCount());
            jsonObject.put("isolateCount", dutyRecord.getIsolateCount());
        }else{
            jsonObject.put("totalCount", 0);
            jsonObject.put("faultCount", 0);
            jsonObject.put("isolateCount", 0);
        }
        return jsonObject;
    }
    
    /**@MethodName: initFindTime 初始化查询时间
     * @Description: 
     * @Param: [dr]
     * @Return: com.xjt.cloud.report.core.entity.report.DutyRecord
     * @Author: zhangZaiFa
     * @Date:2019/11/13 14:10 
     **/
    private DutyRecord initFindTime(DutyRecord dr) {
        try {
            if(dr.getEndTime()!=null){
                int dataType = 2;
                String timeFormat = "yyyy-MM";
                if("month".equals(dr.getDateType())){
                    dataType = Calendar.MONTH;
                }else if("day".equals(dr.getDateType())){
                    dataType = Calendar.DAY_OF_MONTH;
                    timeFormat = "yyyy-MM-dd";
                }
                SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
                // 结束时间按类型加1
                Date startTime = sdf.parse(dr.getStartTime());
                Date endTime = sdf.parse(dr.getEndTime());
                Calendar ca = Calendar.getInstance();
                ca.setTime(endTime);
                // 把日期设置为当月第一天
                ca.set(Calendar.DATE, 1);
                // 日期回滚一天，也就是最后一天
                ca.roll(Calendar.DATE, -1);
                int maxDate = ca.get(Calendar.DATE);
                dr.setPageSize(maxDate);
                sdf = new SimpleDateFormat("yyyy-MM-dd");

                // 初始化时间
                dr.setStartTime(sdf.format(startTime));
                dr.setEndTime(sdf.format(ca.getTime()));
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return dr;
    }

    /**@MethodName: findByDutyRecord 查询记录
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 10:29
     **/
    @Override
    public Data findByDutyRecord(String json) {
        DutyRecord dr = JSONObject.parseObject(json, DutyRecord.class);
        dr = dutyRecordDao.findByDutyRecord(dr);
        if(dr != null){
            List<String> list = dutyRecordDao.findByDutyRecordImageUrlList(dr.getId());
            dr.setImageUrls(list);
        }
        return asseData(dr);
    }

    /**@MethodName: findByDataChart 查询数据图表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 14:05
     **/
    @Override
    public Data findByDataChart(String json) {
        DutyRecord dr = JSONObject.parseObject(json, DutyRecord.class);
        dr = initFindTime(dr);
        List<DutyRecord> list = dutyRecordDao.findByDataChart(dr);

        int listSize = list.size();
        List<Integer> faultList = new ArrayList<>(listSize);
        List<Integer> isolateList = new ArrayList<>(listSize);
        List<String> timeList = new ArrayList<>(listSize);
        for (DutyRecord entity : list) {
            faultList.add(entity.getFaultCount());
            isolateList.add(entity.getIsolateCount());
            timeList.add(entity.getFillTime());
        }

        Map<String,Object> map = new HashMap<>(3);
        map.put("faultList", faultList);
        map.put("isolateList", isolateList);
        map.put("timeList", timeList);
        return asseData(map);
    }

    /**@MethodName: findByProjectMonthRecordCount
     * @Description: 查询项目月记录数
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/26 14:07
     **/
    @Override
    public Data findByProjectMonthRecordCount(String json) {
        DutyRecord dr = JSONObject.parseObject(json, DutyRecord.class);
        dr = dutyRecordDao.findByProjectMonthRecordCount(dr);
        return asseData(dr);
    }
}
