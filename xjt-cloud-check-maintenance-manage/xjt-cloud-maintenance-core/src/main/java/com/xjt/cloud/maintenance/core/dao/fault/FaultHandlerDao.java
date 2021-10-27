package com.xjt.cloud.maintenance.core.dao.fault;

import com.xjt.cloud.maintenance.core.entity.fault.FaultHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 故障处理人 Dao
 *
 * @author huanggc
 * @date 2019/09/02
 **/
@Repository
public interface FaultHandlerDao {

    /**
     * 功能描述: 批量保存 故障处理人(维修人)
     *
     * @param faultHandler 故障处理人实体
     * @author huanggc
     * @date 2019/11/12
     * @return Integer
     */
    Integer saveFaultHandler(FaultHandler faultHandler);

    /**
     * 功能描述: 批量保存 故障处理人(维修人)
     *
     * @param faultHandlerList 故障处理人实体
     * @author huanggc
     * @date 2019/11/12
     * @return Integer
     */
    Integer saveFaultHandlerList(List<FaultHandler> faultHandlerList);

}
