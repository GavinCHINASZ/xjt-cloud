package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.InviteConnection;
import org.springframework.stereotype.Repository;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-09-26 15:58
 **/
@Repository
public interface InviteConnectionDao {
    /**@MethodName: save 添加新连接
     * @Description: 
     * @Param: [inviteConnection]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/26 18:06 
     **/
    void save(InviteConnection inviteConnection);

    /**@MethodName: findByProjectNotExpiredInviteConnection 查询项目内未过期邀请连接
     * @Description: 
     * @Param: [inviteConnection]
     * @Return: com.xjt.cloud.project.core.entity.InviteConnection
     * @Author: zhangZaiFa
     * @Date:2019/9/26 18:12
     **/
    InviteConnection findByProjectNotExpiredInviteConnection(InviteConnection inviteConnection);
}
