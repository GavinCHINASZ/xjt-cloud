package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.FloorPointPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/30 14:59
 * @Description: 楼层巡检点点位
 */
@RestController
@RequestMapping("/floor/point/position/")
public class FloorPointPositionController extends AbstractController {
    @Autowired
    private FloorPointPositionService floorPointPositionService;

    /**
     *
     * 功能描述:查询楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @RequestMapping(value = "findFloorPointPositionList/{projectId}")
    public Data findFloorPointPositionList(String json){
        return floorPointPositionService.findFloorPointPositionList(json);
    }

    /**
     *
     * 功能描述:保存楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @RequestMapping(value = "saveFloorPointPosition/{projectId}")
    public Data saveFloorPointPosition(String json){
        return floorPointPositionService.saveFloorPointPosition(json);
    }

    /**
     *
     * 功能描述:删除楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @RequestMapping(value = "delFloorPointPosition/{projectId}")
    public Data delFloorPointPosition(String json){
        return floorPointPositionService.delFloorPointPosition(json);
    }
}
