package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.safety.core.dao.project.*;
import com.xjt.cloud.safety.core.entity.project.*;
import com.xjt.cloud.safety.core.service.impl.device.DeviceServiceImpl;
import com.xjt.cloud.safety.core.dao.device.CheckRecordDao;
import com.xjt.cloud.safety.core.dao.device.DeviceSystemDao;
import com.xjt.cloud.safety.core.dao.device.OtherInstructionsDao;
import com.xjt.cloud.safety.core.entity.device.DeviceSystem;
import com.xjt.cloud.safety.core.service.service.device.CheckRecordService;
import com.xjt.cloud.safety.core.service.service.project.CheckProjectService;
import com.xjt.cloud.safety.core.service.service.project.PermissionService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;


/**
 * CheckProjectServiceImpl 检测项目实现类
 *
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
 */
@Service
public class CheckProjectServiceImpl extends AbstractService implements CheckProjectService {
    @Autowired
    private CheckProjectDao checkProjectDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    protected BuildingDao buildingDao;
    @Autowired
    protected OtherInstructionsDao otherInstructionsDao;
    @Autowired
    private DeviceSystemDao deviceSystemDao;
    @Autowired
    private CheckUserDao checkUserDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private PlanManagementDao planManagementDao;

    /**
     * 查询检测项目信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/10 15:46
     **/
    @Override
    public Data findByCheckProjectList(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);

        Integer totalCount = checkProject.getTotalCount();
        Integer pageSize = checkProject.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = checkProjectDao.findByCheckProjectListCount(checkProject);
        }

        List<CheckProject> list = checkProjectDao.findByCheckProjectList(checkProject);
        JSONObject data = new JSONObject(2);
        data.put("totalCount", totalCount);
        data.put("list", list);
        return asseData(data);
    }

    /**
     * 查询我的检测项目列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/14 9:16
     **/
    @Override
    public Data findByMyCheckProjectList(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        checkProject.setUserId(userId);
        List<CheckProject> list = checkProjectDao.findByMyCheckProjectList(checkProject);
        Integer totalCount = checkProject.getTotalCount();
        Integer pageSize = checkProject.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = checkProjectDao.findByMyCheckProjectListCount(checkProject);
        }

        JSONObject data = new JSONObject(2);
        data.put("totalCount", totalCount);
        data.put("list", list);
        return asseData(data);
    }

    /**
     *
     * @param userIds List<Long>
     * @param checkProjectId checkProjectId
     * @param projectId 项目ID
     * @return List<CheckUser>
     * @author huanggc
     * @date 2021/05/08
     */
    private List<CheckUser> initCheckUser(List<Long> userIds, Long checkProjectId, Long projectId) {
        List<CheckUser> list = new ArrayList<>(userIds.size());
        for (Long userId : userIds) {
            CheckUser checkUser = new CheckUser(userId, checkProjectId, projectId);
            list.add(checkUser);
        }
        return list;
    }

    /**
     * 保存检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/10 15:47
     **/
    @Override
    public Data saveCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProject.initHeightAndAcreageAndContractAmount(checkProject.getHeight(), checkProject.getAcreage(), checkProject.getCheckAcreage(), checkProject.getContractAmount());
        //SimpleDateFormat mm = new SimpleDateFormat("MMHHm");
        // 生成检测项目编号
       /* checkProject.setNumber("消检（"+checkProject.getCreditCode().substring(9,checkProject.getCreditCode().length())+
                "）["+ mm.format(new Date()) +"]号");*/
        if (checkProject.getId() != null && checkProject.getId() != 0) {
            // 保存项目检测人
            if (checkProject.getCheckUserIds() != null && checkProject.getCheckUserIds().size() > 0) {
                List<CheckUser> checkUsers = initCheckUser(checkProject.getCheckUserIds(), checkProject.getId(), checkProject.getProjectId());
                CheckUser checkUser = new CheckUser();
                checkUser.setCheckProjectId(checkProject.getId());
                // 先删除项目里老记录再保存新记录
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
        // 保存项目检测人
        if (checkProject.getCheckUserIds() != null && checkProject.getCheckUserIds().size() > 0 && status > 0) {
            List<CheckUser> checkUsers = initCheckUser(checkProject.getCheckUserIds(), checkProject.getId(), checkProject.getProjectId());
            checkUserDao.saveCheckUser(checkUsers);
        }

        return asseData(checkProject);
    }

    /**
     * 删除检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/10 15:47
     **/
    @Override
    public Data delCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProjectDao.delCheckProject(checkProject);
        return Data.isSuccess();
    }

    /**
     * 更新检测项目信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/10 15:47
     **/
    @Override
    public Data updCheckProject(String json) {
        CheckProject checkProject = JSON.parseObject(json, CheckProject.class);
        checkProject.initHeightAndAcreageAndContractAmount(checkProject.getHeight(), checkProject.getAcreage(), checkProject.getCheckAcreage(),
                checkProject.getContractAmount());

        checkProjectDao.updCheckProject(checkProject);
        return asseData(checkProject);
    }

    /**
     * 查询系统列表
     *
     * @param checkProjectId checkProjectId
     * @return java.util.List<com.xjt.cloud.project.core.entity.device.DeviceSystem>
     * @author zhangZaiFa
     * @date 2020/4/12 15:26
     **/
    public List<DeviceSystem> findDeviceTypes(Long checkProjectId) {
        CheckProject checkProject = findCheckProjectId(checkProjectId);
        DeviceSystem deviceSystem = new DeviceSystem();
        deviceSystem.setType(1);
        List<DeviceSystem> list = deviceSystemDao.findDeviceTypeList(deviceSystem);
        //系统类型Id用分号隔开
        String sysTypeIds = "," + checkProject.getSysTypeIds() + ",";
        for (DeviceSystem entity : list) {
            if (sysTypeIds.indexOf(entity.getId() + "") != -1) {
                entity.setChecked(true);
            }
        }
        return list;
    }


    /**
     * 更新检测项目状态
     *
     * @param checkProjectId     checkProjectId
     * @param checkProjectStatus checkProjectStatus
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/11 15:07
     **/
    public Data updCheckProject(Long checkProjectId, Integer checkProjectStatus) {
        CheckProject checkProject = new CheckProject();
        checkProject.setId(checkProjectId);
        checkProject.setStatus(checkProjectStatus);
        checkProjectDao.updCheckProject(checkProject);
        return Data.isSuccess();
    }

    /**
     * 更新检测项目信息
     *
     * @param checkProject checkProject
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/11 15:07
     **/
    private Data updCheckProject(CheckProject checkProject) {
        checkProjectDao.updCheckProject(checkProject);
        return Data.isSuccess();
    }

    /**
     * 通过checkProjectId查询对象
     *
     * @param checkProjectId checkProjectId
     * @return com.xjt.cloud.project.core.entity.project.CheckProject
     * @author zhangZaiFa
     * @date 2020/4/11 15:26
     **/
    private CheckProject findCheckProjectId(Long checkProjectId) {
        CheckProject checkProject = new CheckProject();
        checkProject.setId(checkProjectId);
        checkProject = checkProjectDao.findByCheckProject(checkProject);
        return checkProject;
    }

    /**
     * 查询检测项目信息详情
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/10 15:47
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
     * 查询检测报告扫描信息
     *
     * @param number 项目编号
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/12 10:27
     **/
    @Override
    public Data findCheckProjectReport(String number) {
        CheckProject checkProject = new CheckProject();
        checkProject.setNumber(number);
        checkProject = checkProjectDao.findByCheckProject(checkProject);
        Project project = projectDao.get(checkProject.getProjectId());
        /*PlanManagement planManagement = new PlanManagement();
        planManagement.setNumber(number);
        List<PlanManagement> planManagementList = planManagementDao.findPlanManagementReportList(planManagement);*/

        JSONObject jsonObject = new JSONObject(8);
        jsonObject.put("projectName", project.getProjectName());
        jsonObject.put("checkProjectName", checkProject.getCheckProjectName());
        jsonObject.put("address", project.getAddress());
        jsonObject.put("propertyManagementUnit", checkProject.getPropertyManagementUnit());
       /* if (CollectionUtils.isNotEmpty(planManagementList)) {
            planManagement = planManagementList.get(0);
            jsonObject.put("checkProjectReportUrl", planManagement.getCheckLabelFileUrl());
            jsonObject.put("completeDate", planManagement.getCompleteDate());
            jsonObject.put("reportCode", planManagement.getReportCode());
        }*/


        jsonObject.put("number", checkProject.getNumber());
        jsonObject.put("checkProjectNumber", checkProject.getCheckProjectNumber());
        jsonObject.put("requesterUnit", checkProject.getRequesterUnit());
        jsonObject.put("checkEndTime", checkProject.getNumber());
        jsonObject.put("assessLevel",checkProject.getAssessLevel());
        jsonObject.put("checkLabelFileUrl",checkProject.getCheckLabelFileUrl());
        return asseData(jsonObject);
    }

    /**
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
     * 报告查询
     *
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-10
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
     * @author huanggc
     * @date 2020-04-10
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
     * @author huanggc
     * @date 2019/11/04
     */
    public static void downModel(HttpServletResponse response, String fileUrl, String fileName, Map<String, Object> map, Configuration configuration, String modelName) {
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            OutputStream outt = new FileOutputStream(fileUrl + fileName + ".doc");
            oWriter = new OutputStreamWriter(outt, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板
            Template template = configuration.getTemplate(modelName);
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
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return false;
        }

        try {
            // 新建一个pdf文档
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(wordPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, com.aspose.words.SaveFormat.PDF);
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
    public static boolean deleteFile(String path, String docName, String pdfName) {
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
                if (docName.equals(name) || pdfName.equals(name)) {
                    temp.delete();
                }
            }
            // 递归
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                //deleteFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }


    ///////////////////////////////////合同管理///////////////////

    /**
     * 查询合同列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
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
        //判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = contractDao.findContractListCount(contract);
        }
        List<Contract> list = contractDao.findContractList(contract);
        return asseData(totalCount, list);
    }

    /**
     * 保存合同列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
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
     * 删除合同列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
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
