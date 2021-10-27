package com.xjt.cloud.iot.core.service.service.vesa;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.iot.core.entity.vesa.VesaDevice;
import com.xjt.cloud.iot.core.entity.vesa.VesaRecord;

import java.util.List;

/**
 * 水设备记录解析接口
 * 
 * @author wangzhiwen
 * @date 2019/10/23 09:59
 */
public interface VesaDeviceRecordResolveService {
    /**
     * 功能描述: 极早期设备上传信息参数解析
     *
     * @param json 参数
     * @return List<JSONObject>
     * @author wangzhiwen
     * @date 2019/10/23 13:49
     */
    List<JSONObject> vesaSys(JSONObject json);

    /**
     * 功能描述:保存极早期设备状态信息 、记录信息、事件信息
     *
     * @param vesaDevice VesaDevice
     * @param vesaRecord VesaRecord
     * @return List<JSONObject>
     */
    List<JSONObject> saveVesaInfo(VesaDevice vesaDevice, VesaRecord vesaRecord);

    /**
     * 功能描述: 极早期设备上传信息处理接口
     *
     * @param jsonObject JSONObject
     * @return List<JSONObject>
     * @author wangzhiwen
     * @date 2019/9/27 15:24
     */
    List<JSONObject> vesaDeviceDataAccess(JSONObject jsonObject);
}
