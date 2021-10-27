package com.xjt.cloud.iot.core.dao.iot.linkage;

import com.xjt.cloud.iot.core.entity.linkage.LinkageControl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 联动控制 Dao
 * @Author huanggc
 * @Date 2019/09/19
 **/
@Repository
public interface LinkageControlDao {
    /**
     * 功能描述:查询 联动控制 列表
     *
     * @param linkageDevice 联动控制  实体
     * @auther: huanggc
     * @date: 2019/09/19
     * @return: List<LinkageControl>
     */
    List<LinkageControl> findLinkageControlList(LinkageControl linkageDevice);

    /**
     * 功能描述:查询 联动控制 总条数
     *
     * @param linkageDevice 联动控制  实体
     * @auther: huanggc
     * @date: 2019/09/19
     * @return: Integer
     */
    Integer findLinkageControlTotalCount(LinkageControl linkageDevice);

    /**
     * 功能描述: 删除
     *
     * @param linkageControl
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: Integer
     */
    Integer deleteLinkageControl(LinkageControl linkageControl);

    /**
     * 功能描述: 新增
     *
     * @param linkageControl
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: Integer
     */
    Integer saveLinkageControl(LinkageControl linkageControl);

    /**
     * 功能描述: 新增
     *
     * @param linkageControl
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: Integer
     */
    Integer updateLinkageControl(LinkageControl linkageControl);

    /**
     * 功能描述: 根据 联动动作 中的 设备ID 查询 联动控制
     *
     * @param deviceId 设备ID
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: Integer
     */
    List<LinkageControl> findFaultControl(Long deviceId);
}
