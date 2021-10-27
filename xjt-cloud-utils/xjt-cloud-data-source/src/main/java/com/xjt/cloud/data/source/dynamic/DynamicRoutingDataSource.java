package com.xjt.cloud.data.source.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态切换数据源
 *
 * @author wangzhiwen
 * @date 2017-08-15 11:37
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * 功能描述: 根据数据源名称，切换数据源
     *
     * @param
     * @return: java.lang.Object
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:15
     */
    @Override
    protected Object determineCurrentLookupKey() {
        logger.debug("当前数据源为[{}]", DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();//切换数据源
    }
}
