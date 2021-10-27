package com.xjt.cloud.fault.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.fault.core.common.ConstantsDevice;
import com.xjt.cloud.fault.core.dao.project.OrgUserDao;
import com.xjt.cloud.fault.core.dao.project.ProjectDao;
import com.xjt.cloud.fault.core.dao.project.RoleDao;
import com.xjt.cloud.fault.core.dao.fault.FaultHandlerDao;
import com.xjt.cloud.fault.core.dao.fault.FaultRepairRecordDao;
import com.xjt.cloud.fault.core.dao.fault.RepairProgressDao;
import com.xjt.cloud.fault.core.entity.fault.FaultHandler;
import com.xjt.cloud.fault.core.entity.fault.FaultRepairRecord;
import com.xjt.cloud.fault.core.entity.fault.RepairProgress;
import com.xjt.cloud.fault.core.entity.project.OrgUser;
import com.xjt.cloud.fault.core.entity.project.Project;
import com.xjt.cloud.fault.core.entity.project.Role;
import com.xjt.cloud.fault.core.service.service.FaultRepairRecordService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * 故障报修
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Service
public class FaultRepairRecordServiceImpl extends AbstractService implements FaultRepairRecordService {
    @Autowired
    private FaultRepairRecordDao faultRepairRecordDao;
    @Autowired
    private RepairProgressServiceImpl repairProgressService;
    @Autowired
    private RepairProgressDao repairProgressDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private FaultHandlerDao faultHandlerDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private Configuration configuration;

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findFaultRepairRecordList(String json) {
        FaultRepairRecord faultRepairRecord = asseFindFaultRepairRecord(json);

        Integer[] overdues = faultRepairRecord.getOverdues();
        if (null != overdues && overdues.length == 1) {
            faultRepairRecord.setOverdue(overdues[0]);
        }

        Date startDate = faultRepairRecord.getStartDate();
        Date endDate = faultRepairRecord.getEndDate();
        if (null != startDate && null != endDate) {
            faultRepairRecord.setCreateTime(DateUtils.startDayDate(startDate));
            faultRepairRecord.setLastModifyTime(DateUtils.endDayDate(endDate));
        }

        if (null == faultRepairRecord.getOrderCols()) {
            String[] orderCols = {"lastModifyTime"};
            faultRepairRecord.setOrderCols(orderCols);
            faultRepairRecord.setOrderDesc(true);
        }

        if (null == startDate && null != faultRepairRecord.getCreateTime()) {
            Date createTime = faultRepairRecord.getCreateTime();
            faultRepairRecord.setCreateTime(DateUtils.monthStarDate(createTime));
            faultRepairRecord.setLastModifyTime(DateUtils.monthEndDate(createTime));
        }

        // 此为毫秒为单位
        long startTime = System.currentTimeMillis();
        List<FaultRepairRecord> repairRecordList = faultRepairRecordDao.findFaultRepairRecordList(faultRepairRecord);
        long endTime = System.currentTimeMillis();
        SysLog.info("时间差：" + (endTime - startTime));

        List<FaultRepairRecord> repairRecords = faultRepairRecordDao.findFaultRepairRecords(faultRepairRecord);
        Integer totalCount = faultRepairRecord.getTotalCount();
        Integer pageSize = faultRepairRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = faultRepairRecordDao.findFaultRepairRecordTotalCount(faultRepairRecord);
        }

        Map<String, Object> map = new HashMap<>(3);
        map.put("listObj", repairRecordList);
        // 各种状态数量
        map.put("repairRecords", repairRecords);
        map.put("totalCount", totalCount);
        return asseData(map);
    }

    /**
     * 组装查询条件
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/26 10:45
     * @return com.xjt.cloud.fault.core.entity.fault.FaultRepairRecord
     */
    private FaultRepairRecord asseFindFaultRepairRecord(String json){
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        if (StringUtils.isNotEmpty(faultRepairRecord.getAppId())) {
            String projects = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, faultRepairRecord.getAppId());
            String[] split = projects.split(",");

            Long[] projectIdArr = ConvertUtils.stringToLong(split);
            faultRepairRecord.setProjectIds(projectIdArr);
        }

        Boolean permissionAll = faultRepairRecord.getPermissionAll();
        // 没有 faultrepair_manage_edit 权限
        if (null != permissionAll && !permissionAll) {
            String userName = SecurityUserHolder.getUserName();
            Long userId = getLoginUserId(userName);

            OrgUser orgUser = new OrgUser();
            orgUser.setUserId(userId);
            orgUser.setProjectId(faultRepairRecord.getProjectId());
            orgUser = orgUserDao.getOrgUser(orgUser);

            if (null != orgUser) {
                faultRepairRecord.setOrgUserId(orgUser.getId());
            }
            faultRepairRecord.setCreateUserId(userId);
        }
        return faultRepairRecord;
    }

    /**
     * 查询app首页故障报修信息
     *
     * @param json 参数
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return JSONObject
     */
    @Override
    public JSONObject findUserProjectFaultRepairData(String json){
        FaultRepairRecord faultRepairRecord = asseFindFaultRepairRecord(json);
        List<FaultRepairRecord> repairRecords = faultRepairRecordDao.findFaultRepairRecords(faultRepairRecord);

        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("modelIndex", 15);
        if (CollectionUtils.isNotEmpty(repairRecords)) {
            for (FaultRepairRecord frr : repairRecords){
                // 待指派
                if (1 == frr.getWorkOrderStatus()){
                    jsonObject.put("toBeAssigned", frr.getTotalCount());
                }else if (3 == frr.getWorkOrderStatus()){
                    // 审核中
                    jsonObject.put("audit", frr.getTotalCount());
                }
            }
        }else{
            jsonObject.put("toBeAssigned", 0);
            jsonObject.put("audit", 0);
        }
        return jsonObject;
    }

    /**
     * 功能描述:查询 故障报修 单个
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-03-27
     */
    @Override
    public Data findFaultRepairRecord(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        FaultRepairRecord entity = faultRepairRecordDao.findFaultRepairRecordOne(faultRepairRecord);
        return asseData(entity);
    }

    /**
     * 功能描述: 大屏--故障报修列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-08
     */
    @Override
    public Data findScreenList(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        String timeType = faultRepairRecord.getTimeType();
        Calendar c = Calendar.getInstance();
        if ("month".equals(timeType)) {
            c.set(Calendar.DATE, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            faultRepairRecord.setStartTime(c.getTime());

            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            // 月份加一
            c.add(Calendar.MONTH, +1);
            // 日减一 (减一后是上个月的最后一天)
            c.add(Calendar.DATE, -1);
            faultRepairRecord.setEndTime(c.getTime());
            faultRepairRecord.setDateNum(c.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if ("year".equals(timeType)) {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            faultRepairRecord.setStartTime(c.getTime());

            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            c.set(Calendar.HOUR, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            faultRepairRecord.setEndTime(c.getTime());
            faultRepairRecord.setDateNum(12);
        }

        faultRepairRecord.setCreateTime(DateUtils.getYearStartTime(System.currentTimeMillis()));
        faultRepairRecord.setLastModifyTime(new Date());

        Integer totalCount = faultRepairRecordDao.findFaultRepairRecordTotalCount(faultRepairRecord);
        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecordDao.findScreenList(faultRepairRecord);
        return asseData(totalCount, faultRepairRecordList);
    }

    /**
     * 功能描述: 地铁大屏--故障报修列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-05-13
     */
    @Override
    public Data findMetroScreenList(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        // Y
        String queryDate = faultRepairRecord.getQueryDate();
        Calendar cal = Calendar.getInstance();
        String[] split = queryDate.split("-");
        int strLength = split.length;
        // 例:2020-03
        if (2 == strLength) {
            cal.set(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 1);
            cal.add(Calendar.MONTH, -1);
        } else {
            // 例:2020
            cal.set(Integer.parseInt(split[0]), 0, 1);
        }
        Date time = cal.getTime();

        Date starDate = DateUtils.monthStarDate(time);

        Date endDate;
        if (2 == strLength) {
            endDate = DateUtils.monthEndDate(time);
        } else {
            cal.add(Calendar.MONTH, 11);
            Date date = cal.getTime();
            endDate = DateUtils.monthEndDate(date);
        }

        faultRepairRecord.setCreateTime(starDate);
        faultRepairRecord.setLastModifyTime(endDate);

        if (null == faultRepairRecord.getOrderCols()) {
            String[] orderCols = {"createTime"};
            faultRepairRecord.setOrderCols(orderCols);
            faultRepairRecord.setOrderDesc(true);
        }

        Integer totalCount = faultRepairRecord.getTotalCount();
        Integer pageSize = faultRepairRecord.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = faultRepairRecordDao.findFaultRepairRecordTotalCount(faultRepairRecord);
        }

        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecordDao.findMetroScreenList(faultRepairRecord);
        return asseData(totalCount, faultRepairRecordList);
    }

    /**
     * 功能描述:查询 故障报修 各状态数量
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findFaultRepairRecords(String json) {
        FaultRepairRecord faultRepairRecord = asseFindFaultRepairRecord(json);

        List<FaultRepairRecord> repairRecordList = faultRepairRecordDao.findFaultRepairRecordList(faultRepairRecord);
        List<FaultRepairRecord> repairRecords = faultRepairRecordDao.findFaultRepairRecords(faultRepairRecord);

        Integer totalCount = faultRepairRecord.getTotalCount();
        Integer pageSize = faultRepairRecord.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = faultRepairRecordDao.findFaultRepairRecordTotalCount(faultRepairRecord);
        }

        Map<String, Object> map = new HashMap<>(3);
        map.put("listObj", repairRecordList);
        // 各种状态的数量
        map.put("repairRecords", repairRecords);
        map.put("totalCount", totalCount);
        return asseData(map);
    }

    /**
     * 功能描述: 项目主页接口--数量  大屏故障报修--数量统计
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-03-19
     */
    @Override
    public Data findProjectCount(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        Boolean permissionAll = faultRepairRecord.getPermissionAll();
        // 没有编辑权限,只查看与自己相关数据
        if (null != permissionAll && !permissionAll) {
            Long userId;
            if (faultRepairRecord.getCreateUserId() == null) {
                String userName = SecurityUserHolder.getUserName();
                userId = getLoginUserId(userName);
            } else {
                userId = faultRepairRecord.getCreateUserId();
            }
            OrgUser orgUser = new OrgUser();
            orgUser.setUserId(userId);
            orgUser.setProjectId(faultRepairRecord.getProjectId());
            orgUser = orgUserDao.getOrgUser(orgUser);

            if (null != orgUser) {
                faultRepairRecord.setCreateUserId(orgUser.getId());
            }
        }

        Integer buttonType = faultRepairRecord.getButtonType();
        // 本月
        if (null != buttonType && 1 == buttonType) {
            Date date = new Date();
            faultRepairRecord.setCreateTime(DateUtils.monthStarDate(date));
            faultRepairRecord.setLastModifyTime(DateUtils.monthEndDate(date));
        } else if (null != buttonType && 2 == buttonType) {
            // 今年
            faultRepairRecord.setCreateTime(DateUtils.getYearStartTime(System.currentTimeMillis()));
            faultRepairRecord.setLastModifyTime(new Date());
        }

        FaultRepairRecord repairRecord = faultRepairRecordDao.findProjectCount(faultRepairRecord);
        return asseData(repairRecord);
    }

    /**
     * 功能描述: PC故障报修 导出表格功能
     *
     * @param json 参数
     * @param resp HttpServletResponse
     * @author huanggc
     * @date 2019/09/04
     */
    @Override
    public void downFaultRepairRecord(String json, HttpServletResponse resp) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        if (null == faultRepairRecord.getOrderCols()) {
            String[] orderCols = {"lastModifyTime"};
            faultRepairRecord.setOrderCols(orderCols);
            faultRepairRecord.setOrderDesc(true);
        }
        List<FaultRepairRecord> list = faultRepairRecordDao.findFaultRepairRecordList(faultRepairRecord);

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        Long projectId = faultRepairRecord.getProjectId();

        // 从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        // 表名
        jsonObject.put("title", projectName + "--故障报修列表");

        String[] keys = {"rowNum", "workOrderNumber", "deviceName", "deviceQrNo", "checkPointName", "checkPointQrNo",
                "deviceLocationDesc", "faultDescriptionDesc", "workOrderStatusDesc", "overdueDesc", "repairUserDesc", "examineUserDesc"};

        ExcelUtils.createAndDownloadExcel(resp, list, keys, ConstantsDevice.FAULT_REPAIR_RECORD_MODEL_FILE_PATH, 3, null, jsonObject, "1:0");
    }

    /**
     * 功能描述: 报表GB25201--B1导出方法
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020-04-09
     */
    @Override
    public void downWordB1(String json, HttpServletResponse response) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        Date createTime = faultRepairRecord.getCreateTime();
        Date starDate = DateUtils.monthStarDate(createTime);
        Date endDate = DateUtils.monthEndDate(createTime);

        faultRepairRecord.setCreateTime(starDate);
        faultRepairRecord.setLastModifyTime(endDate);

        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecordDao.findFaultRepairRecordList(faultRepairRecord);

        // 从缓存中取
        Long projectId = faultRepairRecord.getProjectId();
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        if (StringUtils.isEmpty(projectName)) {
            projectName = "";
        }
        // 导出的文件名 及 文件格式 .doc
        String fileName = projectName + "_建筑消防设施故障维修记录表.doc";

        Map<String, Object> map = new HashMap<>(3);
        map.put("list", faultRepairRecordList);
        map.put("projectName", projectName);
        map.put("item", DateUtils.getChangeDate(starDate) + "--" + DateUtils.getChangeDate(endDate));

        downModels(response, fileName, map, projectName, configuration, "GB25201B1.xml");
    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileName    导出的文件名+文件后缀
     * @param map         数据
     * @param projectName 项目名称
     * @param modelName   模板名
     * @author huanggc
     * @date 2019/11/04
     */
    public static void downModel(HttpServletResponse response, String fileName, Map<String, Object> map, String projectName, Configuration configuration,
                                 String modelName) {
        // String fileUrl = System.getProperty("user.dir") + ConstantsDeviceMsg.REPORT_FILE_URL;
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            OutputStream out = response.getOutputStream();
            oWriter = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板 "GB25201B1.xml"
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
        //docToPdf(fileUrl + fileName, fileUrl + "123.pdf");
        //PdfUtils.docToPdf(fileUrl + fileName, fileUrl + "123.pdf");
    }

    /**
     * 验证License 若不验证则转化出的pdf文档会有水印产生
     *
     * @return boolean
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            ClassLoader classLoader = FaultRepairRecordServiceImpl.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 功能描述: word文档 转成 pdf
     *
     * @param wordPath 需要被转换的word全路径带文件名   例: D:/logs/Java多线程大合集.doc
     * @param pdfPath  转换之后pdf的全路径带文件名  例: D:/logs/123.pdf
     * @author huanggc
     * @date 2020/04/10
     */
    public static void docToPdf(String wordPath, String pdfPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!getLicense()) {
            return;
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
    }

    /**
     * 功能描述: 导出 word 公共方法抽取
     *
     * @param fileName    导出的文件名+文件后缀
     * @param map         数据
     * @param projectName 项目名称
     * @param modelName   模板名
     * @author huanggc
     * @date 2020-04-20
     */
    private static void downModels(HttpServletResponse response, String fileName, Map<String, Object> map, String projectName, Configuration configuration,
                                   String modelName) {
        OutputStream out = null;
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;

        try {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            out = response.getOutputStream();
            oWriter = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板 "GB25201B1.xml"
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
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能描述: 故障报修 删除功能
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/04
     */
    @Override
    public Data deletedFaultRepairRecord(String json) {
        JSONObject jsonValue = JSON.parseObject(json);
        String companyId = jsonValue.get("ids").toString();
        String[] str = companyId.split(";");

        List<Long> idList = new ArrayList<>(str.length);
        for (String string : str) {
            idList.add(Long.parseLong(string));
        }

        int num = faultRepairRecordDao.deletedFaultRepairRecord(idList);
        if (num > 0) {
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 功能描述: 点击"工单号"查看报修进度(详情)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/09
     */
    @Override
    public Data findFaultProgress(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        Boolean permissionAll = faultRepairRecord.getPermissionAll();
        faultRepairRecord.setPermissionAll(null);

        faultRepairRecord = faultRepairRecordDao.findFaultRepairRecordOne(faultRepairRecord);
        if (StringUtils.isNotEmpty(faultRepairRecord.getImageUrl())) {
            // 现场图片
            String[] split = faultRepairRecord.getImageUrl().split(";");
            faultRepairRecord.setImageUrls(split);
        }

        if (StringUtils.isNotEmpty(faultRepairRecord.getAfterImageUrl())) {
            // 处理后图片
            String[] split = faultRepairRecord.getAfterImageUrl().split(";");
            faultRepairRecord.setAfterImageUrls(split);
        }

        Long id = faultRepairRecord.getId();
        Long projectId = faultRepairRecord.getProjectId();
        // 根据 "故障报修"ID 查询 报修进度
        RepairProgress repairProgress = new RepairProgress();
        repairProgress.setFaultRepairRecordId(id);

        List<RepairProgress> repairProgressList = repairProgressService.findRepairProgressLists(repairProgress);

        Integer workOrderStatus = faultRepairRecord.getWorkOrderStatus();
        // 工单状态:  所有待办(1待指派,  2维修中,  3审核中); 所有完成: 4已完成  5不予处理
        if (workOrderStatus == 2 || workOrderStatus == 3) {
            // 没有 faultrepair_manage_edit 权限
            if (null != permissionAll && !permissionAll) {
                String userName = SecurityUserHolder.getUserName();
                Long userId = getLoginUserId(userName);

                OrgUser orgUser = new OrgUser();
                orgUser.setUserId(userId);
                orgUser.setProjectId(faultRepairRecord.getProjectId());
                orgUser = orgUserDao.getOrgUser(orgUser);

                if (null != orgUser) {
                    Long orgUserId = orgUser.getId();

                    FaultHandler faultHandler = new FaultHandler();
                    faultHandler.setFaultRepairRecordId(faultRepairRecord.getId());
                    if (workOrderStatus == 2) {
                        faultHandler.setRepairUserId(orgUserId);
                    } else {
                        faultHandler.setExamineUserId(orgUserId);
                    }
                    Integer faultHandlerNum = faultHandlerDao.findFaultHandlerNum(faultHandler);
                    if (faultHandlerNum > 0) {
                        faultRepairRecord.setFaultHandlerNum(faultHandlerNum);
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<>(2);
        // 故障报修
        map.put("faultRepairRecord", faultRepairRecord);
        // 报修进度
        map.put("repairProgressList", repairProgressList);
        return asseData(map);
    }

    /**
     * 微信公众号推送通知
     *
     * @param faultRepairRecord 故障报修
     * @param value             值
     * @author zhangZaiFa
     * @date 2019/12/6 16:00
     **/
    private void weChatSendMessage(FaultRepairRecord faultRepairRecord, String value) {
        JSONObject first = new JSONObject(2);
        first.put("value", value);
        first.put("color", "#000000");
        JSONObject jsonObject = new JSONObject(4);
        jsonObject.put("first", first);

        JSONObject event = new JSONObject(2);
        event.put("value", faultRepairRecord.getFaultDescription());
        event.put("color", "#000000");
        jsonObject.put("event", event);

        JSONObject finishTime = new JSONObject(2);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        finishTime.put("value", dateString);
        finishTime.put("color", "#000000");
        jsonObject.put("finish_time", finishTime);

        JSONObject remark = new JSONObject(2);
        remark.put("value", "关注身边的消防安全，从你我做起！");
        remark.put("color", "#000000");
        jsonObject.put("remark", remark);

        SysLog.info(faultRepairRecord.getOpenId() + "-------->报修人微信OPENID");
        SysLog.info(ConstantsDevice.WE_CHAT_TEMPLATE_ID + "-------->微信模板ID");
        SysLog.info(jsonObject.toJSONString() + "-------->信息参数");
        try {
            String result = WeChatUtils.weChatSendMessage(faultRepairRecord.getOpenId(), ConstantsDevice.WE_CHAT_TEMPLATE_ID, null, jsonObject);
            SysLog.info(result + "-------->微信返回结果");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 报修工单进度更新
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/09
     */
    @Override
    public Data updateFaultProgress(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        Long projectId = faultRepairRecord.getProjectId();
        Project project = projectDao.findById(projectId);
        String projectName = project.getProjectName();
        // 批量操作时id为 null
        Long id = faultRepairRecord.getId();
        FaultRepairRecord idEntity = new FaultRepairRecord();
        if (null != id) {
            idEntity.setId(id);
            idEntity = faultRepairRecordDao.findFaultRepairRecordOne(idEntity);
        }

        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);

        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(userId);
        orgUser.setProjectId(projectId);
        orgUser = orgUserDao.getOrgUser(orgUser);
        if (null != orgUser) {
            // 项目内成员名称
            String orgUserName = orgUser.getUserName();
            if (StringUtils.isNotEmpty(orgUserName)) {
                userName = orgUserName;
            }
        }

        SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd");
        RepairProgress repairProgress = new RepairProgress();
        repairProgress.setCreateUserId(userId);

        Integer buttonType = faultRepairRecord.getButtonType();
        // 不予处理(按钮)
        if (buttonType == 1) {
            faultRepairRecord.setWorkOrderStatus(5);
            faultRepairRecord.setExamineUser(userName);

            if (idEntity != null && idEntity.getFromType() != null && idEntity.getFromType() == 3 && idEntity.getOpenId() != null) {
                faultRepairRecord.setOpenId(idEntity.getOpenId());
                faultRepairRecord.setFaultDescription(idEntity.getFaultDescription());
                weChatSendMessage(faultRepairRecord, "您好，经审核您申报的故障/隐患类型不存在。");
            }
            repairProgress.setTitle("不予处理");

            StringBuilder sb = new StringBuilder();
            sb.append("审核人:");
            sb.append(userName);
            sb.append(";  备注:");
            sb.append(faultRepairRecord.getAuditOpinion());
            repairProgress.setContent(sb.toString());
        } else if (buttonType == 2) {
            // 待审核:通过(按钮)
            // 4已完成
            faultRepairRecord.setWorkOrderStatus(4);
            faultRepairRecord.setExamineUser(userName);

            repairProgress.setTitle("已修复");

            // 微信消息
            if (idEntity != null && idEntity.getFromType() != null && idEntity.getFromType() == 3 && idEntity.getOpenId() != null) {
                faultRepairRecord.setOpenId(idEntity.getOpenId());
                faultRepairRecord.setFaultDescription(idEntity.getFaultDescription());
                weChatSendMessage(faultRepairRecord, "您好，您申报的故障/隐患已处理完毕，感谢支持！");
            }

            StringBuilder sb = new StringBuilder();
            sb.append("审核人:").append(userName).append(";  审核意见:").append(faultRepairRecord.getAuditOpinion());
            if (1 == faultRepairRecord.getRunSystem()) {
                sb.append(";  是否恢复系统运行: 是");
            } else {
                sb.append(";  是否恢复系统运行: 否");
            }

            if (1 == faultRepairRecord.getExcludeFault()) {
                sb.append(";  是否排除故障: 是");
            } else {
                sb.append(";  是否排除故障: 否");
            }
            repairProgress.setContent(sb.toString());

            JSONObject jsonObject = getJsonObject(projectName, idEntity);

            if (faultRepairRecord.getIds() != null) {
                // 批量操作
                updateFaultList(faultRepairRecord);
            } else {
                // 审核:通过消息
                FaultHandler faultHandler = new FaultHandler();
                faultHandler.setFaultRepairRecordId(id);
                // 旧数据(维修人,审核人 有空)
                String repairUserIdString = faultHandlerDao.findRepairUserIdString(faultHandler);
                if (StringUtils.isNotEmpty(repairUserIdString)) {
                    Long[] orgUserIdArray = ConvertUtils.stringToLongArray(repairUserIdString);

                    OrgUser data = new OrgUser();
                    data.setIds(orgUserIdArray);
                    JSONObject orgUserObj = HttpUtils.httpGetToken(ConstantsDevice.ORG_USER_URL + projectId, JSONObject.toJSONString(data), "json",
                            SecurityUserHolder.getToken());

                    SysLog.info("------------>获取orgUserObj:");
                    JSONArray listObj = orgUserObj.getJSONArray("listObj");
                    if (null != listObj) {
                        List<Long> userList = new ArrayList<>(listObj.size());
                        for (int i = 0; i < listObj.size(); i++) {
                            JSONObject obj = listObj.getJSONObject(i);
                            userList.add(obj.getLong("userId"));
                        }

                        StringBuilder sbu = new StringBuilder();
                        sbu.append("【");
                        sbu.append(projectName);
                        sbu.append("】 ");
                        sbu.append(projectName);
                        sbu.append("/");
                        sbu.append(idEntity.getBuildingName());
                        sbu.append("/");
                        sbu.append(idEntity.getFloorName());
                        sbu.append(" ");
                        sbu.append(idEntity.getDeviceLocation());
                        sbu.append("  ");
                        sbu.append(idEntity.getCheckPointName());
                        sbu.append("  ");
                        sbu.append(idEntity.getCheckPointQrNo());
                        sbu.append("  ");
                        sbu.append(idEntity.getDeviceName());
                        sbu.append("  ");
                        sbu.append(idEntity.getDeviceQrNo());
                        sbu.append(" 故障维修任务已通过审核，请前往【故障报修】查看详情。");
                        // 给 维修人 发消息
                        messageService.saveMessageUser(4, userList, "审核结果", 2, 0, sbu.toString(),
                                "url", projectId, id, "data", jsonObject);
                    }
                }

                Integer runSystem = faultRepairRecord.getRunSystem();
                if (null != runSystem && runSystem == 1) {
                    List<String> roleSignList = new ArrayList<>(1);
                    // 编辑权限
                    roleSignList.add("faultrepair_manage_edit");

                    StringBuilder str = new StringBuilder();
                    str.append("【");
                    str.append(projectName);
                    str.append("】  ");
                    str.append(projectName);
                    str.append("/");
                    str.append(idEntity.getBuildingName());
                    str.append("/");
                    str.append(idEntity.getFloorName());
                    str.append("  ");
                    str.append(idEntity.getDeviceLocation());
                    str.append("  ");
                    str.append(idEntity.getCheckPointName());
                    str.append("  ");
                    str.append(idEntity.getCheckPointQrNo());
                    str.append("  ");
                    str.append(idEntity.getDeviceName());
                    str.append(" ");
                    str.append(idEntity.getDeviceQrNo());
                    str.append(" 因");
                    str.append(idEntity.getFaultDescriptionDesc());
                    str.append("故障，现已启用系统。");

                    messageService.saveMessageRole(4, roleSignList, "启用系统", 8, 1, str.toString(), "url", projectId,
                            null, "data", jsonObject);
                }
            }
        } else if (buttonType == 3) {
            // 发布任务(按钮)  指派
            // 2维修中
            faultRepairRecord.setWorkOrderStatus(2);

            repairProgress.setTitle("已指派");

            StringBuilder sb = new StringBuilder();
            sb.append("维修人:").append(faultRepairRecord.getRepairUser());
            sb.append(";  审核人:").append(faultRepairRecord.getExamineUser());
            sb.append(";  处理时间:").append(sdfLong.format(faultRepairRecord.getStartHandlingTime()));
            sb.append(" -- ").append(sdfLong.format(faultRepairRecord.getEndHandlingTime()));
            sb.append(";  工单描述:").append(faultRepairRecord.getWorkOrderDescription());
            Integer isStopSystem = faultRepairRecord.getIsStopSystem();
            if (null != isStopSystem && isStopSystem == 1) {
                sb.append(";  是否停用系统:是");
                sb.append(";  停用时间是:").append(sdfLong.format(faultRepairRecord.getStopSystemDate()));
            } else {
                sb.append(";  是否停用系统:否");
            }

            repairProgress.setContent(sb.toString());
            // 批量操作
            if (faultRepairRecord.getIds() != null) {
                updateFaultList(faultRepairRecord);
            } else {
                // 维修人
                Long[] repairUserIds = faultRepairRecord.getRepairUserIds();
                Long faultRepairRecordId = faultRepairRecord.getId();

                List<FaultHandler> faultHandlerList = new ArrayList<>(repairUserIds.length);
                for (Long i : repairUserIds) {
                    FaultHandler entity = new FaultHandler();
                    entity.setFaultRepairRecordId(faultRepairRecordId);
                    entity.setRepairUserId(i);
                    faultHandlerList.add(entity);
                }

                // 先删除
                FaultHandler faultEntity = new FaultHandler();
                faultEntity.setFaultRepairRecordId(faultRepairRecordId);
                faultEntity.setRepairUserId(-1L);
                faultHandlerDao.deletedFaultHandler(faultEntity);

                faultHandlerDao.saveFaultHandlers(faultHandlerList);

                // 审核人
                Long[] examineUsers = faultRepairRecord.getExamineUsers();
                List<FaultHandler> faultHandlerLists = new ArrayList<>(examineUsers.length);
                for (Long i : examineUsers) {
                    FaultHandler faultHandlers = new FaultHandler();
                    faultHandlers.setFaultRepairRecordId(faultRepairRecordId);
                    faultHandlers.setExamineUserId(i);
                    faultHandlerLists.add(faultHandlers);
                }

                // 先删除
                FaultHandler faultEntitys = new FaultHandler();
                faultEntitys.setFaultRepairRecordId(faultRepairRecordId);
                faultEntitys.setExamineUserId(-1L);
                faultHandlerDao.deletedFaultHandler(faultEntitys);

                faultHandlerDao.saveFaultHandlers(faultHandlerLists);

                // [项目] 项目/建筑物/位置+设备/巡查点名称+巡查点ID +设备名称+设备ID 因***********故障，请前往查看。
                StringBuilder sbu = new StringBuilder();
                sbu.append("【");
                sbu.append(projectName);
                sbu.append("】 ");
                sbu.append(projectName).append("/");
                sbu.append(idEntity.getBuildingName());
                sbu.append("/");
                sbu.append(idEntity.getFloorName());
                sbu.append(" ");
                sbu.append(idEntity.getDeviceLocation());
                sbu.append(" ");
                sbu.append(idEntity.getCheckPointName());
                sbu.append("  ");
                sbu.append(idEntity.getCheckPointQrNo());
                sbu.append("  ");
                sbu.append(idEntity.getDeviceName());
                sbu.append("  ");
                sbu.append(idEntity.getDeviceQrNo());
                sbu.append("  ");
                sbu.append(idEntity.getWorkOrderDescription());

                Long[] repairUserId = faultRepairRecord.getRepairUserId();
                List<Long> userIds = new ArrayList<>(repairUserId.length);
                Collections.addAll(userIds, repairUserId);
                SysLog.info("------------------------>维修人：" + userIds);

                JSONObject jsonObject = getJsonObject(projectName, idEntity);

                // 给 维修人 发消息
                messageService.saveMessageUser(4, userIds, "维修通知", 2, 0, sbu.toString()
                        + "故障待维修,请前往 [故障报修] 完成任务。", "url", projectId, id, "data", jsonObject);

                if (null != isStopSystem && isStopSystem == 1) {
                    // 发消息给 项目 下管理权限的人
                    List<String> roleSignList = new ArrayList<>(1);
                    // 编辑权限
                    roleSignList.add("faultrepair_manage_edit");
                    messageService.saveMessageRole(4, roleSignList, "停用系统", 0, 0, sbu.toString() + ",现已停用系统。",
                            "url", projectId, id, "data", jsonObject);
                }
            }
        } else if (buttonType == 4) {
            // 维修中:提交(按钮)
            // 3审核中
            faultRepairRecord.setWorkOrderStatus(3);
            // 更新维修人
            faultRepairRecord.setRepairUser(userName);

            repairProgress.setTitle("已处理");

            StringBuilder sb = new StringBuilder();
            sb.append("维修方法:").append(faultRepairRecord.getMaintenanceMethod());
            if (StringUtils.isNotEmpty(faultRepairRecord.getMaintenanceResult())) {
                sb.append(";  故障维修描述:").append(faultRepairRecord.getMaintenanceResult());
            }
            if (StringUtils.isNotEmpty(faultRepairRecord.getSecurityMeasures())) {
                sb.append(";  安全保护措施:").append(faultRepairRecord.getSecurityMeasures());
            }
            sb.append(";  处理人:").append(userName);
            repairProgress.setContent(sb.toString());

            /*
             * 维修提交后给审核人发消息
             * 1:通过"故障报修ID"查找出"审核人ID"
             * 2:通过"审核人ID"找到 用户的ID
             * 3:给用户发消息
             */
            FaultHandler faultHandler = new FaultHandler();
            // 批量操作
            if (faultRepairRecord.getIds() != null) {
                faultHandler.setFaultRepairRecordIds(faultRepairRecord.getIds());
            } else {
                faultHandler.setFaultRepairRecordId(id);
            }
            String repairUserIdString = faultHandlerDao.findExamineUserIdString(faultHandler);
            if (StringUtils.isNotEmpty(repairUserIdString)) {
                Long[] orgUserIdArray = ConvertUtils.stringToLongArray(repairUserIdString);

                OrgUser data = new OrgUser();
                data.setIds(orgUserIdArray);
                JSONObject orgUserObj = HttpUtils.httpGetToken(ConstantsDevice.ORG_USER_URL + projectId, JSONObject.toJSONString(data), "json",
                        SecurityUserHolder.getToken());
                SysLog.info("------------>获取orgUserObj:");
                JSONArray listObj = orgUserObj.getJSONArray("listObj");
                if (null != listObj) {
                    List<Long> userList = new ArrayList<>(listObj.size());
                    for (int i = 0; i < listObj.size(); i++) {
                        JSONObject obj = listObj.getJSONObject(i);
                        userList.add(obj.getLong("userId"));
                    }

                    StringBuilder sbu = new StringBuilder();
                    sbu.append("【");
                    sbu.append(projectName);
                    sbu.append("】  ");
                    sbu.append(projectName);
                    sbu.append("/");
                    sbu.append(idEntity.getBuildingName());
                    sbu.append("/");
                    sbu.append(idEntity.getFloorName());
                    sbu.append(" ");
                    sbu.append(idEntity.getDeviceLocation());
                    sbu.append("  ");
                    sbu.append(idEntity.getCheckPointName());
                    sbu.append("  ");
                    sbu.append(idEntity.getCheckPointQrNo());
                    sbu.append("  ");
                    sbu.append(idEntity.getDeviceName());
                    sbu.append("  ");
                    sbu.append(idEntity.getDeviceQrNo());
                    sbu.append(" 因");
                    sbu.append(idEntity.getFaultDescriptionDesc());
                    sbu.append("故障已修复,请前往【故障报修】进行审核。");

                    JSONObject jsonObject = getJsonObject(projectName, idEntity);
                    messageService.saveMessageUser(4, userList, "维修审核", 3, 1, sbu.toString(), "url", projectId,
                            idEntity.getId(),"data", jsonObject);
                }
            }
        } else if (buttonType == 5) {
            // 驳回(按钮)
            // 2维修中
            faultRepairRecord.setWorkOrderStatus(2);
            // 更新审核人
            faultRepairRecord.setExamineUser(userName);

            repairProgress.setTitle("已驳回");

            StringBuilder sb = new StringBuilder();
            sb.append("审核人:").append(userName);
            sb.append(";  审核意见:").append(faultRepairRecord.getAuditOpinion());
            if (1 == faultRepairRecord.getRunSystem()) {
                sb.append(";  是否恢复系统运行: 是");
            } else {
                sb.append(";  是否恢复系统运行: 否");
            }

            if (1 == faultRepairRecord.getExcludeFault()) {
                sb.append(";  是否排除故障: 是");
            } else {
                sb.append(";  是否排除故障: 否");
            }

            sb.append(";  维修人:");
            sb.append(faultRepairRecord.getRepairUser());
            sb.append(";  处理时间:");
            sb.append(sdfLong.format(faultRepairRecord.getStartHandlingTime()));
            sb.append("--");
            sb.append(sdfLong.format(faultRepairRecord.getEndHandlingTime()));
            repairProgress.setContent(sb.toString());
            // 批量操作
            if (faultRepairRecord.getIds() != null) {
                updateFaultList(faultRepairRecord);
            } else {
                // 维修人
                Long[] repairUserIds = faultRepairRecord.getRepairUserIds();
                Long faultRepairRecordId = faultRepairRecord.getId();

                List<FaultHandler> faultHandlerList = new ArrayList<>(repairUserIds.length);
                for (Long iL : repairUserIds) {
                    FaultHandler entity = new FaultHandler();
                    entity.setFaultRepairRecordId(faultRepairRecordId);
                    entity.setRepairUserId(iL);
                    faultHandlerList.add(entity);
                }

                // 先删除
                FaultHandler faultEntity = new FaultHandler();
                faultEntity.setFaultRepairRecordId(faultRepairRecordId);
                faultEntity.setRepairUserId(-1L);
                faultHandlerDao.deletedFaultHandler(faultEntity);

                faultHandlerDao.saveFaultHandlers(faultHandlerList);

                StringBuilder sbu = new StringBuilder();
                sbu.append("【").append(projectName).append("】 ").append(projectName);
                sbu.append("/").append(idEntity.getBuildingName()).append("/").append(idEntity.getFloorName()).append(" ").append(idEntity.getDeviceLocation());
                sbu.append("  ").append(idEntity.getCheckPointName()).append("  ").append(idEntity.getCheckPointQrNo()).append("  ");
                sbu.append(idEntity.getDeviceName()).append("  ").append(idEntity.getDeviceQrNo());

                Long[] repairUserId = faultRepairRecord.getRepairUserId();
                List<Long> userIds = new ArrayList<>(repairUserId.length);
                Collections.addAll(userIds, repairUserId);

                JSONObject jsonObject = getJsonObject(projectName, idEntity);

                // 维修通知: [项目名称] 项目名称/建筑物/楼层+位置+设备名称+设备码+故障原因+故障待维修,请前往 [故障报修] 完成任务
                // 给 维修人 发消息
                messageService.saveMessageUser(4, userIds, "维修通知", 2, 0, sbu.toString()
                        + " 故障待维修,请前往【故障报修】完成任务。", "url", projectId, id, "data", jsonObject);

                // 驳回消息
                FaultHandler faultHandler = new FaultHandler();
                faultHandler.setFaultRepairRecordId(id);
                String repairUserIdString = faultHandlerDao.findRepairUserIdString(faultHandler);
                if (StringUtils.isNotEmpty(repairUserIdString)) {
                    Long[] orgUserIdArray = ConvertUtils.stringToLongArray(repairUserIdString);

                    OrgUser data = new OrgUser();
                    data.setIds(orgUserIdArray);
                    JSONObject orgUserObj = HttpUtils.httpGetToken(ConstantsDevice.ORG_USER_URL + projectId, JSONObject.toJSONString(data), "json",
                            SecurityUserHolder.getToken());
                    SysLog.info("------------>获取orgUserObj:");
                    JSONArray listObj = orgUserObj.getJSONArray("listObj");
                    if (null != listObj) {
                        List<Long> userList = new ArrayList<>(listObj.size());
                        for (int i = 0; i < listObj.size(); i++) {
                            JSONObject obj = listObj.getJSONObject(i);
                            userList.add(obj.getLong("userId"));
                        }
                        // 给 维修人 发消息
                        messageService.saveMessageUser(4, userList, "审核结果", 2, 0, sbu.toString()
                                + " 故障维修任务已被驳回，请前往【故障报修】查看详情。", "url", projectId, id, "data", null);
                    }
                }

                Integer runSystem = faultRepairRecord.getRunSystem();
                if (null != runSystem && runSystem == 1) {
                    List<String> roleSignList = new ArrayList<>();
                    // 编辑权限
                    roleSignList.add("faultrepair_manage_edit");

                    StringBuilder str = new StringBuilder();
                    str.append("【");
                    str.append(projectName);
                    str.append("】 ");
                    str.append(projectName);
                    str.append("/");
                    str.append(idEntity.getBuildingName());
                    str.append("/");
                    str.append(idEntity.getFloorName());
                    str.append("  ");
                    str.append(idEntity.getDeviceLocation());
                    str.append("  ");
                    str.append(idEntity.getCheckPointName());
                    str.append("  ");
                    str.append(idEntity.getCheckPointQrNo());
                    str.append("  ");
                    str.append(idEntity.getDeviceName());
                    str.append(" ");
                    str.append(idEntity.getDeviceQrNo());
                    str.append(" 因");
                    str.append(idEntity.getFaultDescriptionDesc());
                    str.append("故障，现已启用系统。");

                    messageService.saveMessageRole(4, roleSignList, "启用系统", 8, 1, str.toString(), "url", projectId,
                            null, "data", jsonObject);
                }
            }
        }

        faultRepairRecord.setUpdateUserId(userId);
        faultRepairRecord.setUpdateUserName(userName);
        faultRepairRecordDao.updateFaultRepairRecord(faultRepairRecord);

        Long[] ids = faultRepairRecord.getIds();
        if (ids != null && ids.length > 0) {
            List<RepairProgress> repairProgressList = new ArrayList<>(ids.length);
            String title = repairProgress.getTitle();
            String content = repairProgress.getContent();

            for (Long i : ids) {
                RepairProgress progress = new RepairProgress();
                progress.setTitle(title);
                progress.setContent(content);
                progress.setFaultRepairRecordId(i);

                repairProgressList.add(progress);
            }
            repairProgressDao.saverRepairProgressList(repairProgressList);
        } else {
            repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());
            repairProgressService.saverRepairProgress(repairProgress);
        }

        if (buttonType == 2){
            if (id != null){
                Long[] longs = new Long[1];
                longs[0] = id;
                faultRepairRecord.setIds(longs);
            }
            faultRepairRecordDao.updateCheckRecord(faultRepairRecord);
            SysLog.info("故障报修更新巡检记录--2已恢复ids=" + faultRepairRecord.toString());
        }

        // 保修进度
        List<RepairProgress> repairProgressList = repairProgressService.findRepairProgressLists(repairProgress);

        Map<String, Object> map = new HashMap<>(2);
        map.put("faultRepairRecord", faultRepairRecord);
        map.put("repairProgressList", repairProgressList);
        return asseData(map);
    }

    /**
     * 功能描述: APP保存继续维修(临时保存)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-03-27
     */
    @Override
    public Data updateFaultRepair(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);

        RepairProgress repairProgress = new RepairProgress();
        repairProgress.setTitle("保存继续维修");
        repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());

        StringBuilder sb = new StringBuilder();
        sb.append("维修方法:");
        sb.append(faultRepairRecord.getMaintenanceMethod());

        if (StringUtils.isNotEmpty(faultRepairRecord.getSecurityMeasures())) {
            sb.append(";  安全保护措施:");
            sb.append(faultRepairRecord.getSecurityMeasures());
        }

        if (StringUtils.isNotEmpty(faultRepairRecord.getMaintenanceResult())) {
            sb.append(";  故障维修描述:");
            sb.append(faultRepairRecord.getMaintenanceResult());
        }
        repairProgress.setContent(sb.toString());
        repairProgressDao.saverRepairProgress(repairProgress);

        faultRepairRecordDao.updateFaultRepairRecord(faultRepairRecord);
        return asseData(faultRepairRecord);
    }

    /**
     * 功能描述: 新建故障报修(APP)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/12
     */
    @Override
    public Data saveFaultRepairRecord(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecord.getFaultRepairRecordList();

        Long projectId = faultRepairRecord.getProjectId();
        Project project = projectDao.findById(projectId);
        // 批量新增
        if (CollectionUtils.isNotEmpty(faultRepairRecordList)) {
            FaultRepairRecord record = faultRepairRecordList.get(0);

            String userName = "";
            Long userId;
            Integer fromType = record.getFromType();
            if (fromType == 1) {
                userName = SecurityUserHolder.getUserName();
                userId = getLoginUserId(userName);
            } else {
                userId = record.getCreateUserId();
            }
            OrgUser orgUser = new OrgUser();
            orgUser.setUserId(userId);
            orgUser.setProjectId(record.getProjectId());
            orgUser = orgUserDao.getOrgUser(orgUser);

            if (null != orgUser) {
                String orgUserName = orgUser.getUserName();
                if (StringUtils.isNotEmpty(orgUserName)) {
                    userName = orgUserName;
                }
            }

            // APP报修管理
            if (record.getFromType() == 1) {
                String buildingName = faultRepairRecord.getBuildingName();
                String floorName = faultRepairRecord.getFloorName();
                Long checkPointId = faultRepairRecord.getCheckPointId();
                String checkPointQrNo = faultRepairRecord.getCheckPointQrNo();
                String checkPointName = faultRepairRecord.getCheckPointName();
                String deviceLocation = faultRepairRecord.getDeviceLocation();

                for (FaultRepairRecord entity : faultRepairRecordList) {
                    entity.setBuildingName(buildingName);
                    entity.setFloorName(floorName);
                    entity.setCheckPointId(checkPointId);
                    entity.setCheckPointQrNo(checkPointQrNo);
                    entity.setCheckPointName(checkPointName);
                    entity.setDeviceLocation(deviceLocation);
                    // 设置 工单号
                    entity.setWorkOrderNumber(createNo(projectId));
                    entity.setCreateUserName(userName);
                    entity.setCreateUserId(userId);
                }
            } else {
                for (FaultRepairRecord entity : faultRepairRecordList) {
                    entity.setWorkOrderNumber(createNo(projectId));
                    if (fromType != 3) {
                        entity.setCreateUserName(userName);
                        entity.setCreateUserId(userId);
                    }
                }
            }

            faultRepairRecordDao.saveFaultRepairRecordList(faultRepairRecordList);

            // "[项目名称] 项目名称/建筑物/楼层+位置+工单号......,新增故障报修"
            String projectName = project.getProjectName();

            List<String> roleSignList = new ArrayList<>(1);
            // 故障报修编辑权限
            roleSignList.add("faultrepair_manage_edit");
            for (FaultRepairRecord frr : faultRepairRecordList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("buildingId", frr.getBuildingId());

                StringBuilder str = new StringBuilder();
                str.append("【");
                str.append(projectName);
                str.append("】 ");
                str.append(projectName);
                str.append("/");
                str.append(frr.getBuildingName());
                str.append("/");
                str.append(frr.getFloorName());
                str.append("  ");
                str.append(frr.getDeviceLocation());
                str.append("  ");
                str.append(frr.getCheckPointName());
                str.append("  ");
                str.append(frr.getCheckPointQrNo());
                str.append("  ");
                str.append(frr.getDeviceName());
                str.append(" ");
                str.append(frr.getDeviceQrNo());
                str.append(" 因");
                str.append(frr.getFaultDescriptionDesc());
                str.append("故障待维修,请前往【故障报修】完成任务。");

                messageService.saveMessageRole(4, roleSignList, "报修通知", 7, 1, str.toString(),
                        "url", projectId, frr.getId(), "data", jsonObject);
            }

            List<RepairProgress> repairProgressList = new ArrayList<>(faultRepairRecordList.size());
            RepairProgress repairProgress = new RepairProgress();
            repairProgress.setTitle("故障发起");
            repairProgress.setCreateUserId(userId);
            for (FaultRepairRecord repairRecord : faultRepairRecordList) {
                repairProgress.setContent("报修人:" + userName);
                repairProgress.setFaultRepairRecordId(repairRecord.getId());
                repairProgressList.add(repairProgress);
            }

            repairProgressDao.saverRepairProgressList(repairProgressList);
        } else {
            faultRepairRecord.setWorkOrderNumber(createNo(projectId));

            RepairProgress repairProgress = new RepairProgress();
            // fromType=3 微信端没有用户ID和用户名
            if (faultRepairRecord.getFromType() != 3) {
                String userName = SecurityUserHolder.getUserName();
                Long userId = getLoginUserId(userName);
                ;

                OrgUser orgUser = new OrgUser();
                orgUser.setUserId(userId);
                orgUser.setProjectId(faultRepairRecord.getProjectId());
                orgUser = orgUserDao.getOrgUser(orgUser);

                String orgUserName = orgUser.getUserName();
                if (StringUtils.isNotEmpty(orgUserName)) {
                    userName = orgUserName;
                }
                faultRepairRecord.setCreateUserName(userName);
                faultRepairRecord.setCreateUserId(userId);

                repairProgress.setCreateUserId(userId);
                repairProgress.setCreateUserName(userName);
                repairProgress.setTitle("故障发起");
                repairProgress.setContent("报修人:" + userName);
            } else if (faultRepairRecord.getFromType() == 3) {
                repairProgress.setTitle("故障发起");
                repairProgress.setContent("来自微信:" + (faultRepairRecord.getCreateUserName() == null ? "/" : faultRepairRecord.getCreateUserName()));
            }
            faultRepairRecordDao.saveFaultRepairRecord(faultRepairRecord);

            FaultRepairRecord entity = new FaultRepairRecord();
            entity.setId(faultRepairRecord.getId());
            FaultRepairRecord repairRecord = faultRepairRecordDao.findFaultRepairRecordOne(entity);

            // "[项目名称] 项目名称/建筑物/楼层+位置+工单号......,新增故障报修"
            String projectName = project.getProjectName();
            StringBuilder str = new StringBuilder();
            str.append("【").append(projectName).append("】 ").append(projectName).append("/");
            str.append(repairRecord.getBuildingName()).append("/").append(repairRecord.getFloorName()).append(" ").append(repairRecord.getDeviceLocation()).append("  ");
            str.append(repairRecord.getCheckPointName()).append("  ").append(repairRecord.getCheckPointQrNo()).append("  ").append(repairRecord.getDeviceName()).append("  ");
            str.append(repairRecord.getDeviceQrNo()).append(" 因").append(repairRecord.getFaultDescriptionDesc()).append("故障待维修,请前往【故障报修】完成任务。");

            List<String> roleSignList = new ArrayList<>(1);
            // 编辑权限
            roleSignList.add("faultrepair_manage_edit");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("buildingId", faultRepairRecord.getBuildingId());
            SysLog.info("-------->新增故障报修");
            messageService.saveMessageRole(4, roleSignList, "报修通知", 7, 1, str.toString(), "url", projectId,
                    faultRepairRecord.getId(), "data", jsonObject);

            if (faultRepairRecord.getFromType() != 3) {
                repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());
                repairProgressDao.saverRepairProgress(repairProgress);
            }
        }

        return asseData(faultRepairRecord);
    }

    /**
     * 功能描述: 设备报修(巡检记录)--新建故障报修(APP)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/12
     */
    @Override
    public Data saveFaultRepairRecords(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecord.getFaultRepairRecordList();

        Long projectId = faultRepairRecord.getProjectId();
        Project project = projectDao.findById(projectId);
        // 批量新增
        if (CollectionUtils.isNotEmpty(faultRepairRecordList)) {
            FaultRepairRecord record = faultRepairRecordList.get(0);

            String userName = "";
            Long userId = 0L;
            Integer fromType = record.getFromType();
            if (fromType == 1) {
                userName = SecurityUserHolder.getUserName();
                if (StringUtils.isEmpty(userName)) {
                    userName = "";
                } else {
                    userId = getLoginUserId(userName);
                }
            } else {
                userId = record.getCreateUserId();
            }
            OrgUser orgUser = new OrgUser();
            orgUser.setUserId(userId);
            orgUser.setProjectId(record.getProjectId());
            orgUser = orgUserDao.getOrgUser(orgUser);

            if (null != orgUser) {
                String orgUserName = orgUser.getUserName();
                if (StringUtils.isNotEmpty(orgUserName)) {
                    userName = orgUserName;
                }
            }
            // APP报修管理
            if (record.getFromType() == 1) {
                String buildingName = faultRepairRecord.getBuildingName();
                String floorName = faultRepairRecord.getFloorName();
                Long checkPointId = faultRepairRecord.getCheckPointId();
                String checkPointQrNo = faultRepairRecord.getCheckPointQrNo();
                String checkPointName = faultRepairRecord.getCheckPointName();
                String deviceLocation = faultRepairRecord.getDeviceLocation();

                for (FaultRepairRecord entity : faultRepairRecordList) {
                    entity.setBuildingName(buildingName);
                    entity.setFloorName(floorName);
                    entity.setCheckPointId(checkPointId);
                    entity.setCheckPointQrNo(checkPointQrNo);
                    entity.setCheckPointName(checkPointName);
                    entity.setDeviceLocation(deviceLocation);

                    entity.setWorkOrderNumber(createNo(projectId));
                    entity.setCreateUserName(userName);
                    entity.setCreateUserId(userId);
                }
            } else {
                for (FaultRepairRecord entity : faultRepairRecordList) {
                    entity.setWorkOrderNumber(createNo(projectId));
                    if (fromType != 3) {
                        entity.setCreateUserName(userName);
                        entity.setCreateUserId(userId);
                    }
                }
            }

            faultRepairRecordDao.saveFaultRepairRecordList(faultRepairRecordList);

            // "[项目名称] 项目名称/建筑物/楼层+位置+工单号......,新增故障报修"
            String projectName = project.getProjectName();

            List<String> roleSignList = new ArrayList<>(1);
            // 编辑权限
            roleSignList.add("faultrepair_manage_edit");
            for (FaultRepairRecord frr : faultRepairRecordList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("buildingId", frr.getBuildingId());

                StringBuilder str = new StringBuilder();
                str.append("【").append(projectName).append("】 ").append(projectName).append("/");
                str.append(frr.getBuildingName()).append("/").append(frr.getFloorName()).append(" ").append(frr.getDeviceLocation()).append("  ");
                str.append(frr.getCheckPointName()).append(" ").append(frr.getCheckPointQrNo()).append(" ").append(frr.getDeviceName()).append(" ");
                str.append(frr.getDeviceQrNo()).append(" 因").append(frr.getFaultDescriptionDesc()).append("故障待维修，请前往【故障报修】完成任务。");

                messageService.saveMessageRole(4, roleSignList, "报修通知", 7, 1, str.toString(),
                        "url", projectId, frr.getId(), "data", jsonObject);
            }

            List<RepairProgress> repairProgressList = new ArrayList<RepairProgress>();
            RepairProgress repairProgress = new RepairProgress();
            repairProgress.setTitle("故障发起");
            repairProgress.setCreateUserId(userId);
            for (FaultRepairRecord repairRecord : faultRepairRecordList) {
                repairProgress.setContent("报修人:" + userName);
                repairProgress.setFaultRepairRecordId(repairRecord.getId());
                repairProgressList.add(repairProgress);
            }

            repairProgressDao.saverRepairProgressList(repairProgressList);
        } else {
            faultRepairRecord.setWorkOrderNumber(createNo(projectId));

            RepairProgress repairProgress = new RepairProgress();
            // fromType=3 微信端没有用户ID和用户名
            if (faultRepairRecord.getFromType() != 3) {
                String userName = SecurityUserHolder.getUserName();
                Long userId = 0L;
                if (StringUtils.isEmpty(userName)) {
                    userName = "";
                } else {
                    userId = getLoginUserId(userName);
                }
                OrgUser orgUser = new OrgUser();
                orgUser.setUserId(userId);
                orgUser.setProjectId(faultRepairRecord.getProjectId());
                orgUser = orgUserDao.getOrgUser(orgUser);

                String orgUserName = orgUser.getUserName();
                if (StringUtils.isNotEmpty(orgUserName)) {
                    userName = orgUserName;
                }
                faultRepairRecord.setCreateUserName(userName);
                faultRepairRecord.setCreateUserId(userId);

                repairProgress.setCreateUserId(userId);
                repairProgress.setCreateUserName(userName);
                repairProgress.setTitle("故障发起");
                repairProgress.setContent("报修人:" + userName);
            } else if (faultRepairRecord.getFromType() == 3) {
                repairProgress.setTitle("故障发起");
                repairProgress.setContent("来自微信:" + (faultRepairRecord.getCreateUserName() == null ? "/" : faultRepairRecord.getCreateUserName()));
            }
            faultRepairRecordDao.saveFaultRepairRecord(faultRepairRecord);

            FaultRepairRecord entity = new FaultRepairRecord();
            entity.setId(faultRepairRecord.getId());
            FaultRepairRecord repairRecord = faultRepairRecordDao.findFaultRepairRecordOne(entity);

            // "[项目名称] 项目名称/建筑物/楼层+位置+工单号......,新增故障报修"
            StringBuilder str = new StringBuilder();
            String projectName = project.getProjectName();
            str.append("【").append(projectName).append("】 ").append(projectName).append("/");
            str.append(repairRecord.getBuildingName()).append("/").append(repairRecord.getFloorName()).append(" ").append(repairRecord.getDeviceLocation());
            str.append(" ").append(repairRecord.getCheckPointName()).append(" ").append(repairRecord.getCheckPointQrNo()).append(" ");
            str.append(repairRecord.getDeviceName()).append(" ").append(repairRecord.getDeviceQrNo());
            str.append(" 因").append(repairRecord.getFaultDescriptionDesc()).append("故障待维修，请前往【故障报修】完成任务。");

            List<String> roleSignList = new ArrayList<>(1);
            // 编辑权限
            roleSignList.add("faultrepair_manage_edit");

            JSONObject jsonObject = new JSONObject(1);
            jsonObject.put("buildingId", faultRepairRecord.getBuildingId());

            messageService.saveMessageRole(4, roleSignList, "报修通知", 7, 1, str.toString(),
                    "url", projectId, faultRepairRecord.getId(), "data", jsonObject);

            if (faultRepairRecord.getFromType() != 3) {
                repairProgress.setFaultRepairRecordId(faultRepairRecord.getId());
                repairProgressDao.saverRepairProgress(repairProgress);
            }
        }

        return asseData(faultRepairRecord);
    }


    /**
     * 功能描述: 维修人, 审核人(项目中的成员)
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/10/28
     */
    @Override
    public Data faultUser(String json) {
        OrgUser orgUser = JSONObject.parseObject(json, OrgUser.class);
        Long projectId = orgUser.getProjectId();
        List<OrgUser> orgUserList = orgUserDao.findByProject(orgUser);

        Role role = new Role();
        role.setSourceId(projectId);
        List<Role> roleList = roleDao.findRoleList(role);

        Map<String, Object> map = new HashMap<>(2);
        // 项目内成员(内层,成员在角色下)
        map.put("orgUserList", orgUserList);
        // 角色(外层)
        map.put("roleList", roleList);
        return asseData(map);
    }

    /**
     * 功能描述: 更新(会提供给其它模块调用)
     *
     * @param faultRepairRecord 故障报修 实体
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/11
     */
    public Data updateFaultRepairRecord(FaultRepairRecord faultRepairRecord) {
        int num = faultRepairRecordDao.updateFaultRepairRecord(faultRepairRecord);
        return asseData(num);
    }

    /**
     * 功能描述: 批量操作 故障报修时 更新 维修人或操作人
     *
     * @param faultRepairRecord 故障报修 实体
     * @author huanggc
     * @date 2019/11/14
     */
    private void updateFaultList(FaultRepairRecord faultRepairRecord) {
        Long[] ids = faultRepairRecord.getIds();
        Long projectId = faultRepairRecord.getProjectId();
        Integer buttonType = faultRepairRecord.getButtonType();

        // 维修人
        if (faultRepairRecord.getRepairUserIds() != null) {
            FaultHandler handler = new FaultHandler();
            handler.setFaultRepairRecordIds(ids);
            handler.setRepairUserId(-1L);
            // 先把已有的数据批量删除
            faultHandlerDao.deletedFaultHandler(handler);

            // 在插入新的
            List<FaultHandler> faultHandlerList = new ArrayList<FaultHandler>();
            Long[] repairUserIds = faultRepairRecord.getRepairUserIds();
            for (int i = 0; i < ids.length; i++) {
                Long faultRepairRecordId = ids[i];
                for (int j = 0; j < repairUserIds.length; j++) {
                    FaultHandler faultHandler = new FaultHandler();
                    faultHandler.setFaultRepairRecordId(faultRepairRecordId);
                    faultHandler.setRepairUserId(repairUserIds[j]);
                    faultHandlerList.add(faultHandler);
                }
            }
            faultHandlerDao.saveFaultHandlers(faultHandlerList);
        }

        // 审核人
        if (faultRepairRecord.getExamineUsers() != null) {
            FaultHandler handler = new FaultHandler();
            handler.setFaultRepairRecordIds(ids);
            handler.setExamineUserId(-1L);
            faultHandlerDao.deletedFaultHandler(handler);

            Long[] examineUsers = faultRepairRecord.getExamineUsers();
            List<FaultHandler> faultHandlerList = new ArrayList<>();
            for (int i = 0; i < ids.length; i++) {
                Long faultRepairRecordId = ids[i];
                for (int j = 0; j < examineUsers.length; j++) {
                    FaultHandler faultHandler = new FaultHandler();
                    faultHandler.setFaultRepairRecordId(faultRepairRecordId);
                    faultHandler.setExamineUserId(examineUsers[j]);
                    faultHandlerList.add(faultHandler);
                }
            }
            faultHandlerDao.saveFaultHandlers(faultHandlerList);
        }

        // 消息部份
        FaultRepairRecord entity = new FaultRepairRecord();
        entity.setIds(ids);
        List<FaultRepairRecord> faultRepairRecordList = faultRepairRecordDao.findFaultRepairRecordList(entity);

        Project project = projectDao.findById(projectId);

        if (buttonType == 5) {
            // 驳回
            FaultHandler faultHandler = new FaultHandler();
            faultHandler.setFaultRepairRecordIds(ids);
            String repairUserIdString = faultHandlerDao.findRepairUserIdString(faultHandler);
            if (StringUtils.isNotEmpty(repairUserIdString)) {
                Long[] orgUserIdArray = ConvertUtils.stringToLongArray(repairUserIdString);

                OrgUser data = new OrgUser();
                data.setIds(orgUserIdArray);
                JSONObject orgUserObj = HttpUtils.httpGetToken(ConstantsDevice.ORG_USER_URL + projectId, JSONObject.toJSONString(data), "json",
                        SecurityUserHolder.getToken());
                JSONArray listObj = orgUserObj.getJSONArray("listObj");
                List<Long> userList = new ArrayList<>();
                if (null != listObj) {
                    for (int i = 0; i < listObj.size(); i++) {
                        JSONObject obj = listObj.getJSONObject(i);
                        userList.add(obj.getLong("userId"));
                    }
                }

                String projectName = project.getProjectName();
                for (FaultRepairRecord idEntity : faultRepairRecordList) {
                    JSONObject jsonObject = getJsonObject(projectName, idEntity);

                    StringBuilder sbu = new StringBuilder();
                    sbu.append("【").append(projectName).append("】 ").append(projectName).append("/");
                    sbu.append(idEntity.getBuildingName()).append("/").append(idEntity.getFloorName());
                    sbu.append(" ").append(idEntity.getDeviceLocation()).append("  ").append(idEntity.getCheckPointName()).append("  ");
                    sbu.append(idEntity.getCheckPointQrNo()).append("  ").append(idEntity.getDeviceName());
                    sbu.append("  ").append(idEntity.getDeviceQrNo()).append(" 故障维修任务已被驳回，请前往【故障报修】查看详情。");
                    // 给 维修人 发消息
                    messageService.saveMessageUser(4, userList, "审核结果", 2, 0, sbu.toString(), "url", projectId, idEntity.getId(),
                            "data", jsonObject);
                }
            }

            Integer runSystem = faultRepairRecord.getRunSystem();
            if (null != runSystem && runSystem == 1) {
                List<String> roleSignList = new ArrayList<>(1);
                roleSignList.add("faultrepair_manage_edit");
                String projectName = project.getProjectName();

                for (FaultRepairRecord idEntity : faultRepairRecordList) {
                    StringBuilder str = new StringBuilder();
                    str.append("【").append(projectName).append("】 ").append(projectName).append("/");
                    str.append(idEntity.getBuildingName()).append("/").append(idEntity.getFloorName()).append("  ");
                    str.append(idEntity.getDeviceLocation()).append("  ").append(idEntity.getCheckPointName()).append("  ").append(idEntity.getCheckPointQrNo());
                    str.append("  ").append(idEntity.getDeviceName());
                    str.append(" ").append(idEntity.getDeviceQrNo()).append(" 因").append(idEntity.getFaultDescriptionDesc()).append("故障，现已启用系统。");

                    JSONObject jsonObject = getJsonObject(projectName, idEntity);
                    messageService.saveMessageRole(4, roleSignList, "启用系统", 8, 1, str.toString(), "url", projectId,
                            null, "data", jsonObject);
                }
            }
        } else if (buttonType == 2) {
            // 审核通过消息
            FaultHandler faultHandler = new FaultHandler();
            faultHandler.setFaultRepairRecordIds(ids);
            String repairUserIdString = faultHandlerDao.findRepairUserIdString(faultHandler);
            if (StringUtils.isNotEmpty(repairUserIdString)) {
                Long[] orgUserIdArray = ConvertUtils.stringToLongArray(repairUserIdString);

                OrgUser data = new OrgUser();
                data.setIds(orgUserIdArray);
                JSONObject orgUserObj = HttpUtils.httpGetToken(ConstantsDevice.ORG_USER_URL + projectId, JSONObject.toJSONString(data), "json",
                        SecurityUserHolder.getToken());
                JSONArray listObj = orgUserObj.getJSONArray("listObj");
                List<Long> userList = new ArrayList<>();
                if (null != listObj) {
                    for (int i = 0; i < listObj.size(); i++) {
                        JSONObject obj = listObj.getJSONObject(i);
                        userList.add(obj.getLong("userId"));
                    }
                    String projectName = project.getProjectName();
                    for (FaultRepairRecord idEntity : faultRepairRecordList) {
                        StringBuilder sbu = new StringBuilder();
                        sbu.append("【").append(projectName).append("】 ").append(projectName).append("/");
                        sbu.append(idEntity.getBuildingName()).append("/").append(idEntity.getFloorName());
                        sbu.append(" ").append(idEntity.getDeviceLocation()).append(" ").append(idEntity.getCheckPointName()).append("  ");
                        sbu.append(idEntity.getCheckPointQrNo()).append("  ").append(idEntity.getDeviceName());
                        sbu.append("  ").append(idEntity.getDeviceQrNo()).append(" 故障维修任务已通过审核，请前往【故障报修】查看详情。");

                        JSONObject jsonObject = getJsonObject(projectName, idEntity);
                        // 给 维修人 发消息
                        messageService.saveMessageUser(4, userList, "审核结果", 2, 0, sbu.toString(),
                                "url", projectId, idEntity.getId(), "data", jsonObject);
                    }
                }
            }

            Integer runSystem = faultRepairRecord.getRunSystem();
            if (null != runSystem && runSystem == 1) {
                List<String> roleSignList = new ArrayList<>(1);
                roleSignList.add("faultrepair_manage_edit");

                String projectName = project.getProjectName();
                for (FaultRepairRecord idEntity : faultRepairRecordList) {
                    StringBuilder str = new StringBuilder();
                    str.append("【").append(projectName).append("】 ").append(projectName).append("/");
                    str.append(idEntity.getBuildingName()).append("/").append(idEntity.getFloorName()).append("  ").append(idEntity.getDeviceLocation());
                    str.append("  ").append(idEntity.getCheckPointName()).append("  ").append(idEntity.getCheckPointQrNo()).append("  ");
                    str.append(idEntity.getDeviceName()).append(" ").append(idEntity.getDeviceQrNo());
                    str.append(" 因").append(idEntity.getFaultDescriptionDesc()).append("故障，现已启用系统。");

                    JSONObject jsonObject = getJsonObject(projectName, idEntity);
                    messageService.saveMessageRole(4, roleSignList, "启用系统", 8, 1, str.toString(), "url", projectId,
                            null, "data", jsonObject);
                }
            }
        }

        // 给 维修人 发消息
        if (faultRepairRecord.getRepairUserIds() != null) {
            Long[] repairUserId = faultRepairRecord.getRepairUserId();
            List<Long> userIds = new ArrayList<>(repairUserId.length);
            Collections.addAll(userIds, repairUserId);

            String projectName = project.getProjectName();
            for (FaultRepairRecord idEntity : faultRepairRecordList) {
                StringBuilder sbu = new StringBuilder();
                sbu.append("【").append(projectName).append("】 ").append(projectName).append("/");
                sbu.append(idEntity.getBuildingName()).append("/").append(idEntity.getFloorName());
                sbu.append(" ").append(idEntity.getDeviceLocation()).append("  ").append(idEntity.getCheckPointName());
                sbu.append( "  ").append(idEntity.getCheckPointQrNo()).append("  ").append(idEntity.getDeviceName());
                sbu.append("  ").append(idEntity.getDeviceQrNo()).append(idEntity.getFaultDescriptionDesc()).append("故障待维修,请前往【故障报修】完成任务。");

                JSONObject jsonObject = getJsonObject(projectName, idEntity);
                messageService.saveMessageUser(4, userIds, "维修通知", 2, 1, sbu.toString(), "url", projectId, idEntity.getId(),
                        "data", jsonObject);
            }
        }

        if (null != faultRepairRecord.getIsStopSystem() && faultRepairRecord.getIsStopSystem() == 1) {
            // 发消息给 项目 下管理权限的人
            List<String> roleSignList = new ArrayList<>(1);
            // 编辑权限
            roleSignList.add("faultrepair_manage_edit");

            String projectName = project.getProjectName();

            for (FaultRepairRecord idEntity : faultRepairRecordList) {
                StringBuilder sbu = new StringBuilder();
                sbu.append("【");
                sbu.append(projectName);
                sbu.append("】 ");
                sbu.append(projectName);
                sbu.append("/");
                sbu.append(idEntity.getBuildingName());
                sbu.append("/");
                sbu.append(idEntity.getFloorName());
                sbu.append("  ");
                sbu.append(idEntity.getCheckPointName());
                sbu.append("  ");
                sbu.append(idEntity.getCheckPointQrNo());
                sbu.append("  ");
                sbu.append(idEntity.getDeviceName());
                sbu.append("  ");
                sbu.append(idEntity.getDeviceQrNo());
                sbu.append("  ");
                sbu.append(idEntity.getWorkOrderDescription());
                sbu.append(",现已停用系统。");

                JSONObject jsonObject = getJsonObject(projectName, idEntity);

                messageService.saveMessageRole(4, roleSignList, "停用系统", 6, 1, sbu.toString(), "url", projectId,
                        idEntity.getId(), "data", jsonObject);
            }
        }
    }

    /**
     * 消息内容组装
     *
     * @param projectName 项目名称
     * @param faultRepairRecord FaultRepairRecord
     * @return JSONObject
     */
    private JSONObject getJsonObject(String projectName, FaultRepairRecord faultRepairRecord) {
        JSONObject jsonObject = new JSONObject(10);
        jsonObject.put("projectName", projectName);
        jsonObject.put("buildingName", faultRepairRecord.getBuildingName());
        jsonObject.put("floorName", faultRepairRecord.getFloorName());
        jsonObject.put("deviceLocation", faultRepairRecord.getDeviceLocation());
        jsonObject.put("deviceName", faultRepairRecord.getDeviceName());
        jsonObject.put("qrNo", faultRepairRecord.getDeviceQrNo());
        jsonObject.put("pointQrNo", faultRepairRecord.getCheckPointQrNo());
        jsonObject.put("event", "");
        jsonObject.put("buildingId", faultRepairRecord.getBuildingId());
        //jsonObject.put("sensorNo", idEntity.getSensorNo());
        jsonObject.put("date", "");
        return jsonObject;
    }

    /**
     * 生成工单号
     *
     * @param projectId 项目ID
     * @return String 工单号,例:"20190331001"
     */
    private synchronized String createNo(Long projectId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());

        String keyPrefix = "keyPrefix" + projectId;
        Integer keySuffix = redisUtils.getInteger(keyPrefix);

        if (null == keySuffix) {
            keySuffix = 0;
        }
        keySuffix++;

        Long expiresTime = getSecondsNextEarlyMorning();
        // 设置 key 凌晨过期
        redisUtils.set(keyPrefix, keySuffix, expiresTime.intValue());

        // 保留三位,不够三位自动补全三位,超过三位的都保留
        String keySuffixStr = String.format("%03d", keySuffix);
        return format + keySuffixStr;
    }

    /**
     * 判断当前时间距离第二天凌晨的秒数
     *
     * @return Long 返回值单位为[s:秒]
     */
    private Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 查询故障分析数据
     *
     * @param json 参数 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/5/13 11:27
     **/
    @Override
    public Data findFaultRepairAnalysis(String json) {
        FaultRepairRecord faultRepairRecord = JSONObject.parseObject(json, FaultRepairRecord.class);
        FaultRepairRecord entity = faultRepairRecordDao.findFaultRepairPieChartAnalysis(faultRepairRecord);
        List<Map<String, Integer>> list = faultRepairRecordDao.findFaultRepairBarGraphAnalysis(faultRepairRecord);

        JSONObject data = new JSONObject(5);
        data.put("deviceNum", entity.getDeviceNum() == null ? 0 : entity.getDeviceNum());
        data.put("normalNum", entity.getNormalNum() == null ? 0 : entity.getNormalNum());
        data.put("faultNum", entity.getFaultNum() == null ? 0 : entity.getFaultNum());
        BigDecimal deviceNum = new BigDecimal(entity.getDeviceNum() == null ? 0 : entity.getDeviceNum());
        BigDecimal normalNum = new BigDecimal(entity.getNormalNum() == null ? 0 : entity.getNormalNum());
        BigDecimal faultNum = new BigDecimal(entity.getFaultNum() == null ? 0 : entity.getFaultNum());
        data.put("normalRateDesc", calculateUtil(normalNum, deviceNum));
        data.put("faultRateDesc", calculateUtil(faultNum, deviceNum));

        JSONObject result = new JSONObject(2);
        result.put("data", data);
        result.put("list", list);
        return asseData(result);
    }

    /**
     * 对超过16位有效位的数进行精确的运算
     *
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2020/5/13 11:06
     **/
    private static String calculateUtil(BigDecimal a, BigDecimal b) {
        String percent =
                b == null ? "0.00%" :
                        b.compareTo(new BigDecimal(0)) == 0 ? "0.00%" :
                                a == null ? "0.00%" :
                                        a.multiply(new BigDecimal(100)).divide(b, 2, BigDecimal.ROUND_HALF_UP) + "%";
        return percent;
    }
}
