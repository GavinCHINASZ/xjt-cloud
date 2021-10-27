package com.xjt.cloud.safety.core.dao.device;

import com.xjt.cloud.safety.core.entity.device.Instrument;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName InstrumentDao
 * @Description 仪器管理
 * @Author wangzhiwen
 * @Date 2021/5/7 16:14
 **/
@Repository
public interface InstrumentDao {
    /**
     * @Description 新增仪器信息
     *
     * @param instrument
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return int
     */
    int saveInstrument(Instrument instrument);

    /**
     * @Description 修改仪器信息
     *
     * @param instrument
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    int modifyInstrument(Instrument instrument);

    /**
     * @Description 查询仪器信息列表
     *
     * @param instrument
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<Instrument> findInstrumentList(Instrument instrument);

    /**
     * @Description 查询仪器信息列表
     *
     * @param instrument
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    int findInstrumentListCount(Instrument instrument);

    /////////////////////////////////////评估项目仪器管理 //////////////////////////////////////
    /**
     * @Description 新增评估项目仪器信息
     *
     * @param instrument
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return int
     */
    int saveCheckProjectInstrument(Instrument instrument);

    /**
     * @Description 删除评估项目仪器信息
     *
     * @param instrument
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return int
     */
    int delCheckProjectInstrument(Instrument instrument);
}
