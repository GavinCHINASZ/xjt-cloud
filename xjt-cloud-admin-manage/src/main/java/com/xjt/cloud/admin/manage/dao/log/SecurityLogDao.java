package com.xjt.cloud.admin.manage.dao.log;

import com.xjt.cloud.admin.manage.entity.log.SecurityLog;
import org.springframework.stereotype.Repository;

/**
 * 日志 DAO
 *
 * @author huanggc
 * @date 2020/10/21
 */
@Repository
public interface SecurityLogDao {

    /**
     * 删除日志数据
     *
     * @param securityLog 防护等级
     * @author huangGuiChuan
     * @date 2020/10/21
     */
    void deletedSecurityLog(SecurityLog securityLog);
}
