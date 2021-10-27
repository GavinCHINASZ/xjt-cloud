package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.sys.core.entity.WeChatUser;
import org.springframework.stereotype.Repository;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/31 17:45
 * @Description:
 */
@Repository
public interface WeChatDao {

    int updateWeChatUser(WeChatUser weChatUser);

    WeChatUser findWeChatUserByOpenid(String openid);

    int saveWeChatUser(WeChatUser weChatUser);
}
