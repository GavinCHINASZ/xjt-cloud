package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.sys.core.dao.sys.TableColConfigDao;
import com.xjt.cloud.sys.core.entity.TableColConfig;
import com.xjt.cloud.sys.core.service.service.TableColConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/10/31 10:14
 * @Description: 页面表格显示配置接口实现类
 */
@Service
public class TableColConfigServiceImpl extends AbstractService implements TableColConfigService {
    @Autowired
    private TableColConfigDao tableColConfigDao;

    /**
     *
     * 功能描述:查询用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    @Override
    public Data findTableColConfig(String json){
        TableColConfig tableColConfig = JSONObject.parseObject(json, TableColConfig.class);
        //从缓存中取默认值
        //TableColConfig defaultConfig = CacheUtils.getCacheByTypeAndKey(Constants.TABLE_COL_CONFIG_CACHE_KEY, "00" + tableColConfig.getType(), TableColConfig.class);

        if (tableColConfig == null) {//如果未传参，表示缓存初使化
            List<TableColConfig> list = tableColConfigDao.findTableColConfigList(null);
            if (CollectionUtils.isNotEmpty(list)){//如果数据库中有数据，把数据添加到缓存中
                String[] keys = {"userId","projectId","type"};
                CacheUtils.setCacheByList(list, Constants.TABLE_COL_CONFIG_CACHE_KEY, keys, TableColConfig.class);
                return null;
                //defaultConfig = CacheUtils.getCacheByTypeAndKey(Constants.TABLE_COL_CONFIG_CACHE_KEY, "00" + tableColConfig.getType(), TableColConfig.class);
            }
        }
        TableColConfig userProjectConfig = CacheUtils.getCacheByTypeAndKey(Constants.TABLE_COL_CONFIG_CACHE_KEY,
                tableColConfig.getUserId().toString() + tableColConfig.getProjectId().toString() + tableColConfig.getType(), TableColConfig.class);

        JSONObject jsonObject =  new JSONObject();
        //jsonObject.put("defaultConfig", defaultConfig);
        jsonObject.put("userProjectConfig", userProjectConfig);
        return asseData(jsonObject);
    }

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    @Override
    public Data saveTableColConfig(String json){
        TableColConfig tableColConfig = JSONObject.parseObject(json, TableColConfig.class);
        List<TableColConfig> list = tableColConfigDao.findTableColConfigList(tableColConfig);
        int num;
        if (CollectionUtils.isNotEmpty(list)){
            tableColConfig.setId(list.get(0).getId());
            num = tableColConfigDao.modifyTableColConfig(tableColConfig);
        }else{
            num = tableColConfigDao.saveTableColConfig(tableColConfig);
        }
        if (num > 0){
            setCache(tableColConfig);
        }
        return asseData(tableColConfig);
    }

    /**
     *
     * 功能描述:保存用户项目表格配置信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/10/31 10:36
     */
    @Override
    public Data modifyTableColConfig(String json){
        TableColConfig tableColConfig = JSONObject.parseObject(json, TableColConfig.class);
        int num = tableColConfigDao.modifyTableColConfig(tableColConfig);
        if (num > 0){
            setCache(tableColConfig);
        }
        return asseData(num);
    }

    /**
     *
     * 功能描述:
     *
     * @param tableColConfig
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/10/31 14:45
     */
    private void setCache(TableColConfig tableColConfig){
        String key = Constants.TABLE_COL_CONFIG_CACHE_KEY + "_" + tableColConfig.getUserId() + tableColConfig.getProjectId() + tableColConfig.getType();
        redisUtils.del(key);
        redisUtils.set(key, JSONObject.toJSONString(tableColConfig), Constants.WEEK_CACHE_CANCEL);
    }
}
