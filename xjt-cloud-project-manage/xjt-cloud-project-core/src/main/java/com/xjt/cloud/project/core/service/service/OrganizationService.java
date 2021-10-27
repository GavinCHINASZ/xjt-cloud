package com.xjt.cloud.project.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.entity.Organization;

/**
 * @ClassName OrganizationService 公司/部门
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
public interface OrganizationService {
    /**
     * @MethodName: findByOrgId 查询组织结构ID
     * @Description:
     * @Param: [orgId]
     * @Return: com.xjt.cloud.project.core.entity.Organization
     * @Author: zhangZaiFa
     * @Date:2019/7/23 17:56
     **/
    Organization findByOrgId(Long orgId);



    /**@MethodName: updateDepartment 修改部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:44
     **/
    Data updateDepartment(String json);

    /**@MethodName: deleteDepartment 删除部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:44
     **/
    Data deleteDepartment(String json);

    /**@MethodName: saveDepartment 添加部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:44
     **/
    Data saveDepartment(String json);


    /**@MethodName: saveCompany 添加公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:44
     **/
    Data saveCompany(String json);

    /**@MethodName: deleteCompany 删除公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:44
     **/
    Data deleteCompany(String json);

    /**@MethodName: updateCompany 修改公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:44
     **/
    Data updateCompany(String json);

    /**@MethodName: findByCompanyList 查询公司列表
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:08 
     **/
    Data findByCompanyList(String json);

    /**@MethodName: findByDepartmentList 查询部门树结构
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:12
     **/
    Data findByDepartmentTree(String json);

    /**@MethodName: orgCacheInit 添加初始化缓存
     * @Description: 
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/29 11:07 
     **/
    void orgCacheInit();

    /**
     *
     * 功能描述: 以sql文查询部门信息列表
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 11:06
     */
    Data findOrgListBySql(String json);

    Data findAllOrgLayerOrder();
}
