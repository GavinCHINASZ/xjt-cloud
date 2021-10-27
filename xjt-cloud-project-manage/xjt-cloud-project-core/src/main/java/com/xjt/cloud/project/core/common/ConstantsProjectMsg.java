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
    //#默认密码，如没有配置则随机生成密码
    String DEFAULT_PASS = PropertyUtils.getProperty("default.pass");

    //用户下载模版路径
    String  REPORT_PROJECT_ORG_USER_MODEL_FILE_PATH= PropertyUtils.getProperty("report.project.org.user.model.file.path");
    //保存故障记录URL
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
    //项目烟感设备数量URL
    String FIND_PROJECT_SMOKE_DEVICE_COUNT_URL = PropertyUtils.getProperty("find.project.smoke.device.count.url");
    //项目声光设备数量URL
    String FIND_PROJECT_LINKAGE_DEVICE_COUNT_URL = PropertyUtils.getProperty("find.project.linkage.device.count.url");
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
    //查询项目任务角标数量url
    String FIND_PROJECT_TASK_SUBSCRIPT_URL = PropertyUtils.getProperty("find.project.task.subscript.url");


    /*地铁大屏接口，各模块URL地址*/
    //统计任务工单饼图URL
    String FIND_SCREEN_TASK_DATA_ANALYZE_URL = PropertyUtils.getProperty("find.screen.task.data.analysis.url");
    //统计设备管理饼图URL
    String FIND_SCREEN_DEVICE_MANAGE_DATA_ANALYZE_URL = PropertyUtils.getProperty("find.screen.device.manage.data.analysis.url");
    //统计故障报修饼图URL
    String FIND_SCREEN_FAULT_REPAIR_DATA_ANALYZE_URL = PropertyUtils.getProperty("find.screen.fault.repair.data.analysis.url");
    //统计火警主机条形图URL
    String FIND_SCREEN_FIRE_ALARM_DATA_ANALYZE_URL = PropertyUtils.getProperty("find.screen.fire.alarm.data.analysis.url");
    //要查询的项目IDS
    String FIND_SCREEN_PROJECT_IDS = PropertyUtils.getProperty("find.screen.project.ids");
    //是否是本地文件下载
    String PROJECT_DOWNLOAD_IS_NATIVE_FILE = PropertyUtils.getProperty("project.download.is.native.file");
    //平台轮播图查询URL
    String FIND_CLOUD_BACKGROUND_MAP_PATH = PropertyUtils.getProperty("find.cloud.backgroud.map.path");


    //app首页数据查询接口url
    //巡查工单/任务管理
    String FIND_USER_PROJECT_CHECK_WORD_ORDER_DATA = PropertyUtils.getProperty("find.user.project.check.word.order.data");
    //火警主机
    String FIND_USER_PROJECT_FIRE_ALARM_DATA = PropertyUtils.getProperty("find.user.project.fire.alarm.data");
    //智能烟感
    String FIND_USER_PROJECT_SMOKE_DATA = PropertyUtils.getProperty("find.user.project.smoke.data");
    //水压设备/水浸设备/智能消火栓
    String FIND_USER_PROJECT_WATER_DATA = PropertyUtils.getProperty("find.user.project.water.data");
    //设备管理
    String FIND_USER_PROJECT_DEVICE_DATA = PropertyUtils.getProperty("find.user.project.device.data");
    //维保记录
    String FIND_USER_PROJECT_CHECK_RECORD_DATA = PropertyUtils.getProperty("find.user.project.check.record.data");
    //故障报修
    String FIND_USER_PROJECT_FAULT_REPAIR_DATA = PropertyUtils.getProperty("find.user.project.fault.repair.data");
    //值班记录
    String FIND_USER_PROJECT_DUTY_RECORD_DATA = PropertyUtils.getProperty("find.user.project.duty.record.data");
    //极早期预警
    String FIND_USER_PROJECT_VESA_DATA = PropertyUtils.getProperty("find.user.project.vesa.data");
    //月度工单
    String FIND_USER_PROJECT_TASK_OVERVIEW_DATA = PropertyUtils.getProperty("find.user.project.task.overview.data");
    //声光报警
    String FIND_USER_PROJECT_LINKAGE_DATA = PropertyUtils.getProperty("find.user.project.linkage.data");
    //类脑火眼
    String FIND_USER_PROJECT_FIRE_EYE_DATA = PropertyUtils.getProperty("find.user.project.fire.eye.data");


    String BACKGROUND = "BACKGROUND";
    int SUCCESS = 200;
    int FAIL = 600;
    //首页应用默认模板排序
    String HOME_APPLY_DEFAULT_MODULE =  PropertyUtils.getProperty("home.apply.default.module");
    //app首页模版默认配置
    String APP_HOME_USER_PROJECT_DEFAULT_MODULE = PropertyUtils.getProperty("app.home.user.project.default.module");
}
