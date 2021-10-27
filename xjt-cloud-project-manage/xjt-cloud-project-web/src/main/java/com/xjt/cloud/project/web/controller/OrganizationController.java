package com.xjt.cloud.project.web.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName orgUserController 组织架构
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@RestController
@RequestMapping("/project/organization")
public class OrganizationController extends AbstractController {

    @Autowired
    private OrganizationService organizationService;



    /**@MethodName: saveCompany 添加公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:38
     **/
    @RequestMapping(value = "saveCompany/{projectId}")
    public Data saveCompany(String json){
        return organizationService.saveCompany(json);
    }

    /**@MethodName: deleteCompany 删除公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:40
     **/
    @RequestMapping(value = "deleteCompany/{projectId}")
    public Data deleteCompany(String json){
        return organizationService.deleteCompany(json);
    }

    /**@MethodName: deleteCompany 修改公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:40
     **/
    @RequestMapping(value = "updateCompany/{projectId}")
    public Data updateCompany(String json){
        return organizationService.updateCompany(json);
    }

    /**@MethodName: saveDepartment 添加部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:39
     **/
    @RequestMapping(value = "saveDepartment/{projectId}")
    public Data saveDepartment(String json){
        return organizationService.saveDepartment(json);
    }

    /**@MethodName: deleteDepartment 删除部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:41
     **/
    @RequestMapping(value = "deleteDepartment/{projectId}")
    public Data deleteDepartment(String json){
        return organizationService.deleteDepartment(json);
    }

    /**@MethodName: updateDepartment 修改部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 14:42
     **/
    @RequestMapping(value = "updateDepartment/{projectId}")
    public Data updateDepartment(String json){
        return organizationService.updateDepartment(json);
    }

    /**@MethodName: findByDepartment 查询部门树结构
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:07
     **/
    @RequestMapping(value = "findByDepartmentTree/{projectId}")
    public Data findByDepartmentList(String json){
        return organizationService.findByDepartmentTree(json);
    }

    /**@MethodName: findByCompanyList 查询公司列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:08
     **/
    @RequestMapping(value = "findByCompanyList/{projectId}")
    public Data findByCompanyList(String json){
        return organizationService.findByCompanyList(json);
    }

    /**@MethodName: orgCacheInit 初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/29 10:47
     **/
    @RequestMapping(value = "/orgCacheInit")
    public void orgCacheInit() {
        organizationService.orgCacheInit();
    }

    
    /**
     *
     * 功能描述: 以sql文查询部门信息列表
     *
     * @param json
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/11/26 11:06
     */
    @RequestMapping(value = "/findOrgListBySql")
    public Data findOrgListBySql(String json) {
        return organizationService.findOrgListBySql(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-07-24 11:21
     *@Description 生成添加层次序
     */
    @RequestMapping(value = "/findAllOrgLayerOrder")
    public void findAllOrgLayerOrder(){
        organizationService.findAllOrgLayerOrder();
    }
}
