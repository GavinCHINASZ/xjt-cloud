package com.xjt.cloud.task.core.entity;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.task.core.common.ConstantsDevice;
import com.xjt.cloud.task.core.entity.check.CheckRecord;

import java.util.Date;
import java.util.List;

/**
 * 任务巡检点
 * 关联任务设备
 *
 * @author dwt
 * @version 1.0
 * @date 2019-07-24 18:00
 */
public class TaskCheckPoint extends BaseEntity {
    //任务id
    private Long taskId;
    private Long taskParentId;
    //巡检点id
    private Long checkPointId;
    //巡检点名称
    private String checkPointName;

    //建筑物ID
    private Long buildingId;
    //楼层ID
    private Long buildingFloorId;
    //建筑物名称
    private String buildingName;
    //楼层名称
    private String floorName;
    //巡检点位置
    private String pointLocation;
    //任务巡查点状态  0、未检  1、故障   2、正常
    private Integer taskCheckPointStatus = 0;
    //巡检点二维码
    private String qrNo;
    //部门名称
    private String orgName;
    //任务名称
    private String taskName;
    //部门ID
    private Long orgId;

    //巡查点设备图片
    private String imgUrl;

    // 巡检的开始时间
    private Date checkStartTime;

    //巡检记录
    private List<CheckRecord> checkRecords;
    private List<String> taskExecutorsName;//任务执行人

    // 任务巡检点图片
    private List<String> imageUrls;
    private Long[] checkPointIds;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<CheckRecord> getCheckRecords() {
        return checkRecords;
    }

    public void setCheckRecords(List<CheckRecord> checkRecords) {
        this.checkRecords = checkRecords;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

   /* public String getOrgName() {
        return orgName;
    }*/

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public Long getBuildingFloorId() {
        return buildingFloorId;
    }

    public void setBuildingFloorId(Long buildingFloorId) {
        this.buildingFloorId = buildingFloorId;
    }

    /*public String getBuildingName() {
        return buildingName;
    }*/

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getPointLocation() {
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(Long taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public String getCheckPointName() {
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
    }

    public Integer getTaskCheckPointStatus() {
        return taskCheckPointStatus;
    }

    public void setTaskCheckPointStatus(Integer taskCheckPointStatus) {
        this.taskCheckPointStatus = taskCheckPointStatus;
    }

    public List<String> getTaskExecutorsName() {
        return taskExecutorsName;
    }

    public void setTaskExecutorsName(List<String> taskExecutorsName) {
        this.taskExecutorsName = taskExecutorsName;
    }

    public Long[] getCheckPointIds() {
        return checkPointIds;
    }

    public void setCheckPointIds(Long[] checkPointIds) {
        this.checkPointIds = checkPointIds;
    }

    /**
     * 功能描述:得到建筑物名称
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getBuildingName() {
        if (getBuildingId() != null && getBuildingId() != 0) {
            buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
        }
        if (StringUtils.isEmpty(buildingName)) {
            return "";
        }
        return buildingName;
    }

    /**
     * 功能描述:得到建筑物楼层名称
     *
     * @param
     * @return java.lang.String
     * @auther wangzhiwen
     * @date 2019/10/30 10:12
     */
    public String getFloorName() {
        if (getBuildingFloorId() != null && getBuildingFloorId() != 0) {
            floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
        }
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }

    public String getOrgName() {
        if (getOrgId() != null && getOrgId() != 0) {
            orgName = CacheUtils.getCacheValueByTypeAndId(Constants.ORG_CACHE_KEY, getOrgId(), "orgName");
        }
        if (StringUtils.isEmpty(orgName)) {
            return "";
        }
        return orgName;
    }

    public String getImgUrl() {
        if (StringUtils.isEmpty(imgUrl)) {
            imgUrl = ConstantsDevice.CHECK_POINT_IMAGE_URL;
        }
        return imgUrl;
    }

}
