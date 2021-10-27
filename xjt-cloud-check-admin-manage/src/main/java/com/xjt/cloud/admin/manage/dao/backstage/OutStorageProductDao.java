package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.OutStorageProduct;

import java.util.List;

/**
 * @ClassName OutStorageProductDao
 * @Description  出库管理
 * @Author wangzhiwen
 * @Date 2020/8/26 15:15
 **/
public interface OutStorageProductDao {
    /**
     *
     * 功能描述:查询出库信息关联产品列表
     *
     * @param outStorageProduct
     * @return: List<OutStorageProduct>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    List<OutStorageProduct> findOutStorageProductList(OutStorageProduct outStorageProduct);

    /**
     *
     * 功能描述:查询出库信息关联产品列表总数
     *
     * @param outStorageProduct
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int findOutStorageProductListTotalCount(OutStorageProduct outStorageProduct);

    /**
     *
     * 功能描述:保存出库信息关联产品
     *
     * @param outStorageProduct
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int saveOutStorageProduct(OutStorageProduct outStorageProduct);

    /**
     *
     * 功能描述:保存出库信息关联产品
     *
     * @param list
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int saveBatchOutStorageProduct(List<OutStorageProduct> list);

    /**
     *
     * 功能描述:修改出库信息关联产品
     *
     * @param outStorageProduct
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    int modifyOutStorageProduct(OutStorageProduct outStorageProduct);
}
