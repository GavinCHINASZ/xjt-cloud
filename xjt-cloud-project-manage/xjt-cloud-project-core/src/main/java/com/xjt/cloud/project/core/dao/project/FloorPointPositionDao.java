package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.FloorPointPosition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/30 15:00
 * @Description: 楼层巡检点点位
 */
@Repository
public interface FloorPointPositionDao {

    /**
     *
     * 功能描述:保存楼层巡检点点位
     *
     * @param floorPointPosition
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int saveFloorPointPosition(FloorPointPosition floorPointPosition);

    /**
     *
     * 功能描述:修改楼层巡检点点位
     *
     * @param floorPointPosition
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int modifyFloorPointPosition(FloorPointPosition floorPointPosition);

    /**
     *
     * 功能描述:删除楼层巡检点点位
     *
     * @param floorPointPosition
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    int delFloorPointPosition(FloorPointPosition floorPointPosition);

    /**
     *
     * 功能描述:查询楼层巡检点点位信息列表
     *
     * @param floorPointPosition
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:22
     */
    List<FloorPointPosition> findFloorPointPositionList(FloorPointPosition floorPointPosition);
}
