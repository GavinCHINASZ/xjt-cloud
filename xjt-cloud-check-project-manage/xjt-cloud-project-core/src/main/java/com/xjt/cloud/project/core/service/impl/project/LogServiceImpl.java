package com.xjt.cloud.project.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.dao.project.LogDao;
import com.xjt.cloud.project.core.entity.project.Log;
import com.xjt.cloud.project.core.service.service.project.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * @ClassName ProjectPermissionServiceImpl 项目参与成员实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class LogServiceImpl extends AbstractService implements LogService {

    @Autowired
    private LogDao logDao;

    /**
     * @MethodName: findByProjectLogList 查询项目日志
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/20 16:41
     **/

    @Override
    public Data findByProjectLogList(String json) {
        Log projectLog = JSONObject.parseObject(json, Log.class);
        projectLog = addSearch(projectLog);
        List<Log> list = logDao.findByProjectLogList(projectLog);
        Integer totalCount = projectLog.getTotalCount();
        Integer pageSize = projectLog.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = logDao.findByProjectLogListCount(projectLog);
        }
        return asseData(totalCount, list);

    }

    /**@MethodName: addSearch
     * @Description: 添加搜索条件
     * @Param: [projectLog]
     * @Return: com.xjt.cloud.project.core.entity.Log
     * @Author: zhangZaiFa
     * @Date:2020/3/25 10:09
     **/
    private Log addSearch(Log projectLog) {
        try {
            if(projectLog.getStartTime()!=null){
                projectLog.setStartTime(projectLog.getStartTime().replaceAll("/","-"));
            }
            if(projectLog.getEndTime()!=null){
                Date endTime = DateUtils.parseDate(projectLog.getEndTime().replaceAll("/","-"));
                endTime = DateUtils.add24Hours(endTime);
                projectLog.setEndTime(DateUtils.formatDate(endTime));
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        if(projectLog.getOrderCols()==null||projectLog.getOrderCols().length==0){
            String[] orderCols = {"createTime"};
            projectLog.setOrderCols(orderCols);
            projectLog.setOrderDesc(true);
        }
        return projectLog;
    }

    /**
     * @MethodName: findByProjectLogTypeList 查询项目日志类型
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/8 14:18
     **/
    @Override
    public Data findByProjectLogTypeList(String json) {
        Log projectLog = JSONObject.parseObject(json, Log.class);
        List<String> logNames = logDao.findByProjectLogTypeList(projectLog);
        return asseData(logNames);

    }

    /**@MethodName: downloadProjectLog
     * @Description: 导出项目日志Excel
     * @Param: [response, json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/3/25 10:28
     **/
    @Override
    public void downloadProjectLog(HttpServletResponse response, String json) {
        Log projectLog = JSONObject.parseObject(json, Log.class);
        projectLog = addSearch(projectLog);
        JSONObject projectJson = CacheUtils.getCacheByTypeAndId(Constants.PROJECT_CACHE_KEY, projectLog.getProjectId());
        projectLog.setTitle(projectJson.getString("projectName") + "-操作日志记录表");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(projectLog);
        projectLog.setPageSize(0);
        List<Log> list = logDao.findByProjectLogList(projectLog);
        String[] keys = {"rowNum", "createTimeDesc", "actionName", "userName", "content"};
        ExcelUtils.createAndDownloadExcel(response, list, keys, ConstantsProjectMsg.PROJECT_LOG_FILE_PATH,
                3, null, jsonObject, "1:0");
    }
}
