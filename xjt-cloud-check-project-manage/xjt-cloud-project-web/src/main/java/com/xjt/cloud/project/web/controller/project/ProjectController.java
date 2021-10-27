package com.xjt.cloud.project.web.controller.project;

        import com.xjt.cloud.commons.abstracts.AbstractController;
        import com.xjt.cloud.commons.utils.Data;
        import com.xjt.cloud.project.core.service.service.project.ProjectService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ProjectController 项目
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@RestController
@RequestMapping("/project")
public class ProjectController extends AbstractController {

    @Autowired
    private ProjectService projectService;


    /**
     * @MethodName: get 按Id查询
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:18
     **/
    @RequestMapping(value = "/findById/{projectId}")
    public Data findById(String json) {
        return projectService.findById(json);
    }

    /**
     * @MethodName: findByProjectList 按项目属性搜索项目列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:18
     **/
    @RequestMapping(value = "/findByProjectList")
    public Data findByProjectList(String json) {
        return projectService.findByProjectList(json);
    }



    /**
     * @MethodName: addProject  添加项目信息
     * @Description:
     * @Param: [json] 项目属性
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:18
     **/
    @RequestMapping(value = "/addProject")
    public Data addProject(String json) {
        return projectService.addProject(json);
    }

    /**
     * @MethodName: deleteProject 删除项目信息
     * @Description:
     * @Param: [json] List<String> 结构
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:18
     **/
    @RequestMapping(value = "/deleteProject")
    public Data deleteProject(String json) {
        return projectService.deleteProject(json);
    }

    /**
     * @MethodName: updateProject 修改项目信息
     * @Description:
     * @Param: [json] 项目属性
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date: 14:19
     **/
    @RequestMapping(value = "/updateProject/{projectId}")
    public Data updateProject(String json) {
        return projectService.updateProject(json);
    }

    /**
     * @MethodName: projectMenu 项目菜单
     * @Description:
     * @Param: [projectId, json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/8/1 9:57
     **/
    @RequestMapping(value = "/projectMenu/{projectId}")
    public Data projectMenu(String json) {
        return projectService.projectMenu(json);
    }

    /**
     * 功能描述: 项目信息缓存初使化
     *
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/9 9:38
     */
    @RequestMapping(value = "/projectCacheInit")
    public void projectCacheInit() {
        projectService.projectCacheInit();
    }

    /**@MethodName: projectTransfer 项目转让
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/9/23 9:53
     **/
    @RequestMapping(value = "/projectTransfer/{projectId}")
    public Data projectTransfer(String json) {
        return projectService.projectTransfer(json);
    }

    /**@MethodName: projectReview 项目审核
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 13:56
     **/
    @RequestMapping(value = "/projectReview")
    public Data projectReview(String json) {
        return projectService.projectReview(json);
    }


    /**@MethodName: findProjectHomeData
     * @Description: 查询项目首页数据
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/10 15:44
     **/
    @RequestMapping(value = "/findProjectHomeData")
    public Data findProjectHomeData(String json) {
        return projectService.findProjectHomeData(json);
    }

    @RequestMapping(value = "/getHtml")
    public Data getHtml(String json) {
        return projectService.getHtml(json);
    }

}
