package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.ProjectFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:项目文件
 * @author: zhangzaifa
 * @create: 2020-05-18 14:31
 **/
@Repository
public interface ProjectFileDao {
    /**@MethodName: findProjectFileList
     * @Description: 查询项目文件列表
     * @Param: [projectFile]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectFile>
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:44
     **/
    List<ProjectFile> findProjectFileList(ProjectFile projectFile);

    /**@MethodName: findProjectFileListCount
     * @Description: 查询项目文件数量
     * @Param: [projectFile]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:44
     **/
    Integer findProjectFileListCount(ProjectFile projectFile);

    /**@MethodName: updateProjectFile
     * @Description: 修改项目文件内容
     * @Param: [projectFile]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:44
     **/
    Integer updateProjectFile(ProjectFile projectFile);

    /**@MethodName: saveProjectFile
     * @Description: 保存项目文件
     * @Param: [projectFile]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/5/18 14:44
     **/
    Integer saveProjectFile(ProjectFile projectFile);

    /**@MethodName: findParentIdProjectFile
     * @Description: 查询
     * @Param: [ids]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectFile>
     * @Author: zhangZaiFa
     * @Date:2020/5/28 14:43
     **/
    List<ProjectFile> findProjectFileParentIds(@Param("list") List<Long> list);

    /**@MethodName: findProjectFileParentId
     * @Description: 查询项目
     * @Param: [parentId]
     * @Return: com.xjt.cloud.project.core.entity.ProjectFile
     * @Author: zhangZaiFa
     * @Date:2020/6/1 9:54
     **/
    ProjectFile findProjectFileId(@Param("id") Long id);

    /**@MethodName: updateProjectFileChild
     * @Description: 修改项目文件子目录信息
     * @Param: [projectFile]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2020/6/1 10:18
     **/
    Integer updateProjectFileChild(ProjectFile projectFile);
}
