package com.xjt.cloud.report.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.report.core.dao.fault.FaultRepairRecordDao;
import com.xjt.cloud.report.core.dao.project.ProjectDao;
import com.xjt.cloud.report.core.dao.report.ReportDao;
import com.xjt.cloud.report.core.dao.report.ReportSystemDao;
import com.xjt.cloud.report.core.entity.fault.FaultRepairRecord;
import com.xjt.cloud.report.core.entity.project.Project;
import com.xjt.cloud.report.core.entity.report.Report;
import com.xjt.cloud.report.core.service.service.ReportService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表
 * 
 * @author huanggc
 * @date 2019/11/04
 */
@Service
public class ReportServiceImpl extends AbstractService implements ReportService {
    @Autowired
    private Configuration configuration;
    @Autowired
    protected ReportDao reportDao;
    @Autowired
    private FaultRepairRecordDao faultRepairRecordDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ReportSystemDao reportSystemDao;

    /**
     * 功能描述: GB25201.B1  导出word
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/11/04
     */
    public Data downWordB1(String json, HttpServletResponse response) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        String period = faultRepairRecord.getPeriod();
        Date star = starDate(period);
        Date end = endDate(period);
        faultRepairRecord.setCreateTime(star);
        faultRepairRecord.setLastModifyTime(end);
        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecordDao.reportB1(faultRepairRecord);

        Long projectId = faultRepairRecord.getProjectId();
        Project project = projectDao.findById(projectId);
        String projectName = project.getProjectName();
        // 导出的文件名及文件格式
        String fileName = projectName + "_建筑消防设施故障维修记录表.doc";
        // 封装数据
        Map<String, Object> map = new HashMap<>(2);
        map.put("list", faultRepairRecordList);
        map.put("projectName", projectName);

        downModel(response, fileName, map, projectName, configuration, "GB25201B1.xml");
        return asseData(200);
    }

    /**
     * 功能描述: 报表--列表接口,如:GB25201 C1
     *
     * @param json 参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2019/11/06
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data report(String json, HttpServletResponse response) {
        Report report = JSONObject.parseObject(json, Report.class);
        // 时间: 2019-11
        String period = report.getPeriod();
        // 每月开始时间
        Date startTime = starDate(period);
        // 每月结束时间
        Date endTime = endDate(period);
        report.setCreateTime(startTime);
        report.setLastModifyTime(endTime);

        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY,report.getProjectId() + "","checkItemVsType");
        if (StringUtils.isNotEmpty(checkItemVsType)){
            report.setCheckItemVsType(Integer.parseInt(checkItemVsType));
        }else{
            report.setCheckItemVsType(1);
        }
        // 报表系统
        List<Report> list = reportSystemDao.findByReportNameAndNoBySql(report);

        // 导出 word文档
        if (StringUtils.isNotEmpty(report.getDownType())){
            // 例: GB25201
            String reportName = report.getReportName();
            // 例: B1 C1
            String reportNo = report.getReportNo();
            Long projectId = report.getProjectId();

            Project project = projectDao.findById(projectId);
            String projectName = project.getProjectName();

            StringBuffer times = new StringBuffer("");
            DateFormat cdf = new SimpleDateFormat("yyyy年MM月dd日");
            times.append(cdf.format(startTime));
            times.append("-");
            times.append(cdf.format(endTime));

            Map<String, Object> map = new HashMap<>(3);
            map.put("list", list);
            map.put("projectName", projectName);
            map.put("times", times);

            String fileName = times.toString() + " " + projectName + "_" + reportName + "_" + reportNo + ".doc";
            String modelName = reportName + reportNo + ".xml";
            downModel(response, fileName, map, projectName, configuration, modelName);
            return asseData(200);
        }

        return asseData(list);
    }

    /**
     * 功能描述: 每月开始时间   2019-11-01 00:01:20
     *
     * @param period 日期: "2019-11"
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/11/04
     */
    private static Date starDate(String period){
        // 2019-11
        String[] split = period.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 1, 0, 0, 0);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 功能描述: 每月结束时间   2019-11-30 23:59:59
     *
     * @param str 2019-11
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/11/04
     */
    private static Date endDate(String str){
        // 2019-11
        String[] split = str.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 1, 23, 59, 59);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileName 导出的文件名+文件后缀
     * @param map 数据
     * @param projectName 项目名称
     * @param modelName 模板名
     * @author huanggc
     * @date 2019/11/04
     */
    private static void downModel(HttpServletResponse response, String fileName, Map<String, Object> map, String projectName, Configuration configuration, String modelName){
        OutputStream out = null;
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;

        try {
            response.setContentType("multipart/form-data");

            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            out = response.getOutputStream();
            oWriter = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板 "GB25201B1.xml"
            Template template = configuration.getTemplate(modelName);
            template.setOutputEncoding("UTF-8");

            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            writer.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (oWriter != null) {
                    oWriter.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
