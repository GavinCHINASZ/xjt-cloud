package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.Path;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/12/10 10:12
 * @Description:
 */
@Repository
public interface PathDao {

    /**@MethodName: pathListPage
     * @Description: path集合
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2019/12/12 9:51
     **/
    List<Path> findPathListPage(Path path);

    /**@MethodName: pathListPageCount
     * @Description: path总数
     * @Param: [path]
     * @Return: java.lang.Integer
     * @Author: zhangZaiFa
     * @Date:2019/12/12 9:51
     **/
    Integer findPathListPageCount(Path path);

    /**@MethodName: savePath
     * @Description: 添加Path
     * @Param: [path]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:33
     **/
    void savePath(Path path);

    /**@MethodName: modifyPath
     * @Description: 修改Path
     * @Param: [path]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:33
     **/
    void modifyPath(Path path);

    /**@MethodName: delPath
     * @Description: 删除Path
     * @Param: [path]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/12 15:33
     **/
    void delPath(Path path);

    /**@MethodName: findPathList
     * @Description: 查询Path列表
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2019/12/13 18:08
     **/
    List<Path> findPathList(Path path);

    /**@MethodName: findId
     * @Description: 查询Path
     * @Param: [pathId]
     * @Return: com.xjt.cloud.admin.manage.entity.project.Path
     * @Author: zhangZaiFa
     * @Date:2019/12/16 18:16
     **/
    Path findId(@Param("id") Long id);

    /**@MethodName: findPermissionTree
     * @Description: 查询权限的树结构
     * @Param: [path]
     * @Return: java.util.List<com.xjt.cloud.admin.manage.entity.project.Path>
     * @Author: zhangZaiFa
     * @Date:2020/4/1 15:00
     **/

    List<Path> findPermissionPathTree(Path path);
}
