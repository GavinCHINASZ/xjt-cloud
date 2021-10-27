package com.xjt.cloud.project.core.service.impl.project;

import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.project.core.dao.project.InviteConnectionDao;
import com.xjt.cloud.project.core.entity.project.InviteConnection;
import com.xjt.cloud.project.core.service.service.project.InviteConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/** 连接邀请
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-09-26 15:31
 **/
@Service
public class InviteConnectionServiceImpl extends AbstractService implements InviteConnectionService {

    @Autowired
    private InviteConnectionDao inviteConnectionDao;

    /**@MethodName: findByProjectInviteConnection 生成连接
     * @Description: 
     * @Param: [inviteConnection]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/26 17:33
     **/
    @Override
    public InviteConnection findByProjectInviteConnection(InviteConnection inviteConnection) {
        InviteConnection oldInviteConnection =  inviteConnectionDao.findByProjectNotExpiredInviteConnection(inviteConnection);

        if(oldInviteConnection == null || (inviteConnection.getState()!=null && inviteConnection.getState()==1 )){//重新生成新的连接记录
            inviteConnection.setRandomCode(StringUtils.generateRanNum(36));
            inviteConnection.setCreateTime(new Date());
            String userName = SecurityUserHolder.getUserName();
            inviteConnection.setCreateUserName(userName);
            inviteConnection.setCreateUserId(getLoginUserId(userName));
            //inviteConnection = setEntityUserIdName(SecurityUserHolder.getUserName(),inviteConnection.getProjectId(),inviteConnection);
            inviteConnectionDao.save(inviteConnection);
        }else{
            inviteConnection = oldInviteConnection ;
        }
        inviteConnection.setCountdownTime(new Date());
        return inviteConnection;
    }

    /**@MethodName: findByProjectNotExpiredInviteConnection 校验链接邀请时间是否过期
     * @Description:
     * @Param: [inviteConnection]
     * @Return: com.xjt.cloud.project.core.entity.InviteConnection
     * @Author: zhangZaiFa
     * @Date:2019/9/27 15:24 
     **/
    @Override
    public InviteConnection findByProjectNotExpiredInviteConnection(InviteConnection inviteConnection) {
        return inviteConnectionDao.findByProjectNotExpiredInviteConnection(inviteConnection);
    }
}
