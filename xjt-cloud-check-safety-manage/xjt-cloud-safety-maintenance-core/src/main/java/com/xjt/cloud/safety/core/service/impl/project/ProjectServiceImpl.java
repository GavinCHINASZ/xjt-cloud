package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.safety.core.dao.project.OrgUserDao;
import com.xjt.cloud.safety.core.dao.project.PermissionDao;
import com.xjt.cloud.safety.core.entity.project.*;
import com.xjt.cloud.safety.core.common.ConstantsProjectMsg;
import com.xjt.cloud.safety.core.dao.project.BuildingDao;
import com.xjt.cloud.safety.core.dao.project.ProjectDao;
import com.xjt.cloud.safety.core.service.service.project.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * @ClassName OrgUserServiceImpl 项目实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
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
    @Value("${refresh.permission.init.url}")
    private String USER_PERMISSIONS_URL;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private PermissionDao permissionDao;


    /***@MethodName: findById  按ID查询
     * @Description:
     * @Param: [id]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:02
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
        return asseData(entity);
    }

    /***@MethodName: addProject 添加项目
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:01
     **/
    @Override
    public Data addProject(String json) {
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        Project project = JSONObject.parseObject(json, Project.class);
        Project entity = projectDao.findByProjectName(project);
        if (entity != null) {
            return asseData(600, "项目名称已存在");
        }

        project.setCreateUserId(userId);
        project.setProjectStatus(2);
        project.setCreateUserName(userName);
        project.setOwnerId(userId);
        projectDao.save(project);
        //新增一下项目管理员角色
        roleService.addProjectRoleAdmin(project.getId());
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_PROJECT", SecurityUserHolder.getUserName(), project, null, project.getId(), 2);
        User user = new User();
        user.setLoginName(userName);
        user.setId(userId);
        String data = JSON.toJSONString(user);
        HttpUtils.httpGet(USER_PERMISSIONS_URL + StringUtils.urlEncode(data));
        projectCacheInit();
        return Data.isSuccess();
    }


    /***@MethodName: modifyProject 修改项目信息
     * @Description:
     * @Param: [json] 项目成员属性
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:00
     **/
    @Override
    public Data updateProject(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        Project entity = projectDao.findByProjectName(project);
        if (entity != null) {
            return asseData(600, "项目名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        project.setUpdateUserId(getLoginUserId(userName));
        project.setUpdateUserName(userName);
        if (project.getProjectStatus() != null && project.getProjectStatus() == 2) {
            project.setProjectStatus(1);
        }
        Project oldProject = projectDao.get(project.getId());
        projectDao.updateProject(project);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_PROJECT", SecurityUserHolder.getUserName(), project, oldProject, project.getId(), 2);
        projectCacheInit();
        return Data.isSuccess();
    }


    /***@MethodName: deleteProject  删除项目
     * @Description:
     * @Param: [json] 项目id数组
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 13:59
     *
     **/
    @Override
    public Data deleteProject(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        String userName = SecurityUserHolder.getUserName();
        project.setUpdateUserId(getLoginUserId(userName));
        project.setUpdateUserName(userName);
        projectDao.deleteProject(project);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_PROJECT", SecurityUserHolder.getUserName(), project, null, project.getId(), 2);
        projectCacheInit();
        return Data.isSuccess();
    }


    /**
     * @MethodName: findByProjectList  按条件查询项目
     * @Description:
     * @Param: [json]  项目成员属性
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:00
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
     * @MethodName: projectMenu 项目菜单
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/1 9:58
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
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/9 9:38
     */
    @Override
    public void projectCacheInit() {
        List<Project> list = projectDao.findByProjectAllList();
        CacheUtils.setCacheByList(list, Constants.PROJECT_CACHE_KEY, Project.class);//初使化缓存
    }


    /**
     * @MethodName: projectTransfer 项目转让
     * @Description:
     * @Param: [json]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/23 9:55
     **/
    @Override
    public Data projectTransfer(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        User user = new User();
        user.setId(project.getOwnerId());
        user.setCaptcha(project.getCaptcha());
        Data data = orgUserService.checkProjectTransferCaptcha(JSONObject.toJSONString(user));
        if (data.getStatus() != 200) {
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
     * @MethodName: projectReview 项目审核
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 13:56
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
        return Data.isSuccess();
    }

    /**
     * @MethodName: findByPassProjectList 查询已通过项目列表
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019-11-06 13:45
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
     * @MethodName: projectHome 项目首页
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/11 9:46
     **/
    @Override
    public Data projectHome(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        project = projectDao.get(project.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("project", project);
        return asseData(map);
    }

    public Project findProjectId(Long projectId) {
        Project project = new Project();
        project.setId(projectId);
        project = projectDao.get(projectId);
        return project;
    }

    /**
     * @MethodName: findIsPhotoPermission
     * @Description: 查询是否有拍照权限
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/1/15 11:04
     **/
    @Override
    public Data findIsPhotoPermission(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        List<String> signList = new ArrayList<>();
        signList.add("photo_graph_manage_edit");
        Integer count = orgUserRoleService.findByIsOrgUserRoleSign(userId, project.getId(), signList);
        if (count == 0) {
            return asseData(false);
        } else {
            return asseData(true);
        }
    }

    /**
     * @MethodName: findProjectStatistics
     * @Description: 查询项目统计信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/10 15:44
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

    /***@MethodName: findProjectHomeData
     * @Description: 查询项目首页数据
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/18 9:32
     **/
    @Override
    public Data findProjectHomeData(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        JSONArray dataList = new JSONArray();
        OrgUser orgUser = new OrgUser();
        orgUser.setProjectId(project.getId());
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        orgUser.setUserId(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("projectId", project.getId());
        jsonObject.put("createUserId", userId);
        List<String> list = permissionDao.findByOrgUserProjectPermission(orgUser);
        try {
            //统计项目成员数量
            int orgUserCount = orgUserDao.findByOrgListCount(orgUser);
            dataList = initData(dataList, orgUserCount, null, list.contains("project_manage"), "orgUser");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        http:
//120.76.193.19:7083/check/point/findCheckPointAndDeviceReport/465?access_token=bb3db089-1660-4f08-90dc-935271dc6f89
        try {
            //统计项目建筑物数量
            Building building = new Building();
            building.setProjectId(project.getId());
            Integer buildingCount = buildingDao.findByProjectListCount(building);
            dataList = initData(dataList, buildingCount, null, list.contains("building_manage"), "building");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计项目巡查点数量
            JSONObject checkPointObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_CHECK_POINT_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            //巡检点总数
            dataList = initData(dataList, checkPointObj.getInteger("object"), null, list.contains("device_manage"), "device");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计项目当前登录人待办任务
            JSONObject taskObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_USER_PROJECT_UPCOMING_TASK_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject taskData = taskObj.getJSONObject("object");
            Integer executing = taskData.getInteger("executing");
            Integer toBeAudit = taskData.getInteger("toBeAudit");
            if (executing == null) {
                executing = 0;
            }
            if (toBeAudit == null) {
                toBeAudit = 0;
            }
            //待办任务总数
            dataList = initData(dataList, toBeAudit + executing, null, list.contains("task_manage"), "task");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目月巡检记录
            JSONObject checkRecordObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_FAULT_CHECK_RECORD_COUNT_URL + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            //巡检记录当月异常总数
            dataList = initData(dataList, checkRecordObj.getInteger("object"), null, true, "checkRecord");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目月任务汇总总数
            JSONObject monthTaskObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_MONTH_TASK_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject mtObj = monthTaskObj.getJSONObject("obj");
            //月任务总数     月任务待办任务数
            dataList = initData(dataList, mtObj.getInteger("taskNum"), mtObj.getInteger("faultCount"), list.contains("task_manage"), "monthTask");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目火警主机故障总数/
            JSONObject fireAlarmObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_FIRE_ALARM_DEVICE_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject faObj = fireAlarmObj.getJSONObject("object");
            //火警主机设备总数   火警主机设备故障数
            dataList = initData(dataList, faObj.getInteger("fireDeviceSum"), faObj.getInteger("offlineNum"), list.contains("fire_manage"), "fireAlarm");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目极早期设备总数/异常总数
            JSONObject vesdaObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_VESA_DEVICE_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject vesObj = vesdaObj.getJSONObject("object");
            //极早期设备总数    极早期设备故障数
            dataList = initData(dataList, vesObj.getInteger("deviceTotal"), vesObj.getInteger("offlineDeviceTotal"), list.contains("vesa_manage"), "vesa");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目月值班记录故障数
            JSONObject dutyRecordObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_MONTH_RECORD_COUNT_URL + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject drObj = dutyRecordObj.getJSONObject("obj");
            //当月值记录总数   当月值班记录故障总数
            dataList = initData(dataList, drObj.getInteger("recordCount"), drObj.getInteger("faultSumCount"), list.contains("report_manage"), "report");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目水压设备总数/异常总数
            jsonObject.put("deviceTypes", new Integer[]{2, 13, 14});
            JSONObject waterGageObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject wgObj = waterGageObj.getJSONObject("object");
            //水压设备总数    故障水压设备故障数
            dataList = initData(dataList, wgObj.getInteger("waterTotal"), wgObj.getInteger("failWaterTotal"), list.contains("waterGagePermission"), "waterGage");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目水浸设备总数/异常总数
            jsonObject.put("deviceType", 3);
            JSONObject waterImmObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_WATER_DEVICE_COUNT_URL + project.getId() + "&access_token=" + SecurityUserHolder.getToken(), jsonObject.toJSONString(), "json");
            JSONObject wiObj = waterImmObj.getJSONObject("object");
            //水浸设备总数    故障水浸设备故障数
            dataList = initData(dataList, wiObj.getInteger("waterTotal"), wiObj.getInteger("failWaterTotal"), list.contains("waterImmersion_manage"), "waterImmersion");
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        try {
            //统计当前项目月故障记录总数/待修复
            jsonObject.put("permissionAll", list.contains("faultrepair_manage_edit"));
            JSONObject faultRepairObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_PROJECT_FALUT_REPAIR_COUNT_URL, jsonObject.toJSONString() + "&access_token=" + SecurityUserHolder.getToken(), "json");
            JSONObject frObj = faultRepairObj.getJSONObject("obj");
            dataList = initData(dataList, frObj.getInteger("totalCount"), frObj.getInteger("faultNum"), list.contains("faultrepair_manage"), "faultRepair");//故障报修总数   故障报修未修复数
        } catch (Exception e) {
            SysLog.debug(e.fillInStackTrace());
        }

        return asseData(dataList);
    }

    /**
     * @MethodName: initData
     * @Description: 封装数据结构
     * @Param: [dataList, sumCount, count, permission]
     * @Return: com.alibaba.fastjson.JSONArray
     * @Author: zhangZaiFa
     * @Date:2020/4/9 16:44
     **/
    private JSONArray initData(JSONArray dataList, Integer sumCount, Integer count, Boolean permission, String name) {
        JSONObject data = new JSONObject();
        data.put("sumCount", sumCount == null ? 0 : sumCount);
        data.put("count", count == null ? 0 : count);
        data.put("permission", permission);
        data.put("name", name);
        dataList.add(data);
        return dataList;
    }

    @Override
    public Data getHtml(String json) {
        try {
            JSONObject obj = JSONObject.parseObject(json);
            URL url = new URL(obj.getString("html"));
            // 通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            // 为字符输入流添加缓冲
            BufferedReader br = new BufferedReader(isr);
            String data = br.readLine();// 读取数据
            StringBuffer str = new StringBuffer();
            while (data != null) {// 循环读取数据
                str.append(data);
                data = br.readLine();
            }
            String s = str.toString();
            return asseData(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
