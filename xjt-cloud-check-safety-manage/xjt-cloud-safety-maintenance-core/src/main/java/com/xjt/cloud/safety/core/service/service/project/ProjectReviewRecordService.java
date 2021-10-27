package com.xjt.cloud.safety.core.service.service.project;

import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.entity.project.ProjectReviewRecord;

/**
 * @ClassName ProjectService 项目审核记录
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 */
public interface ProjectReviewRecordService extends BaseService {
    /**
     * @MethodName: save 保存审核信息
     * @Description:
     * @Param: [projectReviewRecord]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/10/24 15:46
     **/
    Data save(ProjectReviewRecord projectReviewRecord);
}
