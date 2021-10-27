package com.xjt.cloud.fault.core.dao.fault;

import com.xjt.cloud.fault.core.entity.fault.FaultHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 故障处理人 Dao
 *
 * @Author huanggc
 * @Date 2019/09/02
 **/
@Repository
public interface FaultHandlerDao {

    /**
     * 功能描述:查询 故障处理人 列表
     *
     * @param faultHandler 故障处理人实体
     * @auther huanggc
     * @date 2019/09/02
     * @return List<FaultHandler>
     */
    List<FaultHandler> findFaultHandlerList(FaultHandler faultHandler);

    /**
     * 功能描述:删除功能
     *
     * @param faultHandler 实体
     * @auther: huanggc
     * @date: 2019/09/06
     * @return: Integer 删除的数量
     */
    Integer deletedFaultHandler(FaultHandler faultHandler);

    /**
     * 功能描述: 批量保存 故障处理人(维修人)
     *
     * @param faultHandlerList 故障处理人实体
     * @auther: huanggc
     * @date: 2019/11/12
     * @return: Integer 新增成功数量
     */
    Integer saveFaultHandlers(List<FaultHandler> faultHandlerList);

    /**
     * 功能描述: 根据 故障处理人实体 查询数量
     *
     * @param faultHandler 故障处理人实体
     * @auther: huanggc
     * @date: 2019/11/12
     * @return: Integer
     */
    Integer findFaultHandlerNum(FaultHandler faultHandler);

    /**
     * 功能描述: 查询 维修人ID 字符串
     *
     * @param faultHandler 故障处理人实体
     * @auther: huanggc
     * @date: 2020-06-09
     * @return: String
     */
    String findRepairUserIdString(FaultHandler faultHandler);

    /**
     * 功能描述: 查询 审核ID 字符串
     *
     * @param faultHandler 故障处理人实体
     * @auther: huanggc
     * @date: 2020-06-09
     * @return: String
     */
    String findExamineUserIdString(FaultHandler faultHandler);
}
