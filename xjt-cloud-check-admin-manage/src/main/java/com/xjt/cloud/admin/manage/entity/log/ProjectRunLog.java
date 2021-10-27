package com.xjt.cloud.admin.manage.entity.log;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;

/**
 * l_project_run_log
 * 监听项目运行状态日志
 *
 * @author huanggc
 * @date 2020/09/08
 */
public class ProjectRunLog extends BaseEntity {
    /**
     * 项编码
     */
    private String itemValue;

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
