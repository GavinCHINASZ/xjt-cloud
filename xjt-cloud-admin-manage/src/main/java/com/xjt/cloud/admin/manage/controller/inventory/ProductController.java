package com.xjt.cloud.admin.manage.controller.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.Product;
import com.xjt.cloud.admin.manage.service.service.inventory.ProductService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @ClassName ProductController
 * @Description 仓库产品管理
 * @Author wangzhiwen
 * @Date 2020/8/17 14:09
 **/
@Controller
@RequestMapping("/inventory/product/")
public class ProductController extends AbstractController {
    @Autowired
    private ProductService productService;


    /**
     *
     * 功能描述:跳转到产品管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:53
     */
    @RequestMapping("toProductListPage")
    public ModelAndView toProductListPage(){
        return new ModelAndView("inventory/productList");
    }

    /**
     *
     * 功能描述:查询产品管理列表
     *
     * @return: List<Product>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "findProductListByProducerId")
    @ResponseBody
    public List<Product> findProductListByProducerId(Product product){
        return productService.findProductListByProducerId(product);
    }

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
    @RequestMapping(value = "findProductList")
    @ResponseBody
    public ScriptPage<Product> findProductList(AjaxPage ajaxPage, Product product){
        return productService.findProductList(ajaxPage, product);
    }

    /**
     *
     * 功能描述:新增厂商管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "saveProduct")
    @ResponseBody
    public Data saveProduct(Product product){
        return productService.saveProduct(product);
    }

    /**
     *
     * 功能描述:修改产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "modifyProduct")
    @ResponseBody
    public Data modifyProduct(Product product){
        return productService.modifyProduct(product);
    }

    /**
     *
     * 功能描述:删除产品管理列表
     *
     * @param product
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "delProduct")
    @ResponseBody
    public Data delProduct(Product product){
        return productService.delProduct(product);
    }
}
