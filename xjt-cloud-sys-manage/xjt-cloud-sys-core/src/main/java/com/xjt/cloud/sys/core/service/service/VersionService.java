package com.xjt.cloud.sys.core.service.service;


import com.xjt.cloud.commons.utils.Data;

import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:45
 * @Description: app版本管理接口
 */
public interface VersionService {

    /**
     *
     * 功能描述:查询版本列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data findVersionList(String json);

    /**
     *
     * 功能描述:查询版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data findVersion(String json);

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data saveVersion(String json);

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data modifyVersion(String json);

    /**
     *
     * 功能描述:删除版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Data delVersion(String json);

    /**@MethodName: findByVersion
     * @Description: 查询版本信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/6/16 23:59
     **/
    Map<String,Object> findByVersion();
}
