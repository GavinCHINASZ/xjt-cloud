package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.AppModule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-07-31 14:05
 **/
@Repository
public interface AppModuleDao {

    /**@MethodName: findByAppModule
     * @Description: 查询
     * @Param: [appModule]
     * @Return: com.xjt.cloud.project.core.entity.AppModule
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:50
     **/
    AppModule findByModule(AppModule appModule);

    /**
     * @param type
     * @return AppModule
     * @Description 查询默认排序
     * @author wangzhiwen
     * @date 2020/11/3 11:27
     */
   AppModule findDefaultModule(@Param("type") int type);

    /**@MethodName: save
     * @Description: 保存
     * @Param: [appModule]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:50
     **/
    void save(AppModule appModule);

    /**@MethodName: delete
     * @Description: 删除
     * @Param: [appModule]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/30 10:57
     **/
    void delete(AppModule appModule);
}
