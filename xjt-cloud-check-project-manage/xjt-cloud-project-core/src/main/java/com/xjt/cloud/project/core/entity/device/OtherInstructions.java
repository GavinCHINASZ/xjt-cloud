package com.xjt.cloud.project.core.entity.device;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.StringUtils;

/**
 * @ClassName OtherInstructions
 * @Author dwt
 * @Date 2020-04-10 15:49
 * @Description 设备登记其他说明
 * @Version 1.0
 */
public class OtherInstructions extends BaseEntity {
    //项目明细id
    private Long projectInfoId;
    //说明
    private String instructions;

    public Long getProjectInfoId() {
        return projectInfoId;
    }

    public void setProjectInfoId(Long projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    public String getInstructions() {
        if (StringUtils.isEmpty(instructions)){
            return "";
        }
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
