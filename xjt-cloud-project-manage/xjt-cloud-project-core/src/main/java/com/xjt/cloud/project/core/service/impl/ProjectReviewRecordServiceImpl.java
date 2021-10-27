package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.project.core.dao.project.ProjectDao;
import com.xjt.cloud.project.core.dao.project.ProjectReviewRecordDao;
import com.xjt.cloud.project.core.entity.Permission;
import com.xjt.cloud.project.core.entity.Project;
import com.xjt.cloud.project.core.entity.ProjectReviewRecord;
import com.xjt.cloud.project.core.entity.User;
import com.xjt.cloud.project.core.service.service.*;
import javafx.scene.chart.PieChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
