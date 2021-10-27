package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 10:29
 * @Description: 数据词典管理接口
 */
public interface DictService extends BaseService {
    /**
     *
     * 功能描述: 数据词典缓存初使化
     *
     * @param
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:41
     */
    Data dictCacheInit();

    /**
     *
     * 功能描述:以父类型code查询子类型列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    Data findDictItemListByParentCode(String json);

    /**
     *
     * 功能描述:以父类型code与子类型code查询子类型
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    Data findDictItemByParentCodeItemCode(String json);
}
