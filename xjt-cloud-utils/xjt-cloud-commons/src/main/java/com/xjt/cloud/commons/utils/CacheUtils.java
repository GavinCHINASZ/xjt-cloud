package com.xjt.cloud.commons.utils;

import com.alibaba.fastjson.JSONObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/8 09:39
 * @Description:缓存管理类
 */
public class CacheUtils {
    private static volatile RedisUtils redisUtils = initRedisUtils();

    /**
     *
     * 功能描述:添加缓存信息列表，按属性id添加
     *
     * @param list 信息列表
     * @param typeKey 缓存key
     * @param clazz
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:44
     */
    public static <T> void setCacheByList(List<T> list, String typeKey, Class<T> clazz){
        setCacheByListByPropertyName(list, typeKey, clazz,"id");
    }

    /**
     *
     * 功能描述:添加缓存信息列表，按属性添加
     *
     * @param list 信息列表
     * @param typeKey 缓存key
     * @param clazz
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:44
     */
    public static <T> void setCacheByList(List<T> list, String typeKey, Class<T> clazz,String propertyName){
        setCacheByListByPropertyName(list, typeKey, clazz,propertyName);
    }

    /**
     * @Description 添加缓存信息列表，按属性添加
     *
     * @param list
     * @param typeKey
     * @param clazz
     * @param propertyName
     * @author wangzhiwen
     * @date 2021/1/11 11:17
     * @return void
     */
    private static <T> void setCacheByListByPropertyName(List<T> list, String typeKey, Class<T> clazz,String propertyName){
        redisUtils.dels(typeKey + "*");
        try {
            for (T t : list){
                Field field = null;
                try {
                    field = clazz.getSuperclass().getDeclaredField(propertyName);//获取父类的属性
                }catch (Exception ex){
                }
                try {
                    if (field == null) {
                        field = clazz.getDeclaredField(propertyName);//获取当前类的属性,如果父类的属性会报错
                    }
                }catch (Exception ex){
                }
                field.setAccessible(true);
                if (field != null) {
                    String property = field.get(t).toString();//设置每一列的值
                    redisUtils.set(typeKey + "_" + property, JSONObject.toJSONString(t), Constants.WEEK_CACHE_CANCEL);
                }
            }
            setCacheListInitProperty(typeKey);
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     *
     * 功能描述:添加缓存信息
     *
     * @param list 信息列表
      @param groupNames 分组列表的列名数组
     * @param typeKey 缓存key
     * @param clazz
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:44
     */
    public static <T> void setCacheByGroupList(List<T> list, String typeKey, String[] keys, String[] groupNames, Class<T> clazz){
        redisUtils.dels(typeKey + "*");
        try {
            String key;
            T t;
            List<T> groupList = new ArrayList<>();
            String groupValue = "";
            String groupValue2 = "";
            boolean isGroup = true;
            for (int i = 0; i < list.size();i++){
                t = list.get(i);
                groupList.add(t);
                if (isGroup) {
                    groupValue = getAppendValue(groupNames, clazz, t);//得到当前对象分组的值
                    isGroup = false;
                }

                if (i < list.size() - 1) {//得到下个对象分组的值
                    groupValue2 = getAppendValue(groupNames, clazz, list.get(i + 1));
                }
                //判断是否是最后一条数据,或与下一条数据不是同一个分组
                if (i == list.size() - 1 || !groupValue.equals(groupValue2)){
                    key = getAppendValue(keys, clazz, t);
                    if (key.length() > 0) {
                        redisUtils.set(typeKey + "_" + key, JSONObject.toJSONString(groupList), Constants.WEEK_CACHE_CANCEL);
                    }
                    groupList = new ArrayList<>();
                    groupValue2 = "";
                    isGroup = true;
                }
            }
            setCacheListInitProperty(typeKey);
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     *
     * 功能描述: 获取对象中多个属性的值
     *
     * @param keys
     * @param clazz
     * @param t
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/11/26 14:03
     */
    private static <T> String getAppendValue(String[] keys, Class<T> clazz,T t) {
        StringBuffer valueAppend = new StringBuffer();
        String value;
        for (String str:keys){//获取各属性的值
            value = getValue(str, clazz, t);
            if (StringUtils.isNotEmpty(value)) {
                valueAppend.append(value);
            }
        }
        return valueAppend.toString();
    }

    /**
     *
     * 功能描述: 心属性,类,与对象,获取对象内容
     *
     * @param param
     * @param clazz
     * @param t
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/11/26 11:53
     */
    private static <T> String getValue(String param, Class<T> clazz,T t){
        try {
            Field field = null;
            Object object;
            try {
                field = clazz.getDeclaredField(param);//获取当前类的属性,如果父类的属性会报错
            }catch (Exception ex){
            }
            if (null == field) {
                field = clazz.getSuperclass().getDeclaredField(param);//获取父类的属性
            }
            field.setAccessible(true);
            object = field.get(t);
            if (object != null) {
                return object.toString();
            }
        }catch (Exception ex){
        }
        return null;
    }


    /**
     *
     * 功能描述:添加缓存信息
     *
     * @param list 信息列表
     * @param typeKey 缓存key
     * @param keys 缓存key组成的属性名称字符数组
     * @param clazz
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/8 9:44
     */
    public static <T> void setCacheByList(List<T> list, String typeKey, String[] keys,  Class<T> clazz){
        redisUtils.dels(typeKey + "*");
        try {
            String key;
            for (T t : list){
                key = getAppendValue(keys, clazz, t);
                if (key.length() > 0) {
                    redisUtils.set(typeKey + "_" + key, JSONObject.toJSONString(t), Constants.WEEK_CACHE_CANCEL);
                }
            }
            setCacheListInitProperty(typeKey);
        }catch (Exception ex){
            SysLog.error(ex);
        }
    }

    /**
     *
     * 功能描述:为缓存列表key值添加已初使化标志
     *
     * @param typeKey
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/11/7 9:29
     */
    private static void setCacheListInitProperty(String typeKey){
        /*String cacheInitKey = typeKey + "Init";
        redisUtils.del(cacheInitKey);*/
    }

    /**
     *
     * 功能描述:从缓存中获取对象信息
     *
     * @param typeKey 缓存类型与缓存数使化接口路径，所配置的key
     * @param id
     * @param clazz
     * @return: T
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:30
     */
    public static <T>  T getCacheByTypeAndId(String typeKey,  Long id, Class<T> clazz){
        if (StringUtils.isEmpty(typeKey) || id == null){
            return null;
        }
        Object obj = getCacheObj(typeKey, id);//从redis缓存中取数据词典信息
        if (null != obj) {
            return JSONObject.parseObject(obj.toString(), clazz);
        }
        return null;
    }

    /**
     *
     * 功能描述:从缓存中获取对象信息
     *
     * @param typeKey 缓存类型与缓存数使化接口路径，所配置的key
     * @param key
     * @param clazz
     * @return: T
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:30
     */
    public static <T>  T getCacheByTypeAndKey(String typeKey,  String key, Class<T> clazz){
        if (StringUtils.isEmpty(typeKey) || key == null){
            return null;
        }
        Object obj = getCacheObj(typeKey, key);//从redis缓存中取数据词典信息
        if (null != obj) {
            return JSONObject.parseObject(obj.toString(), clazz);
        }
        return null;
    }

    /**
     *
     * 功能描述:从缓存中获取对象信息
     *
     * @param typeKey 缓存类型与缓存数使化接口路径，所配置的key
     * @param id
     * @return: JSONObject
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:30
     */
    public static JSONObject getCacheByTypeAndId(String typeKey,  Long id){
        if (StringUtils.isEmpty(typeKey) || id == null){
            return null;
        }
        Object obj = getCacheObj(typeKey, id);//从redis缓存中取数据词典信息
        if (null != obj) {
            return JSONObject.parseObject(obj.toString());
        }
        return null;
    }

    /**
     *
     * 功能描述:从缓存中获取对象信息
     *
     * @param typeKey 缓存类型与缓存数使化接口路径，所配置的key
     * @param key
     * @return: JSONObject
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:30
     */
    public static JSONObject getCacheByTypeAndId(String typeKey,  String key){
        if (StringUtils.isEmpty(typeKey) || key == null){
            return null;
        }
        Object obj = getCacheObj(typeKey, key);//从redis缓存中取数据词典信息
        if (null != obj) {
            return JSONObject.parseObject(obj.toString());
        }
        return null;
    }

    /**
     *
     * 功能描述:从缓存中获取对象信息
     *
     * @param typeKey 缓存类型与缓存数使化接口路径，所配置的key
     * @param id
     * @param valueKey 要取值的属性名称
     * @return: JSONObject
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:30
     */
    public static String getCacheValueByTypeAndId(String typeKey,  Long id, String valueKey){
        if (id == null || id == 0){
            return null;
        }
        return getCacheValueByTypeAndKey(typeKey, id + "", valueKey);
    }

    /**
     *
     * 功能描述: 获取分组的列表信息对象
     *
     * @param typeKey
     * @param key
     * @param clazz
     * @return: java.util.List<T>
     * @auther: wangzhiwen
     * @date: 2019/11/26 14:10
     */
    public static <T> List<T> getCacheGroupListByTypeKey(String typeKey, String key, Class<T> clazz){
        Object obj = getCacheObj(typeKey, key);//从redis缓存中取数据词典信息
        if (null != obj) {
            List<T> list = JSONObject.parseArray(obj.toString(), clazz);
            return list;
        }
        return null;
    }

    /**
     *
     * 功能描述:从缓存中获取对象信息
     *
     * @param typeKey 缓存类型与缓存数使化接口路径，所配置的key
     * @param key
     * @param valueKey 要取值的属性名称
     * @return: JSONObject
     * @auther: wangzhiwen
     * @date: 2019/8/8 10:30
     */
    public static String getCacheValueByTypeAndKey(String typeKey,  String key, String valueKey){
        if (StringUtils.isEmpty(typeKey) || StringUtils.isEmpty(key)){
            return null;
        }
        Object obj = getCacheObj(typeKey, key);//从redis缓存中取数据词典信息
        if (null != obj) {
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            return jsonObject.getString(valueKey);
        }
        return null;
    }

    /**
     *
     * 功能描述: 从缓存中取得obj对象
     *
     * @param typeKey
     * @param id
     * @return: java.lang.Object
     * @auther: wangzhiwen
     * @date: 2019/8/28 15:11
     */
    private static Object getCacheObj(String typeKey,  Long id){
        Object obj  = getCacheObj(typeKey, id + "");
        return obj;
    }

    /**
     *
     * 功能描述: 从缓存中取得obj对象
     *
     * @param typeKey
     * @param key
     * @return: java.lang.Object
     * @auther: wangzhiwen
     * @date: 2019/8/28 15:11
     */
    private static Object getCacheObj(String typeKey,  String key){
        Object obj = redisUtils.get(typeKey + "_" + key);//从redis缓存中取数据词典信息
        if (null == obj){
            if (isCacheInit(typeKey)) {//判断是否需要初使化
                synchronized ("isCacheInit") {
                    if (obj == null && isCacheInit(typeKey)){
                        try {
                            HttpUtils.httpGet(PropertyUtils.getProperty(typeKey));//调用初使化接口
                            redisUtils.set(typeKey + "Init",DateUtils.getDateTime() + Constants.CACHE_INIT_SPACE, Constants.INIT_CACHE_CANCEL);
                        }catch (Exception ex){
                            SysLog.error(ex);
                        }
                    }
                }

                obj = redisUtils.get(key);//再次从缓存中取数据
            }
        }
        return obj;
    }

    /**
     *
     * 功能描述: 以key得到缓存中的json
     *
     * @param key
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/11/5 16:24
     */
    public static String getCacheJsonStr(String key){
        Object obj = redisUtils.get(key);//从redis缓存中取数据词典信息
        if (null != obj) {
            return obj.toString();
        }
        return null;
    }

    /**
     *
     * 功能描述:判断缓存是否需要初使化
     *
     * @param typeKey
     * @return: boolean
     * @auther: wangzhiwen
     * @date: 2020/7/13 10:28
     */
    private static boolean isCacheInit(String typeKey){
        String initTime = redisUtils.getString(typeKey + "Init");
        if (StringUtils.isEmpty(initTime) || Long.parseLong(initTime) < DateUtils.getDateTime()){
            return true;
        }
        return false;
    }

    /**
     *
     * 功能描述: redis工具类初使化
     *
     * @param
     * @return: com.xjt.cloud.commons.utils.RedisUtils
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:30
     */
    public static RedisUtils initRedisUtils(){
        if (redisUtils == null){
            synchronized ("redisUtils") {
                if (redisUtils == null){
                    redisUtils = (RedisUtils) SpringBeanUtil.getBean("redisUtils");
                }
            }
        }
        return redisUtils;
    }
}
