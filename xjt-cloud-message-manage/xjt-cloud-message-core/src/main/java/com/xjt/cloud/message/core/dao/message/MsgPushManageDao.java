package com.xjt.cloud.message.core.dao.message;

import com.xjt.cloud.message.core.entity.MsgPushManage;
import com.xjt.cloud.message.core.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName MsgPushManageDao 消息推送管理
 * @Author zhangZaiFa
 * @Date 2019-11-14 10:10
 * @Description
 */
@Repository
public interface MsgPushManageDao {
     /**@MethodName: findMsgPushManage 查询消消息推送管理
      * @Description:
      * @Param: [msgPushManage]
      * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
      * @Author: zhangZaiFa
      * @Date:2019/11/15 16:34
      **/
     MsgPushManage findMsgPushManage(MsgPushManage msgPushManage) ;

    /**@MethodName: findMsgPushManage 查询消消息推送管理
     * @Description:
     * @Param: [msgPushManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    List<MsgPushManage> findMsgPushManageCV5(MsgPushManage msgPushManage) ;
    /**@MethodName: deleteMsgPushManage 删除消息推送管理
     * @Description:
     * @Param: [msgPushManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    void deleteMsgPushManageCV5(MsgPushManage msgPushManage) ;
    /**@MethodName: findMsgPushManageList
     * @Description: 查询消消息推送管理
     * @Param: [msgPushManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.MsgPushManage>
     * @Author: zhangZaiFa
     * @Date:2020/3/23 15:52
     **/
    List<MsgPushManage> findMsgPushManageListCV5(MsgPushManage msgPushManage);
    /**@MethodName: findSmsUsers
     * @Description: 查询短信用户
     * @Param: [msgPushManage]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/3/23 15:52
     **/
    List<User> findSmsUsersCV5(MsgPushManage msgPushManage);

    /**@MethodName: findPhoneUsers
     * @Description: 查询语音用户
     * @Param: [msgPushManage]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/3/23 15:52
     **/
    List<User> findPhoneUsersCV5(MsgPushManage msgPushManage);

     /**@MethodName: saveMsgPushManage 保存消防推送管理
      * @Description:
      * @Param: [msgPushManage]
      * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
      * @Author: zhangZaiFa
      * @Date:2019/11/15 16:34
      **/
     int saveMsgPushManage(MsgPushManage msgPushManage) ;
    /**@MethodName: saveMsgPushManage 保存消防推送管理
     * @Description:
     * @Param: [msgPushManage]
     * @Return: java.util.List<com.xjt.cloud.message.core.entity.Message>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 16:34
     **/
    int saveMsgPushManageCV5(MsgPushManage msgPushManage) ;

     /**
      * @param msgPushManage
      * @return int
      * @Description 修改信息发送配置
      * @author wangzhiwen
      * @date 2021/1/13 14:35
      */
    int modifyMsgPushManage(MsgPushManage msgPushManage);

     /**
      * @param msgPushManage
      * @return int
      * @Description 保存项目信息级别配置信息
      * @author wangzhiwen
      * @date 2021/1/13 14:35
      */
    int saveProjectMsgLevel(MsgPushManage msgPushManage);

     /**@MethodName: findSmsUsers
      * @Description: 查询短信用户
      * @Param: [msgPushManage]
      * @Return: void
      * @Author: zhangZaiFa
      * @Date:2020/3/23 15:52
      **/
     List<User> findUserList(@Param("projectId")Long projectId,@Param("userIds")String[] userIds);

     /**
      * findMsgPushManageNum 查询 消息推送管理 数量
      *
      * @param msgPushManage
      * @author huanggc
      * @date 2020-09-04
      * @return Integer
      **/
    Integer findMsgPushManageNum(MsgPushManage msgPushManage);
}
