package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.safety.core.entity.project.InviteConnection;

/**
 * 连接邀请
 *
 * @program: xjt-cloud-project-manage
 * @author: zxb
 * @create: 2019-09-26 15:31
 **/

public interface InviteConnectionService {

    /**
     * @MethodName: findByProjectInviteConnection 成员邀请连接生成
     * @Description:
     * @Param: [inviteConnection]
     * @Return: com.xjt.cloud.project.core.entity.InviteConnection
     * @Author: zhangZaiFa
     * @Date:2019/9/27 15:23
     **/
    InviteConnection findByProjectInviteConnection(InviteConnection inviteConnection);

    /**
     * @MethodName: findByProjectNotExpiredInviteConnection 校验邀请连接是否过期
     * @Description:
     * @Param: [inviteConnection]
     * @Return: com.xjt.cloud.project.core.entity.InviteConnection
     * @Author: zhangZaiFa
     * @Date:2019/9/27 15:23
     **/
    InviteConnection findByProjectNotExpiredInviteConnection(InviteConnection inviteConnection);
}
