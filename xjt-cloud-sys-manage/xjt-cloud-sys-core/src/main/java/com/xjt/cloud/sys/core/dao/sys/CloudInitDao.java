package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.sys.core.entity.CloudInit;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/16 11:35
 * @Description: 平台信息初使化管理Dao
 */
@Repository
public interface CloudInitDao {
    /**
     *
     * 功能描述: 查询平台信息初使化列表
     *
     * @param cloudInit
     * @return:  List<CloudInit>
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    List<CloudInit> findCloudInitList(CloudInit cloudInit);

    /**
     *
     * 功能描述: 查询平台信息初使化列表总数
     *
     * @return:  int
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    int findCloudInitListTotalCount(CloudInit cloudInit);

    /**
     *
     * 功能描述: 查询平台信息初使化列表
     *
     * @param cloudInit
     * @return:  List<CloudInit>
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    List<CloudInit> findCloudInitChildList(CloudInit cloudInit);

    /**
     *
     * 功能描述: 查询平台信息初使化列表总数
     *
     * @return:  int
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    int findCloudInitChildListTotalCount(CloudInit cloudInit);

}
