package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.OutStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName OutStorageDao
 * @Description 出库管理
 * @Author wangzhiwen
 * @Date 2020/8/21 15:56
 **/
@Repository
public interface OutStorageDao {
    /**
     *
     * 功能描述:查询出库信息列表
     *
     * @param outStorage
     * @return: List<com.xjt.cloud.admin.manage.entity.iot.Product.OutStorage>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    List<OutStorage> findOutStorageList(OutStorage outStorage);

    /**
     *
     * 功能描述:查询出库信息
     *
     * @param outStorage
     * @return: com.xjt.cloud.admin.manage.entity.iot.Product.OutStorage
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    OutStorage findOutStorage(OutStorage outStorage);

    /**
     *
     * 功能描述:保存出库信息
     *
     * @param outStorage
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int findOutStorageListTotalCount(OutStorage outStorage);

    /**
     *
     * 功能描述:保存出库信息
     *
     * @param outStorage
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int saveOutStorage(OutStorage outStorage);

    /**
     *
     * 功能描述:修改出库信息
     *
     * @param outStorage
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int modifyOutStorage(OutStorage outStorage);
}
