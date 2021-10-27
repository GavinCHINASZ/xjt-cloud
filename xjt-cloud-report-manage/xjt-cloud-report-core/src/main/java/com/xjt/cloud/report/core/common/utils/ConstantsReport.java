package com.xjt.cloud.report.core.common.utils;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * 值班公共参数
 *
 * @author zhangzaifa
 * @date 2020/05/11
 */

public interface ConstantsReport {

    String USER_PROJECT_PERMISSION_URL =  PropertyUtils.getProperty("user.project.permission.url");

    String DI_TIE_USER_URL = PropertyUtils.getProperty("di.tie.user.url");
    String REPORT_FROM_WORD_FILE_NAME = "reportFrom.xml";
    String REPORT_FILE_URL = PropertyUtils.getProperty("report.file.url");// 生成报表文件路径

    String REPORT_FROM_EXCEL_PATH = PropertyUtils.getProperty("report.from.excel.path");// 维保日报表格模板
    String REPORT_FROM_EXCEL_DICT_CODE = "REPORT_FROM_DICT_ITEM_CODE";// 维保日报itemCode
}
