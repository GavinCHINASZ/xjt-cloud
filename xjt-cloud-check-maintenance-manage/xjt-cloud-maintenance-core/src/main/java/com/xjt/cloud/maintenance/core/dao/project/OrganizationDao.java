package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName OrganizationDao 公司/部门Dao
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Repository
public interface OrganizationDao {
    /**
     * @MethodName: findByOrgId 根据Id查询
     * @Description:
     * @Param: [orgId]
     * @Return: com.xjt.cloud.project.core.entity.Organization
     * @Author: zhangZaiFa
     * @Date:2019/7/23 18:01
     **/
    Organization findByOrgId(@Param("id") Long orgId);

    /**@MethodName: saveOrg 添加公司或部门
     * @Description: 
     * @Param: [dep]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:13 
     **/
    void saveOrg(Organization dep);

    /**@MethodName: deleteOrgIds 删除公司或部门
     * @Description: 
     * @Param: [companyIds]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:15 
     **/
    void deleteOrgIds(List<Long> companyIds);

    /**@MethodName: updateOrg 修改公司或部门
     * @Description: 
     * @Param: [dep]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:18 
     **/
    void updateOrg(Organization dep);

    /**@MethodName: findByOrgList 查询公司或部门列表
     * @Description:
     * @Param: [dep]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Organization>
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:31
     **/
    List<Organization> findByOrgList(Organization dep);

    /**@MethodName: findByOrgListCount 查询公司或部门列表总数量
     * @Description:
     * @Param: [dep]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:31
     **/
    Integer findByOrgListCount(Organization dep);

    /**@MethodName: findByOrg 按条件查询
     * @Description: 
     * @Param: [company]
     * @Return: com.xjt.cloud.project.core.entity.Organization
     * @Author: zhangZaiFa
     * @Date:2019/9/10 16:38
     **/
    Organization findByOrg(Organization company);

    /**@MethodName: findByDepartmentTree 查询部门树结构
     * @Description: 
     * @Param: [org]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Organization>
     * @Author: zhangZaiFa
     * @Date:2019/9/12 10:37 
     **/   
    List<Organization> findByDepartmentTree(Organization org);

    /**@MethodName: findByAllList 查询所有公司部门
     * @Description: 
     * @Param: []
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Organization>
     * @Author: zhangZaiFa
     * @Date:2019/10/29 11:08 
     **/
    List<Organization> findByAllList();

    /**@MethodName: findByDepList 查询公司下的部门
     * @Description: 
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Organization>
     * @Author: zhangZaiFa
     * @Date:2019/10/30 16:22
     **/
    List<Organization> findByDepList(List<Long> ids);

    /**@MethodName: findByOrgName 查询项目下名称是否已存在
     * @Description: 
     * @Param: [dep]
     * @Return: com.xjt.cloud.project.core.entity.Organization
     * @Author: zhangZaiFa
     * @Date:2019/11/1 10:10 
     **/
    Organization findByOrgName(Organization dep);

    /**
     *
     * 功能描述: 以sql文查询部门信息列表
     *
     * @param sql
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 11:06
     */
    List<Organization> findOrgListBySql(@Param("sql")String sql);
}
