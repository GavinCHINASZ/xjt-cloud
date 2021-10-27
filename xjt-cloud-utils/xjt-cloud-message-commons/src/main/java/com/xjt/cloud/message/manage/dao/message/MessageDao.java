package com.xjt.cloud.message.manage.dao.message;

import com.xjt.cloud.message.manage.entity.Message;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Auther: zhangZaiFa
 * @Date: 2019/4/29 0029 13:45
 * @Description:消息
 */
@Repository
public interface MessageDao {

    /**@MethodName: batchSave 批量保存
     * @Description:
     * @Param: [msg]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/14 15:53
     **/
    void batchSaveUserMsg(Message msg);

    /**
     * @param msg
     * @Description 保存单个用户信息
     * @author wangzhiwen
     * @date 2021/1/20 13:48
     */
   void saveUserMsg(Message msg);


    /***@MethodName: findUserPhone
     * @Description: 查询用户手机号
     * @Param: [smsUserIds]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2020/3/23 14:44
     **/
    List<String> findUserPhone(@Param("list") List<String> list);

    /**@MethodName: saveProjectMsg
     * @Description: 保存项目信息
     * @Param: [msg]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/8 15:27
     **/
    int saveProjectMsg(Message msg);

    //////////////////////////////////////////////////////////////兼容5.0版本消息DAO//////////////////////////////
    /**@MethodName: batchSave 批量保存
     * @Description:
     * @Param: [msg]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/14 15:53
     **/
    void batchSaveCV5(Message msg);
    /**
     * 查询项目电话提醒接收人
     * @param projectId
     * @param signs
     * @return
     */
    String findProjectPhoneUserCV5(@Param("projectId") Long projectId, @Param("signs") List<String> signs);

    /**
     * 查询项目短信提醒接收人
     * @param projectId
     * @param signs
     * @return
     */
    String findProjectSMSUserCV5(@Param("projectId") Long projectId, @Param("signs") List<String> signs);

    /**@MethodName: findProjectScreenMsgSet
     * @Description: 查询项目大屏消息设置
     * @Param: [projectId]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2020/4/8 15:02
     **/
    String findProjectScreenMsgSetCV5(@Param("projectId") Long projectId);

    /**@MethodName: saveProjectMsg
     * @Description: 保存项目信息
     * @Param: [msg]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/8 15:27
     **/
    void saveProjectMsgCV5(Message msg);
}
