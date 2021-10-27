package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.message.manage.service.service.MessageService;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.dao.project.*;
import com.xjt.cloud.project.core.entity.*;
import com.xjt.cloud.project.core.service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ProjectServiceImpl 项目实现类
 *
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
 */
@Service
public class ProjectServiceImpl extends AbstractService implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private OrgUserRoleService orgUserRoleService;
    @Autowired
    private OrgUserService orgUserService;
    @Autowired
    private ProjectReviewRecordService projectReviewRecordService;
    @Autowired
    private AppModuleService appModuleService;

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private ScreenSetDao screenSetDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private OrganizationDao organizationDao;

    @Value("${refresh.permission.init.url}")
    private String USER_PERMISSIONS_URL;

    /**
     * findById  按ID查询
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 14:02
     **/
    @Override
    public Data findById(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        Project entity = projectDao.get(project.getId());
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        if (userId.equals(entity.getOwnerId())) {
            entity.setIsOwner(true);
        } else {
            entity.setIsOwner(false);
        }

        if (entity.getProjectStatus() == 1) {
            //驳回状态 查询驳回原因
            String reviewDescription = projectDao.findProjectReviewDescription(entity.getId());
            entity.setReviewDescription(reviewDescription);
        }
        return asseData(entity);
    }

    /**
     * addProject 添加项目
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 14:01
     **/
    @Override
    public Data addProject(String json) {
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        Project project = JSONObject.parseObject(json, Project.class);
        Project entity = projectDao.findByProjectName(project);
        if (entity != null) {
            return asseData(ConstantsProjectMsg.FAIL, "项目名称已存在");
        }

        project.setCreateUserId(userId);
        project.setProjectStatus(0);
        project.setCreateUserName(userName);
        project.setOwnerId(userId);
        projectDao.save(project);
        //新增一下项目管理员角色
        roleService.addProjectRoleAdmin(project.getId());
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_PROJECT", SecurityUserHolder.getUserName(), project, null, project.getId(), 2);
        User user = new User();
        user.setLoginName(userName);
        user.setId(userId);
        ScreenSet screenSet = new ScreenSet(project.getId(), userId, userName, "1;2;6;12;15");
        screenSetDao.save(screenSet);
        String data = JSON.toJSONString(user);
        HttpUtils.httpGet(USER_PERMISSIONS_URL + StringUtils.urlEncode(data));
        projectCacheInit();
        return Data.isSuccess();
    }

    /**
     * modifyProject 修改项目信息
     *
     * @param json 项目成员属性
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 14:00
     **/
    @Override
    public Data updateProject(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        Project entity = projectDao.findByProjectName(project);
        if (entity != null) {
            return asseData(ConstantsProjectMsg.FAIL, "项目名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        project.setUpdateUserId(getLoginUserId(userName));
        project.setUpdateUserName(userName);
        Project oldProject = projectDao.get(project.getId());
        SysLog.info("旧项目状态----》" + oldProject.getProjectStatus());
        if (oldProject.getProjectStatus() != null && oldProject.getProjectStatus() == 1) {
            SysLog.info("项目状态----》" + oldProject.getProjectStatus());
            project.setProjectStatus(0);
        }
        SysLog.info("新项目状态----》" + project.getProjectStatus());
        projectDao.updateProject(project);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_PROJECT", SecurityUserHolder.getUserName(), project, oldProject, project.getId(), 2);
        projectCacheInit();
        return Data.isSuccess();
    }

    /***
     * deleteProject  删除项目
     *
     * @param json 项目id数组
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 13:59
     **/
    @Override
    public Data deleteProject(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        project.setUpdateUserId(userId);
        project.setUpdateUserName(userName);
        Project oldProject = projectDao.get(project.getId());
        SysLog.info(oldProject.getOwnerId() + "---------->" + userId);
        if (!oldProject.getOwnerId().equals(userId)) {
            return asseData(ConstantsProjectMsg.FAIL, "没有权限");
        }
        projectDao.deleteProject(project);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_PROJECT", SecurityUserHolder.getUserName(), project, null, project.getId(), 2);
        projectCacheInit();
        return Data.isSuccess();
    }

    /**
     * findByProjectList  按条件查询项目
     *
     * @param json 项目成员属性
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 14:00
     **/
    @Override
    public Data findByProjectList(String json) {
        Project entity = new Project();
        String userName = SecurityUserHolder.getUserName();
        entity.setOwnerId(getLoginUserId(userName));
        List<Project> list = projectDao.findByProjectList(entity);
        return asseData(list);
    }

    /**
     * projectMenu 项目菜单
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/8/1 9:58
     **/
    @Override
    public Data projectMenu(String json) {
        Permission entity = JSONObject.parseObject(json, Permission.class);
        String userName = SecurityUserHolder.getUserName();
        entity.setUserId(getLoginUserId(userName));
        List<Permission> list = permissionService.findByUserPermission(entity);
        //List<Permission> parents = listToTree(list);
        return asseData(list);
    }

    /**
     * 功能描述: 项目信息缓存初使化
     *
     * @author wangzhiwen
     * @date 2019/8/9 9:38
     */
    @Override
    public void projectCacheInit() {
        List<Project> list = projectDao.findByProjectAllList();
        //初使化缓存
        CacheUtils.setCacheByList(list, Constants.PROJECT_CACHE_KEY, Project.class);
    }

    /**
     * projectTransfer 项目转让
     *
     * @param json 参数
     * @return void
     * @author zhangZaiFa
     * @date 2019/9/23 9:55
     **/
    @Override
    public Data projectTransfer(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        User user = new User();
        user.setId(project.getOwnerId());
        user.setCaptcha(project.getCaptcha());
        Data data = orgUserService.checkProjectTransferCaptcha(JSONObject.toJSONString(user));
        if (data.getStatus() != ConstantsProjectMsg.SUCCESS) {
            return data;
        }
        //更改项目所属人
        projectDao.projectTransfer(project);
        //将项目管理员转让给新用户，自己移致到普通用户
        String userName = SecurityUserHolder.getUserName();
        Long oldOwnerId = getLoginUserId(userName);
        //将项目管理员转让给新用户
        orgUserRoleService.updateAdmin(oldOwnerId, project.getOwnerId(), project.getId());
        //将旧的项目所属人添加普通权限
        orgUserRoleService.addOrdinary(oldOwnerId, project.getId());
        project = projectDao.get(project.getId());
        OrgUser orgUser = new OrgUser();
        orgUser.setUserId(project.getOwnerId());
        orgUser.setProjectId(project.getId());
        orgUser = orgUserService.findOrgUser(orgUser);
        project.setOwnerName(orgUser.getUserName());
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_PROJECT_TRANSFER", SecurityUserHolder.getUserName(), project, null, project.getId(), 2);
        //更新用户权限
        user.setLoginName(SecurityUserHolder.getUserName());
        HttpUtils.httpGet(USER_PERMISSIONS_URL + StringUtils.urlEncode(JSON.toJSONString(user)));
        //projectCacheInit();
        return Data.isSuccess();
    }

    /**
     * projectReview 项目审核
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/24 13:56
     **/
    @Override
    public Data projectReview(String json) {
        ProjectReviewRecord projectReviewRecord = JSONObject.parseObject(json, ProjectReviewRecord.class);
        Project project = projectDao.get(projectReviewRecord.getProjectId());
        //审核记录初始化数据
        projectReviewRecord = projectReviewRecord.initialValues(projectReviewRecord, project);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        projectReviewRecord.setReviewUserId(userId);
        //以现在时间作为版本号
        String versionNumber = DateUtils.formatDate("yyyyMMddHHmmss", new Date());
        projectReviewRecord.setVersionNumber(versionNumber);
        //修改项目的审核状态
        project.setProjectStatus(projectReviewRecord.getProjectStatus());
        projectDao.projectReview(project);
        //保存审核记录
        projectReviewRecordService.save(projectReviewRecord);
        List<Long> userIds = new ArrayList<>(1);
        userIds.add(project.getOwnerId());
        //【项目名称】尊敬的用户，您提交的[项目名称]项目创建审核流程，已经通过审核。请登录消检通智能管理平台查看详情。
        //【项目名称】尊敬的用户，您提交的[项目名称]项目创建审核流程，未通过审核。请登录消检通智能管理平台查看详情。
        String content = "【" + project.getProjectName() + "】尊敬的用户，您提交的[" + project.getProjectName() + "]项目创建审核流程，";
        if (project.getProjectStatus() == 1) {
            // 驳回
            content += "未通过审核。请登录消检通智能管理平台查看详情";
        } else {
            //通过
            content += "已经通过审核。请登录消检通智能管理平台查看详情";
        }
        messageService.saveMessageUser(-4, userIds, "项目审核", 0, 0, content, null, project.getId(), project.getId(),
                null, null);
        return Data.isSuccess();
    }

    /**
     * findByPassProjectList 查询已通过项目列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019-11-06 13:45
     **/
    @Override
    public Data findByPassProjectList(String json) {
        Project entity = new Project();
        String userName = SecurityUserHolder.getUserName();
        entity.setOwnerId(getLoginUserId(userName));
        entity.setProjectStatus(2);
        List<Project> list = projectDao.findByProjectList(entity);
        return asseData(list);
    }

    /**
     * projectHome 项目首页
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/11 9:46
     **/
    @Override
    public Data projectHome(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        project = projectDao.get(project.getId());
        Map<String, Object> map = new HashMap<>(1);
        map.put("project", project);
        return asseData(map);
    }

    /**
     * findIsPhotoPermission
     * 查询是否有拍照权限
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/1/15 11:04
     **/
    @Override
    public Data findIsPhotoPermission(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        List<String> signList = new ArrayList<>(1);
        signList.add("photo_graph_manage_edit");
        Integer count = orgUserRoleService.findByIsOrgUserRoleSign(userId, project.getId(), signList);
        if (count == 0) {
            return asseData(false);
        } else {
            return asseData(true);
        }
    }

    /**
     * findProjectStatistics
     * 查询项目统计信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/3/10 15:44
     **/
    @Override
    public Data findProjectStatistics(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        project = buildingDao.findByProjectBuildInfo(project);
        OrgUser orgUser = new OrgUser();
        orgUser.setProjectId(project.getId());
        int orgUserCount = orgUserDao.findByOrgListCount(orgUser);
        project.setOrgUserCount(orgUserCount);
        return asseData(project);
    }

    /***
     * findProjectHomeData
     *  查询项目首页数据
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/3/18 9:32
     **/
    @Override
    public Data findProjectHomeData(String json) {
        Project project = JSONObject.parseObject(json, Project.class);

        OrgUser orgUser = new OrgUser();
        orgUser.setProjectId(project.getId());
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        orgUser.setUserId(userId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", project.getId());
        //jsonObject.put("createUserId", userId);
        String jsonStr = jsonObject.toJSONString();
        String token = SecurityUserHolder.getToken();
        List<String> list;

        if(Constants.COMPATIBLE_VERSION.equals("5.0")){
            list = permissionDao.findByOrgUserProjectPermissionCV5(orgUser);
        }else{
            list = permissionDao.findByOrgUserProjectPermission(orgUser);
        }

        JSONArray dataList = new JSONArray();
        try {
            Organization organization = new Organization();
            organization.setProjectId(project.getId());
            organization.setOrgType(2);
            int companyCount = organizationDao.findByOrgListCount(organization);
            initData(dataList, companyCount, null, list.contains("companyNum"), "company");
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            // 统计项目成员数量
            int orgUserCount = orgUserDao.findByOrgListCount(orgUser);
            initData(dataList, orgUserCount, null, list.contains("project_manage"), "orgUser");
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            // 统计项目建筑物数量
            Building building = new Building();
            building.setProjectId(project.getId());
            Integer buildingCount = buildingDao.findByProjectListCount(building);
            initData(dataList, buildingCount, null, list.contains("building_manage"), "building");
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_CHECK_POINT_COUNT_URL)) {
                // 统计项目巡查点数量
                JSONObject deviceObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_CHECK_POINT_COUNT_URL + project.getId(), jsonStr,
                        "json", token);
                JSONObject deviceData = deviceObj.getJSONObject("object");
                // 巡检点总数 设备总数
                initData(dataList, deviceData.getInteger("pointTotalNum"), deviceData.getInteger("deviceTotalNum"),
                        list.contains("device_manage"), "device");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_USER_PROJECT_UPCOMING_TASK_COUNT_URL)) {
                // 统计项目当前登录人待办任务
                JSONObject taskObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_USER_PROJECT_UPCOMING_TASK_COUNT_URL + project.getId(), jsonStr,
                        "json", token);
                JSONObject taskData = taskObj.getJSONObject("object");
                Integer executing = taskData.getInteger("executing");
                Integer toBeAudit = taskData.getInteger("toBeAudit");
                if (executing == null) {
                    executing = 0;
                }
                if (toBeAudit == null) {
                    toBeAudit = 0;
                }
                // 待办任务总数
                initData(dataList, toBeAudit + executing, null, list.contains("task_manage"), "task");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_FAULT_CHECK_RECORD_COUNT_URL)) {
                // 统计当前项目月巡检记录
                JSONObject checkRecordObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_FAULT_CHECK_RECORD_COUNT_URL, jsonStr,
                        "json", token);
                // 巡检记录当月异常总数    异常巡检数
                initData(dataList, checkRecordObj.getInteger("object"), null, true, "checkRecord");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_MONTH_TASK_COUNT_URL)) {
                // 统计当前项目月任务汇总总数
                JSONObject monthTaskObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_MONTH_TASK_COUNT_URL + project.getId(), jsonStr,
                        "json", token);
                JSONObject mtObj = monthTaskObj.getJSONObject("obj");
                // 月任务总数     月任务待办任务数
                initData(dataList, mtObj.getInteger("taskNum"), mtObj.getInteger("faultCount"), list.contains("task_manage"), "monthTask");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_FIRE_ALARM_DEVICE_COUNT_URL)) {
                // 统计当前项目火警主机故障总数/
                JSONObject fireAlarmObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_FIRE_ALARM_DEVICE_COUNT_URL + project.getId(),
                        project.getId().toString(), "projectId", token);
                JSONObject faObj = fireAlarmObj.getJSONObject("object");
                // 火警主机设备总数   火警主机设备故障数
                initData(dataList, faObj.getInteger("fireDeviceSum"), faObj.getInteger("offlineNum"), list.contains("fire_manage"), "fireAlarm");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_VESA_DEVICE_COUNT_URL)) {
                // 统计当前项目极早期设备总数/异常总数
                JSONObject vesdaObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_VESA_DEVICE_COUNT_URL + project.getId(), jsonStr,
                        "json", token);
                JSONObject vesObj = vesdaObj.getJSONObject("object");
                // 极早期设备总数    极早期设备故障数
                initData(dataList, vesObj.getInteger("deviceTotal"), vesObj.getInteger("failVesaTotal"), list.contains("vesa_manage"), "vesa");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_MONTH_RECORD_COUNT_URL)) {
                // 统计当前项目月值班记录故障数
                JSONObject dutyRecordObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_MONTH_RECORD_COUNT_URL, jsonStr, "json", token);
                JSONObject drObj = dutyRecordObj.getJSONObject("obj");
                // 当月值记录总数   当月值班记录故障总数
                initData(dataList, drObj.getInteger("recordCount"), drObj.getInteger("faultSumCount"), list.contains("report_manage"), "report");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_SMOKE_DEVICE_COUNT_URL)) {
                // 统计当前项目烟感
                JSONObject smokeObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_SMOKE_DEVICE_COUNT_URL + project.getId(), jsonStr, "json",
                        token);
                smokeObj = smokeObj.getJSONObject("obj");
                initData(dataList, smokeObj.getInteger("deviceTotal"), smokeObj.getInteger("failDevice") + smokeObj.getInteger("offLine") +
                        smokeObj.getInteger("smokeNum") + smokeObj.getInteger("sensorFault") + smokeObj.getInteger("lowPower"),
                        list.contains("smoke_manage"), "smoke");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_LINKAGE_DEVICE_COUNT_URL)) {
                // 统计当前项目声光
                JSONObject linkageObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_LINKAGE_DEVICE_COUNT_URL + project.getId(), jsonStr,
                        "json", token);
                linkageObj = linkageObj.getJSONObject("obj");
                initData(dataList, linkageObj.getInteger("deviceTotal"), linkageObj.getInteger("failDevice") + linkageObj.getInteger("offLine"),
                        list.contains("linkage_manage"), "linkage");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL)) {
                // 统计当前项目水压设备总数/异常总数
                jsonObject.put("deviceTypes", new Integer[]{2, 13, 14});
                JSONObject waterGageObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL + project.getId(),
                        jsonObject.toJSONString(), "json", token);
                JSONObject wgObj = waterGageObj.getJSONObject("object");
                // 水压设备总数    故障水压设备故障数
                initData(dataList, wgObj.getInteger("waterTotal"), wgObj.getInteger("failWaterTotal"), list.contains("waterGagePermission"),
                        "waterGage");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL)) {
                // 统计当前项目水浸设备总数/异常总数
                jsonObject.remove("deviceTypes");
                jsonObject.put("deviceType", 3);
                JSONObject waterImmObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL + project.getId(),
                        jsonObject.toJSONString(), "json", token);
                JSONObject wiObj = waterImmObj.getJSONObject("object");
                // 水浸设备总数    故障水浸设备故障数
                initData(dataList, wiObj.getInteger("waterTotal"), wiObj.getInteger("failWaterTotal"), list.contains("waterImmersion_manage"),
                        "waterImmersion");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL)) {
                // 统计当前项目消火栓
                jsonObject.remove("deviceTypes");
                jsonObject.put("deviceType", 8);
                JSONObject waterImmObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL + project.getId(),
                        jsonObject.toJSONString(), "json", token);
                JSONObject wiObj = waterImmObj.getJSONObject("object");
                // 水浸设备总数    故障水浸设备故障数
                initData(dataList, wiObj.getInteger("waterTotal"), wiObj.getInteger("failWaterTotal"), list.contains("hydrant_manage_edit"), "hydrant");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        try {
            if (StringUtils.isNotEmpty(ConstantsProjectMsg.FIND_PROJECT_FALUT_REPAIR_COUNT_URL)) {
                // 统计当前项目月故障记录总数/待修复
                jsonObject.put("permissionAll", list.contains("faultrepair_manage_edit"));
                JSONObject faultRepairObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_FALUT_REPAIR_COUNT_URL, jsonStr, "json", token);
                JSONObject frObj = faultRepairObj.getJSONObject("obj");
                // 故障报修总数   故障报修未修复数
                initData(dataList, frObj.getInteger("totalCount"), frObj.getInteger("faultNum"), list.contains("faultrepair_manage"), "faultRepair");
            }
        } catch (Exception e) {
            SysLog.info(e.fillInStackTrace());
        }

        return asseData(dataList);
    }

    /**
     * initData
     * 封装数据结构
     *
     * @param dataList   JSONArray
     * @param sumCount   Integer
     * @param count      Integer
     * @param permission Boolean
     * @param name       name
     * @return com.alibaba.fastjson.JSONArray
     * @author zhangZaiFa
     * @date 2020/4/9 16:44
     **/
    private JSONArray initData(JSONArray dataList, Integer sumCount, Integer count, Boolean permission, String name) {
        JSONObject data = new JSONObject(4);
        data.put("sumCount", sumCount == null ? 0 : sumCount);
        data.put("count", count == null ? 0 : count);
        data.put("permission", permission);
        data.put("name", name);
        dataList.add(data);
        return dataList;
    }

    /**
     * findProjectFunSubscript
     * 查询项目功能角标
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/6/1 13:44
     **/
    @Override
    public Data findProjectFunSubscript(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        JSONObject data = new JSONObject();
        try {
            //查询当前登录人的用户角标
            JSONObject taskObj = HttpUtils.httpGetToken(ConstantsProjectMsg.FIND_PROJECT_TASK_SUBSCRIPT_URL + project.getProjectId(), json, "json",
                    SecurityUserHolder.getToken());
            data.put("task", taskObj.getJSONObject("object"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asseData(data);
    }

    /**
     * 查询是否有权限
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/1/15 11:03
     */
    @Override
    public Data findIsPermission(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        Integer count = orgUserRoleService.findByIsOrgUserRoleSign(project.getUserId(), project.getId(), project.getSignList());
        if (count == 0) {
            return asseData(false);
        } else {
            return asseData(true);
        }
    }


    /**
     * 项目信息级别缓存初使化
     *
     * @author wangzhiwen
     * @date 2021/1/11 10:46
     * @return void
     */
    @Override
    public Data projectMsgLevelCacheInit(){
        List<ProjectMsgLevel> list = projectDao.findProjectMsgLevelList();
        //初使化缓存
        CacheUtils.setCacheByList(list, Constants.PROJECT_MSG_LEVEL_CACHE_KEY, ProjectMsgLevel.class, "projectId");
        return Data.isSuccess();
    }

    /**
     * @Description 查询app首页用户项目数据
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 10:41
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findAppHomeUserProjectData(String json){
        Project project = JSONObject.parseObject(json, Project.class);
        Long projectId = project.getProjectId();
        Long userId = getLoginUserId(SecurityUserHolder.getUserName());
        JSONObject dataJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        String moduleType = appModuleService.findAppHomeUserProjectModule(projectId,userId);
        dataJson.put("appHomeModule", moduleType);
        moduleType = "," + moduleType + ",";

        String token = SecurityUserHolder.getToken();

        if (moduleType.indexOf(",1,") != -1){//巡查工单
            JSONObject jsonJson = JSONObject.parseObject(json);
            Integer[] taskStatusArr = {1,4} ;
            jsonJson.put("taskStatusArr",taskStatusArr);
            jsonJson.put("typeTask",1);
            jsonJson.put("isAll",true);
            jsonJson.put("moduleIndex",1);
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_CHECK_WORD_ORDER_DATA,projectId,jsonJson.toJSONString(),token);
        }

        if (moduleType.indexOf(",4,") != -1){//火警主机
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_FIRE_ALARM_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",8,") != -1){//智能烟感
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_SMOKE_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",9,") != -1){//消火栓
            JSONObject jsonJson = JSONObject.parseObject(json);
            jsonJson.put("deviceType",8);
            jsonJson.put("type",9);
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_WATER_DATA,projectId,jsonJson.toJSONString(),token);
        }

        if (moduleType.indexOf(",10,") != -1){//水压监测
            JSONObject jsonJson = JSONObject.parseObject(json);
            Integer[] deviceTypes = {2,13,14};
            jsonJson.put("deviceTypes",deviceTypes);
            jsonJson.put("type",10);
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_WATER_DATA,projectId,jsonJson.toJSONString(),token);
        }

        if (moduleType.indexOf(",11,") != -1){//水浸监测
            try {
                JSONObject jsonJson = JSONObject.parseObject(json);
                jsonJson.put("deviceType",3);
                jsonJson.put("type",11);
                setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_WATER_DATA,projectId,jsonJson.toJSONString(),token);
            }catch (Exception ex){
                SysLog.info(ex);
            }
        }

        if (moduleType.indexOf(",13,") != -1){//设备管理
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_DEVICE_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",14,") != -1){//维保记录
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_CHECK_RECORD_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",15,") != -1){//故障报修
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_FAULT_REPAIR_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",16,") != -1){//值班记录
            JSONObject jsonJson = JSONObject.parseObject(json);
            String date = DateUtils.getDateYearMonth(new Date());
            jsonJson.put("startTime",date);
            jsonJson.put("endTime",date);
            jsonJson.put("dateType","month");
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_DUTY_RECORD_DATA,projectId,jsonJson.toJSONString(),token);
        }

        if (moduleType.indexOf(",20,") != -1){//极早期预警
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_VESA_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",21,") != -1){//月度工单
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_TASK_OVERVIEW_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",28,") != -1){//声光报警
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_LINKAGE_DATA,projectId,json,token);
        }

        if (moduleType.indexOf(",29,") != -1){//任务管理
            JSONObject jsonJson = JSONObject.parseObject(json);
            Integer[] taskStatusArr = {1,0} ;
            jsonJson.put("taskStatusArr",taskStatusArr);
            jsonJson.put("typeTask",0);
            jsonJson.put("moduleIndex",29);
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_CHECK_WORD_ORDER_DATA,projectId,jsonJson.toJSONString(),token);
        }

        if (moduleType.indexOf(",31,") != -1){//类脑火眼
            setAppHomeData(jsonArray,ConstantsProjectMsg.FIND_USER_PROJECT_FIRE_EYE_DATA,projectId,json,token);
        }
        dataJson.put("homeData", jsonArray);
        return asseData(dataJson);
    }

    /**
     * @Description 组装app首页信息
     *
     * @param jsonArray
     * @param url
     * @param projectId
     * @param json
     * @param token
     * @author wangzhiwen
     * @date 2021/3/26 13:50
     * @return com.alibaba.fastjson.JSONArray
     */
    private JSONArray setAppHomeData(JSONArray jsonArray, String url, Long projectId, String json, String token){
        try {
            JSONObject jsonObject = HttpUtils.httpGetToken(url + projectId, json, "json", token);
            if (jsonObject != null) {
                jsonArray.add(jsonObject);
            }
        }catch (Exception ex){
            SysLog.info("url:" + url + " projectId:" + projectId + " json:" + json);
            SysLog.info(ex);
        }
        return jsonArray;
    }
}
