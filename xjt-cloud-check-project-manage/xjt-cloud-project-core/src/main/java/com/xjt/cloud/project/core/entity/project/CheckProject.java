package com.xjt.cloud.project.core.entity.project;

import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.text.DecimalFormat;
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
    private Double acreage;
    //高度
    private Double height;
    //面积*100 入库数据
    private Integer acreageInt;
    //高度*100 入库数据
    private Integer heightInt;
    //层数
    private Integer floor;
    //项目ID
    private List<Long> projectIds;
    //详细地址
    private String address;
    //ids
    private List<Long> ids;

    private String buildingType;//建筑物类别
    private Integer checkRange;//检测范围 1、整体检测 2、局部检测
    private Integer checkType;//检测类型  1、竣工检测   2、年度检测
    private String checkPart;//检测部位
    private Double checkAcreage;//检测面积
    private Integer checkAcreageInt;//检测面积*100入库数据

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
    private String contactUserName;//联系人
    private Double contractAmount;//合同金额
    private Integer focusUnit;//重点单位 1、是  2不是
    //合同金额*100 入库数据
    private Integer contractAmountInt;
    private String fireApproval;//消防批文
    private String sysTypeIds;//系统类型Id用分号隔开
    private String leadingUserName;//负责人

    //审批人(批准人)
    private String approvalUserName;
    /**
     * orgId
     */
    private Long approvalOrgId;
    // 批准人用户ID
    private Long approveUserId;

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

    //审核人
    private String auditUserName;
    //审核人 userId
    private Long auditUserId;
    private Long auditOrgId;

    // 文件路径, 1系统, 2本地
    private Integer urlType;
    //检测员用户ID
    private List<Long>  checkUserIds;
    private String  userCertificateNumber;
    //项目负责人用户ID
    private Long leadingUserId;

    //用户Id(只用于传参搜索)
    private Long userId;
    ////1、执行中  2、待签章  3、已签章   5、已发布
    private List<Integer> checkProjectStatusList;
    private String limitSign;//以权限限制数据

    public String getLimitSign() {
        return limitSign;
    }

    public void setLimitSign(String limitSign) {
        this.limitSign = limitSign;
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
        if (2 == checkResult){
            return "√";
        }
        return "□";
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    public Integer[]  getSysTypeIdList() {
        if(sysTypeIds==null){
            return  new Integer[0];
        }
        String[] arr = sysTypeIds.split(";");
        Integer[]sysTypeIdList =new Integer[arr.length];
        int a = -1;
        for(int i=0;i<arr.length;i++){
            if(!arr[i].equals("")){
                a++;
                sysTypeIdList[a] = Integer.valueOf(arr[i]);
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

    public Long getApprovalOrgId() {
        return approvalOrgId;
    }

    public void setApprovalOrgId(Long approvalOrgId) {
        this.approvalOrgId = approvalOrgId;
    }

    public Long getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(Long auditUserId) {
        this.auditUserId = auditUserId;
    }

    public Long getAuditOrgId() {
        return auditOrgId;
    }

    public void setAuditOrgId(Long auditOrgId) {
        this.auditOrgId = auditOrgId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getCheckProjectName() {
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

    public Double getAcreage() {
        if (this.acreageInt!=null){
            this.acreage = this.acreageInt/100.00d;
        }
        return acreage;
    }

    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    public Double getCheckAcreage() {
        if (this.checkAcreageInt!=null){
            this.checkAcreage = this.checkAcreageInt/100.00d;
        }
        return checkAcreage;
    }

    public void setCheckAcreage(Double checkAcreage) {
        this.checkAcreage = checkAcreage;
    }



    public Double getHeight() {
        if(this.heightInt!=null){
            this.height = this.heightInt/100.00d;
        }
        return height;
    }


    public Double getContractAmount() {
        if(this.contractAmountInt!=null){
            this.contractAmount = this.contractAmountInt/100.00d;
        }
        return contractAmount;
    }



    public void setContractAmountInt(Integer contractAmountInt) {
        this.contractAmountInt = contractAmountInt;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getAcreageInt() {
        return acreageInt;
    }

    public void setAcreageInt(Integer heightInt) {
        this.acreageInt = heightInt;
    }

    public Integer getHeightInt() {
        return heightInt;
    }

    public void setHeightInt(Integer heightInt) {
        this.heightInt = heightInt;
    }
    public void initHeightAndAcreageAndContractAmount(Double height,Double acreage,Double checkAcreage,Double contractAmount){
        DecimalFormat df = new DecimalFormat("0");
        if(acreage != null){
            this.acreageInt = Integer.parseInt(df.format(acreage * 100.0f));
        }
        if(height != null){
            this.heightInt = Integer.parseInt(df.format(height * 100.0f));
        }
        if(checkAcreage != null){
            //checkAcreage= df.format(checkAcreage * 100.0f);
            this.checkAcreageInt =  Integer.parseInt(df.format(checkAcreage * 100.0f));

        }
        if(contractAmount != null){
            this.contractAmountInt = Integer.parseInt(df.format(contractAmount * 100.0f));
        }
        setSysTypeIds(null);
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Integer getContractAmountInt() {
        return contractAmountInt;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }
}
