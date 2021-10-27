package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.Contract;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ContractDao
 * @Description 合同管理
 * @Author wangzhiwen
 * @Date 2021/4/17 10:56
 **/
@Repository
public interface ContractDao{

    /**
     * @Description 保存合同列表
     *
     * @param Contract
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return int
     */
    int saveContract(Contract Contract);

    /**
     * @Description 删除合同列表
     *
     * @param Contract
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return int
     */
    int delContract(Contract Contract);

    /**
     * @Description 修改删除合同列表
     *
     * @param Contract
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return int
     */
    int modifyContract(Contract Contract);

    /**
     * @Description 查询合同列表
     *
     * @param Contract
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return List<Contract>
     */
    List<Contract> findContractList(Contract Contract);

    /**
     * @Description 查询合同总数
     *
     * @param Contract
     * @author wangzhiwen
     * @date 2021/4/17 10:52
     * @return List<Contract>
     */
    Integer findContractListCount(Contract Contract);
}
