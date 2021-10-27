package com.xjt.cloud.maintenance.core.service.impl.project;

import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.maintenance.core.dao.project.BuildingFloorDao;
import com.xjt.cloud.maintenance.core.entity.project.BuildingFloor;
import com.xjt.cloud.maintenance.core.service.service.project.BuildingFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ClassName BuildingServiceImpl 建筑物实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class BuildingFloorServiceImpl extends AbstractService implements BuildingFloorService {

    @Autowired
    private BuildingFloorDao buildingFloorDao;

    /**
     * @MethodName: saveBuildingFloor 保存建筑物楼层
     * @Description:
     * @Param: [buildingFloors, buildingId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:28
     **/
    @Override
    public Data saveBuildingFloor(List<BuildingFloor> buildingFloors, Long buildingId) {
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        for (BuildingFloor buildingFloor : buildingFloors) {
            buildingFloor.setBuildingId(buildingId);
            buildingFloor.setCreateUserId(userId);
            buildingFloor.setCreateUserName(userName);
        }
        buildingFloorDao.saveBuildingFloor(buildingFloors);
        return Data.isSuccess();
    }

    /**
     * @MethodName: deleteBuildingIdBuildingFloor 按建筑物ID删除楼层
     * @Description:
     * @Param: [buildingId]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/7/31 16:32
     **/
    @Override
    public Data deleteBuildingIdBuildingFloor(Long buildingId) {
        buildingFloorDao.deleteBuildingIdBuildingFloor(buildingId);
        return Data.isSuccess();
    }

    /**
     * @MethodName: findByBuildIngFloors 查询建筑物楼层
     * @Description:
     * @Param: [buildingId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.BuildingFloor>
     * @Author: zhangZaiFa
     * @Date:2019/10/16 9:36
     **/
    @Override
    public List<BuildingFloor> findByBuildIngFloors(Long buildingId) {
        return buildingFloorDao.findByBuildIngFloors(buildingId);
    }

    /**
     * @MethodName: updateBuildingFloor
     * @Description: web页面传过来建筑物楼层，不知删除了那些楼层，先假删所有楼层，再将还有的楼层更新数据
     * @Param: [oldBuildingFloors, id]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/16 9:36
     **/
    @Override
    public Data updateBuildingFloor(List<BuildingFloor> oldBuildingFloors, Long buildingId, Long projectId) {
        //将所有旧楼层信息假删
        deleteBuildingIdBuildingFloor(buildingId);
        //更新楼层信息
        for (BuildingFloor buildingFloor : oldBuildingFloors) {
            buildingFloor.setDeleted(false);
            setEntityUserIdName(SecurityUserHolder.getUserName(), projectId, buildingFloor);
            buildingFloorDao.updateBuildingFloor(buildingFloor);
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: findByBuildingAllList 查询所有建筑物楼层信息
     * @Description:
     * @Param: []
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.BuildingFloor>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 9:56
     **/
    @Override
    public List<BuildingFloor> findByBuildingAllList() {
        return buildingFloorDao.findByBuildingAllList();
    }
}
