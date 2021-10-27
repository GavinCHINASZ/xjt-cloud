package com.xjt.cloud.log.manage.dao.log;

import com.xjt.cloud.log.manage.entity.SecurityLog;
import org.springframework.stereotype.Repository;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 11:52
 * @Description:数据词典DAO
 */
@Repository
public interface SecurityLogDao {

    int saveSecurityLog(SecurityLog securityLog);
}
