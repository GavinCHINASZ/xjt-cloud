package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.AppModule;
import org.springframework.stereotype.Repository;

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
