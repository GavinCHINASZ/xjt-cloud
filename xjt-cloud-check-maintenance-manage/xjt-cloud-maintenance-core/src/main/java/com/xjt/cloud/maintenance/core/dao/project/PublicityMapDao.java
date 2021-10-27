package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.PublicityMap;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:宣传图
 * @author: zxb
 * @create: 2019-12-02 16:02
 **/
public interface PublicityMapDao {
    /**@MethodName: findByPublicityMapImageUrlList
     * @Description: 查询宣传图路径集合
     * @Param: [pm]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.PublicityMap>
     * @Author: zhangZaiFa
     * @Date:2019/12/2 16:04
     **/
    List<String> findByPublicityMapImageUrlList(PublicityMap pm);

    /**@MethodName: savePublicityMapList
     * @Description: 保存宣传图路径集合
     * @Param: [list]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/2 17:20
     **/
    void savePublicityMapList(List<PublicityMap> list);

    /**@MethodName: deleteProjectPublicityMap
     * @Description: 删除项目宣传图
     * @Param: [projectId]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/2 17:21
     **/
    void deleteProjectPublicityMap(Long projectId);
}
