package com.xjt.cloud.data.source.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * 多数据源动态切换管理类
 *
 * @author wangzhiwen
 * @date 2017 -08-15 14:26
 */
public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    public static final String MASTER = "MASTER";
    public static final String WRITE_TYPE = "WRITE";
    public static final String READ_TYPE = "READ";
    private static final String DAO = ".DAO.";//dao的包名，不区分大小写
    //读的方法名开始关键字
    private static final String[] QUERY_PREFIX = {"SELECT","FIND","GET","SEL"};
    //写的方法名开始关键字
    private static final String[] WRITE_PREFIX = {"MODIFY","EDIT","SAVE","ADD","DEL","UPDATE"};
    /**
     * 为每个线程维护变量，以避免影响其他线程
     */
    public static  ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 所有数据源key列表
     */
    public static List<Object> dataSourceKeys = new ArrayList<>();

    /**
     * 数据源map，包含非默认数源的所有读写数据源
     * 分为两个map 一个读的map 与一个写的map
     */
    public static Map<String, Map<String, List<String>>> dataSourceTypeMap = new HashMap<>(2);

    /**
     *
     * 功能描述: 切换数据源，只是切换key，在DynamicRoutingDataSource类中才正式切换
     *
     * @param key
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:24
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     *
     * 功能描述: 切换为主数据源，只是切换key，在DynamicRoutingDataSource类中才正式切换
     *
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:24
     */
    public static void useDefaultDataSource() {
        CONTEXT_HOLDER.set(MASTER);
    }

    /**
     *
     * 功能描述: 切换为主数据源，只是切换key，在DynamicRoutingDataSource类中才正式切换
     *
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:24
     */
    public static void removeDataSource() {
        CONTEXT_HOLDER.remove();
    }

    /**
     *
     * 功能描述:计算所要切换到的数据源的key
     *
     * @param classPath dao方法所在类的路径
     * @param methodName dao方法名称
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:27
     */
    public static void useDataSource(String classPath, String methodName) {
        try {
            Map<String, List<String>> map;
            if (isQueryMethod(methodName)){//根据方法名称得到读或写数据源map
                map = dataSourceTypeMap.get(READ_TYPE);
            }else if (isWriteMethod(methodName)){//根据方法名称得到读或写数据源map
                map = dataSourceTypeMap.get(WRITE_TYPE);
            }else{//如果不是读与写，直接为主数据源
                useDefaultDataSource();//切换失败时切换到默认数据源
                return;
            }
            if (map == null || map.size() == 0){//判断是否存在要切换的数据源
                useDefaultDataSource();//切换失败时切换到默认数据源
                return;
            }
            //得到数据源列表
            Iterator<Map.Entry<String,List<String>>> iterator = map.entrySet().iterator();
            List<String> list = new ArrayList<>();
            while (iterator.hasNext()){//找到与切入方法类所属包对应的数据源key
                Map.Entry<String,List<String>> entry = iterator.next();
                if (classPath.toUpperCase().contains(DAO + entry.getKey() + ".")){//判断类的包路径是否与数据源列表的key所对应
                    list = entry.getValue();
                    break;
                }
            }
            //随机得到所对应的数据源列表
            Random rand = new Random();
            int datasourceKeyIndex = rand.nextInt(list.size());
            setDataSourceKey(String.valueOf(list.get(datasourceKeyIndex)));
        } catch (Exception e) {
            logger.info("切换从数据源失败，错误消息是 {}");
            useDefaultDataSource();//切换失败时切换到默认数据源
        }
    }

    /**
     *
     * 功能描述: 得到数据源有key，用已切换数据源
     *
     * @param
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:33
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 检查给定数据源是否在当前数据源列表中
     *
     * @param key the key
     * @return boolean boolean
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }

    /**
     *
     * 功能描述:判断切入的方法名，是否是以读的关键开头
     *
     * @param methodName
     * @return: java.lang.Boolean
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:34
     */
    private static Boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.toUpperCase().startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * 功能描述:判断切入的方法名，是否是以写的关键开头
     *
     * @param methodName
     * @return: java.lang.Boolean
     * @auther: wangzhiwen
     * @date: 2019/7/4 16:34
     */
    private static Boolean isWriteMethod(String methodName) {
        for (String prefix : WRITE_PREFIX) {
            if (methodName.toUpperCase().startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
