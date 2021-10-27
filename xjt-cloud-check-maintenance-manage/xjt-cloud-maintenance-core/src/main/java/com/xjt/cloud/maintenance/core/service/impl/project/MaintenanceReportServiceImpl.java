package com.xjt.cloud.maintenance.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.maintenance.core.common.ConstantsProjectMsg;
import com.xjt.cloud.maintenance.core.dao.device.CheckRecordDao;
import com.xjt.cloud.maintenance.core.dao.device.OtherInstructionsDao;
import com.xjt.cloud.maintenance.core.dao.project.*;
import com.xjt.cloud.maintenance.core.entity.device.CheckRecord;
import com.xjt.cloud.maintenance.core.entity.device.DeviceSystem;
import com.xjt.cloud.maintenance.core.entity.project.*;
import com.xjt.cloud.maintenance.core.service.service.project.MaintenanceReportService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 维保报告
 *
 * @author huanggc
 * @date 2021/04/17
 */
@Service
public class MaintenanceReportServiceImpl extends AbstractService implements MaintenanceReportService {
    @Autowired
    private CheckProjectServiceImpl checkProjectService;
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
    private CheckRecordDao checkRecordDao;
    @Autowired
    private PlanManagementDao planManagementDao;

    /**
     * 打印标签
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021-04-14
     **/
    @Override
    public Data saveLabel(String json, HttpServletResponse response) {
        CheckProject entity = JSON.parseObject(json, CheckProject.class);
        CheckProject checkProject = checkProjectDao.findByCheckProject(entity);
        Project project = projectDao.get(checkProject.getProjectId());
        if (project == null){
            project = new Project();
        }

        // 生成二维码
        /*String url = ConstantsProjectMsg.QR_CODE_WORD_FILE_URL + checkProject.getNumber();
        BufferedImage bufferedImage = QrCodeUtils.initQrCode(url, 220, 220, 0, -16777216, -1);
        String imgBase64Str = QrCodeUtils.generateWaterFile(bufferedImage);
        checkProject.setCheckCode(imgBase64Str);*/

        // 封装数据
        Map<String, Object> hashMap = new HashMap<>(3);
        hashMap.put("checkProject", checkProject);
        hashMap.put("project", project);

        // 维保计划
        PlanManagement plan = new PlanManagement();
        plan.setId(entity.getPlanId());
        PlanManagement planEntity = planManagementDao.findPlanManagement(plan);
        hashMap.put("planManagement", planEntity);

        String fileUrl = System.getProperty("user.dir") + ConstantsProjectMsg.REPORT_FILE_URL;
        String fileName = System.currentTimeMillis() + "";
        // 导出方法
        CheckProjectServiceImpl.downModel(response, fileUrl, fileName, hashMap, configuration, "maintenanceLabelModel.xml");

        // 上传
        Data data = new Data();
        File file = new File(fileUrl + fileName + ".pdf");
        String result = HttpUtils.restTemplateTransferFile(ConstantsProjectMsg.PDF_UPLOAD_URL, file, "checkrecord");
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String checkLabelFileUrl = jsonObject.getString("object");
            // 标签PDF路径
            entity.setCheckLabelFileUrl(checkLabelFileUrl);

            // 更新 维保计划
            PlanManagement planManagement = new PlanManagement();
            planManagement.setId(entity.getPlanId());
            planManagement.setCheckLabelFileUrl(checkLabelFileUrl);
            planManagementDao.updatePlanManagement(planManagement);

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
        CheckProjectServiceImpl.deleteFile(fileUrl, fileNames);
        return data;
    }

    /**
     * 生成 维保报告
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data saveMaintenanceReport(String json, HttpServletResponse response) {
        CheckProject entity = JSON.parseObject(json, CheckProject.class);

        // 首页
        CheckProject checkProject = checkProjectDao.findByCheckProject(entity);
        Long checkProjectId = checkProject.getId();
        Project project = projectDao.get(checkProject.getProjectId());
        if (project == null) {
            project = new Project();
        }

        Date date = new Date();
        String checkProjectNum= "";
        if (entity.getPlanState() != null && entity.getPlanState() == 3) {
            // 签章时间
            checkProject.setCheckProjectReportCreateTime(date);

            String url = ConstantsProjectMsg.MAINTENANCE_QR_CODE_WORD_FILE_URL + checkProject.getNumber();
            BufferedImage bufferedImage = QrCodeUtils.initQrCode(url, 220, 220, 0, -16777216, -1);
            // 二维码
            String imgBase64Str = QrCodeUtils.generateWaterFile(bufferedImage);
            checkProject.setCheckCode(imgBase64Str);

            // 报告编号
            checkProjectNum = checkProjectService.generateProjectCheckNumber(project.getUserCode(), "粤消维", 10);
            checkProject.setCheckProjectNumber(checkProjectNum);
        }

        // 封装数据
        Map<String, Object> hashMap = new HashMap<>(18);
        hashMap.put("checkProject", checkProject);
        hashMap.put("project", project);
        SimpleDateFormat mmCn = new SimpleDateFormat("yyyy年MM月dd日");
        // 报告时间
        hashMap.put("reportDate", DateUtils.getDateYearMonthDays(date));
        // 编制日期
        hashMap.put("reportDateCn", mmCn.format(date));

        // 维保计划
        PlanManagement plan = new PlanManagement();
        plan.setId(entity.getPlanId());
        PlanManagement planEntity = planManagementDao.findPlanManagement(plan);
        hashMap.put("planManagement", planEntity != null ? planEntity : plan);

        // 建筑消防设施内容
        List<DeviceSystem> deviceDateList = checkProjectService.findDeviceTypes(checkProjectId);
        List<DeviceSystem> deviceList = new ArrayList<>();
        List<DeviceSystem> deviceLists = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(deviceDateList)) {
            for (int i = 0; i < deviceDateList.size(); i++) {
                // 奇数
                if (i % 2 != 0) {
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

        // 建筑物情况表  buildingDao.findByProjectList(building)
        Building building = new Building();
        building.setCheckProjectId(checkProjectId);
        List<Building> buildingList = buildingDao.findByProjectList(building);
        if (CollectionUtils.isEmpty(buildingList)) {
            hashMap.put("building", building);
            hashMap.put("buildingList", new ArrayList<Building>(1));
        } else {
            hashMap.put("building", buildingList.get(0));
            buildingList.remove(0);
            hashMap.put("buildingList", buildingList);
        }

        // 3建筑消防设施巡查记录汇总表
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCheckProjectId(checkProjectId);
        checkRecord.setDeviceSysIds(checkProject.getSysTypeIdList());
        checkRecord.setPlanId(entity.getPlanId());
        List<CheckRecord> checkRecordList = checkRecordDao.findAllCheckRecordList(checkRecord);
        if (CollectionUtils.isEmpty(checkRecordList)) {
            checkRecordList = new ArrayList<>(1);
            // 测试数据
            /*for (int i = 0; i < 18; i++) {
                CheckRecord c = new CheckRecord();
                if (i < 7) {
                    c.setDeviceSysName("deviceSysName1");
                    c.setDeviceTypeName("deviceTypeName" + (i < 3 ? 1 : 2));
                    c.setCheckName("checkName" + i);

                    c.setCheckNum(i);
                } else {
                    c.setDeviceSysName("deviceSysName2");
                    c.setDeviceTypeName("deviceTypeName" + (i < 12 ? 11 : 22));
                    c.setCheckName("checkName" + i);

                    c.setCheckNum(i);
                }
                checkRecordList.add(c);
            }*/
            hashMap.put("contactUserName", "");
        }else {
            hashMap.put("contactUserName", checkProject.getContactUserName());
        }
        hashMap.put("checkRecordList", checkRecordList);

        // 4建筑消防设施测试记录汇总表
        checkRecord.setStartDate(DateUtils.monthStarDate(new Date()));
        checkRecord.setEndDate(DateUtils.monthEndDate(new Date()));
        List<CheckRecord> checkRecordLists = checkRecordDao.findTestAllCheckRecordList(checkRecord);
        if (CollectionUtils.isEmpty(checkRecordLists)) {
            checkRecordLists = new ArrayList<>(1);
            // 测试数据
            /*for (int i = 0; i < 18; i++) {
                CheckRecord c = new CheckRecord();
                if (i < 7) {
                    c.setDeviceSysName("deviceSysName1");
                    c.setDeviceTypeName("deviceTypeName" + (i < 3 ? 1 : 2));
                    c.setCheckName("checkName" + i);

                    c.setCheckNum(i);
                } else {
                    c.setDeviceSysName("deviceSysName2");
                    c.setDeviceTypeName("deviceTypeName" + (i < 12 ? 11 : 22));
                    c.setCheckName("checkName" + i);

                    c.setCheckNum(i);
                }
                checkRecordLists.add(c);
            }*/

            hashMap.put("contactUserNames", "");
            // 测试结论
            hashMap.put("testConclusion", "正常");
        }else {
            hashMap.put("contactUserNames", checkProject.getContactUserName());
            hashMap.put("testConclusion", "正常");
            for (CheckRecord record : checkRecordLists) {
                if (StringUtils.isNotEmpty(record.getCheckFailNumD())){
                    hashMap.put("testConclusion", "故障");
                    break;
                }
            }
        }
        hashMap.put("checkRecordLists", checkRecordLists);

        // 测试人（签名）
        String createUserNameAndCertificateNumber = checkRecordDao.findCreateUserName(entity.getId());
        String certificateNumber = "";
        String createUserName = "";
        if(StringUtils.isNotEmpty(createUserNameAndCertificateNumber)){
            String[] split = createUserNameAndCertificateNumber.split(";");
            if (split.length > 0){
                createUserName = split[0];
                if (split.length == 2){
                    certificateNumber = split[1];
                }
            }
        }
        hashMap.put("certificateNumber", certificateNumber);
        hashMap.put("createUserName", createUserName);

        // 5设备实测记录 checkRecordDao.findCheckRecordList(record)
        CheckRecord record = new CheckRecord();
        record.setCheckProjectId(checkProjectId);
        record.setCheckType(3);
        record.setStartDate(DateUtils.monthStarDate(new Date()));
        record.setEndDate(DateUtils.monthEndDate(new Date()));
        List<CheckRecord> deviceCheckRecordList = checkRecordDao.findCheckRecordList(record);
        if (CollectionUtils.isEmpty(deviceCheckRecordList)) {
            // 测试数据
            /*String path = System.getProperty("user.dir");
            String base64ByImgUrl = Base64Util.getImgFileToBase64(path + "/config/static/model/whiteImage.png");
            deviceCheckRecordList = new ArrayList<>(3);
            for (int i = 0; i < 3; i++) {
                CheckRecord c = new CheckRecord();
                //String base64ByImgUrl = ImgUtils.getBase64ByImgUrl(url1);
                c.setImgUrl(base64ByImgUrl);
                c.setImgUrl2(base64ByImgUrl);
                deviceCheckRecordList.add(c);
            }
            hashMap.put("deviceCheckRecordList", deviceCheckRecordList);*/

            hashMap.put("deviceCheckRecordList", new ArrayList<CheckRecord>(1));
            hashMap.put("deviceMeasuredRecord", "");
        } else {
            String path = System.getProperty("user.dir");
            SysLog.info("--------------->deviceCheckRecordList.size()=" + deviceCheckRecordList.size());

            initCheckRecordListImg(deviceCheckRecordList, path);

            hashMap.put("deviceCheckRecordList", deviceCheckRecordList);
            hashMap.put("deviceMeasuredRecord", "显示");
        }

        // 6 设备巡查记录
        CheckRecord recordEntity = new CheckRecord();
        recordEntity.setCheckProjectId(checkProjectId);
        recordEntity.setCheckType(2);
        recordEntity.setStartDate(DateUtils.monthStarDate(new Date()));
        recordEntity.setEndDate(DateUtils.monthEndDate(new Date()));
        List<CheckRecord> devicePatrolList = checkRecordDao.findCheckRecordList(recordEntity);
        if (CollectionUtils.isEmpty(devicePatrolList)) {
            // 测试数据
            /*String path = System.getProperty("user.dir");
            String base64ByImgUrl = Base64Util.getImgFileToBase64(path + "/config/static/model/whiteImage.png");
            deviceCheckRecordList = new ArrayList<>(3);
            for (int i = 0; i < 3; i++) {
                CheckRecord c = new CheckRecord();
                //String base64ByImgUrl = ImgUtils.getBase64ByImgUrl(url1);
                c.setImgUrl(base64ByImgUrl);
                c.setImgUrl2(base64ByImgUrl);
                deviceCheckRecordList.add(c);
            }
            hashMap.put("deviceCheckRecordList", deviceCheckRecordList);*/

            hashMap.put("devicePatrolList", new ArrayList<CheckRecord>(1));
            hashMap.put("devicePatrols", "");
        } else {
            String path = System.getProperty("user.dir");
            SysLog.info("--------------->devicePatrolList.size()=" + devicePatrolList.size());

            initCheckRecordListImg(devicePatrolList, path);

            hashMap.put("devicePatrolList", devicePatrolList);
            hashMap.put("devicePatrols", "显示");
        }

        String fileUrl = System.getProperty("user.dir") + ConstantsProjectMsg.REPORT_FILE_URL;
        String fileName = System.currentTimeMillis() + "";
        // 导出方法
        CheckProjectServiceImpl.downModel(response, fileUrl, fileName, hashMap, configuration, "maintenanceReport.xml");

        // 签章
        /*String outputPdfName = System.currentTimeMillis() + "";
        // signText="中国人民共和国\n一级消防工程师\n黄佳明\n14418000097"
        SignHighPdf.signMethod(fileUrl, fileName, "", outputPdfName, 2);*/

        Data data = new Data();
        // 上传
        File file = new File(fileUrl + fileName + ".pdf");
        String result = HttpUtils.restTemplateTransferFile(ConstantsProjectMsg.PDF_UPLOAD_URL, file, "checkrecord");
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            // PDF路径
            String checkProjectReportUrl = jsonObject.getString("object");

            PlanManagement planManagement = new PlanManagement();
            planManagement.setId(entity.getPlanId());
            if (entity.getPlanState() != null && entity.getPlanState() == 3) {
                planManagement.setPlanState(4);
                planManagement.setReportCode(checkProjectNum);
                planManagement.setCompleteDate(date);
                planManagement.setReportFileUrl(checkProjectReportUrl);
            } else {
                planManagement.setTemReportFileUrl(checkProjectReportUrl);
            }
            planManagement.setNumber(checkProject.getNumber());
            planManagementDao.updatePlanManagement(planManagement);

            entity.setCheckProjectReportUrl(checkProjectReportUrl);
            SysLog.debug("报告状态-----planState------>" + planManagement.getPlanState() + "-----id------>" + planManagement.getId());
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

        List<String> fileNames = new ArrayList<>(3);
        fileNames.add(fileName + ".pdf");
        fileNames.add(fileName + ".doc");
        CheckProjectServiceImpl.deleteFile(fileUrl, fileNames);
        return data;
    }

    /**
     * 组装图片
     *
     * @param devicePatrolList List<CheckRecord>
     * @param path 路径
     * @author huanggc
     * @date 2021/05/08
     */
    private void initCheckRecordListImg(List<CheckRecord> devicePatrolList, String path) {
        for (CheckRecord records : devicePatrolList) {
            String checkImgUrls = records.getCheckImgUrls();
            if (StringUtils.isNotEmpty(checkImgUrls) && checkImgUrls.length() > 10) {
                String[] split = checkImgUrls.split(";");
                if (split.length > 0) {
                    String base64ByImgUrl = ImgUtils.getBase64ByImgUrl(split[0]);
                    String base64ByImgUrl2 = ImgUtils.getBase64ByImgUrl(split.length > 1 ? split[1] : "");
                    if (StringUtils.isEmpty(base64ByImgUrl)) {
                        String base641 = Base64Util.getImgFileToBase64(path + "/config/static/model/whiteImage.png");
                        SysLog.info("--------------->base641--------path------->" + path + "/config/static/model/whiteImage.png");
                        records.setImgUrl(base641);
                    } else {
                        SysLog.info("--------------->base64ByImgUrl--------------->");
                        records.setImgUrl(base64ByImgUrl);
                    }

                    if (StringUtils.isEmpty(base64ByImgUrl2)) {
                        String base642 = Base64Util.getImgFileToBase64(path + "/config/static/model/whiteImage.png");
                        records.setImgUrl2(base642);
                    } else {
                        records.setImgUrl2(base64ByImgUrl2);
                    }
                }
            } else {
                String base641 = Base64Util.getImgFileToBase64(path + "/config/static/model/whiteImage.png");
                SysLog.info("--------------->base641----else----path------->" + path + "/config/static/model/whiteImage.png");
                records.setImgUrl(base641);
                records.setImgUrl2(base641);
            }
        }
    }

    /**
     * 报告查询
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
     **/
    @Override
    public Data findReport(String json) {

        return null;
    }

    /**
     * 报告下载
     *
     * @param json 参数
     * @author huanggc
     * @date 2020-04-10
     **/
    @Override
    public void downReport(String json, HttpServletResponse response) {

    }

}
