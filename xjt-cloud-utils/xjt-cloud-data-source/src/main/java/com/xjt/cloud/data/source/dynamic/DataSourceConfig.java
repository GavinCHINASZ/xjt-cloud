package com.xjt.cloud.data.source.dynamic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 19:03
 * @Description: 多数据源资源配置文件
 */
@Component
@ConfigurationProperties
public class DataSourceConfig {

    /**
     * master 为主数据源，为默认数据源  write开头的为写源，read为只读源 必需
     * 数据源名称与dao包名对应关系，如：writeSys 则是，主数据源对应dao.sys.*下的所有dao；readSys则为从数据源，对应dao.sys.*下的所有dao
     * 同一包下的数据源， 同一库同一类型（读/写）顺序排列
     *
     */
    @Value("${spring.datasource.hikari.data-source-names-package}")
    private String dataSourceNamesPackage;
    //表实应实体扫描路径，mapper里对应的sql参数type
    @Value("${spring.datasource.hikari.mybatis.type-aliases-package}")
    private String typeAliasesPackage;
    //mapper扫描路径
    @Value("${spring.datasource.hikari.mybatis.mapper-locations}")
    private String mapperLocations;
    //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
    @Value("${spring.datasource.hikari.connection-timeout}")
    private String connectionTimeout;
    //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
    @Value("${spring.datasource.hikari.idle-timeout}")
    private String idleTimeout;
    //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
    @Value("${spring.datasource.hikari.max-lifetime}")
    private String maxLifetime;
    //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private String maximumPoolSize;
    //最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size
    @Value("${spring.datasource.hikari.minimum-idle}")
    private String minimumIdle;
    //指定校验连接合法性执行的sql语句
    @Value("${spring.datasource.hikari.connection-test-query}")
    private String connectionTestQuery;

    public String getDataSourceNamesPackage() {
        return dataSourceNamesPackage;
    }

    public void setDataSourceNamesPackage(String dataSourceNamesPackage) {
        this.dataSourceNamesPackage = dataSourceNamesPackage;
    }

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(String idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public String getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(String maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public String getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(String maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public String getConnectionTestQuery() {
        return connectionTestQuery;
    }

    public void setConnectionTestQuery(String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }

    public String getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(String minimumIdle) {
        this.minimumIdle = minimumIdle;
    }
}
