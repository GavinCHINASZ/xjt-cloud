package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.safety.core.dao.device.InstrumentDao;
import com.xjt.cloud.safety.core.dao.device.RiskProblemDao;
import com.xjt.cloud.safety.core.dao.project.*;
import com.xjt.cloud.safety.core.common.ConstantsProjectMsg;
import com.xjt.cloud.safety.core.dao.device.CheckRecordDao;
import com.xjt.cloud.safety.core.entity.device.*;
import com.xjt.cloud.safety.core.entity.project.*;
import com.xjt.cloud.safety.core.service.service.project.CheckProjectReportService;
import com.xjt.cloud.safety.core.service.service.project.SafetyReportService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class SafetyReportServiceImpl extends AbstractService implements SafetyReportService {
    @Autowired
    private CheckProjectServiceImpl checkProjectService;
    @Autowired
    private CheckProjectReportService checkProjectReportService;
    @Autowired
    private CheckProjectDao checkProjectDao;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    protected BuildingDao buildingDao;
    @Autowired
    private CheckRecordDao checkRecordDao;
    @Autowired
    private AssessmentUserDao assessmentUserDao;
    @Autowired
    private InstrumentDao instrumentDao;
    @Autowired
    private RiskProblemDao riskProblemDao;

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


            if (StringUtils.isNotEmpty(checkLabelFileUrl)) {
                data.setStatus(Constants.SUCCESS_CODE);
                data.setMsg("标签生成成功");
                data.setObject(checkLabelFileUrl);
            } else {
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("标签生成失败");
            }
        }
        CheckProjectServiceImpl.deleteFile(fileUrl, fileName + ".pdf", fileName + ".doc");
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
    public Data saveSafetyReport(String json, HttpServletResponse response) {
        CheckProject entity = JSON.parseObject(json, CheckProject.class);

        // 首页
        CheckProject checkProject = checkProjectDao.findByCheckProject(entity);
        Long checkProjectId = checkProject.getId();
        Integer checkProjectStatus = entity.getCheckProjectStatus();
        Project project = projectDao.get(checkProject.getProjectId());
        if (project == null) {
            project = new Project();
        }

        Date date = new Date();
        String checkProjectNum= "";
        checkProject.setCheckCode("");
        if (checkProjectStatus == 3) {
            // 签章时间
            checkProject.setCheckProjectReportCreateTime(date);

            String url = ConstantsProjectMsg.SAFETY_QR_CODE_WORD_FILE_URL + checkProject.getNumber();
            BufferedImage bufferedImage = QrCodeUtils.initQrCode(url, 220, 220, 0, -16777216, -1);
            // 二维码
            String imgBase64Str = QrCodeUtils.generateWaterFile(bufferedImage);
            checkProject.setCheckCode(imgBase64Str);

            // 报告编号
            checkProjectNum = checkProjectService.generateProjectCheckNumber(project.getUserCode(), "粤消维", 10);
            checkProject.setCheckProjectNumber(checkProjectNum);
        }

        // 评估依据
        Map<String, Object> hashMap = new HashMap<>(10);
        String[] assessmentBasiss = checkProject.getAssessmentBasis().split("\\r\\n");
        hashMap.put("assessmentBasiss", CollectionUtils.isEmpty(assessmentBasiss) ? new ArrayList<>(1) : assessmentBasiss);
        //报告编写人
        Long userId = checkProject.getCompilingUserId();
        String userName = userId == null ? null :getUserPropertyByIdOrLoginName(userId,null,"userName");
        checkProject.setCompilingUserName(userName == null ? "": userName);

        //项目负责人
        userId = checkProject.getLeadingUserId();
        userName = userId == null ? null :getUserPropertyByIdOrLoginName(userId,null,"userName");
        checkProject.setLeadingUserName(userName == null ? "": userName);

        //报告审核人
        userId = checkProject.getApprovalUserId();
        userName = userId == null ? null :getUserPropertyByIdOrLoginName(userId,null,"userName");
        checkProject.setAuditUserName(userName == null ? "": userName);

        //报告批准人
        userId = checkProject.getApproveUserId();
        userName = userId == null ? null :getUserPropertyByIdOrLoginName(userId,null,"userName");
        checkProject.setApproveUserName(userName == null ? "": userName);

        hashMap.put("checkProject", checkProject);
        hashMap.put("project", project);
        SimpleDateFormat mmCn = new SimpleDateFormat("yyyy-MM-dd");
        // 报告时间
        hashMap.put("reportDate", DateUtils.getDateYearMonthDays(date));
        // 编制日期
        hashMap.put("assessmentDate", mmCn.format(checkProject.getAssessmentDate()));

        //查询评估组成员信息
        AssessmentUser assessmentUser = new AssessmentUser();
        assessmentUser.setCheckProjectId(checkProjectId);
        String[] orderCols = {"assessmentUserType"};
        assessmentUser.setOrderCols(orderCols);
        List<AssessmentUser> assessmentUserList = assessmentUserDao.findAssessmentUserList(assessmentUser);
        if(CollectionUtils.isEmpty(assessmentUserList)){
            assessmentUserList = new ArrayList<>(1);
            assessmentUserList.add(new AssessmentUser());
        }
        hashMap.put("assessmentUserList", assessmentUserList);

        //查询测试设备
        Instrument instrument = new Instrument();
        instrument.setCheckProjectId(checkProjectId);
        instrument.setProjectId(project.getId());
        List<Instrument> instrumentList = instrumentDao.findInstrumentList(instrument);
        if(CollectionUtils.isEmpty(instrumentList)){
            instrumentList = new ArrayList<>(1);
            instrumentList.add(new Instrument());
        }
        hashMap.put("instrumentList", instrumentList);

        //消防安全问题
        RiskProblem riskProblem = new RiskProblem();
        RiskProblem tempRiskProblem = new RiskProblem();
        riskProblem.setCheckProjectId(checkProjectId);
        riskProblem.setSafetyType(1);
        List<RiskProblem> riskProblemList = riskProblemDao.findRiskProblemList(riskProblem);
        if(CollectionUtils.isEmpty(riskProblemList)){
            riskProblemList = new ArrayList<>(1);
            riskProblemList.add(tempRiskProblem);
        }
        hashMap.put("riskProblemList1", riskProblemList);
        riskProblem.setSafetyType(2);
        riskProblemList = riskProblemDao.findRiskProblemList(riskProblem);
        if(CollectionUtils.isEmpty(riskProblemList)){
            riskProblemList = new ArrayList<>(1);
            riskProblemList.add(tempRiskProblem);
        }
        hashMap.put("riskProblemList2", riskProblemList);
        riskProblem.setSafetyType(3);
        riskProblemList = riskProblemDao.findRiskProblemList(riskProblem);
        if(CollectionUtils.isEmpty(riskProblemList)){
            riskProblemList = new ArrayList<>(1);
            riskProblemList.add(tempRiskProblem);
        }
        hashMap.put("riskProblemList3", riskProblemList);

        //消防安全对策、措施及建议
        riskProblem.setCountermeasure("true");
        riskProblem.setSafetyType(1);
        riskProblemList = riskProblemDao.findRiskProblemList(riskProblem);
        if(CollectionUtils.isEmpty(riskProblemList)){
            riskProblemList = new ArrayList<>(1);
            riskProblemList.add(tempRiskProblem);
        }
        hashMap.put("countermeasures1", riskProblemList);

        riskProblem.setSafetyType(2);
        riskProblemList = riskProblemDao.findRiskProblemList(riskProblem);
        if(CollectionUtils.isEmpty(riskProblemList)){
            riskProblemList = new ArrayList<>(1);
            riskProblemList.add(tempRiskProblem);
        }
        hashMap.put("countermeasures2", riskProblemList);

        riskProblem.setSafetyType(3);
        riskProblemList = riskProblemDao.findRiskProblemList(riskProblem);
        if(CollectionUtils.isEmpty(riskProblemList)){
            riskProblemList = new ArrayList<>(1);
            riskProblemList.add(tempRiskProblem);
        }
        hashMap.put("countermeasures3", riskProblemList);

        // 查询综合评估得分
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCheckProjectId(checkProjectId);
        checkRecord.setDeviceSysIds(checkProject.getSysTypeIdList());
        checkRecord.setBuildingType(checkProject.getBuildingType());
        List<CheckReport> checkRecordList = checkRecordDao.findCheckRecordSynthesisScoreList(checkRecord);
        double totalScore = 0D;
        if (CollectionUtils.isEmpty(checkRecordList)) {
            checkRecordList = new ArrayList<>(1);
            checkRecordList.add(new CheckReport());
        }else{
            totalScore = checkRecordSynthesisScoreListCalculate(checkRecordList);
        }
        String assessLevel = "不合格";
        if (checkProject.getCheckResult() != 2) {
            if (totalScore > 80) {
                assessLevel = "良好";
            } else if (totalScore > 60) {
                assessLevel = "一般";
            }
        }
        hashMap.put("totalScore", String.format("%.2f", totalScore));//总分
        hashMap.put("assessLevel", assessLevel);//评级
        hashMap.put("totalScoreList", checkRecordList);

        // 建筑消防安全评估检查测试表
        checkRecord.setDeviceType(1);
        CheckReport checkReport = new CheckReport();
        List<CheckReport> checkRecordLists = checkRecordDao.findCheckRecordItemScoreList(checkRecord);
        if(CollectionUtils.isEmpty(checkRecordLists)){
            checkRecordLists = new ArrayList<>(1);
            checkRecordLists.add(checkReport);
        }
        hashMap.put("checkRecordList1", checkRecordLists);
        checkRecord.setDeviceType(2);
        checkRecordLists = checkRecordDao.findCheckRecordItemScoreList(checkRecord);
        if(CollectionUtils.isEmpty(checkRecordLists)){
            checkRecordLists = new ArrayList<>(1);
            checkRecordLists.add(checkReport);
        }
        hashMap.put("checkRecordList2", checkRecordLists);
        checkRecord.setDeviceType(3);
        checkRecordLists = checkRecordDao.findCheckRecordItemScoreList(checkRecord);
        if(CollectionUtils.isEmpty(checkRecordLists)){
            checkRecordLists = new ArrayList<>(1);
            checkRecordLists.add(checkReport);
        }
        hashMap.put("checkRecordList3", checkRecordLists);

        /*String createUserName = checkRecordDao.findCreateUserName(entity.getId());
        hashMap.put("createUserName", StringUtils.isNotEmpty(createUserName) ? createUserName : "");*/

        String fileUrl = System.getProperty("user.dir") + ConstantsProjectMsg.REPORT_FILE_URL;
        String fileName = System.currentTimeMillis() + "";
        //String fileName = "123456";//System.currentTimeMillis() + "";
        // 导出方法
        CheckProjectServiceImpl.downModel(response, fileUrl, fileName, hashMap, configuration, "buildingFireSafetyAssessmentReport.xml");

        Data data = new Data();
        // 上传
        File file = new File(fileUrl + fileName + ".pdf");
        String result = HttpUtils.restTemplateTransferFile(ConstantsProjectMsg.PDF_UPLOAD_URL, file, "checkrecord");
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            // PDF路径
            String checkProjectReportUrl = jsonObject.getString("object");

            entity.setCheckProjectReportUrl(checkProjectReportUrl);
            if (StringUtils.isNotEmpty(checkProjectReportUrl)) {
                //修改报告信息
                CheckProject tem = new CheckProject();
                tem.setId(checkProject.getId());
                tem.setCheckProjectStatus(checkProjectStatus);
                tem.setScore(String.format("%.2f", totalScore));
                tem.setAssessLevel(assessLevel);
                if (checkProjectStatus == 3){
                    tem.setCheckLabelFileUrl(checkProjectReportUrl);
                    tem.setCheckProjectNumber(checkProjectNum);
                }else{
                    tem.setCheckProjectReportUrl(checkProjectReportUrl);
                }
                checkProjectDao.updCheckProject(tem);

                //保存报告生成记录信息
                CheckProjectReport checkProjectReport = new CheckProjectReport();
                checkProjectReport.setReportName(DateUtils.formatDate(DateUtils.DATETIME,date));
                setEntityUserIdName(SecurityUserHolder.getUserName(), project.getId(), checkProjectReport);
                checkProjectReport.setCheckProjectStatus(checkProjectStatus);
                checkProjectReport.setCheckProjectId(checkProjectId);
                checkProjectReport.setProjectId(project.getId());
                checkProjectReport.setFileUrl(checkProjectReportUrl);
                checkProjectReport.setIsModify(1);
                checkProjectReport.setReportNumber(checkProjectNum);
                checkProjectReportService.saveCheckProjectReport(checkProjectReport);

                data.setStatus(Constants.SUCCESS_CODE);
                data.setMsg("报告生成成功");
                data.setObject(checkProjectReportUrl);
            } else {
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("报告生成失败");
            }
            data.setObj(entity);
        }
        CheckProjectServiceImpl.deleteFile(fileUrl, fileName + ".doc", fileName + ".pdf");
        return data;
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

    /**
     * @Description 计算综合评估得分
     *
     * @param list
     * @author wangzhiwen
     * @date 2021/5/13 14:45
     * @return java.util.List<com.xjt.cloud.safety.core.entity.device.CheckReport>
     */
    private double checkRecordSynthesisScoreListCalculate(List<CheckReport> list){
        int itemScore = 0;
        double totalScore = 0;
        int beginIndex = 0;
        CheckReport tem;
        int size = list.size();
        for (int i = 0;i < size;i++){
            tem = list.get(i);
            itemScore += (tem.getScore() == 0 ? (tem.getWeight() == null ? 0 : tem.getWeight()) * 100 : tem.getScore());
            if (i == size - 1 || !tem.getDeviceType().equals(list.get(i + 1).getDeviceType())){
                itemScore = itemScore * tem.getTypeWeight();
                double s = (double)itemScore / 10000.00D;
                String temScores = String.format("%.2f", s);
                for (int n = beginIndex;n <= i;n++){
                    tem = list.get(n);
                    tem.setItemScore(temScores);
                }
                beginIndex = i + 1;
                totalScore += Double.parseDouble(temScores);
                itemScore = 0;
            }
        }
        /*for (CheckReport checkReport:list){
            checkReport.setTotalScore(totalScore);
        }*/
        return totalScore;
    }
}
