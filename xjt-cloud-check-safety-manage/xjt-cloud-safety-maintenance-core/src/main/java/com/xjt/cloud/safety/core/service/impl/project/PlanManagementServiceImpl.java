package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.safety.core.dao.project.PlanManagementDao;
import com.xjt.cloud.safety.core.entity.project.PlanManagement;
import com.xjt.cloud.safety.core.entity.project.PlanManagementReport;
import com.xjt.cloud.safety.core.service.service.project.PlanManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 计划管理
 *
 * @author huanggc
 * @date 2019-07-29 15:15
 */
@Service
public class PlanManagementServiceImpl extends AbstractService implements PlanManagementService {
    @Autowired
    private PlanManagementDao planManagementDao;

    /**
     * 保存 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data savePlanManagement(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);

        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);

        int saveNum = 0;
        String planYear = planManagement.getPlanYear();
        String planName = planManagement.getPlanName();
        int planYearInt = Integer.parseInt(planYear);
        List<String> planNameList = null;
        List<PlanManagement> planManagementList = null;
        if ("1季度".equals(planName)) {
            planNameList = new ArrayList<>(3);
            planManagementList = new ArrayList<>(3);
            for (int i = 1; i <= 3; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else if ("2季度".equals(planName)) {
            planNameList = new ArrayList<>(3);
            planManagementList = new ArrayList<>(3);
            for (int i = 4; i <= 6; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else if ("3季度".equals(planName)) {
            planNameList = new ArrayList<>(3);
            planManagementList = new ArrayList<>(3);
            for (int i = 7; i <= 9; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else if ("4季度".equals(planName)) {
            planNameList = new ArrayList<>(3);
            planManagementList = new ArrayList<>(3);
            for (int i = 10; i <= 12; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else if ("上半年".equals(planName)) {
            planNameList = new ArrayList<>(6);
            planManagementList = new ArrayList<>(6);
            for (int i = 1; i <= 6; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else if ("下半年".equals(planName)) {
            planNameList = new ArrayList<>(6);
            planManagementList = new ArrayList<>(6);
            for (int i = 7; i <= 12; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else if ("年度".equals(planName)) {
            planNameList = new ArrayList<>(12);
            planManagementList = new ArrayList<>(12);
            for (int i = 1; i <= 12; i++) {
                planManagementInitList(planManagement, planYear, planYearInt, planNameList, planManagementList, i, userId);
            }
        } else {
            planManagement.setPlanName(planManagement.getPlanYear() + "年" + planManagement.getPlanName() + "维保");

            PlanManagement entity = planManagementDao.findPlanManagement(planManagement);
            if (entity != null) {
                Data data = new Data();
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("计划已存在");
                return data;
            }
            planManagement.setCreateUserId(userId);
            saveNum = planManagementDao.savePlanManagement(planManagement);
        }

        if (CollectionUtils.isNotEmpty(planManagementList)) {
            // 查询 客户已生成的计划
            List<PlanManagement> planManagementList1 = planManagementDao.findPlanManagementListByPlanName(planNameList, planManagement);
            if (CollectionUtils.isNotEmpty(planManagementList1)) {
                List<PlanManagement> planManagementList2 = new ArrayList<>(planManagementList1.size());
                for (PlanManagement a : planManagementList1) {
                    String planName1 = a.getPlanName();
                    for (PlanManagement b : planManagementList) {
                        if (planName1.equals(b.getPlanName())) {
                            planManagementList2.add(b);
                        }
                    }
                }

                planManagementList.removeAll(planManagementList2);
            }

            //saveNum = planManagementDao.savePlanManagementList(planNameList, planManagement);
            if (CollectionUtils.isNotEmpty(planManagementList)) {
                saveNum = planManagementDao.savePlanManagementLists(planManagementList);
            }
        }

        if (saveNum > 0) {
            if (CollectionUtils.isNotEmpty(planManagementList)) {
                Long[] ids = new Long[planManagementList.size()];
                for (int i = planManagementList.size() - 1; i >= 0; i--) {
                    ids[i] = planManagementList.get(i).getId();
                }
                planManagement.setIds(ids);
            } else {
                Long[] ids = {planManagement.getId()};
                planManagement.setIds(ids);
            }

            if (planManagement.getProjectPersonId() != null && planManagement.getProjectPersonId().length > 0) {
                planManagement.setPlanDataType(1);
                planManagementDao.savePlanDeviceSystem(planManagement);
            }

            if (planManagement.getMaintenancePersonId() != null && planManagement.getMaintenancePersonId().length > 0) {
                planManagement.setPlanDataType(2);
                planManagementDao.savePlanDeviceSystem(planManagement);
            }

            if (planManagement.getMaintenanceContentIdArr() != null && planManagement.getMaintenanceContentIdArr().length > 0) {
                planManagement.setPlanDataType(3);
                planManagementDao.savePlanDeviceSystem(planManagement);
            }
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * @param planManagement     PlanManagement
     * @param planYear           年
     * @param planYearInt        年
     * @param planNameList       List<String>
     * @param planManagementList List<PlanManagement>
     * @param i                  int
     * @author huanggc
     * @date 2021/04/28
     */
    private void planManagementInitList(PlanManagement planManagement, String planYear, int planYearInt, List<String> planNameList,
                                        List<PlanManagement> planManagementList, int i, Long userId) {

        planNameList.add(planYear + "年" + i + "月维保");

        PlanManagement entity = planManagement.clone();
        entity.setPlanName(planYear + "年" + i + "月维保");
        Date dateByYearAndMonth = DateUtils.getDateByYearAndMonth(planYearInt, i, 0);
        entity.setStartDate(DateUtils.monthStarDate(dateByYearAndMonth));
        entity.setEndDate(DateUtils.monthEndDate(dateByYearAndMonth));
        entity.setCreateUserId(userId);
        planManagementList.add(entity);
    }

    /**
     * 查询 维保计划管理首页列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data findPlanManagementPage(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);

        planManagement.setStartDate(DateUtils.strToY_M_D(DateUtils.formatDate(DateUtils.monthStarDate(new Date()))));
        planManagement.setEndDate(DateUtils.add24Hours(DateUtils.monthEndDate(new Date())));

        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        planManagement.setCreateUserId(userId);
        Integer totalCount = planManagement.getTotalCount();
        Integer pageSize = planManagement.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = planManagementDao.findPlanManagementPageCount(planManagement);
        }

        List<PlanManagementReport> planManagementReports = planManagementDao.findPlanManagementPage(planManagement);
        return asseData(totalCount, planManagementReports);
    }

    /**
     * 查询 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data findPlanManagementList(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);

        // app只看当月计划
        if (planManagement.getClientType() != null && planManagement.getClientType() == 1) {
            planManagement.setStartDate(DateUtils.monthStarDate(new Date()));
            planManagement.setEndDate(DateUtils.monthEndDate(new Date()));
        }

        Integer totalCount = planManagement.getTotalCount();
        Integer pageSize = planManagement.getPageSize();
        if (planManagement.getCheckDate() != null) {
            planManagement.setCheckDate(new Date());
        }
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = planManagementDao.findPlanManagementCount(planManagement);
        }

        if (planManagement.getOrderCols() == null || planManagement.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            planManagement.setOrderCols(orderCols);
        }
        List<PlanManagement> planManagementList = planManagementDao.findPlanManagementList(planManagement);

        return asseData(totalCount, planManagementList);
    }

    /**
     * 查询 计划管理 已生成了报告
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data findPlanManagementReportList(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);

        Integer totalCount = planManagement.getTotalCount();
        Integer pageSize = planManagement.getPageSize();
        if (planManagement.getCheckDate() != null) {
            planManagement.setCheckDate(new Date());
        }
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = planManagementDao.findPlanManagementReportCount(planManagement);
        }

        if (planManagement.getOrderCols() == null || planManagement.getOrderCols().length == 0) {
            String[] orderCols = {"createTime"};
            planManagement.setOrderCols(orderCols);
        }
        List<PlanManagement> planManagementList = planManagementDao.findPlanManagementReportList(planManagement);

        return asseData(totalCount, planManagementList);
    }

    /**
     * 删除 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data delPlanManagement(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);
        int num = planManagementDao.delPlanManagement(planManagement);
        if (num > 0) {
            planManagementDao.delPlanDeviceSystem(planManagement);
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 更新 计划管理
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/17
     **/
    @Override
    public Data updatePlanManagement(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);

        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        planManagement.setUpdateUserId(userId);
        int updateNum = planManagementDao.updatePlanManagement(planManagement);

        if (updateNum > 0) {
            Long[] ids = {planManagement.getId()};
            planManagement.setIds(ids);
            planManagement.setId(null);
            if (planManagement.getProjectPersonId() != null && planManagement.getProjectPersonId().length > 0) {
                planManagement.setPlanDataType(1);
                planManagementDao.delPlanDeviceSystem(planManagement);
                planManagementDao.savePlanDeviceSystem(planManagement);
            }

            if (planManagement.getMaintenancePersonId() != null && planManagement.getMaintenancePersonId().length > 0) {
                planManagement.setPlanDataType(2);
                planManagementDao.delPlanDeviceSystem(planManagement);
                planManagementDao.savePlanDeviceSystem(planManagement);
            }

            if (planManagement.getMaintenanceContentIdArr() != null && planManagement.getMaintenanceContentIdArr().length > 0) {
                planManagement.setPlanDataType(3);
                planManagementDao.delPlanDeviceSystem(planManagement);
                planManagementDao.savePlanDeviceSystem(planManagement);
            }
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 查询计划维设备系统汇总报表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    @Override
    public Data findPlanDeviceSysReportList(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        planManagement.setCreateUserId(userId);
        return asseData(planManagementDao.findPlanDeviceSysReportList(planManagement));
    }

    /**
     * 查询计划维设备系统巡检测试列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/04/17
     **/
    @Override
    public Data findPlanDeviceSysCheckReportList(String json) {
        PlanManagement planManagement = JSON.parseObject(json, PlanManagement.class);
        return asseData(planManagementDao.findCheckRecordList(planManagement));
    }
}
