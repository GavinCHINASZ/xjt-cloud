package com.xjt.cloud.project.core.dao.device;

import com.xjt.cloud.project.core.entity.device.CheckItemConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/11 09:30
 * @Description:
 */
@Repository
public interface CheckItemConfigDao {

    /**
     *
     * 功能描述:新增pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckItemConfig
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer saveCheckItemConfig(CheckItemConfig checkItemConfig);

    /**
     *
     * 功能描述:修改pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckItemConfig
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer modifyCheckItemConfig(CheckItemConfig checkItemConfig);

    /**
     *
     * 功能描述:修改pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckItemConfig
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer delCheckItemConfig(List<CheckItemConfig> list);

    /**
     *
     * 功能描述:新增pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckItemConfig
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer saveCheckItemConfigList(List<CheckItemConfig> checkItemConfig);

    /**
     *
     * 功能描述:修改pc检测项配置信息
     *
     * @param
     * @return: com.xjt.cloud.device.core.entity.CheckItemConfig
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    Integer modifyCheckItemConfigList(List<CheckItemConfig> checkItemConfig);

    /**
     *
     * 功能描述:查询pc检测项配置信息列表总行数
     *
     * @param checkItemConfig
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    Integer findCheckItemConfigListCount(CheckItemConfig checkItemConfig);

    /**
     *
     * 功能描述:查询pc检测项配置信息列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.CheckItemConfig>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    List<CheckItemConfig> findCheckItemConfigList(CheckItemConfig checkItemConfig);

    /**
     *
     * 功能描述:查询pc检测项配置信息列表
     *
     * @param
     * @return: java.util.List<com.xjt.cloud.device.core.entity.CheckItemConfig>
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:15
     */
    CheckItemConfig findCheckResult(CheckItemConfig checkItemConfig);
}
