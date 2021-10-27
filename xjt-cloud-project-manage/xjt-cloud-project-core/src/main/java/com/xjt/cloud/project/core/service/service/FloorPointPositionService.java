package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/30 15:00
 * @Description: 楼层巡检点点位
 */
public interface FloorPointPositionService {

    /**
     *
     * 功能描述:查询楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data findFloorPointPositionList(String json);

    /**
     *
     * 功能描述:保存楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data saveFloorPointPosition(String json);

    /**
     *
     * 功能描述:删除楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    Data delFloorPointPosition(String json);

}
