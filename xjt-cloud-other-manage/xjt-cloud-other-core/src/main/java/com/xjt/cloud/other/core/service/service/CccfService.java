package com.xjt.cloud.other.core.service.service;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName CccfService cccf查询
 * @Author zhangZaiFa
 * @Date 2020-01-13 15:15
 * @Description
 */
public interface CccfService {

    /**@MethodName: findLabelInfoByCode
     * @Description: CCCF按条形码查询产品信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/1/13 14:15
     **/
    Data findLabelInfoByCode(String json);
}
