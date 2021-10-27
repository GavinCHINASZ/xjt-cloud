package com.xjt.cloud.commons.abstracts;

import com.xjt.cloud.commons.base.BaseService;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:
 */
public abstract class MsgAbstractService extends AbstractService implements BaseService {
    protected String getDatabaseName(Long projectId, Integer msgType){
        return "message_manage";
    }

    protected String getTableName(Long projectId, Integer msgType){
        return "m_message";
    }

}
