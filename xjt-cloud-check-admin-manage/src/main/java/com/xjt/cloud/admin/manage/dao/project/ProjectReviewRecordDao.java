package com.xjt.cloud.admin.manage.dao.project;

import com.xjt.cloud.admin.manage.entity.project.Building;
import com.xjt.cloud.admin.manage.entity.project.BuildingFloor;
import com.xjt.cloud.admin.manage.entity.project.ProjectReviewRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectReviewRecordDao {

    /**
     * 功能描述:查询项目审核记录
     * @Author zhangZaiFa
     * @Date 2019/5/23
     * @return com.xjt.cloud.commons.utils.Data
     */
    List<ProjectReviewRecord> findProjectReviewRecordList(ProjectReviewRecord projectReviewRecord);

    /**
     *
     * 功能描述: 示例项目任务
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/7/23 16:29
     */
    void examplesProjectTask();
}
