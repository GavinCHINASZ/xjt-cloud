package com.xjt.cloud.admin.manage.entity.sys;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.dict.DictUtils;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:46
 * @Description: app版本管理实体
 */
public class Version extends BaseEntity {

    //版本号 （版本）
    private String versionNum;
    //版本大号，版本的细分
    private String versionSize;
    //版本更新的标题
    private String versionTitle;
    //版本更新的内容
    private String updateContent;
    //app类型 1 安卓 2 大屏安卓
    private Integer type;
    //来源，区分各种app分组
    private Integer sourceType;
    //下载路径
    private String url;
    private Integer fileSize;//文件大小，单位M
    private Integer updateType;//更新类型　1推送一次　２每次推送　３强制更新

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(String versionSize) {
        this.versionSize = versionSize;
    }

    public String getVersionTitle() {
        return versionTitle;
    }

    public void setVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("APP_TYPE",type + "");
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceTypeDesc() {
        return DictUtils.getItemNameByDictAndItemValue("APP_SOURCE_TYPE",sourceType + "");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }
}
