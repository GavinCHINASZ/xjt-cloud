package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.QuartzConfig;

import java.util.List;

/**
 * QuartzConfigDao
 *
 * @author huanggc
 * @date 2020/09/15
 */
public interface QuartzConfigDao {
    /**
     * 功能描述:
     *
     * @param id id
     * @author huanggc
     * @date 2020/09/15
     * @return int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 功能描述:
     *
     * @param quartzConfig 实体
     * @author huanggc
     * @date 2020/09/15
     * @return int
     */
    int insert(QuartzConfig quartzConfig);

    /**
     * 功能描述:
     *
     * @param id id
     * @author huanggc
     * @date 2020/09/15
     * @return QuartzConfig
     */
    QuartzConfig selectByPrimaryKey(Long id);

    /**
     * 功能描述:
     *
     * @author huanggc
     * @date 2020/09/15
     * @return QuartzConfig
     */
    List<QuartzConfig> selectAll();

    /**
     * 功能描述:更新
     *
     * @param quartzConfig 实体
     * @author huanggc
     * @date 2020/09/15
     * @return int
     */
    int updateByPrimaryKey(QuartzConfig quartzConfig);

    /**
     * 功能描述:
     *
     * @param quartzConfig 实体
     * @author huanggc
     * @date 2020/09/15
     */
    void saveTaskLog(QuartzConfig quartzConfig);

    /**
     * 功能描述:查询 定时任务
     *
     * @param quartzConfig 实体
     * @author huanggc
     * @date 2020/09/15
     * @return List<QuartzConfig>
     */
    List<QuartzConfig> findQuartzConfigList(QuartzConfig quartzConfig);

    /**
     * 功能描述:查询 定时任务 数量
     *
     * @author huanggc
     * @date 2020/09/15
     * @return Integer
     */
    Integer findQuartzConfigListTotalCount();

}