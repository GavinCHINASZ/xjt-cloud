package com.xjt.cloud.admin.manage.service.service.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.Product;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;

/**
 * @ClassName ProductService
 * @Description  仓库产品管理
 * @Author wangzhiwen
 * @Date 2020/8/17 14:10
 **/
public interface ProductService {
    /**
     *
     * 功能描述:查询产品管理列表
     *
     * @return: List<Product>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    List<Product> findProductListByProducerId(Product product);

    /**
     *
     * 功能描述:查询产品管理列表
     *
     * @param ajaxPage
     * @param product
     * @return: ScriptPage<Product>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    ScriptPage<Product> findProductList(AjaxPage ajaxPage, Product product);

    /**
     *
     * 功能描述:新增厂商管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Data saveProduct(Product product);

    /**
     *
     * 功能描述:修改产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Data modifyProduct(Product product);

    /**
     *
     * 功能描述:删除产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Data delProduct(Product product);
}
