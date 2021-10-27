package com.xjt.cloud.iot.core.entity.inter;

/**
 * @ClassName InterEntity
 * @Author dwt
 * @Date 2020-07-15 15:11
 * @Version 1.0
 */
public class InterEntity {
    //外围
    private Integer outerNum;
    //洁净区
    private Integer cleanAreaNum;

    private String createTime;

    public Integer getOuterNum() {
        return outerNum;
    }

    public void setOuterNum(Integer outerNum) {
        this.outerNum = outerNum;
    }

    public Integer getCleanAreaNum() {
        return cleanAreaNum;
    }

    public void setCleanAreaNum(Integer cleanAreaNum) {
        this.cleanAreaNum = cleanAreaNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
