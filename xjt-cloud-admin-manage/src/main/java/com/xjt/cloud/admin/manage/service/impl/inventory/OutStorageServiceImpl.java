package com.xjt.cloud.admin.manage.service.impl.inventory;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.backstage.OutStorageDao;
import com.xjt.cloud.admin.manage.dao.backstage.OutStorageProductDao;
import com.xjt.cloud.admin.manage.dao.backstage.PutStorageProductDao;
import com.xjt.cloud.admin.manage.dao.device.DeviceDao;
import com.xjt.cloud.admin.manage.dao.project.ProjectDao;
import com.xjt.cloud.admin.manage.entity.device.Device;
import com.xjt.cloud.admin.manage.entity.inventory.OutStorage;
import com.xjt.cloud.admin.manage.entity.inventory.OutStorageProduct;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import com.xjt.cloud.admin.manage.entity.project.Project;
import com.xjt.cloud.admin.manage.service.service.inventory.OutStorageService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @ClassName OutStorageServiceImpl
 * @Description 出库管理
 * @Author wangzhiwen
 * @Date 2020/8/21 15:56
 **/
@Service
public class OutStorageServiceImpl extends AbstractAdminService implements OutStorageService {
    @Autowired
    private OutStorageDao outStorageDao;
    @Autowired
    private OutStorageProductDao outStorageProductDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PutStorageProductDao putStorageProductDao;
    @Autowired
    private DeviceDao deviceDao;

    /**
     *
     * 功能描述:查询出库信息列表
     *
     * @param ajaxPage
     * @param outStorage
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2020/7/30 11:11
     */
    @Override
    public ScriptPage<OutStorage> findOutStorageList(AjaxPage ajaxPage, OutStorage outStorage){
        outStorage = asseFindObj(outStorage, ajaxPage);
        return asseScriptPage(outStorageDao.findOutStorageList(outStorage), outStorageDao.findOutStorageListTotalCount(outStorage));
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
    @Override
    public Data saveOutStorage(OutStorage outStorage){
        return asseData(outStorageDao.saveOutStorage(outStorage));
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
    @Override
    public Data modifyOutStorage(OutStorage outStorage){
        return asseData(outStorageDao.modifyOutStorage(outStorage));
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
    @Override
    public Data delOutStorage(OutStorage outStorage){
        return asseData(outStorageDao.modifyOutStorage(outStorage));
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
    @Override
    public ScriptPage<OutStorageProduct> findOutStorageProductList(AjaxPage ajaxPage, OutStorageProduct outStorageProduct){
        outStorageProduct = asseFindObj(outStorageProduct, ajaxPage);
        return asseScriptPage(outStorageProductDao.findOutStorageProductList(outStorageProduct), outStorageProductDao.findOutStorageProductListTotalCount(outStorageProduct));
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
    @Override
    public Data saveOutStorageProduct(OutStorageProduct outStorageProduct){
        if (StringUtils.isNotEmpty(outStorageProduct.getDeviceIdAndDeviceType())){
            String[] deviceIdAndDeviceType = outStorageProduct.getDeviceIdAndDeviceType().split("_");
            outStorageProduct.setDeviceId(Long.parseLong(deviceIdAndDeviceType[0]));
        }
        return asseData(outStorageProductDao.saveOutStorageProduct(outStorageProduct));
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
    @Override
    public Data modifyOutStorageProduct(OutStorageProduct outStorageProduct){
        return asseData(outStorageProductDao.modifyOutStorageProduct(outStorageProduct));
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
    @Override
    public Data delOutStorageProduct(OutStorageProduct outStorageProduct){
        return asseData(outStorageProductDao.modifyOutStorageProduct(outStorageProduct));
    }

    /**
     * @Description 下载批量导入出库信息表格模板
     *
     * @param response
     * @author wangzhiwen
     * @date 2020/10/10 9:59
     * @return void
     */
    @Override
    public void downOutProductModelExcel(HttpServletResponse response){
        String[] keys = {"rowNum", "orderNum", "projectName1", "property1", "deviceQrNo", "iccid", "sensorNo", "memo"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "出库信息模板表");

        ExcelUtils.createAndDownloadExcel(response, null, keys,  ConstantsClient.OUT_STORAGE_PRODUCER_MODEL_PATH, 3, null,
                jsonObject, "1:0");
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
    @Override
    public Data uploadOutProducerExcelFile(MultipartFile file, OutStorageProduct outStorageProduct){
        String[] keys = {"rowNum", "orderNum", "projectName1", "property1", "deviceQrNo", "iccid", "sensorNo", "memo"};
        // 解析表格   rowindex从0开始记算
        List<OutStorageProduct> list = ExcelUtils.readyExcel(file, 3,0,0, keys, OutStorageProduct.class);

        if (CollectionUtils.isNotEmpty(list)){
            StringBuilder productNumSql = new StringBuilder();
            StringBuilder deviceQrNoSql = new StringBuilder();
            StringBuilder iccidSql = new StringBuilder();
            StringBuilder sensorNoSql = new StringBuilder();

            String productNum;
            Long outStorageId = null;
            String deviceQrNo;
            String iccid;
            String sensorNo;

            OutStorageProduct temOsp = list.get(0);
            if (StringUtils.isEmpty(temOsp.getProjectName1())) {
                return asseData(ServiceErrCode.REQ_ERR.getCode(), "项目名称不能为空！");
            }
            Project project = new Project();
            project.setProjectName(temOsp.getProjectName1());
            project = projectDao.findProject(project);

            if (StringUtils.isNotEmpty(temOsp.getOrderNum())) {
                OutStorage outStorage = new OutStorage();
                outStorage.setOrderNum(temOsp.getOrderNum());
                outStorage = outStorageDao.findOutStorage(outStorage);
                if (outStorage == null){
                    return asseData(ServiceErrCode.REQ_ERR.getCode(), "出库单号不正确！");
                }
                outStorageId = outStorage.getId();
            }

            for (OutStorageProduct osp : list){
                if (StringUtils.isNotEmpty(osp.getProperty1())) {
                    productNum = " SELECT '" + osp.getProperty1() + "' property_1 UNION ";
                    if (productNumSql.indexOf(productNum) == -1) {//产品序号
                        productNumSql.append(productNum);
                    }
                }

                if (StringUtils.isNotEmpty(osp.getDeviceQrNo())) {
                    deviceQrNo = " SELECT '" + osp.getDeviceQrNo() + "' qr_no UNION ";
                    if (deviceQrNoSql.indexOf(deviceQrNo) == -1) {//设备id
                        deviceQrNoSql.append(deviceQrNo);
                    }
                }

                if (StringUtils.isNotEmpty(osp.getIccid())) {
                    iccid = " SELECT '" + osp.getIccid() + "' property_1 UNION ";
                    if (iccidSql.indexOf(iccid) == -1) {//物联网卡iccid
                        iccidSql.append(iccid);
                    }
                }

                if (StringUtils.isNotEmpty(osp.getSensorNo())) {
                    sensorNo = " SELECT '" + osp.getSensorNo() + "' qr_no UNION ";
                    if (sensorNoSql.indexOf(sensorNo) == -1) {//传感器id
                        sensorNoSql.append(sensorNo);
                    }
                }
            }

            Data data;
            Map<String,PutStorageProduct> pspMap = new HashMap<>();
            if (StringUtils.isNotEmpty(productNumSql.toString())){//查询产品信息
                data = checkProductNum(productNumSql.toString(), pspMap);
                if (data.getStatus() != Constants.SUCCESS_CODE){
                    return data;
                }
            }

            Map<String,Device> deviceMap = new HashMap<>();
            if (StringUtils.isNotEmpty(deviceQrNoSql.toString())){//查询设备信息
                data = checkDeviceQrNo(deviceQrNoSql.toString(), deviceMap);
                if (data.getStatus() != Constants.SUCCESS_CODE){
                    return data;
                }
            }

            Map<String, PutStorageProduct> iotCardMap = new HashMap<>();
            if (StringUtils.isNotEmpty(iccidSql.toString())){//查询物联网卡信息
                data = checkProductNum(iccidSql.toString(), iotCardMap);
                if (data.getStatus() != Constants.SUCCESS_CODE){
                    return data;
                }
            }

            Map<String, Device> sensorNoMap = new HashMap<>();
            if (StringUtils.isNotEmpty(sensorNoSql.toString())){//查询传感器信息
                data = checkSensorNo(sensorNoSql.toString(), sensorNoMap);
                if (data.getStatus() != Constants.SUCCESS_CODE){
                    return data;
                }
            }
            data = saveOutStorageProductList(list, project.getId(), outStorageId, pspMap, deviceMap, iotCardMap, sensorNoMap);
            return data;
        }

        return Data.isSuccess();
    }

    /**
     * @Description 查询产品入库信息
     *
     * @param productNumSql
     * @param pspMap
     * @author wangzhiwen
     * @date 2020/10/10 11:12
     * @return com.xjt.cloud.commons.utils.Data
     */
    private Data checkProductNum(String productNumSql, Map<String,PutStorageProduct> pspMap){
        productNumSql = productNumSql.substring(0, productNumSql.length() - 6);
        PutStorageProduct putStorageProduct = new PutStorageProduct();
        putStorageProduct.setSql(productNumSql);
        putStorageProduct.setPageSize(null);
        putStorageProduct.setStorageStatus(1);
        List<PutStorageProduct> pspList = putStorageProductDao.findPutStorageProductList(putStorageProduct);
        List<PutStorageProduct> list = new ArrayList<>();
        String property1;
        for (PutStorageProduct psp : pspList){
            property1 = psp.getProperty1() ;
            if (psp.getId() == null){//判断产品编码是否为空
                list.add(psp);
            }else {
                if (!pspMap.containsKey(property1)) {
                    pspMap.put(property1, psp);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, "产品编码不存在");
        }
        return Data.isSuccess();
    }

    /**
     * @Description 查询设备信息
     *
     * @param deviceQrNoSql
     * @param deviceMap
     * @author wangzhiwen
     * @date 2020/10/10 11:12
     * @return com.xjt.cloud.commons.utils.Data
     */
    private Data checkDeviceQrNo(String deviceQrNoSql, Map<String, Device> deviceMap){
        deviceQrNoSql = deviceQrNoSql.substring(0, deviceQrNoSql.length() - 6);
        Device temp = new Device();
        temp.setSql(deviceQrNoSql);
        List<Device> deviceList = deviceDao.findDeviceList(temp);
        List<Device> list = new ArrayList<>();
        String deviceQrNo;
        for (Device device : deviceList){
            deviceQrNo = device.getByQrNo() ;
            if (device.getId() == null){//判断公司名称与部门名称是否为空
                list.add(device);
            }else {
                if (!deviceMap.containsKey(deviceQrNo)) {
                    deviceMap.put(deviceQrNo, device);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, "设备信息不存在");
        }
        return Data.isSuccess();
    }


    /**
     * @Description 查询传感器信息
     *
     * @param sensorNoSql
     * @param waterDeviceMap
     * @author wangzhiwen
     * @date 2020/10/10 11:12
     * @return com.xjt.cloud.commons.utils.Data
     */
    private Data checkSensorNo(String sensorNoSql, Map<String,Device> waterDeviceMap){
        sensorNoSql = sensorNoSql.substring(0, sensorNoSql.length() - 6);
        Device temp = new Device();
        temp.setSensorNoSql(sensorNoSql);
        List<Device> deviceList = deviceDao.findDeviceList(temp);
        List<Device> list = new ArrayList<>();
        String sensorNo;
        for (Device device : deviceList){
            sensorNo = device.getByQrNo() ;
            if (device.getId() == null){//判断公司名称与部门名称是否为空
                list.add(device);
            }else {
                if (!waterDeviceMap.containsKey(sensorNo)) {
                    waterDeviceMap.put(sensorNo, device);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, "传感器id不存在");
        }
        return Data.isSuccess();
    }

    /**
     * @Description 保存出库导入信息
     *
     * @param list 信息列表
     * @param projectId 项目id
     * @param outStorageId 出库单号id
     * @param pspMap 出库产品信息
     * @param deviceMap 出库设备信息
     * @param iotCardMap 出库物联网卡信息
     * @param sensorNoMap 出库物联网卡关联的传感器信息
     * @author wangzhiwen
     * @date 2020/10/12 9:26
     * @return com.xjt.cloud.commons.utils.Data
     */
    private Data saveOutStorageProductList(List<OutStorageProduct> list, Long projectId, Long outStorageId, Map<String,PutStorageProduct> pspMap,
                                           Map<String,Device> deviceMap, Map<String, PutStorageProduct> iotCardMap, Map<String, Device> sensorNoMap){
        if (outStorageId == null){
            OutStorage outStorage = new OutStorage();
            outStorage.setOrderNum(System.currentTimeMillis() + "");
            outStorage.setProjectId(projectId);
            outStorageDao.saveOutStorage(outStorage);
            outStorageId = outStorage.getId();
        }

        PutStorageProduct psp;
        Device device;
        PutStorageProduct iotCardPsp;
        Device sensorNoDevice;
        StringBuilder putStorageProductIds = new StringBuilder();
        if (pspMap != null && pspMap.size() > 0){//判断是否只是设备出库
            for (OutStorageProduct osp : list){
                psp = pspMap.get(osp.getProperty1());
                device = deviceMap.get(osp.getDeviceQrNo());
                asseOutStorageProduct(osp, psp, device, outStorageId, projectId);
                putStorageProductIds.append(psp.getId() + ",");
            }
        }else {//物联网卡出库关联设备
            for (OutStorageProduct osp : list){
                iotCardPsp = iotCardMap.get(osp.getIccid());
                sensorNoDevice = sensorNoMap.get(osp.getSensorNo());
                asseOutStorageProduct(osp, iotCardPsp, sensorNoDevice, outStorageId, projectId);
                osp.setIotId(sensorNoDevice.getIotId());
                putStorageProductIds.append(iotCardPsp.getId() + ",");
            }
        }
        int num  = outStorageProductDao.saveBatchOutStorageProduct(list);//保存出库信息
        if (num > 0){
            psp = new PutStorageProduct();
            psp.setIds(putStorageProductIds.substring(0,putStorageProductIds.length() - 1));
            psp.setStorageStatus(2);
            putStorageProductDao.modifyPutStorageProduct(psp);
        }
        return asseData(num);
    }

    /**
     * @Description 组装出库信息对象
     *
     * @param osp 出库信息对象
     * @param psp 设备入库信息对象
     * @param device 设备信息对象
     * @param outStorageId 出库单主键id
     * @param projectId 项目id
     * @author wangzhiwen
     * @date 2020/10/12 10:31
     * @return com.xjt.cloud.admin.manage.entity.inventory.OutStorageProduct
     */
    private OutStorageProduct asseOutStorageProduct(OutStorageProduct osp, PutStorageProduct psp, Device device, Long outStorageId, Long projectId){
        osp.setOutStorageId(outStorageId);
        osp.setProjectId(projectId);
        osp.setPutStorageId(psp.getPutStorageId());
        osp.setProducerId(psp.getProducerId());
        osp.setProductId(psp.getProductId());
        osp.setPutStorageProductId(psp.getId());
        osp.setBuildingId(device.getBuildingId());
        osp.setBuildingFloorId(device.getBuildingFloorId());
        osp.setCheckPointId(device.getCheckPointId());
        osp.setDeviceId(device.getId());
        return osp;
    }
}
