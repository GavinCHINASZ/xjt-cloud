package com.xjt.cloud.data.source.dynamic;

import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多数据源配置
 *
 * @author wangzhiwen
 * @date 2017 -08-15 11:37
 */
@Configuration
public class DataSourceConfigurer implements ApplicationContextAware {
    @Autowired
    private DataSourceConfig dataSourceConfig;
    private ConfigurableApplicationContext applicationContext;

    private final  String DATASOURCE_BEAN_CLASS = "com.zaxxer.hikari.HikariDataSource";//数据连接池

    private final String DATASOURCE_CONFIG_PREFIX = "spring.datasource.hikari";//数据源配置开始字符串

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext)applicationContext;
    }
    /**
     * 动态数据源
     *
     * @return the data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        String[] dataSourceNamesPackages = dataSourceConfig.getDataSourceNamesPackage().split(",");
        Map<Object, Object> dataSourceMap = new HashMap<>(dataSourceNamesPackages.length);//所有的数据源key

        String key;//资源文件取数据源名称的key
        String packages;//dao的下级包名
        String dataSourceName;//数据源名称
        String driverClassName;//com.mysql.cj.jdbc.Driver
        String jdbcUrl;
        String username;
        String password;
        String[] keyValue;//数据源名称与dao包名对应关系

        Map<String, List<String>> writeMap = new HashMap<>();
        Map<String, List<String>> readMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        int len = dataSourceNamesPackages.length - 1;
        for (int i = 0;i < dataSourceNamesPackages.length; i++){
            keyValue = dataSourceNamesPackages[i].split(":");
            key = DATASOURCE_CONFIG_PREFIX + "." + keyValue[0];
            packages = keyValue[1].toUpperCase();//数据源对应的dao与的包名
            dataSourceName = PropertyUtils.getProperty(key + ".name");//数据源名称
            if (StringUtils.isEmpty(dataSourceName)){//判断是否存在该数据源配置，如不存在，直接下一循环
                continue;
            }
            driverClassName = PropertyUtils.getProperty(key + ".driver-class-name");//数据连接驱动
            jdbcUrl = PropertyUtils.getProperty(key + ".jdbc-url");//连接url
            username = PropertyUtils.getProperty(key + ".username");//用户名
            password = PropertyUtils.getProperty(key + ".password");//密码
            initDataSource(dataSourceName,driverClassName,jdbcUrl,username,password);
            dataSourceMap.put(dataSourceName, applicationContext.getBean(dataSourceName));
            if (dataSourceName.toUpperCase().startsWith(DynamicDataSourceContextHolder.MASTER)){//判断是否是主数据源，默认数据源,是否是以MASTER开始
                // 将主数据源设置为默认值
                dynamicRoutingDataSource.setDefaultTargetDataSource(applicationContext.getBean(dataSourceName));
            }else {//组装非主数据源的key值与数据库对应关系
                //判断当前数据源所对应的库，与读写是否相同，如不是，则表示一个dao的一个包，把对应的读/写数据源处理完成
                if (i < len && (!packages.toUpperCase().equals(dataSourceNamesPackages[i + 1].split(":")[1].toUpperCase()) ||
                        !key.substring(0,6).toUpperCase().equals(dataSourceNamesPackages[i + 1].split(":")[0].substring(0,6).toUpperCase()))){
                    list.add(dataSourceName);
                    if (dataSourceName.toUpperCase().startsWith(DynamicDataSourceContextHolder.WRITE_TYPE)) {//判断是否是以write开头命名的数据源，是则为写数据源
                        writeMap.put(packages, list);
                    }else {//读数据源
                        readMap.put(packages, list);
                    }
                    list = new ArrayList<>();
                }else if (i == len){
                    list.add(dataSourceName);
                    if (dataSourceName.toUpperCase().startsWith(DynamicDataSourceContextHolder.WRITE_TYPE)) {//判断是否是以write开头命名的数据源，是则为写数据源
                        writeMap.put(packages, list);
                    }else {//读数据源
                        readMap.put(packages, list);
                    }
                }
            }

            final String threadDataSourceName = dataSourceName;
            DynamicDataSourceContextHolder.CONTEXT_HOLDER = new ThreadLocal<String>(){
                @Override
                protected String initialValue() {//为每一个源添加线程，并命名
                    return new String(threadDataSourceName);
                }
            };
        }
        if (writeMap.size() > 0) {
            DynamicDataSourceContextHolder.dataSourceTypeMap.put(DynamicDataSourceContextHolder.WRITE_TYPE, writeMap);//添加写源
        }
        if (readMap.size() > 0) {
            DynamicDataSourceContextHolder.dataSourceTypeMap.put(DynamicDataSourceContextHolder.READ_TYPE, readMap);//添加读源
        }

        // 将主数据源和从数据源设置为目标数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        // 将数据源键放入DataSourceContextHolder以判断数据源是否存在
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }

    /**
     * 添加数据源
     * 为了防止多线程添加同一个数据源，这里采用同步,同时会判断是否已存在
     * @param dataSourceName
     * @param driverClassName
     * @param jdbcUrl
     * @param username
     * @param password
     * @return String 新建数据源对应的key，如果已经存在，则返回之前的key。
     */
    public synchronized void initDataSource(String dataSourceName, String driverClassName, String jdbcUrl, String username, String password){
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder;
        // 数据源连接信息配置 创建bean
        beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DATASOURCE_BEAN_CLASS);
        beanDefinitionBuilder.getBeanDefinition().setAttribute("id", dataSourceName);
        beanDefinitionBuilder.getBeanDefinition().setAttribute("destroy-method", "close");
        beanDefinitionBuilder.addPropertyValue("driverClassName", driverClassName);
        beanDefinitionBuilder.addPropertyValue("jdbcUrl", jdbcUrl);
        beanDefinitionBuilder.addPropertyValue("username", username);
        beanDefinitionBuilder.addPropertyValue("password", password);
        if (dataSourceName.toUpperCase().startsWith(DynamicDataSourceContextHolder.MASTER)) {//判断 是否是主数据源必需设置主数据源
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            beanDefinition.setPrimary(true);
        }

        //数据源连接公共配置设置
        //连接只读数据库时配置为true， 保证安全
        if (dataSourceName.toUpperCase().startsWith(DynamicDataSourceContextHolder.READ_TYPE)) {//判断是否是以读为开始的数据源，是则设置为只读
            beanDefinitionBuilder.addPropertyValue("readOnly", true);
        }
        //等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
        beanDefinitionBuilder.addPropertyValue("connectionTimeout", Integer.parseInt(dataSourceConfig.getConnectionTimeout()));
        //一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
        beanDefinitionBuilder.addPropertyValue("idleTimeout", Integer.parseInt(dataSourceConfig.getIdleTimeout()));
        //一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒，参考MySQL wait_timeout参数（show variables like '%timeout%';）
        beanDefinitionBuilder.addPropertyValue("maxLifetime", Integer.parseInt(dataSourceConfig.getMaxLifetime()));
        //连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
        beanDefinitionBuilder.addPropertyValue("maximumPoolSize", Integer.parseInt(dataSourceConfig.getMaximumPoolSize()));
        //最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size
        beanDefinitionBuilder.addPropertyValue("minimumIdle", Integer.parseInt(dataSourceConfig.getMinimumIdle()));
        //指定校验连接合法性执行的sql语句
        beanDefinitionBuilder.addPropertyValue("connectionTestQuery", dataSourceConfig.getConnectionTestQuery());

        // 注册bean
        beanFactory.registerBeanDefinition(dataSourceName, beanDefinitionBuilder.getBeanDefinition());
    }

    /**
     * Sql会话工厂bean。
     * 这里配置SqlSessionFactory的数据源
     * <p>
     * 需要添加@{@code @ConfigurationProperties(prefix = "mybatis")}, 如果使用*.xml文件，
     * 则 {@code 'mybatis.type-aliases-package'} 和 {@code 'mybatis.mapper-locations'}应该设置在
     * {@code 'application.properties'} 否则会出现无效的语句异常
     *
     * @return sql会话工厂bean
     */
    @Bean(name = "sqlSessionFactoryBean")
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // mybatis配置mybatis
        sqlSessionFactoryBean.setTypeAliasesPackage(dataSourceConfig.getTypeAliasesPackage());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dataSourceConfig.getMapperLocations()));
        // 这里很重要，如果不配置这个，将无法切换数据源
        // 将所有数据源放入SqlSessionFactoryBean，然后自动配置SqlSessionFactory
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * 事务管理器平台事务管理器。
     *
     * @return 平台事务管理器
     */
    @Bean(name="dataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager  transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

