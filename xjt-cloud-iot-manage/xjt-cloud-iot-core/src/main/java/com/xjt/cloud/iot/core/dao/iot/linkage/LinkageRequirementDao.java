package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageRequirement;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 触发条件 Dao
 * @Author huanggc
 * @Date 2019/09/26
 **/
@Repository
public interface LinkageRequirementDao {

    /**
     * 功能描述:保存 触发条件
     *
     * @param linkageRequirementList 触发条件 实体集合
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: Integer
     */
    Integer saveLinkageRequirements(List<LinkageRequirement> linkageRequirementList);

    /**
     * 功能描述: 查询 触发条件
     *
     * @param linkageRequirement 触发条件实体
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: List<LinkageRequirement> 触发条件实体list集合
     */
    List<LinkageRequirement> findLinkageRequirements(LinkageRequirement linkageRequirement);
}
