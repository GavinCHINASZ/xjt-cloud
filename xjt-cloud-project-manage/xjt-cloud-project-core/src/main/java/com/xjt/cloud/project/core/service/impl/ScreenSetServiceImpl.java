package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.common.utils.DatesUtils;
import com.xjt.cloud.project.core.dao.project.BuildingDao;
import com.xjt.cloud.project.core.dao.project.OrgUserDao;
import com.xjt.cloud.project.core.dao.project.ProjectDao;
import com.xjt.cloud.project.core.dao.project.ScreenSetDao;
import com.xjt.cloud.project.core.entity.Building;
import com.xjt.cloud.project.core.entity.Project;
import com.xjt.cloud.project.core.entity.ScreenSet;
import com.xjt.cloud.project.core.entity.TreeNode;
import com.xjt.cloud.project.core.service.service.ProjectService;
import com.xjt.cloud.project.core.service.service.ScreenSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zxb
 * @date 2020-04-07 17:02
 **/
@Service
public class ScreenSetServiceImpl extends AbstractService implements ScreenSetService {
    @Autowired
    private ScreenSetDao screenSetDao;
    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectService projectService;

    /**
     * 查询大屏设置
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/7 18:30
     **/
    @Override
    public Data findScreenSet(String json) {
        ScreenSet screenSet = JSON.parseObject(json, ScreenSet.class);
        screenSet = screenSetDao.findScreenSet(screenSet);

        String msgTypes = screenSet.getMsgTypes();
        List<String> ids = null;
        if (StringUtils.isNotEmpty(msgTypes)){
            ids = Arrays.asList(msgTypes.split(";"));
        }

        List<Dict> list = DictUtils.getDictListByDictCode("SCREEN_MSG_MANAGE");
        List<TreeNode> rows = new ArrayList<>();
        for (Dict dict : list) {
            String[] msgId = dict.getMemo().split("_");
            String[] msgName = dict.getItemDescription().split("_");
            String parentId = "p" + dict.getItemId();
            TreeNode parent = new TreeNode(parentId, dict.getItemName(), null, ids);
            rows.add(parent);
            for (int i = 0; i < msgId.length; i++) {
                TreeNode treeNode = new TreeNode(msgId[i], msgName[i], parentId, ids);
                rows.add(treeNode);
            }
        }
        screenSet.setTreeList(rows);

        return asseData(screenSet);
    }

    /**
     * 保存大屏设置
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/7 18:30
     **/
    @Override
    public Data save(String json) {
        ScreenSet screenSet = JSON.parseObject(json, ScreenSet.class);
        int num;
        if (screenSet.getId() == null) {
            num = screenSetDao.save(screenSet);
        } else {
            num = screenSetDao.modifyScreenSet(screenSet);
        }
        projectService.projectMsgLevelCacheInit();
        return asseData(num);
    }

    /**
     * 查询大屏项目列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/9 9:03
     **/
    @Override
    public Data findScreenProjectList(String json) {
        Project project = JSONObject.parseObject(json, Project.class);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        project.setOwnerId(userId);
        List<Project> list;
        if (Constants.COMPATIBLE_VERSION.equals("5.0")) {
            list = screenSetDao.findScreenProjectListCV5(project);
        } else {
            list = screenSetDao.findScreenProjectList(project);
        }
        return asseData(list);
    }

    /**
     * 查询大屏项目地图数据
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/9 9:45
     **/
    @Override
    public Data findScreenProjectMapData(String json) {
        Building building = JSONObject.parseObject(json, Building.class);
        //查询建筑物列表
        List<Building> buildings = buildingDao.findScreenProjectMapData(building);
        //项目成员数量
        Integer orgUserCount = orgUserDao.findProjectOrgUserCount(building.getProjectIds());
        for (Building build : buildings) {
            build.setProjectName(CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, build.getProjectId(), "projectName"));
        }
        // 项目数量
        Integer projectCount = building.getProjectIds().size();
        // 巡查点数量
        Integer checkPointCount = buildings.stream().collect(Collectors.summingInt(Building::getCheckPointCount));
        // 设备数量
        Integer deviceCount = buildings.stream().collect(Collectors.summingInt(Building::getDeviceCount));

        JSONObject data = new JSONObject(5);
        data.put("buildings", buildings);
        data.put("orgUserCount", orgUserCount);
        data.put("projectCount", projectCount);
        data.put("checkPointCount", checkPointCount);
        data.put("deviceCount", deviceCount);
        return asseData(data);
    }

    /**
     * 查询是否有大屏功能
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2020/4/27 11:15
     **/
    @Override
    public Data findUserScreenPermission() {
        Project project = new Project();
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        project.setOwnerId(userId);
        project.setDeleted(false);
        project.setProjectStatus(2);
        Integer projectCount;
        if (Constants.COMPATIBLE_VERSION.equals("5.0")) {
            projectCount = screenSetDao.findScreenProjectListCountCV5(project);
        } else {
            projectCount = screenSetDao.findScreenProjectListCount(project);
        }
        Integer count = projectDao.findByProjectListCount(project);

        JSONObject data = new JSONObject(2);
        data.put("screenPermission", projectCount > 0 ? true : false);
        data.put("isOwnerProject", count > 0 ? true : false);
        return asseData(data);
    }

    /**
     * 查询 地铁大屏
     *
     * @param json findMetroScreen
     * @return com.xjt.cloud.commons.utils.Data
     * @author hunaggc
     * @date 2020-05-09
     **/
    @Override
    public Data findMetroScreen(String json) {
        ScreenSet screenSet = JSON.parseObject(json, ScreenSet.class);
        JSONObject data = new JSONObject();

        if (!("XJT_DITIE_001").equals(screenSet.getAppId())) {
            return asseData(600, "appId错误");
        }

        // 1工单管理, 2设备管理, 3故障报修, 4故障分析, 5火警原因分析
        JSONObject jsonObject = new JSONObject();
        if (screenSet.getModelType() == null) {
            screenSet.setModelType(0);
        }
        ;
        jsonObject.put("startTime", getTime(screenSet.getType(), 0));
        jsonObject.put("endTime", getTime(screenSet.getType(), 1));

        if (StringUtils.isNotEmpty(screenSet.getAppId())) {
            String project = DictUtils.getDictItemValueByDictAndItemCode(Constants.APP_ID_CONFIG, screenSet.getAppId());
            jsonObject.put("projectIds", project.split(","));
        }

        // 1 工单管理(任务) 统计本月工单中的巡查点数
        // 总数, 已检数 已检率=已检数/总数, 未检数 未检率=未检数/总数
        try {
            // 工单管理
            if (screenSet.getModelType() == 0 || screenSet.getModelType() == 1) {
                JSONObject taskObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_SCREEN_TASK_DATA_ANALYZE_URL, jsonObject.toJSONString(), "json");
                data.put("taskJson", taskObj.getJSONObject("object"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 2 设备管理   统计本月巡检的故障设备数
        // 总数  正常数  正常率=正常数（已检+未检）/总数,   异常数 异常率=异常数/总数
        try {
            // 设备管理
            if (screenSet.getModelType() == 0 || screenSet.getModelType() == 2) {
                JSONObject deviceObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_SCREEN_DEVICE_MANAGE_DATA_ANALYZE_URL, jsonObject.toJSONString(), "json");
                data.put("checkDeviceJson", deviceObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject faultRepairObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_SCREEN_FAULT_REPAIR_DATA_ANALYZE_URL, jsonObject.toJSONString(), "json");
            JSONObject object = faultRepairObj.getJSONObject("object");
            // 3 故障报修 注：统计本月巡检的故障设备数
            // 总数,  已修复 已修复率=已修复数/总数,  未修复  未修复率=未修复数/总数
            if (screenSet.getModelType() == 0 || screenSet.getModelType() == 3) {
                data.put("faultRepairRecordJson", object.getJSONObject("data"));
            }
            // 4 故障分析   周, 月,  年
            // 设备名称, 数量/个
            //注：
            //1、按周月年切换查看故障设备排名TOP5的设备
            //2、默认显示月的故障数量 top 5
            if (screenSet.getModelType() == 0 || screenSet.getModelType() == 4) {
                data.put("faultDeviceJsonArray", object.getJSONArray("list"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 5 火警原因分析     周, 月, 年
            // 火警原因分析, 数量/个
            // 注：按发生火警事件的原因排名TOP5 统计, 默认显示月的原因数量 top 5; 2、设备 + 原因
            if (screenSet.getModelType() == 0 || screenSet.getModelType() == 5) {
                JSONObject fireAlarmObj = HttpUtils.httpGet(ConstantsProjectMsg.FIND_SCREEN_FIRE_ALARM_DATA_ANALYZE_URL, jsonObject.toJSONString(), "json");
                data.put("fireAlarmEventJsonArray", fireAlarmObj.getJSONArray("object"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asseData(data);
    }

    /**
     * 查询开始时间和结束时间
     *
     * @param type   1周, 2月, 3年
     * @param status 0、开始 1、结束
     * @return java.lang.String
     * @author zhangZaiFa
     * @date 2020/5/13 17:04
     **/
    private static String getTime(Integer type, Integer status) {
        DateUtils.formatDate(DatesUtils.getBeginDayOfMonth());
        if (type == null) {
            if (status == 0) {
                return DateUtils.formatDate(DatesUtils.getBeginDayOfMonth());
            } else {
                return DateUtils.formatDate(DatesUtils.getEndDayOfMonth());
            }
        }
        if (type == 1) {
            if (status == 0) {
                return DateUtils.formatDate(DatesUtils.getBeginDayOfWeek());
            } else {
                return DateUtils.formatDate(DatesUtils.getEndDayOfWeek());
            }
        } else if (type == 2) {
            if (status == 0) {
                return DateUtils.formatDate(DatesUtils.getBeginDayOfMonth());
            } else {
                return DateUtils.formatDate(DatesUtils.getEndDayOfMonth());
            }
        } else if (type == 3) {
            if (status == 0) {
                return DateUtils.formatDate(DatesUtils.getBeginDayOfYear());
            } else {
                return DateUtils.formatDate(DatesUtils.getEndDayOfYear());

            }
        } else {
            if (status == 0) {
                return DateUtils.formatDate(DatesUtils.getBeginDayOfMonth());
            } else {
                return DateUtils.formatDate(DatesUtils.getEndDayOfMonth());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getTime(1, 0) + "--周--->" + getTime(0, 1));
        System.out.println(getTime(2, 0) + "--月--->" + getTime(2, 1));
        System.out.println(getTime(3, 0) + "--年--->" + getTime(3, 1));
    }

    /**
     * 地铁大屏--跳转
     *
     * @param json findMetroJump
     * @return com.xjt.cloud.commons.utils.Data
     * @author hunaggc
     * @date 2020-05-09
     **/
    @Override
    public Data findMetroJump(String json) {
        ScreenSet screenSet = JSON.parseObject(json, ScreenSet.class);
        return asseData(screenSet);
    }
}
