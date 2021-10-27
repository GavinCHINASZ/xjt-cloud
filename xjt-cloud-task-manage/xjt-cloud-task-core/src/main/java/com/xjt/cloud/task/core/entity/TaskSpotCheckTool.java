package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TaskSpotCheckTool
 * @Author zhangzaifa
 * @Date 2020-05-09 13:58
 * @Description 任务抽查工具
 * @Version 1.0
 */
public class TaskSpotCheckTool extends BaseEntity {
    //任务id
    private Long taskId;
    private Long[] taskIds;

    /**
     * 巡检记录ID
     */
    private Long checkRecordId;
    private Long[] checkRecordIds;
    //工具名称
    private String toolName;
    //规格型号
    private String specificationModel;
    //出厂编号
    private String serialNumber;
    private String deviceSysName;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Long[] taskIds) {
        this.taskIds = taskIds;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public Long[] getCheckRecordIds() {
        return checkRecordIds;
    }

    public void setCheckRecordIds(Long[] checkRecordIds) {
        this.checkRecordIds = checkRecordIds;
    }

    public String getToolName() {
        if (StringUtils.isEmpty(toolName)){
            return "";
        }
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getSpecificationModel() {
        if (StringUtils.isEmpty(specificationModel)){
            return "";
        }
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    public String getSerialNumber() {
        if (StringUtils.isEmpty(serialNumber)){
            return "";
        }
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }
}
