package com.xjt.cloud.project.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @ClassName projectFile 建筑物楼层实体
 * @Author zhangZaiFa
 * @Date 2020-05-18 15:15
 * @Description
 */
public class ProjectFile extends BaseEntity {

    //文件名
    private String fileName;

    //文件保存路径
    private String fileUrl;

    //类型   1、A卷   2、B卷
    private Integer type ;

    //文件类型  文件类型 0、其他  1、pdf  2、word   3、excel  4、txt
    private Integer fileType;

    //父类ID
    private Long parentId;

    //文件目录
    private String fileDirectory;

    //是否是文件  0、文件夹   1、文件
    private Integer isFile;

    //ids
    private List<Long> ids;

    //
    private List<ProjectFile> child;

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    private String oldFileName;

    private String oldFileDirectory;

    public List<ProjectFile> getChild() {
        return child;
    }

    public void setChild(List<ProjectFile> child) {
        this.child = child;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public String getOldFileDirectory() {
        return oldFileDirectory;
    }

    public void setOldFileDirectory(String oldFileDirectory) {
        this.oldFileDirectory = oldFileDirectory;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Integer getIsFile() {
        return isFile;
    }

    public void setIsFile(Integer isFile) {
        this.isFile = isFile;
    }

    public String getFileDirectory() {
        String patternBracket = "\\[_.*?\\_]";
        if(fileDirectory==null){
            return null;
        }
        return fileDirectory.replaceAll(patternBracket,"");
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
