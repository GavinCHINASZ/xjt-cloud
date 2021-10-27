package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.sys.CloudInit;
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
     * 功能描述: 新增平台信息初使化信息
     *
     * @param cloudInit
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    int saveCloudInit(CloudInit cloudInit);

    /**
     *
     * 功能描述: 修改平台信息初使化信息
     *
     * @param cloudInit
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    int modifyCloudInit(CloudInit cloudInit);
}
