package com.xjt.cloud.admin.manage.dao.message;

import com.xjt.cloud.admin.manage.entity.message.Messages;
import org.springframework.stereotype.Repository;

/**
 * 消息 DAO
 *
 * @author huanggc
 * @date 2021/01/25
 */
@Repository
public interface MessagesDao {

    /**
     * 删除 消息数据
     *
     * @param message 消息
     * @author huangGuiChuan
     * @date 2021/01/25
     */
    void deleteMessage(Messages message);
}
