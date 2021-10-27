package com.xjt.cloud.client.core.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/8 18:20
 * @Description: 设备项目提示信息类
 */
public interface ConstantsDeviceMsg {

    String UPLOAD_PROJECT_NAME_FAIL = "导入的项目名称与当前项目名称不相同！";
    String UPLOAD_CO_ORG_NAME_FAIL = "导入的公司部门名称不存在，或不匹配！";
    String UPLOAD_BUILDING_FLOOR_FAIL = "导入的建筑物与楼层信息不存在，或不匹配！";
    String UPLOAD_QR_NO_FAIL = "导入的二维码在该项目下不存在！";
    String UPLOAD_PARAM_NULL_FAIL = "公司/部门/建筑物/楼层/设备名称/项目名称/有效期/送修期 都不能为空；数量不能为空并且大于0！";

    /*地铁大屏接口，各模块URL地址*/
    // 故障报修列表url
    String FIND_SCREEN_FAULT_DATA_ANALYZE_URL = PropertyUtils.getProperty("find.screen.fault.data.analysis.url");
    // 地铁巡检工单 子任务 列表URL
    String FIND_SCREEN_TASK_DATA_ANALYZE_URL = PropertyUtils.getProperty("find.screen.task.data.analysis.url");
    // 要查询的项目IDS
    String FIND_SCREEN_PROJECT_IDS = PropertyUtils.getProperty("find.screen.project.ids");

}
