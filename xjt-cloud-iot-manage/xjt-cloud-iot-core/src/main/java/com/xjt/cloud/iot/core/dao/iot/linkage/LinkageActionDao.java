package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageAction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 触发动作 Dao
 *
 * @author huanggc
 * @date 2019/09/26
 **/
@Repository
public interface LinkageActionDao {

    /**
     * 功能描述:查询 联动控制 列表
     *
     * @param linkageActionList 触发动作 实体集合
     * @author huanggc
     * @date 2019/09/26
     * @return int
     */
    int saveLinkageActions(List<LinkageAction> linkageActionList);

}
