package com.xjt.cloud.safety.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;

/**
 * @ClassName InstrumentService
 * @Description 仪器管理
 * @Author wangzhiwen
 * @Date 2021/5/7 16:14
 **/
public interface InstrumentService {

    /**
     * @Description 新增仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveInstrument(String json);

    /**
     * @Description 修改仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data modifyInstrument(String json);

    /**
     * @Description 查询仪器信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findInstrumentList(String json);

    /////////////////////////////////////评估项目仪器管理 //////////////////////////////////////
    /**
     * @Description 新增评估项目仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data saveCheckProjectInstrument(String json);
    /**
     * @Description 删除评估项目仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data delCheckProjectInstrument(String json);
}
