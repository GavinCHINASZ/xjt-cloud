package com.xjt.cloud.project.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.service.service.OrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * orgUserController 组织架构
 * 
 * @author zhangZaiFa
 * @date 2019-07-29 15:15
 */
@RestController
@RequestMapping("/project/orgUser")
public class  OrgUserController extends AbstractController {

    @Autowired
    private OrgUserService orgUserService;

    /**
     * 查询用户部门公司树关系
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/7/23 15:07
     **/
    @RequestMapping(value = "/findOrgUserTree/{projectId}")
    public Data findOrgUserTree(String json) {
        return orgUserService.findOrgUserTree(json);
    }

    /** 
     * updateUserProject 更新用户默认项目信息
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/10/12 9:37
     **/
    @RequestMapping(value = "/updateUserProject")
    public Data updateUserProject(String json) {
        return orgUserService.updateUserProject(json);
    }

    /** 
     * findByOrgUserPermission 查询当前用户在项目中权限
     * 
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author zhangZaiFa
     * @date 2019/11/7 10:13
     **/
    @RequestMapping(value = "/findByOrgUserProjectPermission")
    public Data findByOrgUserProjectPermission(String json) {
        return orgUserService.findByOrgUserProjectPermission(json);
    }

    /**
     * 查询 成员 根据 权限
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/09/28
     **/
    @RequestMapping(value = "/findOrgUserByPermission/{projectId}")
    public Data findOrgUserByPermission(String json) {
        return orgUserService.findOrgUserByPermission(json);
    }
}
