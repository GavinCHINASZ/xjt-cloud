package com.xjt.cloud.admin.manage.service.service.info;

import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:42
 * @Description: 资讯信息初使化信息请求接口
 */
public interface InfoApiService {
    /**
     *
     * 功能描述:
     *
     * @param infoContent
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 10:38
     */
    Data findInfoList(InfoContent infoContent);
}
