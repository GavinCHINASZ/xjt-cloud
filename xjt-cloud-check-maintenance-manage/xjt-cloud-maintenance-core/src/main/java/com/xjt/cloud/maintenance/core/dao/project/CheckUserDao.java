package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.CheckUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@ClassName CheckUserDao
 *@Author dwt
 *@Date 2020-04-12 11:21
 *@Version 1.0
 */
@Repository
public interface CheckUserDao {
    /**
     *@Author: dwt
     *@Date: 2020-04-12 13:36
     *@Param: List<CheckUser>
     *@Return: java.lang.Integer
     *@Description 保存检测员
     */
    Integer saveCheckUser(@Param("checkUsers") List<CheckUser> checkUsers);
    /**
     *@Author: dwt
     *@Date: 2020-04-12 13:37
     *@Param: CheckUser
     *@Return: java.lang.Integer
     *@Description 根据检测id删除检测员
     */
    Integer delCheckUser(CheckUser checkUser);
    /**
     *@Author: dwt
     *@Date: 2020-04-12 13:39
     *@Param: CheckUser
     *@Return: List<CheckUser>
     *@Description 根据检测id查询检测员
     */
    List<CheckUser> findCheckUserByCheckProjectId(CheckUser checkUser);
    /**
     *@Author: dwt
     *@Date: 2020-04-12 14:41
     *@Param: CheckUser
     *@Return: CheckUser
     *@Description 查询检测员
     */
    CheckUser findCheckUser(CheckUser checkUser);

    /**@MethodName: findCheckUserIdList
     * @Description: 查询检测人员用户ID列表
     * @Param: [checkUser]
     * @Return: java.util.List<java.lang.Long>
     * @Author: zhangZaiFa
     * @Date:2020/4/15 15:37
     **/
    List<Long> findCheckUserIdList(CheckUser checkUser);
}
