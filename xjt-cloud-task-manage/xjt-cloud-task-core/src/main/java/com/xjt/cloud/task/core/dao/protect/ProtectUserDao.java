package com.xjt.cloud.task.core.dao.protect;

import com.xjt.cloud.task.core.entity.protect.Protect;
import com.xjt.cloud.task.core.entity.protect.ProtectUser;

import java.util.List;

/**
 * 地铁 班前防护 用户 Dao
 *
 * @author huangGuiChuan
 * @date 2020/09/28
 */
public interface ProtectUserDao {
    /**
     * 查询 班前防护 用户
     *
     * @param protectUser 班前防护 用户
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return List<ProtectUser>
     */
    List<ProtectUser> findProtectUserList(ProtectUser protectUser);

    /**
     * 新增 班前防护 用户
     *
     * @param protectUser 班前防护 用户
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void saveProtectUser(ProtectUser protectUser);

    /**
     * 新增 班前防护 用户
     *
     * @param protectUser 班前防护 用户
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void saveProtectUsers(ProtectUser protectUser);

    /**
     * 删除 班前防护 用户
     *
     * @param protectUser 班前防护 用户
     * @author huangGuiChuan
     * @date 2020/09/27
     */
    void deleteProtectUser(ProtectUser protectUser);

    /**
     * 查询 班前防护 用户
     *
     * @param protectUser 班前防护 用户
     * @author huangGuiChuan
     * @date 2020/09/27
     * @return List<Long>
     */
    List<Long> findProtectUserIdList(ProtectUser protectUser);

    /**
     * 根据 作业删除相关人员
     *
     * @param protect Protect
     * @author huangGuiChuan
     * @date 2020/04/06
     */
    void deleteProtectUserByProtect(Protect protect);
}
