package com.xjt.cloud.client.core.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: huanggc
 * @Date: 2019/12/02
 * @Description: 任务公共参数
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
}
