package com.xjt.cloud.task.core.entity.check;

/**
 * @ClassName CheckItem
 * @Author dwt
 * @Date 2019-08-16 10:16
 * @Description 巡检项
 * @Version 1.0
 */
public class CheckItemTask  {
    //巡检项名称
    private String checkName;
    //最小值
    private Integer minValue;
    //最大值
    private Integer maxValue;
    //巡检类型0:默认 1：数值  2：计数  3：描述
    private Integer checkType;
    private Integer checkAction;//巡检项类型1:巡检 2:测试 3:保养 4、抽检
    //类型  1、默认  4、多端项  5、带自动检测的项
    private Integer type;
    //排序
    private Integer sort;
    //终端编号
    private String  terminalCode;
    //地址
    private String  address;
    //巡检项id
    private Long checkId;

    //单位
    private String unit;
    //检查标准
    private String checkSpecification;

    //巡检结果0：正常，1：故障
    private Integer checkResult;
    private Long id;

    private Integer maxCheckValue;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCheckAction() {
        return checkAction;
    }

    public void setCheckAction(Integer checkAction) {
        this.checkAction = checkAction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCheckSpecification(String checkSpecification) {
        this.checkSpecification = checkSpecification;
    }

    public String getCheckSpecification() {
        return checkSpecification;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        if(maxCheckValue != null && maxCheckValue > 0){
            return maxCheckValue;
        }
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMaxCheckValue() {
        return maxCheckValue;
    }

    public void setMaxCheckValue(Integer maxCheckValue) {
        this.maxCheckValue = maxCheckValue;
    }

}
