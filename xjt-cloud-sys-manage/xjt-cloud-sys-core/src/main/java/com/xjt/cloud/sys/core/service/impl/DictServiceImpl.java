package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.sys.core.dao.sys.DictDao;
import com.xjt.cloud.sys.core.service.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 10:29
 * @Description: 数据词典管理接口实现类
 */
@Service
public class DictServiceImpl extends AbstractService implements DictService {
    @Autowired
    private DictDao dictDao;

    /**
     *
     * 功能描述: 数据词典缓存初使化
     *
     * @param
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:41
     */
    @Override
    public Data dictCacheInit(){
        List<Dict> list = dictDao.findDictAllList();//得到所有未删除的数据词点信息

        if (CollectionUtils.isNotEmpty(list)){
            Map<String,List<Dict>> mapAll = new HashMap<>();
            List<Dict> itemList = new ArrayList<>();
            Dict dict;
            //把数据词典列表分按词典与词典项组装成map
            for (int i = 0; i < list.size(); i++){
                dict = list.get(i);
                itemList.add(dict);
                //判断是否是最后一条信息
                if (i < list.size() - 1 && !dict.getDictCode().equals(list.get(i + 1).getDictCode())){
                    mapAll.put(dict.getDictCode(),itemList);
                    itemList = new ArrayList<>();
                }else if (i == list.size() - 1){
                    mapAll.put(dict.getDictCode(),itemList);
                }
            }
            String jsonString = JSON.toJSONString(mapAll);//把信息转成json字符串
            redisUtils.set(Constants.DICT_CACHE_KEY,jsonString);//把信息json字符串添加到缓存中
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     *
     * 功能描述:以父类型code查询子类型列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @Override
    public Data findDictItemListByParentCode(String json){
        Dict dict = JSONObject.parseObject(json, Dict.class);
        List<Dict> list = DictUtils.getDictListByDictCode(dict.getDictCode());
        return asseData(list);
    }

    /**
     *
     * 功能描述:以父类型code与子类型code查询子类型
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @Override
    public Data findDictItemByParentCodeItemCode(String json){
        Dict dict = JSONObject.parseObject(json, Dict.class);
        dict = DictUtils.getDictByDictAndItemCode(dict.getDictCode(), dict.getItemCode());
        return asseData(dict);
    }
}
