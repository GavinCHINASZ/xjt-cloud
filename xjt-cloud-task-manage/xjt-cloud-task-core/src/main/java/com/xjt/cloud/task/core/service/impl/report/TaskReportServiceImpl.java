package com.xjt.cloud.task.core.service.impl.report;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.common.PdfUtils;
import com.xjt.cloud.task.core.dao.report.TaskReportDao;
import com.xjt.cloud.task.core.entity.report.TaskReport;
import com.xjt.cloud.task.core.service.service.report.TaskReportService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务报表,统计,月报 相关共用
 *
 * @author huanggc
 * @date 2020/11/30
 */
@Service
public class TaskReportServiceImpl extends AbstractService implements TaskReportService {
    @Autowired
    private TaskReportDao taskReportDao;
    @Autowired
    private Configuration configuration;

    /**
     * 定时任务:发送故障统计信息(月报)
     *
     * @author huanggc
     * @date 2020/11/30
     * @return Data
     */
    @Override
    public Data taskFaultStatistics() {
        //同 物联设备故障统计 功能
        return null;
    }

    /**
     * 设备异常统计:异常设备统计--柱形图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @Override
    public Data deviceColumnar(String json) {
        TaskReport taskReport = JSONObject.parseObject(json, TaskReport.class);
        dateJudge(taskReport);
        List<TaskReport> taskReportList = taskReportDao.deviceColumnar(taskReport);
        return asseData(taskReportList);
    }

    /**
     * 设备异常统计:饼图
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @Override
    public Data devicePie(String json) {
        TaskReport taskReport = JSONObject.parseObject(json, TaskReport.class);
        dateJudge(taskReport);
        List<TaskReport>  taskReportList = taskReportDao.devicePie(taskReport);
        return asseData(taskReportList);
    }

    /**
     * 设备异常统计:设备类型列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     * @return Data
     */
    @Override
    public Data findDeviceTypeList(String json) {
        TaskReport taskReport = JSONObject.parseObject(json, TaskReport.class);
        dateJudge(taskReport);

        Integer totalCount = taskReport.getTotalCount();
        Integer pageSize = taskReport.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = taskReportDao.findTaskReportTotalCount(taskReport);
        }
        List<TaskReport> taskReportList = taskReportDao.findDeviceTypeList(taskReport);
        return asseData(totalCount, taskReportList);
    }

    /**
     * 设备异常统计:导出功能
     *
     * @param json 参数
     * @author huanggc
     * @date 2020/12/01
     */
    @Override
    public void downDeviceTypeList(String json, HttpServletRequest request, HttpServletResponse response) {
        TaskReport taskReport = JSONObject.parseObject(json, TaskReport.class);
        String createTimeStr = DateUtils.getChangeDate(taskReport.getCreateTime());
        String lastModifyTimeStr = DateUtils.getChangeDate(taskReport.getLastModifyTime());
        dateJudge(taskReport);
        List<TaskReport> taskReportList = taskReportDao.findDeviceTypeList(taskReport);

        // 封装数据
        Map<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("taskReportList", taskReportList);

        // image1.png
        byte[] data = null;
        try {
            // 获取图片片的地址
            URL url = new URL(taskReport.getPieImg());
            // 打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求方式为"GET"
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(3 * 1000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
            data = IoUtils.inputStreamToByteArray(inStream);
        } catch (Exception e) {
            SysLog.info("downDeviceTypeList图片转BASE64Encoder失败");
        }
        hashMap.put("pieImg", data == null ? "" : new BASE64Encoder().encodeBuffer(data));

        Long projectId = taskReport.getProjectId();
        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");

        String fileUrl = System.getProperty("user.dir") + ConstantsDevice.DEVICE_FAULT_REPORT_FILE_URL;
        String fileName = projectName + "__" + "设备异常统计报表";
        hashMap.put("projectName", projectName);
        hashMap.put("createTimeStr", createTimeStr);
        hashMap.put("lastModifyTimeStr", lastModifyTimeStr);
        // 导出方法
        PdfUtils.downModel(request, response, fileUrl, fileName, hashMap, configuration, "deviceFaultReportModel.xml", false);
    }

    /**
     * 时间处理
     *
     * @param taskReport TaskReport
     */
    private void dateJudge(TaskReport taskReport) {
        // 周, 月(默认), 年
        if (taskReport.getCreateTime() != null && taskReport.getLastModifyTime() != null) {
            taskReport.setCreateTime(DateUtils.startDayDate(taskReport.getCreateTime()));
            taskReport.setLastModifyTime(DateUtils.endDayDate(taskReport.getLastModifyTime()));
        } else {
            // 默认本月
            Date date = new Date();
            taskReport.setCreateTime(DateUtils.monthStarDate(date));
            taskReport.setLastModifyTime(date);
        }
    }

}
