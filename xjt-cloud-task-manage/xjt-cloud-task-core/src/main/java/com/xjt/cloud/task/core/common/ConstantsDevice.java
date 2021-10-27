package com.xjt.cloud.task.core.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * 任务公共参数
 *
 * @author huanggc
 * @date 2019/12/02
 */
public interface ConstantsDevice {
    // PC任务管理列表导出模版路径
    String TASK_MODEL_FILE_PATH = PropertyUtils.getProperty("task.model.file.path");
    // PC任务工单列表导出模版路径
    String TASK_WORK_MODEL_FILE_PATH = PropertyUtils.getProperty("task.work.model.file.path");
    // PC巡检记录列表导出模版路径
    String CHECK_RECORD_MODEL_FILE_PATH = PropertyUtils.getProperty("check.record.model.file.path");
    // PC月任务汇总 任务概览表导出列表模版路径
    String TASK_OVERVIEW_MODEL_FILE_PATH = PropertyUtils.getProperty("task.overview.model.file.path");
    // PC月任务汇总 巡查概览表导出列表模版路径
    String CHECK_OVERVIEW_MODEL_FILE_PATH = PropertyUtils.getProperty("check.overview.model.file.path");
    //巡检点图片URL
    String CHECK_POINT_IMAGE_URL = PropertyUtils.getProperty("check.point.image.url");
    //生成故障保修URL
    String FAULT_REPAIR_RECORD_URL = PropertyUtils.getProperty("fault.repair.record.url");
    //任务管理详情导出模板路径
    String TASK_MANAGE_DETAIL_FILE_PATH = PropertyUtils.getProperty("task.manage.detail.file.path");
    String METRO_TASK_MANAGE_DETAIL_FILE_PATH = PropertyUtils.getProperty("metro.task.manage.detail.file.path");
    //任务管理详情非列表表格对应属性
    String TASK_MANAGE_DETAIL_DICT_ITEM_CODE = "TASK_MANAGE_DETAIL";
    String METRO_TASK_MANAGE_DETAIL_DICT_ITEM_CODE = "METRO_TASK_MANAGE_DETAIL";
    //任务工单详情导出模板路径
    String TASK_SHEET_DETAIL_FILE_PATH = PropertyUtils.getProperty("task.sheet.detail.file.path");
    String METRO_TASK_SHEET_DETAIL_FILE_PATH = PropertyUtils.getProperty("metro.task.sheet.detail.file.path");
    //任务设备导入模板下载
    String TASK_UPLOAD_MODEL_FILE_PATH = PropertyUtils.getProperty("task.download.model.file.path");

    // 任务工单设备导入模板下载
    String TASK_SHEET_MODEL_FILE_PATH = PropertyUtils.getProperty("task.sheet.download.model.file.path");

    // 任务管理模板 20210309
    String TASK_MANAGE_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("task.manage.excel.model.file.path");

    //任务工单详情非列表表格对应属性
    String TASK_SHEET_DETAIL_DICT_ITEM_CODE = "TASK_SHEET_DETAIL";
    String METRO_TASK_SHEET_DETAIL_DICT_ITEM_CODE = "METRO_TASK_SHEET_DETAIL";
    // 压缩表格文件路径;
    String ZIP_FILE_URL = PropertyUtils.getProperty("zip.file.url");

    // QSZDYK1014907B2  地铁
    String SEVEN_TABLE_FILE_PATH = PropertyUtils.getProperty("seven.table.file.path");
    String SMOKE_PROBE_DICT_ITEM_CODE = "SMOKE_PROBE_DICT_ITEM_CODE";

    // QSZDYK1014908B2
    String EIGHT_TABLE_FILE_PATH = PropertyUtils.getProperty("eight.table.file.path");
    String EIGHT_PROBE_DICT_ITEM_CODE = "EIGHT_PROBE_DICT_ITEM_CODE";

    // QSZDYK1014909B2
    String NINE_TABLE_FILE_PATH = PropertyUtils.getProperty("nine.table.file.path");
    String NINE_TABLE_DICT_ITEM_CODE = "NINE_TABLE_DICT_ITEM_CODE";

    // QSZDYK1014911B2
    String ELEVEN_TABLE_FILE_PATH = PropertyUtils.getProperty("eleven.table.file.path");
    String ELEVEN_PROBE_DICT_ITEM_CODE = "ELEVEN_PROBE_DICT_ITEM_CODE";

    // 模板文件路径
    String MODEL_FILE_URL = PropertyUtils.getProperty("model.file.url");
    // 报表 word 文件路径
    String REPORT_WORD_FILE_URL = PropertyUtils.getProperty("report.word.file.url");
    // 报表 表格 文件路径
    String REPORT_EXCEL_FILE_URL = PropertyUtils.getProperty("report.excel.file.url");

    // 微型消防站word模板
    String MINIATURE_FIRE_CONTROL_FILE_NAME = "miniatureFireControl.xml";
    // 巡检记录word模板
    String CHECK_RECORD_WORD_FILE_NAME = "checkRecordMetroWord.xml";
    // 运营总部消防设施日常巡查记录word模板
    String DAILY_PATROL_WORD_NAME = "dailyPatrol.xml";

    // 查询 成员名称  http://120.76.193.19:7082/project/orgUser/findUsersName
    String FIND_ORG_USER_NAME_URL = PropertyUtils.getProperty("find.org.user.name.url");

    // 生成报表文件路径
    String DEVICE_FAULT_REPORT_FILE_URL = PropertyUtils.getProperty("device.fault.report.file.url");

    //webSocket发送信息urls
    String WEB_SOCKET_SEND_MSG_URLS = PropertyUtils.getProperty("web.socket.send.msg.urls");

    String TASK = "TASK";
    String CHECK_RECORD = "CheckRecord";
}