package com.xjt.cloud.log.manage.service.service;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 10:29
 * @Description: 安全日志管理接口
 */
public interface SecurityLogService extends BaseService {

    /**
     *
     * 功能描述:保存日志信息
     *
     * @param type 日志类型，数据词典表中的code值，
     * @param loginName 登录用户名
     * @param content 日志内容
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/6 10:41
     */
    Data saveSecurityLog(String type, String loginName,String content, Long sourceId, Integer sourceType);
    /**
     *
     * 功能描述:保存日志信息
     *
     * @param type 日志类型，数据词典表的值
     * @param loginName 登录用户名
     * @param newT 信息对象
     * @param oldT 旧的信息对象，只有在修改时需要
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @loginName
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/6 10:41
     */
    <T> Data saveSecurityLog(String type, String loginName, T newT, T oldT, Long sourceId, Integer sourceType);

    /**
     *
     * 功能描述:
     *
     * @param type
     * @param loginName
     * @param list
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/19 15:54
     */
    <T> Data saveSecurityLog(String type, String loginName, List<T> list, Long sourceId, Integer sourceType);
}
