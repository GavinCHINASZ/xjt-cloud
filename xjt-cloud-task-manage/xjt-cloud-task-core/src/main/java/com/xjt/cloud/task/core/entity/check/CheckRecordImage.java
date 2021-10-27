package com.xjt.cloud.task.core.entity.check;


import java.util.List;

/**
 * 巡检记录图片实体
 *
 * @author dwt
 * @date 2019-08-09 8:47
 * @version 1.0
 */
public class CheckRecordImage {
    private Long  id ;

    /**
     * 巡检记录ID
     */
    private Long checkRecordId;
    private Long[] checkRecordIdList;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务ID
     */
    private Long checkPointId;

    /**
     * 0、是巡检记录图片  1、任务巡查点图片
     */
    private Integer type;
    private String imageUrl;

    public CheckRecordImage() {
    }

    public CheckRecordImage(String imageUrl, Long taskId, Long checkPointId, Integer type) {
        this.imageUrl = imageUrl;
        this.taskId = taskId;
        this.checkPointId = checkPointId;
        this.type = type;
    }

    public CheckRecordImage(String imageUrl, Long checkRecordId, Long taskId, Long checkPointId, int type) {
        this.imageUrl = imageUrl;
        this.taskId = taskId;
        this.checkPointId = checkPointId;
        this.type = type;
        this.checkRecordId=checkRecordId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckRecordId() {
        return checkRecordId;
    }

    public void setCheckRecordId(Long checkRecordId) {
        this.checkRecordId = checkRecordId;
    }

    public Long[] getCheckRecordIdList() {
        return checkRecordIdList;
    }

    public void setCheckRecordIdList(Long[] checkRecordIdList) {
        this.checkRecordIdList = checkRecordIdList;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }
}
