package com.xjt.cloud.fault.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.fault.core.dao.fault.RepairProgressDao;
import com.xjt.cloud.fault.core.entity.fault.RepairProgress;
import com.xjt.cloud.fault.core.service.service.RepairProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 报修进度
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Service
public class RepairProgressServiceImpl extends AbstractService implements RepairProgressService {
    @Autowired
    private RepairProgressDao repairProgressDao;

    /**
     * 功能描述:查询 报修进度 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    @Override
    public Data findRepairProgressList(String json) {
        RepairProgress repairProgress = JSONObject.parseObject(json, RepairProgress.class);

        List<RepairProgress> repairProgressList = repairProgressDao.findRepairProgressList(repairProgress);
        return asseData(repairProgressList);
    }

    /**
     * 功能描述: 删除
     *
     * @param repairProgress 报修进度 实体
     * @return Integer
     * @author huanggc
     * @date 2019/09/06
     */
    public Integer deletedRepairProgressList(RepairProgress repairProgress) {
        return repairProgressDao.deletedRepairProgressList(repairProgress);
    }

    /**
     * 功能描述:查询 报修进度 列表
     *
     * @param repairProgress 报修进度 实体
     * @return List<RepairProgress> 报修进度 实体集合
     * @author huanggc
     * @date 2019/09/09
     */
    public List<RepairProgress> findRepairProgressLists(RepairProgress repairProgress) {
        return repairProgressDao.findRepairProgressList(repairProgress);
    }

    /**
     * 功能描述:保存 报修进度
     *
     * @param repairProgress 报修进度 实体
     * @return Integer 保存成功条数
     * @author huanggc
     * @date 2019/09/09
     */
    public Integer saverRepairProgress(RepairProgress repairProgress) {
        return repairProgressDao.saverRepairProgress(repairProgress);
    }
}
