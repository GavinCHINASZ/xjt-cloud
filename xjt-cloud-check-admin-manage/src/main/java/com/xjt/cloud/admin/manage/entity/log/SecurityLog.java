package com.xjt.cloud.admin.manage.entity.log;

import com.xjt.cloud.commons.base.BaseEntity;

/**
 * SecurityLogRunLog
 * 日志
 *
 * @author huanggc
 * @date 2020/10/21
 */
public class SecurityLog extends BaseEntity {
    /**
     * 操作内容
     */
    private String content;

    //数据库名
    private String databasesName;

    //表名
    private String tableName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatabasesName() {
        return databasesName;
    }

    public void setDatabasesName(String databasesName) {
        this.databasesName = databasesName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
