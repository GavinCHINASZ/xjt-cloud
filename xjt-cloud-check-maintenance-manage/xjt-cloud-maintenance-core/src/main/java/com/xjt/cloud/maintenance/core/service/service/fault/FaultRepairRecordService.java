package com.xjt.cloud.maintenance.core.service.service.fault;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;


/**
 * 故障报修 Service接口
 *
 * @author huanggc
 * @date 2021/04/22
 **/
public interface FaultRepairRecordService extends BaseService {

    /**
     * 新建故障报修
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/12
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveFaultRepairRecord(String json);

    /**
     * 功能描述:查询 故障报修 列表
     *
     * @param json 参数
     * @author huanggc
     * @date 2019/09/02
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findFaultRepairRecordList(String json);

    /**
     * 功能描述:查询 故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    Data findFaultRepairRecord(String json);

    /**
     * 功能描述:查询 故障报修类型
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    Data findFaultType(String json);

    /**
     * 功能描述:更新故障报修
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2019/09/02
     */
    Data updateFaultRepairRecord(String json);

    /**
     * 功能描述:查询 日志/记录 列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2021/04/22
     */
    Data findRepairProgressList(String json);
}
