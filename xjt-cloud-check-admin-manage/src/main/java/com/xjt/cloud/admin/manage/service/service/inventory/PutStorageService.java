package com.xjt.cloud.admin.manage.service.service.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 入库管理接口
 *
 * @author wangzhiwen
 * @date 2020/7/30 11:00
 */
public interface PutStorageService {

    /**
     *
     * 功能描述:查询库入库信息列表
     *
     * @param ajaxPage
     * @param putStorage
     * @return: com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.iot.inventory.PutStorage>
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    ScriptPage<PutStorage> findPutStorageList(AjaxPage ajaxPage, PutStorage putStorage);

    /**
     *
     * 功能描述:保存入库信息
     *
     * @param putStorage
     * @return: Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    Data savePutStorage(PutStorage putStorage);

    /**
     *
     * 功能描述:修改入库信息
     *
     * @param putStorage
     * @return: Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    Data modifyPutStorage(PutStorage putStorage);

    /**
     *
     * 功能描述:删除入库信息
     *
     * @param putStorage
     * @return: Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    Data delPutStorage(PutStorage putStorage);

    /*--------------------------------- 入库信息关联产品列表--------------------------------- */
    /**
     *
     * 功能描述:查询入库信息关联产品列表
     *
     * @param ajaxPage
     * @param putStorageProduct
     * @return: ScriptPage<PutStorageProduct>
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    ScriptPage<PutStorageProduct> findPutStorageProductList(AjaxPage ajaxPage, PutStorageProduct putStorageProduct);

    /**
     *
     * 功能描述:保存入库信息关联产品
     *
     * @param putStorageProduct
     * @return: Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    Data savePutStorageProduct(PutStorageProduct putStorageProduct);

    /**
     *
     * 功能描述:修改入库信息关联产品
     *
     * @param putStorageProduct
     * @return: Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    Data modifyPutStorageProduct(PutStorageProduct putStorageProduct);

    /**
     *
     * 功能描述:删除入库信息关联产品
     *
     * @param putStorageProduct
     * @return: Data
     * @author wangzhiwen
     * @date 2020/7/30 11:11
     */
    Data delPutStorageProduct(PutStorageProduct putStorageProduct);

    /**
     * 功能描述:入库模板下载
     *
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020/7/30 11:11
     */
    void downPutStorageProductModel(HttpServletResponse response);

    /**
     * 功能描述: 批量导入
     *
     * @param file 文件
     * @author huanggc
     * @date 2020/09/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data uploadExcelFile(MultipartFile file, PutStorageProduct putStorageProduct);
}
