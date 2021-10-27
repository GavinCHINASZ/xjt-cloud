package com.xjt.cloud.maintenance.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.maintenance.core.common.ConstantsProjectMsg;
import com.xjt.cloud.maintenance.core.dao.device.CheckRecordDao;
import com.xjt.cloud.maintenance.core.dao.device.DeviceSystemDao;
import com.xjt.cloud.maintenance.core.dao.device.OtherInstructionsDao;
import com.xjt.cloud.maintenance.core.dao.project.*;
import com.xjt.cloud.maintenance.core.entity.device.CheckRecord;
import com.xjt.cloud.maintenance.core.entity.device.CheckReport;
import com.xjt.cloud.maintenance.core.entity.device.DeviceSystem;
import com.xjt.cloud.maintenance.core.entity.device.OtherInstructions;
import com.xjt.cloud.maintenance.core.entity.project.*;
import com.xjt.cloud.maintenance.core.service.impl.device.DeviceServiceImpl;
import com.xjt.cloud.maintenance.core.service.service.device.CheckRecordService;
import com.xjt.cloud.maintenance.core.service.service.project.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @ClassName CheckProjectServiceImpl 检测项目实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class CheckProjectServiceImpl extends AbstractService implements CheckProjectService {
    @Autowired
    private DeviceServiceImpl deviceServiceImpl;
    @Autowired
    private CheckProjectDao checkProjectDao;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    protected BuildingDao buildingDao;
    @Autowired
    protected OtherInstructionsDao otherInstructionsDao;
    @Autowired
    private CheckRecordService checkRecordService;
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private DeviceSystemDao deviceSystemDao;
    @Autowired
    private CheckUserDao checkUserDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private PlanManagementDao planManagementDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * @MethodName: findByCheckProjectList
     * @Description: 查询检测项目信息列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:46
     **/
    @Override
    public Data findByCheckProjectList(String json) {
        CheckProject CheckProject = JSON.parseObject(json, CheckProject.class);
        List<CheckProject> list = checkProjectDao.findByCheckProjectList(CheckProject);
        Integer totalCount = CheckProject.getTotalCount();
        Integer pageSize = CheckProject.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = checkProjectDao.findByCheckProjectListCount(CheckProject);
        }
        JSONObject data = new JSONObject();
        data.put("totalCount", totalCount);
        data.put("list", list);
        return asseData(data);
    }


    /**
     * @MethodName: findByMyCheckProjectList
     * @Description: 查询我的检测项目列表
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/14 9:16
     **/
    @Override
    public Data findByMyCheckProjectList(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        checkProject.setUserId(userId);
        List<CheckProject> list = checkProjectDao.findByMyCheckProjectList(checkProject);
        Integer totalCount = checkProject.getTotalCount();
        Integer pageSize = checkProject.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = checkProjectDao.findByMyCheckProjectListCount(checkProject);
        }
        JSONObject data = new JSONObject();
        data.put("totalCount", totalCount);
        data.put("list", list);
        return asseData(data);
    }

    /**
     * 打印标签
     *
     * @param json
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2020-04-14
     **/
    @Override
    public Data saveLabel(String json, HttpServletResponse response) {
        CheckProject entity = JSON.parseObject(json, CheckProject.class);
        CheckProject checkProject = checkProjectDao.findByCheckProject(entity);
        Project project = projectDao.get(checkProject.getProjectId());

        Integer checkProjectStatus = checkProject.getCheckProjectStatus();
        if (3 == checkProjectStatus || 5 == checkProjectStatus) {// 生成二维码
            String url = ConstantsProjectMsg.QR_CODE_WORD_FILE_URL + checkProject.getNumber();
            BufferedImage bufferedImage = QrCodeUtils.initQrCode(url, 220, 220, 0, -16777216, -1);
            String imgBase64Str = QrCodeUtils.generateWaterFile(bufferedImage);
            checkProject.setCheckCode(imgBase64Str);
        }

        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        String userCertificateNumber = getUserCertificateNumber(userId);
        if (StringUtils.isNotEmpty(userCertificateNumber)) {
            checkProject.setUserCertificateNumber(userCertificateNumber);
        } else {
            checkProject.setUserCertificateNumber("");
        }

        Map<String, Object> hashMap = new HashMap<String, Object>();// 封装数据
        hashMap.put("checkProject", checkProject);
        hashMap.put("project", project);

        String fileUrl = System.getProperty("user.dir") + ConstantsProjectMsg.REPORT_FILE_URL;
        String fileName = System.currentTimeMillis() + "";
        downModel(response, fileUrl, fileName, hashMap, configuration, "checkLabelModel.xml");// 导出方法

        // 上传
        Data data = new Data();
        File file = new File(fileUrl + fileName + ".pdf");
        String result = HttpUtils.restTemplateTransferFile(ConstantsProjectMsg.PDF_UPLOAD_URL, file, "checkrecord");
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String checkLabelFileUrl = jsonObject.getString("object");
            entity.setCheckLabelFileUrl(checkLabelFileUrl);// 标签PDF路径

            updCheckProject(entity);// 更新

            if (StringUtils.isNotEmpty(checkLabelFileUrl)) {
                data.setStatus(Constants.SUCCESS_CODE);
                data.setMsg("标签生成成功");
                data.setObject(checkLabelFileUrl);
            } else {
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("标签生成失败");
            }
        }

        List<String> fileNames = new ArrayList<>(2);
        fileNames.add(fileName + ".pdf");
        fileNames.add(fileName + ".doc");

        deleteFile(fileUrl, fileNames);
        return data;
    }

    private List<CheckUser> initCheckUser(List<Long> userIds, Long checkProjectId, Long projectId) {
        List<CheckUser> list = new ArrayList<>();
        for (Long userId : userIds) {
            CheckUser checkUser = new CheckUser(userId, checkProjectId, projectId);
            list.add(checkUser);
        }
        return list;
    }

    /**
     * @MethodName: saveCheckProject
     * @Description: 保存检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:47
     **/
    @Override
    public Data saveCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProject.initHeightAndAcreageAndContractAmount(checkProject.getHeight(), checkProject.getAcreage(), checkProject.getCheckAcreage(), checkProject.getContractAmount());
        //SimpleDateFormat mm = new SimpleDateFormat("MMHHm");
        //生成检测项目编号
       /* checkProject.setNumber("消检（"+checkProject.getCreditCode().substring(9,checkProject.getCreditCode().length())+
                "）["+ mm.format(new Date()) +"]号");*/
        if (checkProject.getId() != null && checkProject.getId() != 0) {
            if (checkProject.getCheckUserIds() != null && checkProject.getCheckUserIds().size() > 0) {//保存项目检测人
                List<CheckUser> checkUsers = initCheckUser(checkProject.getCheckUserIds(), checkProject.getId(), checkProject.getProjectId());
                CheckUser checkUser = new CheckUser();
                checkUser.setCheckProjectId(checkProject.getId());
                //先删除项目里老记录再保存新记录
                checkUserDao.delCheckUser(checkUser);
                checkUserDao.saveCheckUser(checkUsers);
            }
            updCheckProject(checkProject);
            return asseData(checkProject);
        }
        LocalDateTime nowTime = LocalDateTime.now();
        int year = nowTime.getYear();

        Project project = projectDao.get(checkProject.getProjectId());
        project.setCheckProjectSerialNumber(project.getCheckProjectSerialNumber() + 1);
        projectDao.updateProject(project);
        String checkProjectSerialNumber = "000" + project.getCheckProjectSerialNumber();
        String number = "xfgcszc" + project.getProjectNumber().substring(project.getProjectNumber().length() - 4) + "-" + year + "-" + checkProjectSerialNumber.substring(checkProjectSerialNumber.length() - 4);
        SysLog.debug(number + "------------->生成的项目编号");
        checkProject.setNumber(number);
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        checkProject.setCreateUserId(userId);
        Integer status = checkProjectDao.saveCheckProject(checkProject);
        if (checkProject.getCheckUserIds() != null && checkProject.getCheckUserIds().size() > 0 && status > 0) {//保存项目检测人
            List<CheckUser> checkUsers = initCheckUser(checkProject.getCheckUserIds(), checkProject.getId(), checkProject.getProjectId());
            checkUserDao.saveCheckUser(checkUsers);
        }

        return asseData(checkProject);
    }

    /**
     * @MethodName: delCheckProject
     * @Description: 删除检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:47
     **/
    @Override
    public Data delCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProjectDao.delCheckProject(checkProject);
        return Data.isSuccess();
    }

    /**
     * @MethodName: updCheckProject
     * @Description: 更新检测项目信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:47
     **/
    @Override
    public Data updCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProject.initHeightAndAcreageAndContractAmount(checkProject.getHeight(), checkProject.getAcreage(), checkProject.getCheckAcreage(), checkProject.getContractAmount());
        checkProjectDao.updCheckProject(checkProject);
        return asseData(checkProject);
    }

    /**
     * @MethodName: findDeviceTypes
     * @Description: 查询系统列表
     * @Param: [checkProjectId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.device.DeviceSystem>
     * @Author: zhangZaiFa
     * @Date:2020/4/12 15:26
     **/
    public List<DeviceSystem> findDeviceTypes(Long checkProjectId) {
        CheckProject checkProject = findCheckProjectId(checkProjectId);
        DeviceSystem deviceSystem = new DeviceSystem();
        deviceSystem.setType(1);
        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeList(deviceSystem);
        String sysTypeIds = "," + checkProject.getSysTypeIds() + ",";//系统类型Id用分号隔开
        for (DeviceSystem entity : list) {
            if (sysTypeIds.indexOf(entity.getId() + "") != -1) {
                entity.setChecked(true);
            }
        }
        return list;
    }


    /**
     * @MethodName: updCheckProject
     * @Description: 更新检测项目状态
     * @Param: [checkProjectId, checkProjectStatus]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/11 15:07
     **/
    public Data updCheckProject(Long checkProjectId, Integer checkProjectStatus) {
        CheckProject checkProject = new CheckProject();
        checkProject.setId(checkProjectId);
        checkProject.setStatus(checkProjectStatus);
        checkProjectDao.updCheckProject(checkProject);
        return Data.isSuccess();
    }

    /**
     * @MethodName: updCheckProject
     * @Description: 更新检测项目信息
     * @Param: [checkProjectId, checkProjectStatus]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/11 15:07
     **/
    public Data updCheckProject(CheckProject checkProject) {
        checkProjectDao.updCheckProject(checkProject);
        return Data.isSuccess();
    }

    /**
     * @MethodName: findCheckProjectId
     * @Description: 通过checkProjectId查询对象
     * @Param: [id]
     * @Return: com.xjt.cloud.project.core.entity.project.CheckProject
     * @Author: zhangZaiFa
     * @Date:2020/4/11 15:26
     **/
    public CheckProject findCheckProjectId(Long checkProjectId) {
        CheckProject checkProject = new CheckProject();
        checkProject.setId(checkProjectId);
        checkProject = checkProjectDao.findByCheckProject(checkProject);
        return checkProject;
    }


    /**
     * @MethodName: findByCheckProject
     * @Description: 查询检测项目信息详情
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:47
     **/
    @Override
    public Data findByCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProject = checkProjectDao.findByCheckProject(checkProject);
        OrgUser orgUser = new OrgUser();
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        orgUser.setUserId(userId);
        orgUser.setProjectId(checkProject.getProjectId());
        List<String> permissions = permissionService.findByOrgUserProjectPermission(orgUser);
        if (checkProject.getCheckProjectStatus() == 3 && !permissions.contains("project_manage_edit")) {
            checkProject.setCheckProjectReportUrl("");
        }
        CheckUser checkUser = new CheckUser();
        checkUser.setCheckProjectId(checkProject.getId());
        List<Long> list = checkUserDao.findCheckUserIdList(checkUser);
        checkProject.setCheckUserIds(list);
        return asseData(checkProject);
    }


    /**
     * @MethodName: findCheckProjectReport
     * @Description: 查询检测报告扫描信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/4/12 10:27
     **/
    @Override
    public Data findCheckProjectReport(String number) {
        CheckProject checkProject = new CheckProject();
        checkProject.setNumber(number);
        checkProject = checkProjectDao.findByCheckProject(checkProject);
        Project project = projectDao.get(checkProject.getProjectId());
        PlanManagement planManagement = new PlanManagement();
        planManagement.setNumber(number);
        List<PlanManagement> planManagementList = planManagementDao.findPlanManagementReportList(planManagement);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectName", project.getProjectName());
        jsonObject.put("checkProjectName", checkProject.getCheckProjectName());
        jsonObject.put("address", project.getAddress());
        jsonObject.put("propertyManagementUnit", checkProject.getPropertyManagementUnit());
        if (CollectionUtils.isNotEmpty(planManagementList)) {
            planManagement = planManagementList.get(0);
            jsonObject.put("checkProjectReportUrl", planManagement.getReportFileUrl());
            jsonObject.put("completeDate",planManagement.getCompleteDate());
            jsonObject.put("reportCode",planManagement.getReportCode());
        }


        jsonObject.put("number", checkProject.getNumber());
        jsonObject.put("checkProjectNumber", checkProject.getCheckProjectNumber());
        jsonObject.put("requesterUnit", checkProject.getRequesterUnit());
        jsonObject.put("checkEndTime", checkProject.getNumber());
        return asseData(jsonObject);
    }

    /**
     * generateProjectCheckNumber
     * 生成报告编号
     *
     * @param creditCode 信用代码
     * @param checkName  粤消维/消检
     * @param length     int
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2020/4/12 9:53
     **/
    public String generateProjectCheckNumber(String creditCode, String checkName, int length) {
        if (StringUtils.isEmpty(creditCode) || creditCode.length() < length) {
            return "";
        }

        Integer number = Integer.valueOf(checkProjectDao.findDictCodeDescription("CHECK_PROJECT_REPORT_NUMBER"));
        LocalDateTime nowTime = LocalDateTime.now();
        int year = nowTime.getYear();
        String numberStr = "0000" + number;
        String checkProjectNumber = checkName + "（" + creditCode.substring(creditCode.length() - length) + "）【" + year + "】第"
                + numberStr.substring(numberStr.length() - 5) + "号";

        checkProjectDao.updDictCodeDescription((number + 1) + "", "CHECK_PROJECT_REPORT_NUMBER");

        SysLog.debug(checkProjectNumber + "-----------------》生成的编号");
        return checkProjectNumber;
    }

    /**
     * 报告生成
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2020-04-10
     **/
    @Override
    public Data saveReport(String json, HttpServletResponse response) {
        CheckProject entity = JSON.parseObject(json, CheckProject.class);
        Integer checkProjectStatus = entity.getCheckProjectStatus();

        Data data = new Data();

        // 首页
        CheckProject checkProject = checkProjectDao.findByCheckProject(entity);
        checkProject.setAuditUserName("");
        Long checkProjectId = checkProject.getId();
        Project project = projectDao.get(entity.getProjectId());

        // 二维码
        if (2 == checkProjectStatus) {
            Date date = new Date();
            entity.setCheckProjectReportCreateTime(date);// 签章时间
            checkProject.setCheckProjectReportCreateTime(date);

            String url = ConstantsProjectMsg.QR_CODE_WORD_FILE_URL + checkProject.getNumber();
            BufferedImage bufferedImage = QrCodeUtils.initQrCode(url, 220, 220, 0, -16777216, -1);
            String imgBase64Str = QrCodeUtils.generateWaterFile(bufferedImage);// 二维码
            checkProject.setCheckCode(imgBase64Str);

            // 报告编号
            String checkProjectNum = generateProjectCheckNumber(entity.getCreditCode(), "消检", 8);
            checkProject.setCheckProjectNumber(checkProjectNum);

            entity.setCheckProjectStatus(3);//2、待签章  3、已签章
            entity.setCheckVersion((checkProject.getCheckVersion() + 1));//版本加1
            entity.setCheckProjectNumber(checkProjectNum);
        } else if (checkProjectStatus == 4) {//进入执行中
            entity.setCheckProjectStatus(1);
            updCheckProject(entity);
            data.setStatus(Constants.SUCCESS_CODE);
            data.setMsg("报告已提交执行中");
            data.setObj(entity);
            return data;
        } else if (checkProjectStatus == 5) {
            updCheckProject(entity);
            data.setStatus(Constants.SUCCESS_CODE);
            data.setMsg("报告已发布成功");
            data.setObj(entity);
            return data;
        } else if (checkProjectStatus == 1) {
            // 进入待签章
            entity.setCheckProjectStatus(2);
            entity.setCheckProjectNumber(null);
            checkProjectDao.upCheckProjectNumber(entity);

            CheckUser checkUser = new CheckUser();
            checkUser.setCheckProjectId(checkProject.getId());
            List<Long> list = checkUserDao.findCheckUserIdList(checkUser);

            Role permission = new Role();
            // {"projectId":495,"roleType":4}
            permission.setProjectId(entity.getProjectId());
            permission.setRoleType(4);
            List<OrgUser> orgUserList = roleDao.findProjectRoleUserList(permission);
            StringJoiner sj = new StringJoiner(",");
            if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(orgUserList)) {
                for (int i = 0; i < list.size(); i++) {
                    Long userIdL = list.get(i);
                    for (int j = 0; j < orgUserList.size(); j++) {
                        OrgUser orgUser = orgUserList.get(j);
                        if (userIdL.equals(orgUser.getUserId())) {
                            sj.add(orgUser.getUserName());
                        }
                    }
                }
            }
            entity.setAuditUserName(sj.toString());
            checkProject.setAuditUserName(sj.toString());
        }

        // 封装数据
        Map<String, Object> hashMap = new HashMap<>(10);
        hashMap.put("checkProject", checkProject);
        hashMap.put("project", project);
        SimpleDateFormat mm = new SimpleDateFormat("yyyy/MM/dd");
        String reportDate = mm.format(new Date());
        // 报告时间
        hashMap.put("reportDate", reportDate);

        // 建筑消防设施检测报告
        /*Building building = new Building();
        building.setCheckProjectId(checkProjectId);
        building = buildingDao.findByBuilding(building);
        if (building == null){
            new Building();
        }
        hashMap.put("building", building);*/

        // 检测内容
        List<DeviceSystem> deviceDateList = findDeviceTypes(checkProjectId);
        List<DeviceSystem> deviceList = new ArrayList<>();
        List<DeviceSystem> deviceLists = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deviceDateList)) {
            for (int i = 0; i < deviceDateList.size(); i++) {
                if (i % 2 != 0) {// 奇数
                    deviceLists.add(deviceDateList.get(i));
                } else {
                    deviceList.add(deviceDateList.get(i));
                }
            }
        }
        if (deviceLists.size() < deviceList.size()) {
            DeviceSystem deviceSystem = new DeviceSystem();
            deviceLists.add(deviceSystem);
        }
        hashMap.put("deviceList", deviceList);
        hashMap.put("deviceLists", deviceLists);

        // 消防设备登记表
        List<DeviceSystem> deviceSystemList = deviceServiceImpl.findAllDeviceListByProjectInfoId(checkProjectId);
        if (CollectionUtils.isEmpty(deviceSystemList)) {
            deviceSystemList = new ArrayList<>();
        }
        hashMap.put("deviceSystemList", deviceSystemList);

        // 其他说明
        OtherInstructions otherInstructions = new OtherInstructions();
        otherInstructions.setProjectInfoId(checkProjectId);
        otherInstructions = otherInstructionsDao.findOtherInstructions(otherInstructions);
        if (null == otherInstructions) {
            otherInstructions = new OtherInstructions();
        }
        hashMap.put("otherInstructions", otherInstructions);

        // 单项评定结果
        List<CheckReport> checkReportList = checkRecordService.findCheckReport(checkProjectId);
        if (CollectionUtils.isEmpty(checkReportList)) {
            checkReportList = new ArrayList<>();
        }
        hashMap.put("checkReportList", checkReportList);

        // 检测结论说明
        // ${checkProject.checkConclusion }

        // 检测情况统计表
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCheckProjectId(checkProjectId);
        checkRecord.setDeviceSysIds(checkProject.getSysTypeIdList());
        checkRecord.setDeviceType(2);//2维保巡检 3维保测试 4维保保养
        List<CheckRecord> checkRecordList = checkRecordDao.findAllCheckRecordList(checkRecord);
        if (CollectionUtils.isEmpty(checkRecordList)) {
            checkRecordList = new ArrayList<>();
        }
        hashMap.put("checkRecordList", checkRecordList);

        /*// 消防设施检测不符合规范要求项目
        List<CheckRecord> failCheckRecordList = checkRecordDao.findFailCheckRecordList(checkRecord);
        if (CollectionUtils.isEmpty(failCheckRecordList)) {
            failCheckRecordList = new ArrayList<>();
        }
        hashMap.put("failCheckRecordList", failCheckRecordList);*/

        String fileUrl = System.getProperty("user.dir") + ConstantsProjectMsg.REPORT_FILE_URL;
        String fileName = System.currentTimeMillis() + "";
        downModel(response, fileUrl, fileName, hashMap, configuration, "checkmodel.xml");// 导出方法

        // 上传
        File file = new File(fileUrl + fileName + ".pdf");
        String result = HttpUtils.restTemplateTransferFile(ConstantsProjectMsg.PDF_UPLOAD_URL, file, "checkrecord");
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);

            String checkProjectReportUrl = jsonObject.getString("object");
            entity.setCheckProjectReportUrl(checkProjectReportUrl);// PDF路径
            SysLog.debug("报告状态》》》》》" + entity.getCheckProjectStatus() + "----------->" + entity.getCheckProjectNumber());

            updCheckProject(entity);

            if (StringUtils.isNotEmpty(checkProjectReportUrl)) {
                data.setStatus(Constants.SUCCESS_CODE);
                data.setMsg("报告生成成功");
                data.setObject(checkProjectReportUrl);
            } else {
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("报告生成失败");
            }
            data.setObj(entity);
        }

        List<String> fileNames = new ArrayList<>(2);
        fileNames.add(fileName + ".pdf");
        fileNames.add(fileName + ".doc");

        deleteFile(fileUrl, fileNames);
        return data;
    }

    /**
     * 报告查询
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2020-04-10
     **/
    @Override
    public Data findReport(String json) {
        CheckProject CheckProject = JSON.parseObject(json, CheckProject.class);

        return null;
    }

    /**
     * 报告下载
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Author huanggc
     * @Date 2020-04-10
     **/
    @Override
    public void downReport(String json, HttpServletResponse response) {

    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileUrl   文件生成的路径
     * @param fileName  导出的文件名+文件后缀
     * @param map       数据
     * @param modelName 模板名
     * @author: huanggc
     * @date: 2019/11/04
     * @return: void
     */
    public static void downModel(HttpServletResponse response, String fileUrl, String fileName, Map<String, Object> map, Configuration configuration, String modelName) {
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            OutputStream outt = new FileOutputStream(fileUrl + fileName + ".doc");
            oWriter = new OutputStreamWriter(outt, "UTF-8");// out
            writer = new BufferedWriter(oWriter);

            Template template = configuration.getTemplate(modelName);// 获取模板
            template.setOutputEncoding("UTF-8");
            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            writer.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (oWriter != null) {
                    oWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Boolean aBoolean = docToPdf(fileUrl + fileName + ".doc", fileUrl + fileName + ".pdf");
    }

    private static boolean getLicense() {
        boolean result = false;
        try {
            // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            String s = "<License>\n" +
                    "    <Data>\n" +
                    "        <Products>\n" +
                    "            <Product>Aspose.Total for Java</Product>\n" +
                    "            <Product>Aspose.Words for Java</Product>\n" +
                    "        </Products>\n" +
                    "        <EditionType>Enterprise</EditionType>\n" +
                    "        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "        <LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "    </Data>\n" +
                    "    <Signature>\n" +
                    "        sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=\n" +
                    "    </Signature>\n" +
                    "</License>";
            //File file = new File(System.getProperty("user.dir") + ConstantsProjectMsg.PDF_LICENSE_FILE_URL);
            //InputStream is = new FileInputStream(file) ;

            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            SysLog.error(e.fillInStackTrace());
        }
        return result;
    }

    /**
     * 功能描述: word文档 转成 pdf
     *
     * @param wordPath 需要被转换的word全路径带文件名   例: D:/logs/Java多线程大合集.doc
     * @param pdfPath  转换之后pdf的全路径带文件名  例: D:/logs/123.pdf
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
     */
    public static Boolean docToPdf(String wordPath, String pdfPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }

        try {
            File file = new File(pdfPath); // 新建一个pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(wordPath); // Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return boolean
     * @author huanggc
     * @date 2020-04-12
     */
    public static boolean deleteFile(String path, List<String> fileNames) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp;

        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }

            if (temp.isFile()) {
                String name = temp.getName();
                for (String fileName : fileNames) {
                    if (fileName.equals(name)) {
                        temp.delete();
                    }
                }
            }

            if (temp.isDirectory()) {
                // 递归
                //deleteFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                flag = true;
            }
        }
        return flag;
    }

    /**
     * @MethodName: getUserCertificateNumber
     * @Description: 获取用户证书编号
     * @Param: []
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2020/4/14 15:14
     **/
    public String getUserCertificateNumber(Long userId) {
        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(userId);
        orgUser = orgUserDao.findOrgUser(orgUser);
        if (orgUser == null || orgUser.getCertificateNumber() == null) {
            return "";
        }
        return orgUser.getCertificateNumber();
    }

    ///////////////////////////////////合同管理///////////////////

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 查询合同列表
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    @Override
    public Data findContractList(String json) {
        Contract contract = JSONObject.parseObject(json, Contract.class);
        Integer totalCount = contract.getTotalCount();
        Integer pageSize = contract.getPageSize();
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        contract.setCreateUserId(userId);
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = contractDao.findContractListCount(contract);
        }
        List<Contract> list = contractDao.findContractList(contract);
        return asseData(totalCount, list);
    }

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 保存合同列表
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    @Override
    public Data saveContract(String json) {
        Contract contract = JSONObject.parseObject(json, Contract.class);
        int num;
        if (contract.getId() == null || contract.getId() == 0) {
            String loginName = SecurityUserHolder.getUserName();
            Long userId = getLoginUserId(loginName);
            contract.setCreateUserId(userId);
            contract.setCreateUserName(getLoginUserName(loginName));
            num = contractDao.saveContract(contract);
        } else {
            num = contractDao.modifyContract(contract);
        }
        return asseData(num);
    }

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 删除合同列表
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     */
    @Override
    public Data delContract(String json) {
        Contract contract = JSONObject.parseObject(json, Contract.class);
        int num = contractDao.delContract(contract);
        return asseData(num);
    }
}
