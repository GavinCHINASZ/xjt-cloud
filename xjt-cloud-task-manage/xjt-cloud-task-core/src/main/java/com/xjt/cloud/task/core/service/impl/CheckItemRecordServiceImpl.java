package com.xjt.cloud.task.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.common.ConstantsDeviceMsg;
import com.xjt.cloud.task.core.dao.task.CheckItemRecordDao;
import com.xjt.cloud.task.core.dao.task.CheckRecordDao;
import com.xjt.cloud.task.core.dao.task.TaskDao;
import com.xjt.cloud.task.core.dao.task.TaskSpotCheckToolDao;
import com.xjt.cloud.task.core.entity.check.CheckItemRecord;
import com.xjt.cloud.task.core.entity.check.CheckRecord;
import com.xjt.cloud.task.core.entity.task.Task;
import com.xjt.cloud.task.core.entity.TaskSpotCheckTool;
import com.xjt.cloud.task.core.service.service.CheckItemRecordService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 巡检项记录Service
 * 
 * @author dwt
 * @date 2019-07-26 13:52
 */
@Service
public class CheckItemRecordServiceImpl extends AbstractService implements CheckItemRecordService {
    @Autowired
    private CheckItemRecordDao checkItemRecordDao;
    @Autowired
    private TaskSpotCheckToolDao taskSpotCheckToolDao;
    @Autowired
    private Configuration configuration;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private CheckRecordServiceImpl checkRecordService;

    /**
     * 查询巡检项记录列表
     * 
     * @author dwt
     * @date 2019-07-26 14:08
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findCheckItemRecordList(String json) {
        CheckItemRecord checkItemRecord = JSONObject.parseObject(json, CheckItemRecord.class);
        List<CheckItemRecord> list = checkItemRecordDao.findCheckItemRecordList(checkItemRecord);
        checkItemRecord.setPageSize(0);
        Integer count = checkItemRecordDao.findCheckItemRecordCount(checkItemRecord);
        return asseData(count, list);
    }

    /**
     * 根据id查询巡检项记录
     * 
     * @author dwt
     * @date 2019-07-26 14:09
     * @param id CheckItemRecord.id
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findCheckItemRecordById(Long id) {
        CheckItemRecord checkItemRecord = checkItemRecordDao.findCheckItemRecordById(id);
        return asseData(checkItemRecord);
    }

    /**
     * 保存巡检项记录
     * 
     * @author dwt
     * @date 2019-07-26 14:13
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveCheckItemRecord(String json) {
        CheckItemRecord checkItemRecord = JSONObject.parseObject(json, CheckItemRecord.class);
        Integer id = checkItemRecordDao.saveCheckItemRecord(checkItemRecord);
        return asseData(id);
    }

    /**
     * 地铁报表下载
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     * @author huanggc
     * @date 2020-05-11
     * @return Data
     */
    @Override
    public Data downReportMetro(String json, HttpServletResponse response, HttpServletRequest request) {
        // word 下载报存路径
        String fileUrl = System.getProperty("user.dir") + ConstantsDevice.REPORT_WORD_FILE_URL;
        
        CheckItemRecord checkItemRecord = JSONObject.parseObject(json, CheckItemRecord.class);
        Long[] taskIds = checkItemRecord.getTaskIds();
        Integer downType = checkItemRecord.getDownType();

        Data data = downWordMethod(checkItemRecord, response, request, fileUrl);
        // 1 doc,  2 excel,  3 pdf
        if (null == downType) {
            return data;
        } else {
            // 多个系统合并下载excel
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ids", taskIds);
            jsonObject.put("projectId", checkItemRecord.getProjectId());
            checkRecordService.downReportExcelMetro(jsonObject.toString(), request, response);

            // 压缩包方法
            try {
                FileUtils.zipFiles("地铁机电报表压缩包" + ".zip", fileUrl, request, response);
            } catch (IOException e) {
                SysLog.error("------------>生成word压缩包失败");
            }
            // 压缩包下载后,删除目录内的文件
            FileUtils.deleteFile(fileUrl);
        }
        return asseData(200);
    }

    /**
     * 地铁站务--运营总部消防设施日常巡查记录表 Q/SZDY 0044-04-B3
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     * @author huanggc
     * @date 2020-06-02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findDailyPatrol(String json, HttpServletResponse response, HttpServletRequest request) {
        CheckItemRecord checkItemRecord = JSONObject.parseObject(json, CheckItemRecord.class);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, checkItemRecord.getProjectId() + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkItemRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkItemRecord.setCheckItemVsType(1);
        }
        List<CheckItemRecord> checkItemRecordList = checkItemRecordDao.findDailyPatrol(checkItemRecord);

        Dict dictLocation = DictUtils.getDictByDictAndItemCode("CHECK_LOCATION", "CHECK_LOCATION");
        String checkLocation = "";
        if (null != dictLocation) {
            checkLocation = dictLocation.getItemDescription();
        }

        // 检修地点
        Map<String, Object> map = new HashMap<>(2);
        map.put("checkLocation", checkLocation);
        map.put("checkItemRecordList", checkItemRecordList);

        Integer downType = checkItemRecord.getDownType();
        if (null != downType) {
            Task task = taskDao.findTaskById(checkItemRecord.getTaskId());

            String fileName = "(" + task.getTaskName() + ")" + "运营总部消防设施日常巡查记录.doc";
            String modelName = ConstantsDevice.DAILY_PATROL_WORD_NAME;
            CheckRecordServiceImpl.downModel(response, request, fileName, map, "", configuration, modelName);
            return asseData(200);
        }
        return asseData(map);
    }

    /**
     * word 下载方法
     *
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     * @param fileUrl 文件路径
     * @author huanggc
     * @date 2020-05-18
     * @return void
     */
    private Data downWordMethod(CheckItemRecord checkItemRecord, HttpServletResponse response, HttpServletRequest request, String fileUrl) {
        Map<String, Object> map = new HashMap<>();
        Long projectId = checkItemRecord.getProjectId();

        Dict dictUnit = DictUtils.getDictByDictAndItemCode("FILLING_UNIT", "FILLING_UNIT");
        
        map.put("fillingUnit", dictUnit == null ? "" : dictUnit.getItemDescription());

        Dict dictLocation = DictUtils.getDictByDictAndItemCode("CHECK_LOCATION", "CHECK_LOCATION");
        
        map.put("checkLocation", dictLocation == null ? "" : dictLocation.getItemDescription());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", projectId);
        jsonObject.put("deviceSysName", "FAS报警主机");
        JSONObject fasDevice = HttpUtils.httpGet(ConstantsDeviceMsg.FIND_DEVICE_URL + "/" + projectId, jsonObject.toString(), "json");
        // FAS主机型号 gasDevice.getJSONObject("obj")
        map.put("fasModel", fasDevice.getJSONObject("obj") == null ? "" : fasDevice.getJSONObject("obj").getString("model"));

        jsonObject.put("deviceSysName", "气体灭火控制主机");
        JSONObject gasDevice = HttpUtils.httpGet(ConstantsDeviceMsg.FIND_DEVICE_URL + "/" + projectId, jsonObject.toString(), "json");
        map.put("gasModel", gasDevice.getJSONObject("obj") == null ? "" : gasDevice.getJSONObject("obj").getString("model"));

        jsonObject.put("deviceSysName", "测温主机");
        JSONObject senseDevice = HttpUtils.httpGet(ConstantsDeviceMsg.FIND_DEVICE_URL + "/" + projectId, jsonObject.toString(), "json");
        map.put("senseModel", senseDevice.getJSONObject("obj") == null ? "" : senseDevice.getJSONObject("obj").getString("model"));


        Long[] taskIds = checkItemRecord.getTaskIds();
        checkItemRecord.setTaskIds(null);

        Task task = new Task();
        task.setIds(taskIds);
        List<Task> taskList = taskDao.findTaskList(task);
        List<Long> a = new ArrayList<>();
        List<Long> b = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder sbs = new StringBuilder();
        Date lastModifyTime = null;
        for (Task t : taskList) {
            if (t.getTaskType() != 3) {
                // 巡检
                a.add(t.getId());
                sbs.append(t.getTaskName() + ",");
            } else {
                // 抽检
                b.add(t.getId());
                sb.append(t.getTaskName() + ",");
            }

            if (lastModifyTime == null) {
                lastModifyTime = t.getLastModifyTime();
            }

            if (lastModifyTime.getTime() < t.getLastModifyTime().getTime()) {
                lastModifyTime = t.getLastModifyTime();
            }
        }

        String taskNames = sbs.toString();
        String taskName = sb.toString();
        // 巡检 工单号
        map.put("taskNames", taskNames == null ? "" : taskNames);
        // 抽检 工单号
        map.put("taskName", taskName == null ? "" : taskName);
        map.put("fasTaskName", (taskNames == null ? "" : taskNames) + (taskName == null ? "" : taskName));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String format = simpleDateFormat.format(lastModifyTime);
        map.put("lastModifyTime", format);

        if (CollectionUtils.isNotEmpty(a)) {
            Long[] patrol = new Long[a.size()];
            for (int i = 0; i < a.size(); i++) {
                patrol[i] = a.get(i);
            }
            checkItemRecord.setTaskIds(patrol);
        }

        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setTaskIds(taskIds);
        String checkerName = checkRecordDao.findCheckerName(checkRecord);
        // 检修人
        map.put("checkerName", checkerName == null ? "" : checkerName);

        // word 部份 15-B2、23-B2、25-B2 巡检报表
        // 1 FAS系统月度检修记录表
        checkItemRecord.setDeviceSysName("FAS系统");
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, projectId + "", "checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)) {
            checkItemRecord.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        } else {
            checkItemRecord.setCheckItemVsType(1);
        }
        List<CheckItemRecord> fasList = checkItemRecordDao.findFasList(checkItemRecord);
        // 计量器具
        TaskSpotCheckTool taskSpotCheckTool = new TaskSpotCheckTool();
        taskSpotCheckTool.setTaskIds(taskIds);
        List<TaskSpotCheckTool> fasToolList = taskSpotCheckToolDao.findTaskSpotCheckToolList(taskSpotCheckTool);
        if (CollectionUtils.isEmpty(fasToolList)) {
            fasToolList = new ArrayList<>();
        }
        map.put("fasList", fasList);
        map.put("fasToolList", fasToolList);


        // 2  15-B2 气体灭火控制系统月度检修记录表
        checkItemRecord.setDeviceSysName("气体灭火控制系统");
        List<CheckItemRecord> gasList = checkItemRecordDao.findFasList(checkItemRecord);
        // 计量器具
        List<TaskSpotCheckTool> gasToolList = taskSpotCheckToolDao.findTaskSpotCheckToolList(taskSpotCheckTool);
        if (CollectionUtils.isEmpty(gasToolList)) {
            gasToolList = new ArrayList<>();
        }
        map.put("gasList", gasList);
        map.put("gasToolList", gasToolList);


        // 3 23-B2 感温光纤测温系统月度检修记录表
        checkItemRecord.setDeviceSysName("感温光纤测温系统");
        Integer[] array = {1};
        checkItemRecord.setDeviceDateType(array);
        List<CheckItemRecord> senseList = checkItemRecordDao.findFasList(checkItemRecord);
        // 计量器具
        List<TaskSpotCheckTool> senseToolList = taskSpotCheckToolDao.findTaskSpotCheckToolList(taskSpotCheckTool);
        if (CollectionUtils.isEmpty(senseToolList)) {
            senseToolList = new ArrayList<>();
        }
        map.put("senseList", senseList);
        map.put("senseToolList", senseToolList);

        // 4 25-B2 电气火灾监控系统月度检修记录表
        checkItemRecord.setDeviceSysName("电气火灾监控系统");
        checkItemRecord.setDeviceDateType(null);
        List<CheckItemRecord> electricalList = checkItemRecordDao.findFasList(checkItemRecord);
        // 计量器具
        List<TaskSpotCheckTool> electricaToolList = taskSpotCheckToolDao.findTaskSpotCheckToolList(taskSpotCheckTool);
        if (CollectionUtils.isEmpty(electricalList)) {
            electricalList = new ArrayList<>();
        }
        map.put("electricalList", electricalList);
        map.put("electricaToolList", electricaToolList);
        String projectName = "";
        map.put("projectName", projectName);

        checkItemRecord.setTaskIds(taskIds);
        checkItemRecord.setDeviceSysName("FAS系统");
        List<CheckItemRecord> fasLists = checkItemRecordDao.findFasList(checkItemRecord);
        // 直流电源盘  探测器     电话插孔/挂壁电话      手报及消火栓按钮     吸气式烟雾探测器
        map.put("fasLists", fasLists);

        if (checkItemRecord.getDownType() == null) {
            return asseData(map);
        } else {
            String fileName = "word报表";
            String modelName = "reportMetroWord.xml";
            downModel(response, request, fileName, fileUrl, map, projectName, configuration, modelName);
        }
        return asseData(200);
    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileName    导出的文件名
     * @param fileUrl     文件导出的路径
     * @param map         数据
     * @param projectName 项目名称
     * @param modelName   模板名
     * @author huanggc
     * @date 2019/11/04
     */
    private static void downModel(HttpServletResponse response, HttpServletRequest request, String fileName, String fileUrl,
                                 Map<String, Object> map, String projectName, Configuration configuration, String modelName) {

        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            OutputStream outt = new FileOutputStream(fileUrl + fileName + ".doc");
            oWriter = new OutputStreamWriter(outt, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板
            Template template = configuration.getTemplate(modelName);
            template.setOutputEncoding("UTF-8");
            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            writer.write(result);
        } catch (Exception e) {
            SysLog.error(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (oWriter != null) {
                    oWriter.close();
                }
            } catch (IOException e) {
                SysLog.error(e);
            }
        }
    }

}
