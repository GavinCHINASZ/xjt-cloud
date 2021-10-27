package com.xjt.cloud.admin.manage.service.impl.sys;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.dao.slave.SlaveDao;
import com.xjt.cloud.admin.manage.service.service.sys.SysService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.MailUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * SysServiceImpl
 *
 * @Author wangzhiwen
 * @Date 2020/10/26 9:35
 **/
@Service
public class SysServiceImpl extends AbstractAdminService implements SysService {
    @Autowired
    private SlaveDao slaveDao;

    /**
     * 数据库主从监听任务
     *
     * @author wangzhiwen
     * @date 2020/10/26 9:37
     */
    @Override
    public void databaseMasterSlaveCheckTask() {
        Map<String, Object> map = slaveDao.findDatabaseMasterSlaveStatus();
        if (map != null && map.size() > 0) {
            if (!"Yes".equals(map.get("Slave_IO_Running")) || !"Yes".equals(map.get("Slave_SQL_Running"))) {
                String mailStr = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.DICT_DATABASE_MASTER_SLAVE, ConstantsClient.DICT_DATABASE_MASTER_SLAVE_MAIL,
                        "itemDescription");
                if (StringUtils.isNotEmpty(mailStr)) {
                    MailUtils.send("消检通系统", "主从数据库同步错误！" + map.get("Last_Error"), "主从数据库", mailStr.split(";"));
                }
            }
        }
    }

}
