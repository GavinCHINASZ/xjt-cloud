package com.xjt.cloud.device.core.common;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/8 18:20
 * @Description: 设备项目提示信息类
 */
public interface ConstantsDeviceMsg {

    String UPLOAD_PROJECT_NAME_FAIL = "导入的项目名称与当前项目名称不相同！";
    String UPLOAD_CO_ORG_NAME_FAIL = "导入的公司部门名称不存在，或不匹配！";
    String UPLOAD_DEVICE_TYPE_NAME_FAIL = "导入的设备名称不存在，或不匹配！";
    String UPLOAD_BUILDING_FLOOR_FAIL = "导入的建筑物与楼层信息不存在，或不匹配！";
    String UPLOAD_QR_NO_FAIL = "导入的二维码在该项目下不存在！";
    String UPLOAD_PARAM_NULL_FAIL = "巡检点ID/巡检点名称/公司/部门/建筑物/楼层/设备名称/项目名称 都不能为空；数量不能为空并且大于0！";

}
