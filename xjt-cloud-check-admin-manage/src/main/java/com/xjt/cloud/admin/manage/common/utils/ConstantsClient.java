package com.xjt.cloud.admin.manage.common.utils;


import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/25 10:43
 * @Description: 客户端资源文件信息与常量类
 */
public interface ConstantsClient {

    String PHONE_FAIL = "手机号码不正确！";
    String CAPTCHA_FAIL = "验证码不正确！";
    String CAPTCHA_NULL_FAIL = "验证码为空！";
    String CAPTCHA_EXPIRE = "验证码过期！";
    String REGISTER_FAIL  = "注册失败！";
    String SEND_MSG_FAIL  = "验证码发送失败！";
    String USER_LOGIN_NAME_EXIST  = "用户账号已存在！";
    String USER_PHONE_EXIST  = "用户手机号码已存在！";

    //////////////////定时任务url/////////////////////////
    //周期任务处理接口
    String PERIOD_TASK_HANDLE_URL= PropertyUtils.getProperty("period.task.handle.url");
    //视频设备状态处理接口
    String VIDEO_STATUS_HANDLE_URL = PropertyUtils.getProperty("video.status.handle.url");
    //水压设备过期任务
    String WATER_OFF_LINE_TASK_URL = PropertyUtils.getProperty("water.off.line.task.url");
    //烟感设备过期任务
    String SMOKE_OFF_LINE_TASK_URL = PropertyUtils.getProperty("smoke.off.line.task.url");
    //火警主机设备过期任务
    String FIRE_ALARM_OFF_LINE_TASK_URL = PropertyUtils.getProperty("fire.alarm.off.line.task.url");
    //火警主机modbus网关地址读取
    String FIRE_GATEWAY_READ_ADDRSS_URL = PropertyUtils.getProperty("fire.gateway.read.address.url");
    //极早期设备过期任务
    String VESA_OFF_LINE_TASK_URL = PropertyUtils.getProperty("vesa.off.line.task.url");
    //联动设备校验在线离线
    String LINKAGE_DEVICE_OFF_LINE_TASK_URL = PropertyUtils.getProperty("linkage.device.off.line.task.url");
    //设备过期提醒任务
    String DEVICE_REMIND_URL = PropertyUtils.getProperty("device.remind.url");
    //设备送修任务
    String REPAIR_REMIND_URL = PropertyUtils.getProperty("repair.remind.url");
    //值班消息提醒任务
    String DUTY_NOTICE_URL = PropertyUtils.getProperty("duty.notice.url");
    //电气火灾设备过期任务
    String ELECTRICAL_FIRE_OFF_LINE_TASK_URL = PropertyUtils.getProperty("electrical.fire.off.line.task.url");
    // PV统计定时任务
    String PAGE_VIEW_REPORT_TASK_URL = PropertyUtils.getProperty("page.view.report.task.url");
    // 火眼离线任务URL
    String OFF_LINE_TIME_IOT_FIRE_EYE_DEVICE_URL = PropertyUtils.getProperty("off.line.time.iot.fire_eye.device.url");

    //数据词典缓存初使化接口
    String DICT_CACHE_INIT_URL = PropertyUtils.getProperty("dict.cache.init.url");
    String DEVICE_SYS_CACHE = PropertyUtils.getProperty("deviceSysCache");//设备系统
    String DICT_DATABASE_MASTER_SLAVE = "DICT_DATABASE_MASTER_SLAVE";//数据库主从
    String DICT_DATABASE_MASTER_SLAVE_MAIL = "DICT_DATABASE_MASTER_SLAVE_MAIL";//数据库主从邮箱
    String IOT_REPORT_CONFIG = "IOT_REPORT_CONFIG";//物联设备报表配置信息
    String IOT_DEVICE_REPORT_ORDER = "IOT_DEVICE_REPORT_ORDER";//物联设备报表排序
    String IOT_REPORT_FIND_PROJECT_ID = "IOT_REPORT_FIND_PROJECT_ID";//物联设备报表查询项目

    String PROJECT_CACHE_KEY = PropertyUtils.getProperty("projectCacheKey");//设备系统
    /**
     * 监听所有模块运行状态dict_code
     */
    String MONITOR_PROJECT_RUN_STATE_DICT_CODE = "MONITOR_PROJECT_RUN_STATE_DICT_CODE";
    /**
     * 监听所有模块运行状态接口返回的值
     */
    String MONITOR_PROJECT_RETURN_VALUE = "OK";
    /**
     * Mail_receive 邮件接收方, 以;号分隔
     */
    String MONITOR_PROJECT_MAIL_RECEIVE = "MONITOR_PROJECT_MAIL_RECEIVE";
    /**
     * 电话接收人 以;号分隔
     */
    String MONITOR_PROJECT_PHONE_RECEIVE = "MONITOR_PROJECT_PHONE_RECEIVE";
    String MONITOR_PROJECT_EVENT = "91";
    /**
     * 项目监听配置
     */
    String MONITOR_PROJECT_CONFIG = "MONITOR_PROJECT_CONFIG";
    /**
     *失败多少次后发送邮件
     */
    String FAIL_NUM_SEND_MAIL = "FAIL_NUM_SEND_MAIL";

    /**
     *失败多少次后发送短信
     */
    String FAIL_NUM_SEND_SMS = "FAIL_NUM_SEND_SMS";

    /**
     * 定期删除日志任务
     */
    String SECURITY_LOG_TASK_DICT_CODE = "SECURITY_LOG_TASK_DICT_CODE";

    //项目审核
    String PROJECT_REVIEW_URL = PropertyUtils.getProperty("project.review.url");

    // 物联设备故障统计 周任务
    String IOT_DEVICE_REPORT_ONE_WEEK_URL = PropertyUtils.getProperty("iot.device.report.one.week.url");
    // 物联设备故障统计 月任务
    String IOT_DEVICE_REPORT_ONE_MONTE_URL = PropertyUtils.getProperty("iot.device.report.one.month.path");
    // 物联设备故障统计 季度任务
    String IOT_DEVICE_REPORT_ONE_QUARTER_URL = PropertyUtils.getProperty("iot.device.report.one.quarter.path");
    // 物联设备故障统计 年任务
    String IOT_DEVICE_REPORT_ONE_YEAR_URL = PropertyUtils.getProperty("iot.device.report.one.year.path");

    /////////////////////// 物联网卡 begin ////////////////////////////////
    String IOT_CARD_PARAM = "IOT_CARD_PARAM";//物联卡参数信息列表
    String IOT_CARD_LIMIT_NUM = "IOT_CARD_LIMIT_NUM";//每一次处理物联卡数
    String IOT_CARD_RESIDUE_FLOW = "IOT_CARD_RESIDUE_FLOW";//物联卡剩余流量提醒单位 KB
    String IOT_CARD_RESIDUE_BALANCE = "IOT_CARD_RESIDUE_BALANCE";//物联卡账户剩余余额提醒
    String WARN_MAIL = "WARN_MAIL";//提醒邮箱
    String WARN_MOBILE = "WARN_MOBILE";//提醒手机号码
    String IOT_CARD_PROJECT_TYPE = "IOT_CARD_PROJECT_TYPE";//one link物联卡产品类型值，以','分隔

    // 入库模板表格下载
    String PUT_STORAGE_PRODUCT_MODEL_PATH = PropertyUtils.getProperty("put.storage.product.model.path");
    //出库信息模板表格
    String OUT_STORAGE_PRODUCER_MODEL_PATH = PropertyUtils.getProperty("out.storage.producer.model.path");
    /////////////////////// 物联网卡 end ////////////////////////////////
    //设备二维码导入模板下载
    String DEVICE_QR_NO_MODEL_PATH = PropertyUtils.getProperty("device.qr.no.model.path");
    // 定时任务发送故障统计信息(月报)
    String TASK_FAULT_STATISTICS = PropertyUtils.getProperty("task.fault.statistics.path");

    // 物联设备离线信息报表下载
    String IOT_DEVICE_REPORT_MODEL_PATH = PropertyUtils.getPropertyNull("iot.device.report.model.path");

    /////////////////////// 消息模块 begin ////////////////////////////////
    String MESSAGE_MANAGE_CONFIG = "MESSAGE_MANAGE_CONFIG";
    // 要删除消息的表名 DELETE_MESSAGE_TABLE_NAME    ;号分隔
    String DELETE_MESSAGE_TABLE_NAME = "DELETE_MESSAGE_TABLE_NAME";

    /////////////////////// 消息模块 end ////////////////////////////////
}
