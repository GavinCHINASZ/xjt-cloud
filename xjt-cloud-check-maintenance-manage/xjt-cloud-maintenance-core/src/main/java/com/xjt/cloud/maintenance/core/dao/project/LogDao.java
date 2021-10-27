package com.xjt.cloud.maintenance.core.dao.project;

import com.xjt.cloud.maintenance.core.entity.project.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-08-20 16:41
 **/
@Repository
public interface LogDao {
    /**@MethodName: findByProjectLogList 查询项目日志
     * @Description:
     * @Param: [projectLog]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectLog>
     * @Author: zhangZaiFa
     * @Date:2019/8/20 16:45
     **/
    List<Log> findByProjectLogList(Log projectLog);

    /**@MethodName: findByProjectLogList 查询项目日志总数
     * @Description:
     * @Param: [projectLog]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.ProjectLog>
     * @Author: zhangZaiFa
     * @Date:2019/8/20 16:45
     **/
    Integer findByProjectLogListCount(Log projectLog);

    /**@MethodName: findByProjectLogTypeList 查询项目日志类型
     * @Description: 
     * @Param: [projectLog]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/10/8 14:20
     **/
    List<String> findByProjectLogTypeList(Log projectLog);
}
