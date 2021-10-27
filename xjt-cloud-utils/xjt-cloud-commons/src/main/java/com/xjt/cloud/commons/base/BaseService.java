package com.xjt.cloud.commons.base;

import com.xjt.cloud.commons.utils.Data;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:51
 * @Description:
 */
public interface BaseService {

    /**
     *
     * 功能描述: 以对象信息为条件，查询对象信息
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    <T extends BaseEntity> Data findObj(T t);

    /**
     *
     * 功能描述:  以对象信息为条件，查询对象信息列表
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    <T extends BaseEntity> Data findList(T t);

    /**
     *
     * 功能描述: 保存对象
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    <T extends BaseEntity> Data saveObj(T t);

    /**
     *
     * 功能描述: 修改对象，或逻辑删除
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    <T extends BaseEntity> Data modifyObj(T t);

    /**
     *
     * 功能描述: 查询总行数
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    <T extends BaseEntity> Data countObj(T t);
}
