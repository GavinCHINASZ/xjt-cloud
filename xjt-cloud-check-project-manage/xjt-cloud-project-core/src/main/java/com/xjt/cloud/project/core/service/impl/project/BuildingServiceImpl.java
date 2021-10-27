package com.xjt.cloud.project.core.service.impl.project;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.project.core.common.utils.ProjectThread;
import com.xjt.cloud.project.core.common.utils.ThreadPoolUtils;
import com.xjt.cloud.project.core.dao.project.BuildingDao;
import com.xjt.cloud.project.core.entity.project.Building;
import com.xjt.cloud.project.core.entity.project.BuildingFloor;
import com.xjt.cloud.project.core.service.service.project.BuildingFloorService;
import com.xjt.cloud.project.core.service.service.project.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName BuildingServiceImpl 建筑物实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class BuildingServiceImpl extends AbstractService implements BuildingService {

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private BuildingFloorService buildingFloorService;
    @Autowired
    private SecurityLogService securityLogService;


    /**
     * @MethodName: findByProjectList 查询项目建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:11
     **/
    @Override
    public Data findByProjectList(String json) {
        Building building = JSONObject.parseObject(json, Building.class);
        List<Building> list = buildingDao.findByProjectList(building);
        Integer totalCount = building.getTotalCount();
        Integer pageSize = building.getPageSize();
        Long acreage = buildingDao.findByProjectAcreage(building.getProjectId(),building.getCheckProjectId());
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = buildingDao.findByProjectListCount(building);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", totalCount);
        if(acreage!=null){
            data.put("acreage", acreage/100d);
        }else{
            data.put("acreage", 0);
        }
        data.put("list", list);
        return asseData(data);
    }

    /**
     * @MethodName: deleteBuilding  删除建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:11
     **/
    @Override
    public Data deleteBuilding(String json) {
        Building building = JSONObject.parseObject(json, Building.class);
        for (Long id : building.getIds()) {
            Building oldBuilding = CacheUtils.getCacheByTypeAndKey(Constants.BUILDING_CACHE_KEY, id.toString(), Building.class);
            if (oldBuilding != null) {
                securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_BUILDING", SecurityUserHolder.getUserName(), oldBuilding, null, oldBuilding.getProjectId(), 2);
            }
        }
        buildingDao.deleteBuilding(building.getIds());
        buildingCacheInit();
        return Data.isSuccess();
    }

    /**
     * @MethodName: updateBuilding 修改建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:12
     **/
    @Override
    public Data updateBuilding(String json) {
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        Building building = JSONObject.parseObject(map.get("building").toString(), Building.class);
        Building oldBuilding = buildingDao.findByBuildingName(building);
        building.initHeightAndAcreage(building.getHeight(),building.getAcreage());
        if (oldBuilding != null) {
            return asseData(600, "建筑物名称已存在");
        }
        List<BuildingFloor> buildingFloors = JSONArray.parseArray(map.get("buildingFloor").toString(), BuildingFloor.class);
        building = setEntityUserIdName(SecurityUserHolder.getUserName(), building.getProjectId(), building);
        SysLog.debug(building.getAcreageInt()+"--->"+building.getHeightInt());
        oldBuilding = buildingDao.findByBuilding(building);
        buildingDao.updateBuilding(building);
        List<BuildingFloor> oldBuildingFloors = new ArrayList<>();
        //找出老的楼层更新数据
        for (int i = 0; i < buildingFloors.size(); i++) {
            BuildingFloor buildingFloor = buildingFloors.get(i);
            if (buildingFloor.getId() != null && buildingFloor.getId() != 0) {
                oldBuildingFloors.add(buildingFloor);
                buildingFloors.remove(i);
                i--;
            }
        }
        if (oldBuildingFloors.size() != 0) {
            buildingFloorService.updateBuildingFloor(oldBuildingFloors, building.getId(), building.getProjectId());
        }
        //添加新的楼层
        if (buildingFloors.size() != 0) {
            buildingFloorService.saveBuildingFloor(buildingFloors, building.getId());
        }
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_BUILDING", SecurityUserHolder.getUserName(), building, oldBuilding, building.getProjectId(), 2);
        ThreadPoolUtils.getInstance().execute(new ProjectThread(this,"buildingCacheInit"));
        return Data.isSuccess();
    }

    /**
     * @MethodName: addBuilding  添加建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 14:12
     **/
    @Override
    public Data addBuilding(String json) {
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        Building building = JSONObject.parseObject(map.get("building").toString(), Building.class);
        List<BuildingFloor> buildingFloors = JSONArray.parseArray(map.get("buildingFloor").toString(), BuildingFloor.class);
        Building oldBuilding = buildingDao.findByBuildingName(building);
        if (oldBuilding != null) {
            return asseData(600, "建筑物名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        building.setCreateUserId(userId);
        building.setUpdateUserName(userName);
        building.initHeightAndAcreage(building.getHeight(),building.getAcreage());
        buildingDao.addBuilding(building);
        if (buildingFloors.size() != 0) {
            buildingFloorService.saveBuildingFloor(buildingFloors, building.getId());
        }

        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_BUILDING", SecurityUserHolder.getUserName(), building, null, building.getProjectId(), 2);
        ThreadPoolUtils.getInstance().execute(new ProjectThread(this,"buildingCacheInit"));
        return Data.isSuccess();
    }

    /**
     * @MethodName: findBuildingFloor 查询建筑物楼层
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/5 13:53
     **/

    @Override
    public Data findBuildingFloor(String json) {
        Building building = JSONObject.parseObject(json, Building.class);
        List<BuildingFloor> list = buildingFloorService.findByBuildIngFloors(building.getId());
        return asseData(list);
    }

    /**
     * @MethodName: findByBuildingId 查询建筑物
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/16 13:41
     **/
    @Override
    public Data findByBuilding(String json) {
        Building building = JSONObject.parseObject(json, Building.class);
        Map<String, Object> data = new HashMap<>();
        building = buildingDao.findByBuilding(building);
        List<BuildingFloor> buildingFloors = buildingFloorService.findByBuildIngFloors(building.getId());
        data.put("building", building);
        data.put("buildingFloors", buildingFloors);
        return asseData(data);
    }

    /**
     * @MethodName: buildingCacheInit 添加初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:52
     **/
    @Override
    public void buildingCacheInit() {
        List<Building> buildingList = buildingDao.findByBuildingAllList();
        CacheUtils.setCacheByList(buildingList, Constants.BUILDING_CACHE_KEY, Building.class);//初使化缓存
    }

    /**
     * @MethodName: buildingCacheInit 添加初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:52
     **/
    @Override
    public void projectCacheBuildingFloorInit() {
        List<BuildingFloor> buildingFloorList = buildingFloorService.findByBuildingAllList();
        CacheUtils.setCacheByList(buildingFloorList, Constants.BUILDING_FLOOR_CACHE_KEY, BuildingFloor.class);//初使化缓存
    }

    /**
     *
     * 功能描述:以sql文,查询建筑物列表
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 10:53
     */
    @Override
    public Data findBuildingListBySql(String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<Building> buildingList = buildingDao.findBuildingListBySql(jsonObject.getString("sql"));
        return asseData(buildingList);
    }


    /**@MethodName: findCheckProjectBuilding
     * @Description: 查询检测项目建筑物
     * @Param: [checkProjectId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.project.Building>
     * @Author: zhangZaiFa
     * @Date:2020/4/14 18:33
     **/
    @Override
    public List<Building> findCheckProjectBuilding(Long checkProjectId) {
        Building building = new Building();
        building.setCheckProjectId(checkProjectId);
        List<Building> list  = buildingDao.findByProjectList(building);
        for (Building entity:list) {
            List<BuildingFloor> buildingFloors = buildingFloorService.findByBuildIngFloors(entity.getId());
            entity.setBuildingFloors(buildingFloors);
        }
        return list;
    }
}
