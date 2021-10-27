package com.xjt.cloud.project.core.service.impl.device;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.dao.device.CheckPointDao;
import com.xjt.cloud.project.core.dao.device.CheckRecordDao;
import com.xjt.cloud.project.core.entity.device.CheckRecord;
import com.xjt.cloud.project.core.entity.device.CheckReport;
import com.xjt.cloud.project.core.entity.project.CheckProject;
import com.xjt.cloud.project.core.service.service.device.CheckRecordService;
import com.xjt.cloud.project.core.service.service.project.CheckProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:32
 * @Description:app检测
 */
@Service
public class CheckRecordServiceImpl  extends AbstractService implements CheckRecordService {
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private CheckPointDao checkPointDao;
    @Autowired
    private CheckProjectService checkProjectService;

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findCheckPointList(String json){
        CheckRecord checkRecord = JSONObject.parseObject(json,CheckRecord.class);
        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = checkPointDao.findCheckPointListCount(checkRecord);
        }
        List<CheckRecord> list = checkPointDao.findCheckPointList(checkRecord);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询app检测列表
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data findCheckRecordList(String json){
        CheckRecord checkRecord = JSONObject.parseObject(json,CheckRecord.class);
        Integer totalCount = checkRecord.getTotalCount();
        Integer pageSize = checkRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = checkRecordDao.findCheckRecordListCount(checkRecord);
        }
        List<CheckRecord> list = checkRecordDao.findCheckRecordList(checkRecord);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:新增app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data saveCheckRecord(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<CheckRecord> list = JSONArray.parseArray(jsonObject.getString("list"), CheckRecord.class);
        jsonObject.remove("list");
        int num = 0;
        CheckRecord checkPoint = JSONObject.parseObject(jsonObject.toJSONString(),CheckRecord.class);
        CheckRecord checkRecord;
        if (CollectionUtils.isNotEmpty(list)){
            checkRecord = list.get(0);
            if (checkRecord.getId() == null){
                checkPointDao.saveCheckPoint(checkPoint);
                Long checkPointId = checkPoint.getId();
                for (CheckRecord cr:list){
                    cr.setCheckPointId(checkPointId);
                }
                num = checkRecordDao.saveCheckRecordList(list);
            }else{
                CheckRecord tmpCheckRecord = new CheckRecord();
                tmpCheckRecord.setCheckPointId(checkRecord.getCheckPointId());
                tmpCheckRecord.setStatus(99);
                checkPointDao.modifyCheckPoint(checkPoint);
                checkRecordDao.modifyCheckRecord(tmpCheckRecord);
                num = checkRecordDao.saveCheckRecordList(list);
            }
        }
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data modifyCheckRecord(String json){
        CheckRecord checkRecord = JSONObject.parseObject(json,CheckRecord.class);
        int num = checkRecordDao.modifyCheckRecord(checkRecord);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改app检测
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    @Override
    public Data modifyCheckPoint(String json){
        CheckRecord checkRecord = JSONObject.parseObject(json,CheckRecord.class);
        int num = checkPointDao.modifyCheckPoint(checkRecord);
        return asseData(num);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-11 19:14
     *@Param: java.lang.Long
     *@Return: JSONObject
     *@Description 检测记录导出
     */
    @Override
    public JSONObject findAllCheckRecordByProjectInfoId(Long id) {
        CheckRecord cr = new CheckRecord();
        List<CheckRecord> list = checkRecordDao.findCheckRecordList(cr);
        List<CheckRecord> failList = checkRecordDao.findFailCheckRecordList(cr);
        JSONObject json = new JSONObject();
        json.put("allCheckRecord",list);
        json.put("failCheckRecord",failList);
        return json;
    }

    /**
     *
     * 功能描述:查询检测汇总报表
     *
     * @param checkRecord
     * @return: List<CheckReport>
     * @auther: wangzhiwen
     * @date: 2020/4/11 17:59
     */
    @Override
    public List<CheckReport> findCheckReport(CheckRecord checkRecord){
        List<CheckReport> list = checkRecordDao.findCheckReport(checkRecord);
        if (CollectionUtils.isNotEmpty(list)){
            JSONObject json = new JSONObject();
            json.put("id", checkRecord.getCheckProjectId());
            int checkResult = 1;
            for (CheckReport checkReport:list){
                if (checkReport.getDecisionResult() == 2){
                    checkResult = 2;
                    break;
                }
            }
            json.put("checkResult", checkResult);
            checkProjectService.updCheckProject(json.toJSONString());
        }
        return list;
    }
}
