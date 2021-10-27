package com.xjt.cloud.admin.manage.dao.info;

import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:41
 * @Description: 资讯信息初使化信息配置接口
 */
@Repository
public interface InfoManageDao {

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: List<InfoManage>
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    List<InfoContent> findInfoManageList(InfoContent infoManage);

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: List<InfoManage>
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Integer findInfoManageListTotalCount(InfoContent infoManage);

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    int saveInfoManage(InfoContent infoManage);

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    int modifyInfoManage(InfoContent infoManage);

    ///////////////////////////////////////资讯信息内容/////////////////////////////////////

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: List<InfoContent>
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    List<InfoContent> findInfoContentList(InfoContent infoContent);

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: List<InfoContent>
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Integer findInfoContentListTotalCount(InfoContent infoContent);

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    int saveInfoContent(InfoContent infoContent);

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    int modifyInfoContent(InfoContent infoContent);
}
