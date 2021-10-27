package com.xjt.cloud.admin.manage.controller.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.OutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.OutStorageProduct;
import com.xjt.cloud.admin.manage.service.service.inventory.OutStorageService;
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
 * @ClassName OutStorageController
 * @Description 出库管理
 * @Author wangzhiwen
 * @Date 2020/8/21 15:55
 **/
@Controller
@RequestMapping("/inventory/out/storage/")
public class OutStorageController extends AbstractController {
    @Autowired
    private OutStorageService outStorageService;

    /**
     *
     * 功能描述:跳转到出库管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:09
     */
    @RequestMapping("toOutStorageListPage")
    public ModelAndView toOutStorageListPage(){
        return new ModelAndView("inventory/outStorageList");
    }

    /**
     *
     * 功能描述:查询出库信息列表
     *
     * @param ajaxPage
     * @param outStorage
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.inventory.inventory.OutStorage>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "findOutStorageList")
    @ResponseBody
    public ScriptPage<OutStorage> findOutStorageList(AjaxPage ajaxPage, OutStorage outStorage){
        return outStorageService.findOutStorageList(ajaxPage,outStorage);
    }

    /**
     *
     * 功能描述:保存出库信息
     *
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "saveOutStorage")
    @ResponseBody
    public Data saveOutStorage(OutStorage outStorage){
        return outStorageService.saveOutStorage(outStorage);
    }

    /**
     *
     * 功能描述:修改出库信息
     *
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "modifyOutStorage")
    @ResponseBody
    public Data modifyOutStorage(OutStorage outStorage){
        return outStorageService.modifyOutStorage(outStorage);
    }

    /**
     *
     * 功能描述:删除出库信息
     *
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "delOutStorage")
    @ResponseBody
    public Data delOutStorage(OutStorage outStorage){
        return outStorageService.delOutStorage(outStorage);
    }

    /*--------------------------------- 出库信息关联列表--------------------------------- */
    /**
     *
     * 功能描述:查询出库信息关联产品列表
     *
     * @param ajaxPage
     * @param outStorageProduct
     * @return: ScriptPage<OutStorageProduct>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "findOutStorageProductList")
    @ResponseBody
    public ScriptPage<OutStorageProduct> findOutStorageProductList(AjaxPage ajaxPage, OutStorageProduct outStorageProduct){
        return outStorageService.findOutStorageProductList(ajaxPage,outStorageProduct);
    }

    /**
     *
     * 功能描述:保存出库信息关联产品
     *
     * @param outStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "saveOutStorageProduct")
    @ResponseBody
    public Data saveOutStorageProduct(OutStorageProduct outStorageProduct){
        return outStorageService.saveOutStorageProduct(outStorageProduct);
    }

    /**
     *
     * 功能描述:修改出库信息关联产品
     *
     * @param outStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "modifyOutStorageProduct")
    @ResponseBody
    public Data modifyOutStorageProduct(OutStorageProduct outStorageProduct){
        return outStorageService.modifyOutStorageProduct(outStorageProduct);
    }

    /**
     *
     * 功能描述:删除出库信息关联产品
     *
     * @param outStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @RequestMapping(value = "delOutStorageProduct")
    @ResponseBody
    public Data delOutStorageProduct(OutStorageProduct outStorageProduct){
        return outStorageService.delOutStorageProduct(outStorageProduct);
    }

    ///////////////////////////      批量上传 ///////////////////////////////////////////

    /**
     * @Description 下载批量导入出库信息表格模板
     *
     * @param response
     * @author wangzhiwen
     * @date 2020/10/10 9:59
     * @return void
     */
    @RequestMapping(value = "downOutProductModelExcel")
    public void downOutProductModelExcel(HttpServletResponse response){
        outStorageService.downOutProductModelExcel(response);
    }

    /**
     * @Description 出库信息批量导入表格上传
     *
     * @param file
     * @param outStorageProduct
     * @author wangzhiwen
     * @date 2020/10/10 10:02
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "uploadOutProducerExcelFile")
    @ResponseBody
    public Data uploadOutProducerExcelFile(MultipartFile file,OutStorageProduct outStorageProduct){
        return outStorageService.uploadOutProducerExcelFile(file, outStorageProduct);
    }
}
