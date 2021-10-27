package com.xjt.cloud.admin.manage.controller.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import com.xjt.cloud.admin.manage.service.service.inventory.PutStorageService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * 入库管理
 * 
 * @author wangzhiwen
 * @date 2020/7/30 11:00
 */
@Controller
@RequestMapping("/inventory/put/storage")
public class PutStorageController extends AbstractController {
    @Autowired
    private PutStorageService putStorageService;

    /**
     *
     * 功能描述:跳转到入库管理页面
     *
     * @return org.springframework.web.servlet.ModelAndView
     * @author wangzhiwen
     * @date 2020/7/30 11:09
     */
        @RequestMapping("toPutStorageListPage")
    public ModelAndView toPutStorageListPage(){
        return new ModelAndView("inventory/putStorageList");
    }

    /**
     *
     * 功能描述:查询入库信息列表
     *
     * @param ajaxPage ajaxPage
     * @param putStorage 入库管理实体类
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.inventory.inventory.PutStorage>
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "findPutStorageList")
    @ResponseBody
    public ScriptPage<PutStorage> findPutStorageList(AjaxPage ajaxPage, PutStorage putStorage){
        return putStorageService.findPutStorageList(ajaxPage,putStorage);
    }

    /**
     *
     * 功能描述:保存入库信息
     *
     * @param putStorage 入库管理实体类
     * @return Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "savePutStorage")
    @ResponseBody
    public Data savePutStorage(PutStorage putStorage){
        return putStorageService.savePutStorage(putStorage);
    }

    /**
     *
     * 功能描述:修改入库信息
     *
     * @param putStorage 入库管理实体类
     * @return Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "modifyPutStorage")
    @ResponseBody
    public Data modifyPutStorage(PutStorage putStorage){
        return putStorageService.modifyPutStorage(putStorage);
    }

    /**
     *
     * 功能描述:删除入库信息
     *
     * @param putStorage 入库管理实体类
     * @return Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "delPutStorage")
    @ResponseBody
    public Data delPutStorage(PutStorage putStorage){
        return putStorageService.delPutStorage(putStorage);
    }

    /*--------------------------------- 入库信息关联列表--------------------------------- */
    /**
     *
     * 功能描述:查询入库信息关联产品列表
     *
     * @param ajaxPage ajaxPage
     * @param putStorageProduct 入库管理实体类
     * @return ScriptPage<PutStorageProduct>
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "findPutStorageProductList")
    @ResponseBody
    public ScriptPage<PutStorageProduct> findPutStorageProductList(AjaxPage ajaxPage, PutStorageProduct putStorageProduct){
        return putStorageService.findPutStorageProductList(ajaxPage,putStorageProduct);
    }

    /**
     *
     * 功能描述:保存入库信息关联产品
     *
     * @param putStorageProduct 入库管理实体类
     * @return Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "savePutStorageProduct")
    @ResponseBody
    public Data savePutStorageProduct(PutStorageProduct putStorageProduct){
        return putStorageService.savePutStorageProduct(putStorageProduct);
    }

    /**
     * 功能描述:修改入库信息关联产品
     *
     * @param putStorageProduct 入库管理实体类
     * @return Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "modifyPutStorageProduct")
    @ResponseBody
    public Data modifyPutStorageProduct(PutStorageProduct putStorageProduct){
        return putStorageService.modifyPutStorageProduct(putStorageProduct);
    }

    /**
     * 功能描述:删除入库信息关联产品
     *
     * @param putStorageProduct 入库管理实体类
     * @return Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    @RequestMapping(value = "delPutStorageProduct")
    @ResponseBody
    public Data delPutStorageProduct(PutStorageProduct putStorageProduct){
        return putStorageService.delPutStorageProduct(putStorageProduct);
    }

    /**
     * 功能描述:入库模板表格下载
     *
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020/09/23
     */
    @RequestMapping(value = "downPutStorageProductModel")
    public void downPutStorageProductModel(HttpServletResponse response){
        putStorageService.downPutStorageProductModel(response);
    }

    /**
     * 功能描述: 批量导入
     *
     * @param file 文件
     * @author huanggc
     * @date 2020/09/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "/uploadExcelFile")
    @ResponseBody
    public Data uploadExcelFile(MultipartFile file, PutStorageProduct putStorageProduct){
        return putStorageService.uploadExcelFile(file, putStorageProduct);
    }
}
