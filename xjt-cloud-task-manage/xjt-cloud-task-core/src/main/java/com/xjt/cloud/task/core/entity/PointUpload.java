package com.xjt.cloud.task.core.entity;

/**
 * @ClassName PointUpload
 * @Author dwt
 * @Date 2020-03-24 13:53
 * @Description 任务管理新增任务设备导入
 * @Version 1.0
 */
public class PointUpload {
    private Integer rowNum;
    private String pointQrNo;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getPointQrNo() {
        return pointQrNo;
    }

    public void setPointQrNo(String pointQrNo) {
        this.pointQrNo = pointQrNo;
    }
}
