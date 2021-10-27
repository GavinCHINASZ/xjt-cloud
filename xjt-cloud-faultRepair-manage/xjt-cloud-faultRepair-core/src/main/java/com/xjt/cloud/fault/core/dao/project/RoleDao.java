package com.xjt.cloud.fault.core.dao.project;

import com.xjt.cloud.fault.core.entity.project.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ProjectRoleDao 项目角色Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface RoleDao {

    /**
     * 功能描述: 根据 项目ID 查询 角色
     *
     * @param role 角色
     * @auther: huanggc
     * @date: 2019/10/28
     * @return: List<Role>
     */
    List<Role> findRoleList(Role role);
}
