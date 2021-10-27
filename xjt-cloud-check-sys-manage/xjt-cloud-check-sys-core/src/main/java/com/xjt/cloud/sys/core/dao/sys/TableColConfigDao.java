package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.sys.core.entity.TableColConfig;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/31 10:15
 * @Description: 页面表格显示配置DAO
 */
public interface TableColConfigDao {
    /**
     *
     * 功能描述:查询用户项目表格配置信息列表
     *
     * @param tableColConfig
     * @return: List<TableColConfig>
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    List<TableColConfig> findTableColConfigList(TableColConfig tableColConfig);

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param tableColConfig
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    int saveTableColConfig(TableColConfig tableColConfig);

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param tableColConfig
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    int modifyTableColConfig(TableColConfig tableColConfig);
}
