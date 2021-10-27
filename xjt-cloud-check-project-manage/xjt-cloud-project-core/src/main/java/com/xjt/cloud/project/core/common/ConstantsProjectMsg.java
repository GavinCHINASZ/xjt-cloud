package com.xjt.cloud.project.core.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/6 11:41
 * @Description:
 */
public interface ConstantsProjectMsg {

    String UPLOAD_PARAM_NULL_FAIL = "手机号码/公司/部门 都不能为空";

    String UPLOAD_USER_EXCEED_100 = "上传用户超过100人";

    String REPEAT_USER = "重复用户";

    String REPORT_FILE_URL = PropertyUtils.getProperty("report.file.url");// 生成报表文件路径
    String PDF_LICENSE_FILE_URL = PropertyUtils.getProperty("pdf.license.file.url");// 生成报表文件路径
    String QR_CODE_WORD_FILE_URL = PropertyUtils.getProperty("qr.code.word.file.url");// 报表中的二维码文件路径

    //用户下载模版路径
    String  REPORT_PROJECT_ORG_USER_MODEL_FILE_PATH= PropertyUtils.getProperty("report.project.org.user.model.file.path");
    //保存故障记录产URL
    String SAVE_FAULT_RECORD_URL = PropertyUtils.getProperty("save.fault.record.url");
    //微信公众号故障消息模板ID
    String WE_CHAT_TEMPLATE_ID = PropertyUtils.getProperty("we.chat.template.id");
    //查询当前用户项目的待办任务url
    String FIND_USER_PROJECT_UPCOMING_TASK_COUNT_URL = PropertyUtils.getProperty("find.user.project.upcoming.task.count.url");
    //查询当前项目的巡检点总数URL
    String FIND_PROJECT_CHECK_POINT_COUNT_URL = PropertyUtils.getProperty("find.user.project.check.point.count.url");
    //查询当前项目当月故障巡检记录总数URL
    String FIND_PROJECT_FAULT_CHECK_RECORD_COUNT_URL = PropertyUtils.getProperty("find.project.fault.check.record.count.url");
    //项目日志excel模板文件路径
    String PROJECT_LOG_FILE_PATH = PropertyUtils.getProperty("project.log.file.path");
    //项目当月值班记录数量URL
    String FIND_PROJECT_MONTH_RECORD_COUNT_URL = PropertyUtils.getProperty("find.project.month.record.count.url");
    //统计当前项目极早期设备总数/异常总数URL
    String FIND_PROJECT_VESA_DEVICE_COUNT_URL = PropertyUtils.getProperty("find.project.vesa.device.count.url");
    //统计当前项目火警主机在线/故障数总数URL
    String FIND_PROJECT_FIRE_ALARM_DEVICE_COUNT_URL = PropertyUtils.getProperty("find.project.fire.alarm.device.count.url");
    //统计当前项目水设备正常故障总数URL
    String FIND_PROJECT_WATER_DEVICE_COUNT_URL = PropertyUtils.getProperty("find.project.water.device.count.url");
    //统计当前项目月任务统计URL
    String FIND_PROJECT_MONTH_TASK_COUNT_URL = PropertyUtils.getProperty("find.project.month.task.count.url");
    //统计当前项目故障保修统计URL
    String FIND_PROJECT_FALUT_REPAIR_COUNT_URL = PropertyUtils.getProperty("find.project.fault.repair.count.url");
    // pdf上传url
    String PDF_UPLOAD_URL = PropertyUtils.getProperty("pdf.upload.url");
}
