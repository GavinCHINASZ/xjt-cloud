package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.project.CheckProject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CheckProjectDao {
    /**@MethodName: updCheckProject
     * @Description: 更新检测项目信息
     * @Param: [CheckProject]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:52
     **/
    void updCheckProject(CheckProject checkProject);

    /**@MethodName: findByCheckProject
     * @Description: 查询检测项目信息
     * @Param: [CheckProject]
     * @Return: com.xjt.cloud.project.core.entity.CheckProject
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:52
     **/
    CheckProject findByCheckProject(CheckProject checkProject);

    /**@MethodName: saveCheckProject
     * @Description: 保存检测项目信息
     * @Param: [CheckProject]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:52
     **/
    Integer saveCheckProject(CheckProject checkProject);

    /**@MethodName: delCheckProject
     * @Description: 删除检测项目信息
     * @Param: [CheckProject]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:55
     **/
    void delCheckProject(CheckProject checkProject);


    /**@MethodName: findByCheckProjectList
     * @Description: 查询检测项目信息列表
     * @Param: [CheckProject]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.CheckProject>
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:52
     **/
    List<CheckProject> findByCheckProjectList(CheckProject checkProject);

    /**@MethodName: findByCheckProjectListCount
     * @Description: 查询检测项目信息数量
     * @Param: [CheckProject]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/4/10 15:52
     **/
    Integer findByCheckProjectListCount(CheckProject checkProject);

    /**@MethodName: findDictCodeDescription
     * @Description: 查询数据词典描述
     * @Param: [check_project_report_number]
     * @Return: java.lang.String
     * @Author: zhangZaiFa
     * @Date:2020/4/12 9:33
     **/
    String findDictCodeDescription(@Param("dictCode") String dictCode);

    /**@MethodName: updDictCodeDescription
     * @Description: 更新数据词典描述
     * @Param: [checkProjectNum, check_project_report_number]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/12 9:38
     **/
    void updDictCodeDescription(@Param("description")String description,@Param("dictCode") String check_project_report_number);

    /**@MethodName: findByMyCheckProjectList
     * @Description: 查询我的检测项目列表
     * @Param: [checkProject]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.project.CheckProject>
     * @Author: zhangZaiFa
     * @Date:2020/4/14 9:16
     **/
    List<CheckProject> findByMyCheckProjectList(CheckProject checkProject);

    /**@MethodName: findByMyCheckProjectListCount
     * @Description: 查询我的检测项目列表数量
     * @Param: [checkProject]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/4/14 9:17
     **/
    Integer findByMyCheckProjectListCount(CheckProject checkProject);

    /**@MethodName: upCheckProjectNumber
     * @Description: 修改检测项目报告编号
     * @Param: [entity]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/4/17 17:27
     **/
    void upCheckProjectNumber(CheckProject entity);
}
