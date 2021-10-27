package com.xjt.cloud.task.core.entity.check;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.task.core.entity.TaskSpotCheckTool;
import com.xjt.cloud.task.core.entity.device.Device;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 巡检记录
 *
 * @Author dwt
 * @Date 2019-07-25 9:30
 * @Description:定时任务生成的巡检记录
 * @Version 1.0
 */
public class CheckRecord extends BaseEntity {
    private Integer totalCount;
    private Long[] ids;// CheckRecord的ID数组
    private String idString;

    //任务id
    private Long taskId;
    private Long[] taskIds;
    //任务名称
    private String taskName;
    //任务父id
    private Long taskParentId;

    //项目id
    private Long projectId;
    private String projectName;

    // 巡检的开始时间
    private Date checkStartTime;

    //部门ID
    private Long orgId;
    //部门名称
    private String orgName;

    //设备id
    private Long deviceId;
    private Long deviceTypeId;
    //设备二维码
    private String deviceQrNo;

    //故障设备数量
    private Integer faultDeviceCount;
    //正常设备数量
    private Integer normalDeviceCount;
    //设备总数
    private Integer deviceCount;
    //设备名称
    private String deviceName;
    //设备地址
    private String pointLocation;
    private String imgUrl;

    private Integer checkResult;//巡检结果 0：正常，1：故障
    private Integer[] checkResults;
    private Integer checkType;//巡检类型: 0巡查任务，1(检测)检查任务，2保养任务  3、抽查任务
    private Integer[] checkTypes;

    //任务状态 0:正常,1:异常,2:未检
    private Integer deviceStatus;

    //巡更点二维码
    private String checkPointQrNo;
    //巡检人名称
    private String checkerName;
    //巡检点id
    private Long checkPointId;
    //巡查点名称
    private String checkPointName;
    private Integer faultCheckCount;// 故障巡查数

    private Long buildingId;//建筑物id
    private Long[] buildingIds;//建筑物id
    private String buildingName;// 建筑物名称
    private Long buildingFloorId;//建筑物楼层id
    private Long[] buildingFloorIds;//建筑物楼层id
    //楼层名称
    private String floorName;
    //开始日期
    private Date startDate;
    //结束日期
    private Date endDate;
    //是否修复  0、正常   1、未处理需报修(未修复), 2、已处理(已修复)
    private Integer handleStatus;
    //补充说明 or 描述
    private String faultDescription;
    private String faultDescriptions;

    private Long versionNo;//版本号
    //设备巡检项
    private List<CheckItemTask> checkItemTask;
    private String userName;
    private Integer taskStatus;// 任务状态

    // 巡检结果
    private List<CheckItemRecord> checkItemTaskList;
    private Boolean nearDate;// 近30天
    private List<String> imageUrls;

    // 故障描述(故障类型)
    private Long deviceFaultTypeId;
    private String deviceFaultType;

    private String faultLocation;// 故障部位
    private String faultReason;// 故障原因
    private String deviceImgUrl;//设备图片
    private String manageRegion;//管理区域
    //终端编号(接口地址)
    private String terminalCode;
    //地址(接口描述)
    private String address;

    private String reportName;
    private String reportNo;
    private String sysName;
    private String checkItem;
    private String checkName;
    private String deviceSysName;
    private Long[] deviceCheckItemIds;
    private List<TaskSpotCheckTool> taskSpotCheckTools;

    private Object object;
    private String title;
    private String titleName;
    private Integer type;//1、特殊抽检设备

    //包含建筑物名称楼层名
    private String checkPointLocation;

    // 地铁列
    private Double colA;
    private Double colB;
    private Double colC;
    private Double colD;
    private Double colE;
    private Double colF;
    private Double colG;
    private Double colH;
    private Double colI;

    private String fileUrl;
    private Integer downType;// 1 doc,  2 excel,  3 pdf
    private String unit;// 单位
    /**
     * 别名
     */
    private String sign;
    private Integer checkItemVsType;//巡检项的项目类型，1默认 2精简版 3项目自定义

    public String getCheckPointLocation() {
        return checkPointLocation;
    }

    public void setCheckPointLocation(String checkPointLocation) {
        this.checkPointLocation = checkPointLocation;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getManageRegion() {
        return manageRegion;
    }

    public void setManageRegion(String manageRegion) {
        this.manageRegion = manageRegion;
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

    public List<TaskSpotCheckTool> getTaskSpotCheckTools() {
        return taskSpotCheckTools;
    }

    public void setTaskSpotCheckTools(List<TaskSpotCheckTool> taskSpotCheckTools) {
        this.taskSpotCheckTools = taskSpotCheckTools;
    }

    public String getDeviceImgUrl() {
        return deviceImgUrl;
    }

    public void setDeviceImgUrl(String deviceImgUrl) {
        this.deviceImgUrl = deviceImgUrl;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public Long[] getDeviceCheckItemIds() {
        return deviceCheckItemIds;
    }

    public void setDeviceCheckItemIds(Long[] deviceCheckItemIds) {
        this.deviceCheckItemIds = deviceCheckItemIds;
    }

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public CheckRecord() {

    }

    public CheckRecord(Device device) {
        this.deviceCount = device.getDeviceCount();
        this.deviceId = device.getDeviceId();
        this.checkPointId = device.getCheckPointId();
        this.deviceName = device.getDeviceName();
        this.projectId = device.getProjectId();
        this.deviceQrNo = device.getQrNo();
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public List<CheckItemRecord> getCheckItemTaskList() {
        return checkItemTaskList;
    }

    public void setCheckItemTaskList(List<CheckItemRecord> checkItemTaskList) {
        this.checkItemTaskList = checkItemTaskList;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Integer getFaultDeviceCount() {
        return faultDeviceCount;
    }

    public void setFaultDeviceCount(Integer faultDeviceCount) {
        this.faultDeviceCount = faultDeviceCount;
    }

    public Integer getNormalDeviceCount() {
        return normalDeviceCount;
    }

    public void setNormalDeviceCount(Integer normalDeviceCount) {
        this.normalDeviceCount = normalDeviceCount;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public String getDeviceCountDesc() {
        if (null == deviceCount) {
            return "/";
        }
        return deviceCount + "";
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public String getFaultDescription() {
        if (StringUtils.isEmpty(faultDescription)) {
            return "";
        }
        return faultDescription;
    }

    public String getFaultDescriptionDesc() {
        if (StringUtils.isEmpty(faultDescription)) {
            return "/";
        }
        return faultDescription;
    }

    public String getFaultDescriptions() {
        if (StringUtils.isEmpty(faultDescriptions)) {
            return "";
        }
        return faultDescriptions;
    }

    public void setFaultDescriptions(String faultDescriptions) {
        this.faultDescriptions = faultDescriptions;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public String getDeviceFaultType() {
        return deviceFaultType;
    }

    public void setDeviceFaultType(String deviceFaultType) {
        this.deviceFaultType = deviceFaultType;
    }

    public String getFaultLocation() {
        return faultLocation;
    }

    public void setFaultLocation(String faultLocation) {
        this.faultLocation = faultLocation;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long[] getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(Long[] taskIds) {
        this.taskIds = taskIds;
    }

    public String getPointLocation() {
        if (StringUtils.isEmpty(pointLocation)) {
            return "";
        }
        return pointLocation;
    }

    public void setPointLocation(String pointLocation) {
        this.pointLocation = pointLocation;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCheckPointName() {
        if (StringUtils.isEmpty(checkPointName)) {
            return "";
        }
        return checkPointName;
    }

    public void setCheckPointName(String checkPointName) {
        this.checkPointName = checkPointName;
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

    public Long[] getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(Long[] buildingIds) {
        this.buildingIds = buildingIds;
    }

    public Long[] getBuildingFloorIds() {
        return buildingFloorIds;
    }

    public void setBuildingFloorIds(Long[] buildingFloorIds) {
        this.buildingFloorIds = buildingFloorIds;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskParentId() {
        return taskParentId;
    }

    public void setTaskParentId(Long taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public String getCheckName() {
        if (StringUtils.isEmpty(checkName)) {
            return "";
        }
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getDeviceSysName() {
        return deviceSysName;
    }

    public void setDeviceSysName(String deviceSysName) {
        this.deviceSysName = deviceSysName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public String getCheckResultDesc() {
        if (checkResult == null) {
            return "/";
        }
        if (checkResult == 0) {
            return "正常";
        }
        return "异常";// 5.0:故障,  地铁:异常
    }

    /**
     * 微型消防站巡检卡
     *
     * @return
     */
    public String getCheckResultDescs() {
        if (checkResult == null) {
            return "/";
        }
        if (checkResult == 0) {
            return "√";
        }
        return "×";
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Integer[] getCheckResults() {
        return checkResults;
    }

    public void setCheckResults(Integer[] checkResults) {
        this.checkResults = checkResults;
    }

    public Integer[] getCheckTypes() {
        return checkTypes;
    }

    public void setCheckTypes(Integer[] checkTypes) {
        this.checkTypes = checkTypes;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public String getCheckerNameDesc() {
        if (StringUtils.isEmpty(checkerName)) {
            return "/";
        }
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Long getCheckPointId() {
        return checkPointId;
    }

    public void setCheckPointId(Long checkPointId) {
        this.checkPointId = checkPointId;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceStatusDesc() {
        String deviceStatusDesc = "未检";
        if (deviceStatus != null && deviceStatus == 0) {
            deviceStatusDesc = "已检";
        } else if (deviceStatus != null && deviceStatus == 1) {
            deviceStatusDesc = "故障";
        }
        return deviceStatusDesc;
    }

    public String getDeviceStatusDescs() {
        if (deviceStatus != null && deviceStatus == 0) {
            return "正常";
        } else if (deviceStatus != null && deviceStatus == 1) {
            return "异常";
        }
        return "/";
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDeviceQrNo() {
        return deviceQrNo;
    }

    public void setDeviceQrNo(String deviceQrNo) {
        this.deviceQrNo = deviceQrNo;
    }

    public String getCheckPointQrNo() {
        return checkPointQrNo;
    }

    public void setCheckPointQrNo(String checkPointQrNo) {
        this.checkPointQrNo = checkPointQrNo;
    }

    public List<CheckItemTask> getCheckItemTask() {
        return checkItemTask;
    }

    public void setCheckItemTask(List<CheckItemTask> checkItemTask) {
        this.checkItemTask = checkItemTask;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Boolean getNearDate() {
        return nearDate;
    }

    public void setNearDate(Boolean nearDate) {
        this.nearDate = nearDate;
    }

    public String getCreateTimeDesc() {
        if (getCreateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
            String lastDayOfMonth = sdf.format(getCreateTime());
            return lastDayOfMonth;
        } else {
            return "";
        }
    }

    public String getCreateTimeDescs() {
        if (getCreateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(getCreateTime());
        }
        return "";
    }

    public Integer getFaultCheckCount() {
        return faultCheckCount;
    }

    public void setFaultCheckCount(Integer faultCheckCount) {
        this.faultCheckCount = faultCheckCount;
    }

    public Long getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Long deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
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
            this.buildingName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_CACHE_KEY, getBuildingId(), "buildingName");
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
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/10/30 10:12
     */
    public String getFloorName() {
        if (getBuildingFloorId() != null && getBuildingFloorId() != 0) {
            this.floorName = CacheUtils.getCacheValueByTypeAndId(Constants.BUILDING_FLOOR_CACHE_KEY, getBuildingFloorId(), "floorName");
        }
        if (StringUtils.isEmpty(floorName)) {
            return "";
        }
        return floorName;
    }

    public String getDeviceName() {
        if (getDeviceTypeId() != null && getDeviceTypeId() != 0) {
            this.deviceName = CacheUtils.getCacheValueByTypeAndId(Constants.DEVICE_SYS_CACHE_KEY, getDeviceTypeId(), "deviceSysName");
        }
        if (StringUtils.isEmpty(this.deviceName)) {
            return "";
        }
        return deviceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Double getColA() {
        return colA;
    }

    public String getColADesc() {
        if (null == colA) {
            return "/";
        }
        return colA == 0 ? "√" : "×";
    }

    public void setColA(Double colA) {
        this.colA = colA;
    }

    public Double getColB() {
        return colB;
    }

    public String getColBDesc() {
        if (null == colB) {
            return "/";
        }
        return colB == 0 ? "√" : "×";
    }

    public void setColB(Double colB) {
        this.colB = colB;
    }

    public Double getColC() {
        return colC;
    }

    public String getColCDesc() {
        if (null == colC) {
            return "/";
        }
        return colC == 0 ? "√" : "×";
    }

    public void setColC(Double colC) {
        this.colC = colC;
    }

    public Double getColD() {
        return colD;
    }

    public String getColDDesc() {
        if (null == colD) {
            return "/";
        }
        return colD == 0 ? "√" : "×";
    }

    public void setColD(Double colD) {
        this.colD = colD;
    }

    public Double getColE() {
        return colE;
    }

    public String getColEDesc() {
        if (null == colE) {
            return "/";
        }
        return colE == 0 ? "√" : "×";
    }

    public String getColEDescs() {
        if (null == colE) {
            return "/";
        }
        return colE.toString();
    }

    public void setColE(Double colE) {
        this.colE = colE;
    }

    public Double getColF() {
        return colF;
    }

    public String getColFDesc() {
        if (null == colF) {
            return "/";
        }
        return colF == 0 ? "√" : "×";
    }

    public String getColFDescs() {
        if (null == colF) {
            return "/";
        }
        return colF.toString();
    }

    public void setColF(Double colF) {
        this.colF = colF;
    }

    public Double getColG() {
        return colG;
    }

    public String getColGDesc() {
        if (null == colG) {
            return "/";
        }
        return colG == 0 ? "√" : "×";
    }

    public void setColG(Double colG) {
        this.colG = colG;
    }

    public Double getColH() {
        return colH;
    }

    public String getColHDesc() {
        if (null == colH) {
            return "/";
        }
        return colH == 0 ? "√" : "×";
    }

    public String getColHDescs() {
        if (null == colH) {
            return "/";
        }
        return colH.toString();
    }

    public void setColH(Double colH) {
        this.colH = colH;
    }

    public Double getColI() {
        return colI;
    }

    public String getColIDesc() {
        if (null == colI) {
            return "/";
        }
        return colI == 0 ? "√" : "×";
    }

    public String getColIDescs() {
        if (null == colI) {
            return "/";
        }
        return colI.toString();
    }

    public void setColI(Double colI) {
        this.colI = colI;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getDownType() {
        return downType;
    }

    public void setDownType(Integer downType) {
        this.downType = downType;
    }

    public String getUnit() {
        if (StringUtils.isEmpty(unit)) {
            return "";
        }
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getCheckItemVsType() {
        return checkItemVsType;
    }

    public void setCheckItemVsType(Integer checkItemVsType) {
        this.checkItemVsType = checkItemVsType;
    }
}
