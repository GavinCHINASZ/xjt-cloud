package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.PutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/30 11:17
 * @Description: 入库信息关联产品Dao
 */
@Repository
public interface PutStorageProductDao {
    /**
     *
     * 功能描述:查询入库信息关联产品列表
     *
     * @param putStorageProduct
     * @return: List<PutStorageProduct>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    List<PutStorageProduct> findPutStorageProductList(PutStorageProduct putStorageProduct);

    /**
     *
     * 功能描述:查询入库信息关联产品列表总数
     *
     * @param putStorageProduct
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int findPutStorageProductListTotalCount(PutStorageProduct putStorageProduct);

    /**
     *
     * 功能描述:保存入库信息关联产品
     *
     * @param putStorageProduct
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int savePutStorageProduct(PutStorageProduct putStorageProduct);

    /**
     *
     * 功能描述:修改入库信息关联产品
     *
     * @param putStorageProduct
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int modifyPutStorageProduct(PutStorageProduct putStorageProduct);

    /**
     * 功能描述:修改入物信息
     *
     * @param putStorageProductList 入库信息关联物产品实体类list
     * @return int
     * @author huanggc
     * @date 2020/09/23
     */
    int savePutStorageProductList(@Param("putStorageProductList") List<PutStorageProduct> putStorageProductList,
                                  @Param("putStorage") PutStorage putStorage);
}
