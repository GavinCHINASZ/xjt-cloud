package com.xjt.cloud.iot.core.dao.iot.vesa;


import com.xjt.cloud.iot.core.entity.vesa.VesaAreaType;

/**
 * @ClassName VesaAreaTypeDao
 * @Author dwt
 * @Date 2020-07-14 11:17
 * @Version 1.0
 */
public interface VesaAreaTypeDao {
    VesaAreaType findAreaTypeByLoopAndSensor(VesaAreaType vesaAreaType);
}
