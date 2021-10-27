package com.xjt.cloud.admin.manage.service.impl.device;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.device.CheckPointDao;
import com.xjt.cloud.admin.manage.dao.device.DeviceQrNoDao;
import com.xjt.cloud.admin.manage.entity.device.CheckPoint;
import com.xjt.cloud.admin.manage.entity.device.DeviceQrNo;
import com.xjt.cloud.admin.manage.service.service.device.DeviceQrNoService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.ExcelUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DeviceQrNoServiceImpl
 * @Author dwt
 * @Date 2020-10-28 10:04
 * @Version 1.0
 */
@Service
public class DeviceQrNoServiceImpl extends AbstractAdminService implements DeviceQrNoService {

    @Autowired
    private DeviceQrNoDao deviceQrNoDao;
    @Autowired
    private CheckPointDao checkPointDao;

    @Override
    public Data uploadDeviceQrNorExcelFile(MultipartFile file) {
        String[] keys = {"qrNo","projectId"};
        // 解析表格   rowindex从0开始记算
        List<DeviceQrNo> list = ExcelUtils.readyExcel(file, 1,0,0, keys, DeviceQrNo.class);
        if(CollectionUtils.isNotEmpty(list)){
            deviceQrNoDao.saveCheckPoint(list);
            deviceQrNoDao.saveQrNo(list);
        }
        return Data.isSuccess();
    }

    @Override
    public void downDeviceQrNoModelExcel(HttpServletResponse response) {
        String[] keys = {"巡检ID", "项目ID"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "设备二维码导入模板");
        ExcelUtils.createAndDownloadExcel(response, null, keys,  ConstantsClient.DEVICE_QR_NO_MODEL_PATH, 0, null,
                jsonObject, "1:0");
    }

    /**
     * @Description 查询巡检点id列表
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/11/23 15:40
     * @return com.xjt.cloud.admin.manage.common.utils.ScriptPage<com.xjt.cloud.admin.manage.entity.device.CheckPoint>
     */
    @Override
    public ScriptPage<CheckPoint> findCheckPointQrNoList(AjaxPage ajaxPage, CheckPoint checkPoint){
        checkPoint = asseFindObj(checkPoint, ajaxPage);
        return asseScriptPage(checkPointDao.findCheckPointList(checkPoint), checkPointDao.findCheckPointListCount(checkPoint));
    }

    /**
     * @Description 保存巡检点id
     *
     * @param checkPoint
     * @author wangzhiwen
     * @date 2020/11/23 15:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data saveCheckPoint(CheckPoint checkPoint){
        String qrNo = checkPoint.getQrNo();
        String[] qrNos = qrNo.trim().split("\n");
        checkPoint.setQrNos(qrNos);
        checkPoint.setQrNo(null);
        CheckPoint temp = checkPointDao.findCheckQrNoList(checkPoint);
        if (temp != null){
            Data data = new Data();
            data.setMsg(temp.getQrNo() + "  巡检点ID已存在");
            return data;
        }
        Long projectId = checkPoint.getProjectId();
        List<DeviceQrNo> qrNoList = new ArrayList<>();
        DeviceQrNo deviceQrNo;
        for (String str : qrNos){
            if (StringUtils.isNotEmpty(str)) {
                deviceQrNo = new DeviceQrNo();
                deviceQrNo.setProjectId(projectId);
                deviceQrNo.setQrNo(str);
                qrNoList.add(deviceQrNo);
            }
        }
        deviceQrNoDao.saveCheckPoint(qrNoList);
        deviceQrNoDao.saveQrNo(qrNoList);
        return Data.isSuccess();
    }

    /**
     * @Description 保存巡检点id
     *
     * @param deviceQrNo
     * @author wangzhiwen
     * @date 2020/11/23 15:43
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data modifyCheckPoint(DeviceQrNo deviceQrNo){
        deviceQrNoDao.modifyQrNoProject(deviceQrNo);
        deviceQrNoDao.modifyCheckPointProject(deviceQrNo);
        return Data.isSuccess();
    }
}
