package com.xjt.cloud.safety.core.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 11:11
 * @Description: 设备管理公共参数
 */
public interface ConstantsDevice {
    Long CHECK_POINT_MAX_QR_NO = 9999999999L;//巡检点二维码最大的数字
    Long DEVICE_MAX_QR_NO = 99999999999999L;//设备二维码最大的数字
    //二维码图片上传根目录
    String QR_NO_FILE_PROJECT_NAME = PropertyUtils.getProperty("qr.no.file.project.name");
    String QR_NO_REDIRECT_URL = PropertyUtils.getProperty("qr_no_redirect_url");//二维码扫描跳转路径
    String QR_NO_URL = PropertyUtils.getProperty("qr_no_url");//二维码请求路径

    //安全日志类型
    //编辑设备
    String SECURITY_LOG_TYPE_MODIFY_DEVICE = "SECURITY_LOG_TYPE_MODIFY_DEVICE";
    //添加设备
    String SECURITY_LOG_TYPE_ADD_DEVICE = "SECURITY_LOG_TYPE_ADD_DEVICE";
    //添加设备系统
    String SECURITY_LOG_TYPE_ADD_DEVICE_SYS = "SECURITY_LOG_TYPE_ADD_DEVICE_SYS";
    //编辑设备系统
    String SECURITY_LOG_TYPE_MODIFY_DEVICE_SYS = "SECURITY_LOG_TYPE_MODIFY_DEVICE_SYS";
    //添加设备巡检项
    String SECURITY_LOG_TYPE_ADD_DEVICE_ITEM = "SECURITY_LOG_TYPE_ADD_DEVICE_ITEM";
    //编辑设备巡检项
    String SECURITY_LOG_TYPE_MODIFY_DEVICE_ITEM = "SECURITY_LOG_TYPE_MODIFY_DEVICE_ITEM";
    //添加巡检点
    String SECURITY_LOG_TYPE_ADD_CHECK_POINT = "SECURITY_LOG_TYPE_ADD_CHECK_POINT";
    //编辑巡检点
    String SECURITY_LOG_TYPE_MODIFY_CHECK_POINT = "SECURITY_LOG_TYPE_MODIFY_CHECK_POINT";
    //删除巡检点
    String SECURITY_LOG_TYPE_DEL_CHECK_POINT = "SECURITY_LOG_TYPE_DEL_CHECK_POINT";

    //设备档案导出模版路径
    String REPORT_DEVICE_RECORD_MODEL_FILE_PATH = PropertyUtils.getProperty("report.device.record.model.file.path");
    String REPORT_DEVICE_RECORD_DICT_ITEM_CODE = "REPORT_MODEL_DEVICE_RECORD";//非列表表格对应属性
    //巡检点设备导出模版路径
    String CHECK_POINT_DEVICE_MODEL_FILE_PATH = PropertyUtils.getProperty("check.point.device.model.file.path");

    //跨模块以sql文,查询部门列表
    String FIND_ORG_LIST_BY_SQL = "find.org.list.by.sql";
    //跨模块以sql文,查询建筑物列表
    String FIND_BUILDING_LIST_BY_SQL = "find.building.list.by.sql";

    //微信扫设备二维码信息接口
    String PROJECT_DEVICE_QR_NO_URL = PropertyUtils.getProperty("project.device.qr.no.url");
    //水压设备实时信息
    String WATER_DEVICE_REAL_TIME_VALUE_URL = PropertyUtils.getProperty("water.device.real.time.value.url");

    // 生成故障报修url
    String SAVE_FAULT_URL = PropertyUtils.getProperty("save.fault.url");
    // 故障类型
    String FIND_FAULT_TYPE = PropertyUtils.getProperty("find.fault.type");
}
