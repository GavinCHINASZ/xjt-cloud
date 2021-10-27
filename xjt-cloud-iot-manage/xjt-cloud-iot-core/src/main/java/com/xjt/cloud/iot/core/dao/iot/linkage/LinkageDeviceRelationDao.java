package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageDeviceRelation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 联动设备 与 设备绑定
 *
 * @author huanggc
 * @date 2020/08/19
 **/
@Repository
public interface LinkageDeviceRelationDao {
    /**
     * 功能描述:声光报警 与 设备绑定
     *
     * @param linkageDeviceId 联动设备ID
     * @param linkageDeviceRelationList
     * @auther huanggc
     * @date 2019/09/19
     * @return Integer
     */
    Integer saveLinkageDeviceRelationList(@Param("linkageDeviceId")Long linkageDeviceId,
                                          @Param("linkageDeviceRelationList")List<LinkageDeviceRelation> linkageDeviceRelationList);

    /**
     * 功能描述: 删除 关联的设备
     *
     * @param linkageDeviceRelation
     * @auther huanggc
     * @date 2020/08/20
     * @return Integer
     */
    Integer deleteLinkageRelationDevice(LinkageDeviceRelation linkageDeviceRelation);

    /**
     * 功能描述: 查询声光报警关联的设备
     *
     * @param linkageDeviceRelation
     * @auther huanggc
     * @date 2020/08/26
     * @return List<LinkageDeviceRelation>
     */
    List<LinkageDeviceRelation> findLinkageDeviceRelationList(LinkageDeviceRelation linkageDeviceRelation);

    /**
     * 功能描述:根据 设备ID 查询数量
     *
     * @param deviceIds 声光报警设备表实体
     * @author huanggc
     * @date 2020/09/02
     * @return Integer
     **/
    Integer findDeviceTableNumByDeviceId(Long[] deviceIds);
}
