package com.xjt.cloud.admin.manage.service.impl.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.project.ProjectDao;
import com.xjt.cloud.admin.manage.dao.project.ProjectMsgLevelDao;
import com.xjt.cloud.admin.manage.dao.project.ProjectReviewRecordDao;
import com.xjt.cloud.admin.manage.entity.project.*;
import com.xjt.cloud.admin.manage.service.service.project.ProjectService;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理接口实现类
 *
 * @author wangzhiwen
 * @date 2019/12/10 10:11
 */
@Service
public class ProjectServiceImpl extends AbstractAdminService implements ProjectService {
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectReviewRecordDao projectReviewRecordDao;
    @Autowired
    private ProjectMsgLevelDao projectMsgLevelDao;

    /**
     * 功能描述:查询所有项目列表
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/5/23
     */
    @Override
    public ScriptPage<Project> findAllProjectList(AjaxPage ajaxPage, Project project) {
        project = asseFindObj(project, ajaxPage);
        String[] order = {"projectStatus"};
        project.setOrderCols(order);
        List<Project> list = projectDao.findAllProjectList(project);
        Integer totalCount = projectDao.findAllProjectListPageCount(project);
        return asseScriptPage(list, totalCount);
    }

    /**
     * 功能描述:查询所有项目列表
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/5/23
     */
    @Override
    public List<Project> findAllProject(Project project) {
        project.setPageSize(null);
        String[] order = {"projectName"};
        project.setOrderCols(order);
        return projectDao.findAllProjectList(project);
    }

    /**
     * 功能描述:查询项目审核记录
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/5/23
     */
    @Override
    public List<ProjectReviewRecord> findProjectReviewRecordList(ProjectReviewRecord projectReviewRecord) {
        return projectReviewRecordDao.findProjectReviewRecordList(projectReviewRecord);
    }

    @Override
    public Project findProject(Project project) {
        return projectDao.findProject(project);
    }

    /**
     * 项目审核
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/6/10 16:28
     **/
    @Override
    public Data projectReview(String json) {
        try {
            JSONObject projectObj = HttpUtils.httpGetToken(ConstantsClient.PROJECT_REVIEW_URL, json, "json", SecurityUserHolder.getToken());
            SysLog.info(projectObj.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Data.isSuccess();
    }

    /**
     * projectReview 修改项目
     * 
     * @param project Project
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/24 13:56
     **/
    @Override
    public Data modifyProject(Project project) {
        projectDao.modifyProject(project);
        try {
            HttpUtils.httpGet(ConstantsClient.PROJECT_CACHE_KEY);
        } catch (Exception ex) {
        }
        return Data.isSuccess();
    }

    /**
     * 功能描述: 示例项目任务
     *
     * @author wangzhiwen
     * @date 2020/7/23 16:29
     */
    @Override
    public void examplesProjectTask() {
        projectReviewRecordDao.examplesProjectTask();
    }

    /**
     * 查询建筑物列表
     * 
     * @param building Building
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.Building>
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     */
    @Override
    public List<Building> findBuildingList(Building building) {
        return projectDao.findBuildingList(building);
    }

    /**
     * 查询建筑物楼层列表
     * 
     * @param buildingFloor BuildingFloor
     * @return java.util.List<com.xjt.cloud.admin.manage.entity.project.BuildingFloor>
     * @author wangzhiwen
     * @date 2020/8/27 15:43
     */
    @Override
    public List<BuildingFloor> findBuildIngFloorList(BuildingFloor buildingFloor) {
        return projectDao.findBuildIngFloorList(buildingFloor);
    }

    /**
     * 保存 报警等级
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/01/19
     **/
    @Override
    public Data saveProjectMsgLevel(String json) {
        List<ProjectMsgLevel> projectMsgLevelList = JSONArray.parseArray(json, ProjectMsgLevel.class);
        if (CollectionUtils.isNotEmpty(projectMsgLevelList)){
            Long projectId = projectMsgLevelList.get(0).getProjectId();
            // 查询项目是否已配置
            ProjectMsgLevel projectMsgLevel = new ProjectMsgLevel();
            projectMsgLevel.setProjectId(projectId);

            ProjectMsgLevel entity = new ProjectMsgLevel();
            entity.setProjectId(projectId);
            entity.setStatus(1);
            entity.setType(1);
            assemblyProjectMsgLevel(projectMsgLevelList, entity);

            List<ProjectMsgLevel> projectMsgLevels = projectMsgLevelDao.findProjectMsgLevelList(projectMsgLevel);
            if (CollectionUtils.isEmpty(projectMsgLevels)){
                // 新增
                projectMsgLevelDao.saveProjectMsgLevelList(entity);
            }else {
                // 更新
                projectMsgLevelDao.updateProjectMsgLevelList(entity);
            }
        }
        try {
            HttpUtils.httpGet(PropertyUtils.getProperty(Constants.PROJECT_MSG_LEVEL_CACHE_KEY));
        } catch (Exception ex) {
            SysLog.error("报警等级缓存初使化失败!");
            SysLog.error(ex);
        }
        return Data.isSuccess();
    }

    /**
     * 组装数据
     *
     * @author huanggc
     * @date 2021/01/20
     * @param projectMsgLevelList List<ProjectMsgLevel>
     */
    private void assemblyProjectMsgLevel(List<ProjectMsgLevel> projectMsgLevelList, ProjectMsgLevel projectMsgLevel) {
        StringBuilder eventTypeLevel1 = new StringBuilder();
        StringBuilder eventTypeLevel2 = new StringBuilder();
        StringBuilder eventTypeLevel3 = new StringBuilder();
        StringBuilder eventTypeLevel4 = new StringBuilder();

        StringBuilder eventTypeNameLevel1 = new StringBuilder();
        StringBuilder eventTypeNameLevel2 = new StringBuilder();
        StringBuilder eventTypeNameLevel3 = new StringBuilder();
        StringBuilder eventTypeNameLevel4 = new StringBuilder();

        String eventTypeNameLevel;
        for (ProjectMsgLevel entity : projectMsgLevelList) {
            eventTypeNameLevel = entity.getEventTypeNameLevel();
            if ("级别1".equals(eventTypeNameLevel)){
                eventTypeLevel1.append(entity.getDictItemId());
                eventTypeLevel1.append(";");

                eventTypeNameLevel1.append(entity.getItemName());
                eventTypeNameLevel1.append(";");
            }else if ("级别2".equals(eventTypeNameLevel)){
                eventTypeLevel2.append(entity.getDictItemId());
                eventTypeLevel2.append(";");

                eventTypeNameLevel2.append(entity.getItemName());
                eventTypeNameLevel2.append(";");
            }else if ("级别3".equals(eventTypeNameLevel)){
                eventTypeLevel3.append(entity.getDictItemId());
                eventTypeLevel3.append(";");

                eventTypeNameLevel3.append(entity.getItemName());
                eventTypeNameLevel3.append(";");
            }else {
                eventTypeLevel4.append(entity.getDictItemId());
                eventTypeLevel4.append(";");

                eventTypeNameLevel4.append(entity.getItemName());
                eventTypeNameLevel4.append(";");
            }
        }
        projectMsgLevel.setEventTypeLevel1(eventTypeLevel1.toString());
        projectMsgLevel.setEventTypeLevel2(eventTypeLevel2.toString());
        projectMsgLevel.setEventTypeLevel3(eventTypeLevel3.toString());
        projectMsgLevel.setEventTypeLevel4(eventTypeLevel4.toString());

        projectMsgLevel.setEventTypeNameLevel1(eventTypeNameLevel1.toString());
        projectMsgLevel.setEventTypeNameLevel2(eventTypeNameLevel2.toString());
        projectMsgLevel.setEventTypeNameLevel3(eventTypeNameLevel3.toString());
        projectMsgLevel.setEventTypeNameLevel4(eventTypeNameLevel4.toString());
    }
}
