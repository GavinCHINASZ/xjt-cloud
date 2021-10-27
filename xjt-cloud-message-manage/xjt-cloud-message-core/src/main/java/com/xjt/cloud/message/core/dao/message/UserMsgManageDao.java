package com.xjt.cloud.message.core.dao.message;

import com.xjt.cloud.message.core.entity.UserMsgManage;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserMsgManageDao 用户消息通知管理
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
@Repository
public interface UserMsgManageDao {
    /**@MethodName: deleteUserMsgManage 删除用户消息通知设置
     * @Description:
     * @Param: [userMsgManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
     void deleteUserMsgManage(UserMsgManage userMsgManage) ;
    /**@MethodName: saveUserMsgManage 保存用户消息通知设置
     * @Description:
     * @Param: [userMsgManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
      void saveUserMsgManage(UserMsgManage userMsgManage) ;

    /**@MethodName: saveUserMsgManages 批量保存用户消息通知设置
     * @Description:
     * @Param: [userMsgManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    void saveUserMsgManages(UserMsgManage userMsgManage);
}
