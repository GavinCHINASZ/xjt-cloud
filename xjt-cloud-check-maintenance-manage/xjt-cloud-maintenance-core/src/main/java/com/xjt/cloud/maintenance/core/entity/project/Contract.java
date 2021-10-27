package com.xjt.cloud.maintenance.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * @ClassName Contract
 * @Description 合同管理
 * @Author wangzhiwen
 * @Date 2021/4/17 10:56
 **/
public class Contract extends BaseEntity {
    private Long clientId;//客户id = check_project_id
    private String clientName;//客户名称
    private String contractName;//合同名称
    private Float money;//金额
    private Date validityPeriodBegin;//有效期开始日期
    private Date validityPeriodEnd;//有效期结束日期
    private String filePath;//合同文件路径
    private Long[] ids;
    private String limitSign;//以权限限制数据

    public String getLimitSign() {
        return limitSign;
    }

    public void setLimitSign(String limitSign) {
        this.limitSign = limitSign;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Float getMoney() {
        if (money == null){
            return 0F;
        }
        return money / 1000;
    }

    public Integer getMoneyInt() {
        if (money == null){
            return 0;
        }
        return (int)(money * 1000);
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Date getValidityPeriodBegin() {
        return validityPeriodBegin;
    }

    public void setValidityPeriodBegin(Date validityPeriodBegin) {
        this.validityPeriodBegin = validityPeriodBegin;
    }

    public Date getValidityPeriodEnd() {
        return validityPeriodEnd;
    }

    public void setValidityPeriodEnd(Date validityPeriodEnd) {
        this.validityPeriodEnd = validityPeriodEnd;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
