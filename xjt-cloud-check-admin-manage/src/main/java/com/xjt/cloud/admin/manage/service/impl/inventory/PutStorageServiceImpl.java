package com.xjt.cloud.admin.manage.service.impl.inventory;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.backstage.ProducerDao;
import com.xjt.cloud.admin.manage.dao.backstage.ProductDao;
import com.xjt.cloud.admin.manage.dao.backstage.PutStorageProductDao;
import com.xjt.cloud.admin.manage.dao.backstage.PutStorageDao;
import com.xjt.cloud.admin.manage.entity.inventory.Producer;
import com.xjt.cloud.admin.manage.entity.inventory.Product;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import com.xjt.cloud.admin.manage.service.service.inventory.PutStorageService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 入库管理接口实现类
 *
 * @Auther: wangzhiwen
 * @Date: 2020/7/30 11:01
 */
@Service
public class PutStorageServiceImpl extends AbstractAdminService implements PutStorageService {
    @Autowired
    private PutStorageDao putStorageDao;
    @Autowired
    private PutStorageProductDao putStorageProductDao;
    @Autowired
    private ProducerDao producerDao;
    @Autowired
    private ProductDao productDao;


    /**
     * 功能描述:查询入物信息列表
     *
     * @param ajaxPage
     * @param putStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public ScriptPage<PutStorage> findPutStorageList(AjaxPage ajaxPage, PutStorage putStorage) {
        putStorage = asseFindObj(putStorage, ajaxPage);
        return asseScriptPage(putStorageDao.findPutStorageList(putStorage), putStorageDao.findPutStorageListTotalCount(putStorage));
    }

    /**
     * 功能描述:保存入物信息
     *
     * @param putStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public Data savePutStorage(PutStorage putStorage) {
        return asseData(putStorageDao.savePutStorage(putStorage));
    }

    /**
     * 功能描述:修改入物信息
     *
     * @param putStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public Data modifyPutStorage(PutStorage putStorage) {
        return asseData(putStorageDao.modifyPutStorage(putStorage));
    }

    /**
     * 功能描述:删除入物信息
     *
     * @param putStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public Data delPutStorage(PutStorage putStorage) {
        return asseData(putStorageDao.modifyPutStorage(putStorage));
    }

    /*--------------------------------- 入物信息关联列表--------------------------------- */

    /**
     * 功能描述:查询入物信息关联产品列表
     *
     * @param ajaxPage
     * @param putStorageProduct
     * @return: ScriptPage<PutStorageProduct>
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public ScriptPage<PutStorageProduct> findPutStorageProductList(AjaxPage ajaxPage, PutStorageProduct putStorageProduct) {
        putStorageProduct = asseFindObj(putStorageProduct, ajaxPage);
        return asseScriptPage(putStorageProductDao.findPutStorageProductList(putStorageProduct), putStorageProductDao.findPutStorageProductListTotalCount(putStorageProduct));
    }

    /**
     * 功能描述:保存入物信息关联产品
     *
     * @param putStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public Data savePutStorageProduct(PutStorageProduct putStorageProduct) {
        return asseData(putStorageProductDao.savePutStorageProduct(putStorageProduct));
    }

    /**
     * 功能描述:修改入物信息关联产品
     *
     * @param putStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public Data modifyPutStorageProduct(PutStorageProduct putStorageProduct) {
        return asseData(putStorageProductDao.modifyPutStorageProduct(putStorageProduct));
    }

    /**
     * 功能描述:删除入物信息关联产品
     *
     * @param putStorageProduct
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public Data delPutStorageProduct(PutStorageProduct putStorageProduct) {
        return asseData(putStorageProductDao.modifyPutStorageProduct(putStorageProduct));
    }

    /**
     * 功能描述:入库模板下载
     *
     * @param response HttpServletResponse
     * @author huanggc
     * @date 2020/7/30 11:11
     */
    @Override
    public void downPutStorageProductModel(HttpServletResponse response) {
        String[] keys = {"rowNum", "producerName", "productName", "property2", "property1", "memo", "openDate", "openDate"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "物联卡入库模板表");

        ExcelUtils.createAndDownloadExcel(response, null, keys, ConstantsClient.PUT_STORAGE_PRODUCT_MODEL_PATH, 3, null,
                jsonObject, "1:0");
    }

    /**
     * 功能描述: 批量导入
     *
     * @param file 文件
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/23
     */
    @Override
    public Data uploadExcelFile(MultipartFile file, PutStorageProduct putStorageProduct) {
        // 解析表格
        String[] keys = {"rowNum", "orderNum", "producerName", "productName", "property1", "property2", "orderTime", "memo"};

        // 解析表格   rowindex从0开始记算
        List<PutStorageProduct> list = ExcelUtils.readyExcel(file, 3, 0, 0, keys, PutStorageProduct.class);
        Data data = new Data();
        if (CollectionUtils.isNotEmpty(list)) {
            Long putStorageId = null;
            PutStorage putStorage = new PutStorage();

            PutStorageProduct temPsp = list.get(0);
            if (StringUtils.isNotEmpty(temPsp.getOrderNum())) {
                putStorage.setOrderNum(temPsp.getOrderNum());
                putStorage = putStorageDao.findPutStorage(putStorage);
                if (putStorage == null) {
                    return asseData(ServiceErrCode.REQ_ERR.getCode(), "出库单号不正确！");
                }
                putStorageId = putStorage.getId();
            }

            if (putStorageId == null) {//判断是否入库单信息不存在
                Producer producer = new Producer();
                producer.setProducerName(temPsp.getProducerName());
                producer.setPageSize(null);
                producer = producerDao.findProducer(producer);
                if (producer == null) {
                    return asseData(ServiceErrCode.REQ_ERR.getCode(), "厂商存在！");
                }

                Product product = new Product();
                product.setProductName(temPsp.getProductName());
                product.setPageSize(null);
                product = productDao.findProduct(product);

                if (product == null) {
                    return asseData(ServiceErrCode.REQ_ERR.getCode(), "产品不存在！");
                }

                putStorage.setProducerId(producer.getId());
                putStorage.setProductId(product.getId());
                putStorage.setOrderTime(temPsp.getOrderTime());
                putStorage.setOrderNum(System.currentTimeMillis() + "");
                putStorageDao.savePutStorage(putStorage);
            }

            int saveNum = putStorageProductDao.savePutStorageProductList(list, putStorage);
            if (saveNum > 0) {
                data.setStatus(Constants.SUCCESS_CODE);
                data.setMsg("导入成功");
                data.setStatus(200);
            } else {
                data.setStatus(Constants.FAIL_CODE);
                data.setMsg("导入失败");
                data.setStatus(600);
            }
        }
        return data;
    }
}
