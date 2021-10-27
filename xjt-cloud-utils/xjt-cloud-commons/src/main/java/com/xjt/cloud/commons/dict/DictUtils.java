package com.xjt.cloud.commons.dict;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 15:35
 * @Description: 数据词典公共工具类
 */
public class DictUtils {
    private static volatile RedisUtils redisUtils = CacheUtils.initRedisUtils();
    private final static String PROJECT_SYS_CONFIG = "PROJECT_SYS_CONFIG";//项目系统配置的字典code
    private final static String PROJECT_PARAMETER_CONFIG = "PROJECT_PARAMETER_CONFIG";//项目系统配置的字典项code

    /**
     *
     * 功能描述:以数据词典code值取得词典信息列表
     *
     * @param dictCode
     * @return: java.util.List<com.xjt.cloud.commons.dict.Dict>
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:45
     */
    public static List<Dict> getDictListByDictCode(String dictCode){
        Map<String,JSONArray> map = getDictMap(false);//从缓存中取得数据词典信息
        if (CollectionUtils.isEmpty(map)){//如果为空，重新从数据库中取值
            map = getDictMap(true);//从缓存中取得数据词典信息
        }

        JSONArray jsonArray = map.get(dictCode);//
        if (null == jsonArray){//如果为空，重新从数据库中取值
            map = getDictMap(true);
            jsonArray = map.get(dictCode);
        }
        if (null == jsonArray){//如果为空，重新从数据库中取值
            return null;
        }
        List<Dict> list = JSONObject.parseArray(jsonArray.toJSONString(),Dict.class);//取得词典项列表
        if (CollectionUtils.isNotEmpty(map)){
            return list;
        }

        return null;
    }

    /**
     *
     * 功能描述:以数据词典code与词典项值取得词典信息
     *
     * @param dictCode 词典code
     * @param itemCode 典项值
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:32
     */
    public static Dict getDictByDictAndItemCode(String dictCode, String itemCode){
        List<Dict> list = getDictListByDictCode(dictCode);//取得词典项列表
        if (CollectionUtils.isNotEmpty(list)){
            for (Dict dict : list){
                if (itemCode.equals(dict.getItemCode())){//取得与参数相同的词典信息
                    return dict;
                }
            }
        }
        return null;
    }

    /**
     *
     * 功能描述:以数据词典code与词典项值取得词典信息
     *
     * @param dictCode 词典code
     * @param itemCode 典项值
     * @param propertyName 要获取的属性名称
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:32
     */
    public static String getDictPropertyByDictItemCodePropertyName(String dictCode, String itemCode, String propertyName){
        Dict dict = getDictByDictAndItemCode(dictCode, itemCode);
        if (dict != null){
            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(dict);
            return jsonObject.getString(propertyName);
        }
        return null;
    }

    /**
     *
     * 功能描述:以数据词典code与词典项值取得词典信息
     *
     * @param dictCode 词典code
     * @param itemValue 典项值
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:32
     */
    public static Dict getDictByDictAndItemValue(String dictCode, String itemValue){
        List<Dict> list = getDictListByDictCode(dictCode);//取得词典项列表
        if (CollectionUtils.isNotEmpty(list)){
            for (Dict dict : list){
                if (itemValue.equals(dict.getItemValue())){//取得与参数相同的词典信息
                    return dict;
                }
            }
        }
        return null;
    }

    /**
     *
     * 功能描述:以数据词典code与词典项值取得词典信息
     *
     * @param dictCode 词典code
     * @param itemValue 典项值
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:32
     */
    public static String getItemNameByDictAndItemValue(String dictCode, String itemValue){
        Dict dict = getDictByDictAndItemValue(dictCode, itemValue);
        if (null != dict){
            return dict.getItemName();
        }
        return null;
    }

    /**
     *
     * 功能描述:以数据词典code与词典项值取得词典信息的值
     *
     * @param dictCode 词典code
     * @param itemCode 典项值
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:32
     */
    public static String getDictItemValueByDictAndItemCode(String dictCode, String itemCode){
        Dict dict = getDictByDictAndItemCode(dictCode,itemCode);
        if (null == dict){
            return null;
        }
        return dict.getItemValue();
    }

    /**
     * @Description 以参数key获取数据词典中的配置
     *
     * @param key
     * @author wangzhiwen
     * @date 2021/2/24 13:59
     * @return java.lang.String
     */
    public static String getProjectParameterConfigByKey(String key){
        String PROJECT_PARAMETER_CONFIG_CODE = getDictItemValueByDictAndItemCode(PROJECT_SYS_CONFIG, PROJECT_PARAMETER_CONFIG);
        if (StringUtils.isEmpty(PROJECT_PARAMETER_CONFIG_CODE)){
            return null;
        }
        return getDictItemValueByDictAndItemCode(PROJECT_PARAMETER_CONFIG_CODE,key);
    }

    /**
     *
     * 功能描述:
     *
     * @param
     * @return: java.util.Map<java.lang.String,java.util.List<com.xjt.cloud.commons.dict.Dict>>
     * @auther: wangzhiwen
     * @date: 2019/7/2 16:28
     */
    private static Map<String, JSONArray> getDictMap(boolean isClear){
        Object obj = redisUtils.get(Constants.DICT_CACHE_KEY);//从redis缓存中取数据词典信息
        Map<String,JSONArray> map;
        if (null == obj || isClear){//判断缓存中是否存在数据词典信息
            //如果缓存中不存在数据
            HttpUtils.httpGet(PropertyUtils.getProperty(Constants.DICT_CACHE_INIT_URL));//调用数据词典初使化接口
            obj = redisUtils.get(Constants.DICT_CACHE_KEY);//再次从缓存中取数据
            if (null == obj){
                return null;
            }
            map = (Map<String, JSONArray>) JSON.parse(obj.toString());//把从缓从中取得的词典信息返回
        }else {
            map = (Map<String, JSONArray>) JSON.parse(obj.toString());//把从缓从中取得的词典信息返回
        }
        return map;
    }
}
