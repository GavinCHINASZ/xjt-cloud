package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.project.core.dao.project.FloorPointPositionDao;
import com.xjt.cloud.project.core.entity.FloorPointPosition;
import com.xjt.cloud.project.core.service.service.FloorPointPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/30 15:00
 * @Description: 楼层巡检点点位
 */
@Service
public class FloorPointPositionServiceImpl extends AbstractService implements FloorPointPositionService {

    @Autowired
    private FloorPointPositionDao floorPointPositionDao;

    /**
     *
     * 功能描述:查询楼层巡检点点位列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/4/30 15:12
     */
    @Override
    public Data findFloorPointPositionList(String json){
        FloorPointPosition floorPointPosition = JSONObject.parseObject(json, FloorPointPosition.class);
        return asseData(floorPointPositionDao.findFloorPointPositionList(floorPointPosition));
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
    @Override
    public Data saveFloorPointPosition(String json){
        FloorPointPosition floorPointPosition = JSONObject.parseObject(json, FloorPointPosition.class);
        int num;
        if (floorPointPosition.getId() == null) {
            num = floorPointPositionDao.saveFloorPointPosition(floorPointPosition);
            if (floorPointPosition.getUpperId() != null){
                FloorPointPosition fpp = new FloorPointPosition();
                fpp.setNextId(floorPointPosition.getId());
                fpp.setId(floorPointPosition.getUpperId());
                floorPointPositionDao.modifyFloorPointPosition(fpp);//修改上一个点的下一个节点id
            }
        }else {
            num = floorPointPositionDao.modifyFloorPointPosition(floorPointPosition);
        }
        if (num > 0){
            return asseData(floorPointPosition);
        }
        return Data.isFail();
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
    @Override
    public Data delFloorPointPosition(String json){
        FloorPointPosition floorPointPosition = JSONObject.parseObject(json, FloorPointPosition.class);
        List<FloorPointPosition> list = floorPointPositionDao.findFloorPointPositionList(floorPointPosition);
        if (CollectionUtils.isNotEmpty(list)){
            //modifyFloorPointPosition(list);
            int num = floorPointPositionDao.delFloorPointPosition(floorPointPosition);
            return asseData(num);
        }

        return asseData(220,"");
    }


    /**
     *
     * 功能描述: 修改上一节点与下一节点信息
     *
     * @param list
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/6/28 11:17
     */
    private int modifyFloorPointPosition(List<FloorPointPosition> list){
        FloorPointPosition fpp = list.get(0);
        FloorPointPosition fpp1 = list.get(list.size() - 1);

        FloorPointPosition modifyFpp = new FloorPointPosition();
        if(fpp.getUpperId() != null) {
            //把上一个点的下一个节点id修改为最后一条数据的下一个节点
            modifyFpp.setNextId(fpp1.getNextId() == null ? 0 : fpp1.getNextId());
            modifyFpp.setId(fpp.getUpperId());
            floorPointPositionDao.modifyFloorPointPosition(modifyFpp);//修改上一个点的下一个节点id
        }

        if(fpp.getNextId() != null) {
            //把下一条的上一个节点修改为，每一条的上一个节点
            modifyFpp = new FloorPointPosition();
            modifyFpp.setUpperId(fpp.getUpperId() == null ? 0 : fpp.getUpperId());
            modifyFpp.setId(fpp1.getNextId());
            return floorPointPositionDao.modifyFloorPointPosition(modifyFpp);//修改上一个点的下一个节点id
        }
        return 0;
    }
}
