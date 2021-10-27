package com.xjt.cloud.admin.manage.service.service.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.OutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.OutStorageProduct;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName OutStorageService
 * @Description 出库管理
 * @Author wangzhiwen
 * @Date 2020/8/21 15:56
 **/
public interface OutStorageService {

    /**
     *
     * 功能描述:查询库出库信息列表
     *
     * @param ajaxPage
     * @param outStorage
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.iot.inventory.OutStorage>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    ScriptPage<OutStorage> findOutStorageList(AjaxPage ajaxPage, OutStorage outStorage);

    /**
     *
     * 功能描述:保存出库信息
     *
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    Data saveOutStorage(OutStorage outStorage);

    /**
     *
     * 功能描述:修改出库信息
     *
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    Data modifyOutStorage(OutStorage outStorage);

    /**
     *
     * 功能描述:删除出库信息
     *
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    Data delOutStorage(OutStorage outStorage);

    /*--------------------------------- 出库信息关联产品列表--------------------------------- */
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
    ScriptPage<OutStorageProduct> findOutStorageProductList(AjaxPage ajaxPage, OutStorageProduct outStorageProduct);

    /**
     *
     * 功能描述:保存出库信息关联产品
     *
     * @param outStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    Data saveOutStorageProduct(OutStorageProduct outStorageProduct);

    /**
     *
     * 功能描述:修改出库信息关联产品
     *
     * @param outStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    Data modifyOutStorageProduct(OutStorageProduct outStorageProduct);

    /**
     *
     * 功能描述:删除出库信息关联产品
     *
     * @param outStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    Data delOutStorageProduct(OutStorageProduct outStorageProduct);

    /**
     * @Description 下载批量导入出库信息表格模板
     *
     * @param response
     * @author wangzhiwen
     * @date 2020/10/10 9:59
     * @return void
     */
    void downOutProductModelExcel(HttpServletResponse response);

    /**
     * @Description 出库信息批量导入表格上传
     *
     * @param file
     * @param outStorageProduct
     * @author wangzhiwen
     * @date 2020/10/10 10:02
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data uploadOutProducerExcelFile(MultipartFile file, OutStorageProduct outStorageProduct);
}
