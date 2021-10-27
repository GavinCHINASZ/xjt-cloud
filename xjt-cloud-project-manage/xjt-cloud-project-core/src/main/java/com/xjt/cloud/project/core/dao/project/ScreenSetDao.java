package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.Project;
import com.xjt.cloud.project.core.entity.ScreenSet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2020-04-07 17:13
 **/
@Repository
public interface ScreenSetDao {
    /**@MethodName: delete
     * @Description: 删除
     * @Param: [screenSet]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/7 18:27
     **/
    void delete(ScreenSet screenSet);

    /**@MethodName: save
     * @Description: 保存
     * @Param: [screenSet]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/7 18:28
     **/
    int save(ScreenSet screenSet);

    /**
     * @param screenSet
     * @return int
     * @Description 修改大屏配置信息
     * @author wangzhiwen
     * @date 2021/1/13 17:31
     */
   int modifyScreenSet(ScreenSet screenSet);

    /**@MethodName: findScreed
     * @Description: 查询
     * @Param: [screenSet]
     * @Return: com.xjt.cloud.project.core.entity.ScreenSet
     * @Author: zhangZaiFa
     * @Date:2020/4/7 18:29 
     **/
    ScreenSet findScreenSet(ScreenSet screenSet);

    /**@MethodName: findScreenProjectList
     * @Description: 查询大屏项目列表
     * @Param: [userId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Project>
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:31
     **/
    List<Project> findScreenProjectList(Project project);

    /**@MethodName: findScreenProjectList
     * @Description: 查询大屏项目列表
     * @Param: [userId]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.Project>
     * @Author: zhangZaiFa
     * @Date:2020/4/9 9:31
     **/
    List<Project> findScreenProjectListCV5(Project project);

    Integer findScreenProjectListCount(Project project);
    Integer findScreenProjectListCountCV5(Project project);
}
