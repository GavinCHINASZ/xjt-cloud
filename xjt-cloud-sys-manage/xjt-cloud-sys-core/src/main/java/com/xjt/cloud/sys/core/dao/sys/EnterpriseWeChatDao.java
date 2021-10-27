package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.commons.entity.EnterpriseWeChatUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/3/12 09:43
 * @Description: 企业微信管理
 */
@Repository
public interface EnterpriseWeChatDao {

    /**
     *
     * 功能描述:查询数据库中的企业微信用户信息与平台用户信息绑定关系
     *
     * @param enterpriseWeChatUser
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/3/12 14:59
     */
    EnterpriseWeChatUser findEnterpriseWeChatUserByWeChatUserId(EnterpriseWeChatUser enterpriseWeChatUser);
    /**
     *
     * 功能描述:新增的企业微信用户信息与平台用户信息绑定关系
     *
     * @param enterpriseWeChatUser
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/3/12 14:59
     */
    int saveEnterpriseWeChatUser(EnterpriseWeChatUser enterpriseWeChatUser);
    /**
     *
     * 功能描述:修改的企业微信用户信息与平台用户信息绑定关系
     *
     * @param enterpriseWeChatUser
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/3/12 14:59
     */
    int modifyEnterpriseWeChatUser(EnterpriseWeChatUser enterpriseWeChatUser);

    /**
     *
     * 功能描述:以用户id数组得到用户登录账号
     *
     * @param userIds
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2020/3/17 11:24
     */
    EnterpriseWeChatUser findUserLoginNames(@Param("userIds") String[] userIds);
}
