package com.xjt.cloud.maintenance.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CheckProject 检测项目信息实体
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public class CheckProject extends BaseEntity {

    //检测项目名称
    private String checkProjectName;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //街道
    private String roadNo;
    //经度
    private String lng;
    //纬度
    private String lat;
    //面积
    private Float acreage;
    //高度
    private Integer height;
    //层数
    private Integer floor;
    //项目ID
    private List<Long> projectIds;
    //详细地址
    private String address;
    //ids
    private List<Long> ids;
    //建筑物类别 1 一类高层民用建筑 2 二类高层民用建筑 3 高层厂房 4 高层库房 5 单、多层民用建筑
    // 6 单、多层厂房 7 单、多层库房 8 地下建筑 9  隧道、涵洞 10 其他建筑
    private String buildingType;
    private Integer checkRange;//检测范围 1、整体检测 2、局部检测
    private Integer checkType;//检测类型  1、竣工检测   2、年度检测
    private String checkPart;//检测部位
    private Float checkAcreage;//检测面积

    private String checkPartFun;//检测部位及功能
    private String number;//项目编号
    private String checkProjectNumber;//报告编号
    private String creditCode;//统一信用代码(建筑单位)
    private String requesterUnit;//委托单位
    private String designUnit;//设计单位
    private String constructionUnit;//施工单位
    private String drawingDescription;//竣工图纸提供情况
    private Date drawingTime;//竣工日期
    private Date checkStartTime;//检测开始日期
    private Date checkEndTime;//检测完成日期
    private String contactPhone;//联系电话
    private String contactUserName;//联系人 消防安全管理人
    private Integer contractAmount;//合同金额
    private Integer focusUnit;//重点单位 1、是  2不是
    private String fireApproval;//消防批文
    private String sysTypeIds;//系统类型Id用分号隔开
    private String leadingUserName;//负责人
    private String approvalUserName;//审批人
    private String checkUserName;//检测人
    private String checkCode;// 二维码
    private String checkProgramFileUrl;//检测方案URL
    private String checkLabelFileUrl;// 标签PDF URL
    private String contractFileUrl;//合同扫描件URL
    private String useFunction;//使用功能
    private String checkBase;//检测依据
    private String checkConclusion;//检测结论说明
    private Integer checkResult;//1、合格  2、不合格
    private Integer checkProjectStatus;//1、执行中  2、待签章  3、已签章   5、已发布
    private Integer[] sysTypeIdList;//类型ID数组
    private String checkProjectReportUrl;//检测项目报表PDF路径
    private Date checkProjectReportCreateTime;//报告签章时间
    private Integer checkVersion;//检测版本
    private String auditUserName; //审核人
    private Integer urlType;// 文件路径, 1系统, 2本地
    //检测员用户ID
    private List<Long>  checkUserIds;
    private String  userCertificateNumber;
    //项目负责人用户ID
    private Long leadingUserId;
    //批准人用户ID
    private Long approveUserId;
    //用户Id(只用于传参搜索)
    private Long userId;
    ////1、执行中  2、待签章  3、已签章   5、已发布
    private List<Integer> checkProjectStatusList;

    private String affiliatedUnit;//所属单位
    private String propertyManagementUnit;//物业管理单位
    private Integer automaticFireFighting;//自动消防设施 1有 2无

    private Date contractEndDate;//合同有效期结束日期
    private Integer buildingNum;//建筑数
    private String projectName;//录入单位，项目名称

    private Long planId;//计划id
    /**
     * 计划状态: 1未分配(未开始), 2已分配(执行中), 3待生成报告、4已完成, 5已盖章
     */
    private Integer planState;
    private String limitSign;//以权限限制数据

    public String getLimitSign() {
        return limitSign;
    }

    public void setLimitSign(String limitSign) {
        this.limitSign = limitSign;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Integer getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAffiliatedUnit() {
        return affiliatedUnit;
    }

    public void setAffiliatedUnit(String affiliatedUnit) {
        this.affiliatedUnit = affiliatedUnit;
    }

    public String getPropertyManagementUnit() {
        if (StringUtils.isEmpty(propertyManagementUnit)){
            return "/";
        }
        return propertyManagementUnit;
    }

    public void setPropertyManagementUnit(String propertyManagementUnit) {
        this.propertyManagementUnit = propertyManagementUnit;
    }

    public Integer getAutomaticFireFighting() {
        return automaticFireFighting;
    }

    public void setAutomaticFireFighting(Integer automaticFireFighting) {
        this.automaticFireFighting = automaticFireFighting;
    }

    public List<Integer> getCheckProjectStatusList() {
        return checkProjectStatusList;
    }

    public void setCheckProjectStatusList(List<Integer> checkProjectStatusList) {
        this.checkProjectStatusList = checkProjectStatusList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuditUserName() {
        if (StringUtils.isEmpty(auditUserName)){
            return "";
        }
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public List<Long> getCheckUserIds() {
        return checkUserIds;
    }

    public void setCheckUserIds(List<Long> checkUserIds) {
        this.checkUserIds = checkUserIds;
    }

    public String getUserCertificateNumber() {
        if (StringUtils.isEmpty(userCertificateNumber)){
            return "";
        }
        return userCertificateNumber;
    }

    public void setUserCertificateNumber(String userCertificateNumber) {
        this.userCertificateNumber = userCertificateNumber;
    }

    public Long getLeadingUserId() {
        return leadingUserId;
    }

    public void setLeadingUserId(Long leadingUserId) {
        this.leadingUserId = leadingUserId;
    }

    public Long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(Long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public Date getCheckProjectReportCreateTime() {
        return checkProjectReportCreateTime;
    }

    public String getCheckProjectReportCreateTimeDesc() {
        return DateUtils.getDateCN(checkProjectReportCreateTime);
    }

    public void setCheckProjectReportCreateTime(Date checkProjectReportCreateTime) {
        this.checkProjectReportCreateTime = checkProjectReportCreateTime;
    }

    public Integer getCheckVersion() {
        return checkVersion;
    }

    public void setCheckVersion(Integer checkVersion) {
        this.checkVersion = checkVersion;
    }

    public String getCheckProjectReportUrl() {
        return checkProjectReportUrl;
    }

    public void setCheckProjectReportUrl(String checkProjectReportUrl) {
        this.checkProjectReportUrl = checkProjectReportUrl;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public String getCheckResults() {
        if(checkResult == null){
            return "";
        }
        if (checkResult == 1){
            return "合格";
        }
        return "不合格";
    }

    public String getCheckResultDesc() {
        if(checkResult == null){
            return "□";
        }
        if (1 == checkResult){
            return "√";
        }
        return "□";
    }

    public String getCheckResultDescs() {
        if(checkResult == null){
            return "□";
        }
        if (1 == checkResult){
            return "□";
        }
        return "√";
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Long[]  getSysTypeIdList() {
        if(sysTypeIds==null){
            return  new Long[0];
        }
        String[] arr = sysTypeIds.split(";");
        Long[]sysTypeIdList = new Long[arr.length];
        int a = -1;
        for(int i=0;i<arr.length;i++){
            if(!arr[i].equals("")){
                a++;
                sysTypeIdList[a] = Long.valueOf(arr[i]);
            }
        }

        return sysTypeIdList;
    }

    public void setSysTypeIdList(Integer[] sysTypeIdList) {
        this.sysTypeIdList = sysTypeIdList;
    }


    public String getCheckProjectNumber() {
        if (StringUtils.isEmpty(checkProjectNumber)){
            return "";
        }
        return checkProjectNumber;
    }

    public void setCheckProjectNumber(String checkProjectNumber) {
        this.checkProjectNumber = checkProjectNumber;
    }

    public Integer getCheckProjectStatus() {
        return checkProjectStatus;
    }

    public void setCheckProjectStatus(Integer checkProjectStatus) {
        this.checkProjectStatus = checkProjectStatus;
    }

    public String getCheckBase() {
        return checkBase;
    }

    public void setCheckBase(String checkBase) {
        this.checkBase = checkBase;
    }

    public String getCheckConclusion() {
        if (checkConclusion == null){
            return "";
        }
        return checkConclusion;
    }

    public void setCheckConclusion(String checkConclusion) {
        this.checkConclusion = checkConclusion;
    }

    public String getUseFunction() {
        return useFunction;
    }

    public void setUseFunction(String useFunction) {
        this.useFunction = useFunction;
    }

    public Integer getFocusUnit() {
        return focusUnit;
    }

    public void setFocusUnit(Integer focusUnit) {
        this.focusUnit = focusUnit;
    }

    public Date getDrawingTime() {
        return drawingTime;
    }

    public void setDrawingTime(Date drawingTime) {
        this.drawingTime = drawingTime;
    }

    public String getCheckProgramFileUrl() {
        return checkProgramFileUrl;
    }

    public void setCheckProgramFileUrl(String checkProgramFileUrl) {
        this.checkProgramFileUrl = checkProgramFileUrl;
    }

    public String getCheckCode() {
        if (StringUtils.isEmpty(checkCode)){
            return "";
        }
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getCheckLabelFileUrl() {
        return checkLabelFileUrl;
    }

    public void setCheckLabelFileUrl(String checkLabelFileUrl) {
        this.checkLabelFileUrl = checkLabelFileUrl;
    }

    public String getContractFileUrl() {
        return contractFileUrl;
    }

    public void setContractFileUrl(String contractFileUrl) {
        this.contractFileUrl = contractFileUrl;
    }

    public String getSysTypeIds() {
        return sysTypeIds;
    }

    public void setSysTypeIds(String sysTypeIds) {
        if(this.sysTypeIdList!= null){
            this.sysTypeIds = "";
            for(int i=0;i<this.sysTypeIdList.length;i++){
                this.sysTypeIds+=sysTypeIdList[i]+";";
            }

        }else{
            this.sysTypeIds = sysTypeIds;
        }
    }

    public String getLeadingUserName() {
        if (StringUtils.isEmpty(leadingUserName)){
            return "";
        }
        return leadingUserName;
    }

    public void setLeadingUserName(String leadingUserName) {
        this.leadingUserName = leadingUserName;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckProjectName() {
        if (StringUtils.isEmpty(checkProjectName)){
            return "";
        }
        return checkProjectName;
    }

    public void setCheckProjectName(String checkProjectName) {
        this.checkProjectName = checkProjectName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }


    public Integer getCheckRange() {
        return checkRange;
    }

    public String getCheckRangeDesc() {
        if (checkRange != null && 1 == checkRange){
            return "√";
        }
        return "□";
    }

    public String getCheckRangeDescs() {
        if (checkRange != null && 1 == checkRange){
            return "□";
        }
        return "√";
    }

    public void setCheckRange(Integer checkRange) {
        this.checkRange = checkRange;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public String getCheckTypes() {
        if (checkType != null && 1 == checkType){
            return "竣工检测";
        }
        return "年度检测";
    }

    public String getCheckTypeDesc() {
        if (checkType != null && 1 == checkType){
            return "√";
        }
        return "□";
    }

    public String getCheckTypeDescs() {
        if (checkType != null && 1 == checkType){
            return "□";
        }
        return "√";
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getCheckPart() {
        return checkPart;
    }

    public void setCheckPart(String checkPart) {
        this.checkPart = checkPart;
    }


    public String getCheckPartFun() {
        if (StringUtils.isEmpty(checkPartFun)){
            return "";
        }
        return checkPartFun;
    }

    public void setCheckPartFun(String checkPartFun) {
        this.checkPartFun = checkPartFun;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getRequesterUnit() {
        if (StringUtils.isEmpty(requesterUnit)){
            return "";
        }
        return requesterUnit;
    }

    public void setRequesterUnit(String requesterUnit) {
        this.requesterUnit = requesterUnit;
    }

    public String getDesignUnit() {
        return designUnit;
    }

    public void setDesignUnit(String designUnit) {
        this.designUnit = designUnit;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getDrawingDescription() {
        return drawingDescription;
    }

    public void setDrawingDescription(String drawingDescription) {
        this.drawingDescription = drawingDescription;
    }

    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public String getCheckEndTimeDesc() {
        return DateUtils.getDateCN(checkStartTime) + "至" + DateUtils.getDateCN(checkEndTime);
    }

    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactUserName() {
        if (StringUtils.isEmpty(contactUserName)){
            return "";
        }
        return contactUserName;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }


    public String getFireApproval() {
        return fireApproval;
    }

    public void setFireApproval(String fireApproval) {
        this.fireApproval = fireApproval;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }




    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getAddress() {
        if (StringUtils.isEmpty(address)){
            return "";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Float getAcreage() {
        if (acreage == null){
            return 0F;
        }
        return acreage / 1000;
    }

    public Integer getAcreageInt() {
        if (acreage == null){
            return 0;
        }
        return (int)(acreage * 1000);
    }

    public void setAcreage(Float acreage) {
        this.acreage = acreage;
    }

    public Integer getCheckAcreageInt() {
        if (checkAcreage == null){
            return 0;
        }
        return (int)(checkAcreage * 1000);
    }

    public Float getCheckAcreage() {
        if (checkAcreage == null){
            return 0F;
        }
        return checkAcreage / 1000;
    }

    public void setCheckAcreage(Float checkAcreage) {
        this.checkAcreage = checkAcreage;
    }

    public Integer getHeight() {
        return height;
    }


    public Integer getContractAmount() {
        return contractAmount;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }


    public void initHeightAndAcreageAndContractAmount(Integer height,Float acreage,Float checkAcreage,Integer contractAmount){
        setSysTypeIds(null);
    }

    public void setContractAmount(Integer contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }
}
