package com.xjt.cloud.project.core.service.impl.project;

import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.project.core.dao.project.ProjectReviewRecordDao;
import com.xjt.cloud.project.core.entity.project.ProjectReviewRecord;
import com.xjt.cloud.project.core.service.service.project.ProjectReviewRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrgUserServiceImpl 项目审核记录实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class ProjectReviewRecordServiceImpl extends AbstractService implements ProjectReviewRecordService {

    @Autowired
    private ProjectReviewRecordDao projectReviewRecordDao;

    /**@MethodName: save 保存审核信息
     * @Description:
     * @Param: [projectReviewRecord]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 15:47
     **/
    @Override
    public Data save(ProjectReviewRecord projectReviewRecord) {
        projectReviewRecordDao.save(projectReviewRecord);
        return Data.isSuccess();
    }
}
