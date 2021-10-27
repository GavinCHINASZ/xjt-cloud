package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.PutStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/30 11:02
 * @Description: 入库管理Dao
 */
@Repository
public interface PutStorageDao {
    /**
     *
     * 功能描述:查询入物信息列表
     *
     * @param putStorage
     * @return: List<com.xjt.cloud.admin.manage.entity.iot.Product.PutStorage>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    List<PutStorage> findPutStorageList(PutStorage putStorage);

    /**
     *
     * 功能描述:查询入物信息
     *
     * @param putStorage
     * @return: com.xjt.cloud.admin.manage.entity.iot.Product.PutStorage
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    PutStorage findPutStorage(PutStorage putStorage);

    /**
     *
     * 功能描述:保存入物信息
     *
     * @param putStorage
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int findPutStorageListTotalCount(PutStorage putStorage);

    /**
     *
     * 功能描述:保存入物信息
     *
     * @param putStorage
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int savePutStorage(PutStorage putStorage);

    /**
     *
     * 功能描述:修改入物信息
     *
     * @param putStorage
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int modifyPutStorage(PutStorage putStorage);
}
