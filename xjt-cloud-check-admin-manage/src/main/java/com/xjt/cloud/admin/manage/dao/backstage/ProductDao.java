package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProductDao
 * @Description  仓库产品管理
 * @Author wangzhiwen
 * @Date 2020/8/17 14:10
 **/
@Repository
public interface ProductDao {
    /**
     *
     * 功能描述:查询产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    List<Product> findProductList(Product product);

    /**
     *
     * 功能描述:查询产品管理
     *
     * @param product
     * @return: Product
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Product findProduct(Product product);

    /**
     *
     * 功能描述:查询产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    int findProductListTotalCount(Product product);

    /**
     *
     * 功能描述:新增产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    int saveProduct(Product product);

    /**
     *
     * 功能描述:修改产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    int modifyProduct(Product product);
}
