package com.xjt.cloud.project.core.dao.project;

import com.xjt.cloud.project.core.entity.project.ProjectReviewRecord;
import org.springframework.stereotype.Repository;

/**
 * 项目审核记录
 *
 * @program: xjt-cloud-project-manage
 * @description:
 * @author: zxb
 * @create: 2019-10-24 15:51
 **/
@Repository
public interface ProjectReviewRecordDao {
    /**@MethodName: save 保存信息
     * @Description: 
     * @Param: [projectReviewRecord]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/24 15:53 
     **/
    void save(ProjectReviewRecord projectReviewRecord);
}
