package com.xjt.cloud.maintenance.core.entity.fault;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * 维修人,审核人 实体类
 *
 * @author huanggc
 * @date 2019/09/02
 **/
public class FaultHandler extends BaseEntity {
    /**
     * 故障记录ID(故障报修)
     */
    private Long faultRepairRecordId;

    /**
     * 维修人 orgUserId
     */
    private Long repairUserId;

    /**
     * 审核人 orgUserId
     */
    private Long examineUserId;

    /**
     * 1维修人, 2审核人
     */
    //private Integer userType;

    /**
     * 以下字段 数据库 中没有
     */
    private Long[] faultRepairRecordIds;
    private List<Long> ids;

    public Long getFaultRepairRecordId() {
        return faultRepairRecordId;
    }

    public void setFaultRepairRecordId(Long faultRepairRecordId) {
        this.faultRepairRecordId = faultRepairRecordId;
    }

    public Long getRepairUserId() {
        return repairUserId;
    }

    public void setRepairUserId(Long repairUserId) {
        this.repairUserId = repairUserId;
    }

    public Long getExamineUserId() {
        return examineUserId;
    }

    public void setExamineUserId(Long examineUserId) {
        this.examineUserId = examineUserId;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long[] getFaultRepairRecordIds() {
        return faultRepairRecordIds;
    }

    public void setFaultRepairRecordIds(Long[] faultRepairRecordIds) {
        this.faultRepairRecordIds = faultRepairRecordIds;
    }
}
