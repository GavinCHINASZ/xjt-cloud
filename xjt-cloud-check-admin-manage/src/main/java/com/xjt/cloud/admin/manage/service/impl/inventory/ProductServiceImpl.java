package com.xjt.cloud.admin.manage.service.impl.inventory;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.backstage.ProductDao;
import com.xjt.cloud.admin.manage.entity.inventory.Product;
import com.xjt.cloud.admin.manage.service.service.inventory.ProductService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 仓库产品管理
 *
 * @Author wangzhiwen
 * @Date 2020/8/17 14:10
 **/
@Service
public class ProductServiceImpl extends AbstractAdminService implements ProductService {
    @Autowired
    private ProductDao productDao;

    /**
     * 功能描述:查询产品管理列表
     *
     * @return: List<Product>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public List<Product> findProductListByProducerId(Product product) {
        product.setPageSize(null);
        return productDao.findProductList(product);
    }

    /**
     * 功能描述:查询产品管理列表
     *
     * @param ajaxPage
     * @param product
     * @return: ScriptPage<Product>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public ScriptPage<Product> findProductList(AjaxPage ajaxPage, Product product) {
        product = asseFindObj(product, ajaxPage);
        return asseScriptPage(productDao.findProductList(product), productDao.findProductListTotalCount(product));
    }

    /**
     * 功能描述:新增厂商管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public Data saveProduct(Product product) {
        return asseData(productDao.saveProduct(product));
    }

    /**
     * 功能描述:修改产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public Data modifyProduct(Product product) {
        return asseData(productDao.modifyProduct(product));
    }

    /**
     * 功能描述:删除产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public Data delProduct(Product product) {
        return asseData(productDao.modifyProduct(product));
    }
}
