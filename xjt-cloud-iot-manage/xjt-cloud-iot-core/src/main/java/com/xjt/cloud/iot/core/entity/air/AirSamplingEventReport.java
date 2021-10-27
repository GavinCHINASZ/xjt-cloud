package com.xjt.cloud.iot.core.entity.air;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @ClassName AirSamplingEventReport
 * @Description 空气采样事件报表
 * @Author wangzhiwen
 * @Date 2021/4/1 14:51
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirSamplingEventReport {
    private Integer pipelineSuper1;//管道1超高
    private Integer pipelineUltraLow1;//管道1超低
    private Integer pipelineSuper2;//管道2超高
    private Integer pipelineUltraLow2;//管道2超低
    private Integer totalNum;//事件总数
    private String timeDesc;

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPipelineSuper1() {
        return pipelineSuper1;
    }

    public void setPipelineSuper1(Integer pipelineSuper1) {
        this.pipelineSuper1 = pipelineSuper1;
    }

    public Integer getPipelineUltraLow1() {
        return pipelineUltraLow1;
    }

    public void setPipelineUltraLow1(Integer pipelineUltraLow1) {
        this.pipelineUltraLow1 = pipelineUltraLow1;
    }

    public Integer getPipelineSuper2() {
        return pipelineSuper2;
    }

    public void setPipelineSuper2(Integer pipelineSuper2) {
        this.pipelineSuper2 = pipelineSuper2;
    }

    public Integer getPipelineUltraLow2() {
        return pipelineUltraLow2;
    }

    public void setPipelineUltraLow2(Integer pipelineUltraLow2) {
        this.pipelineUltraLow2 = pipelineUltraLow2;
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }
}
