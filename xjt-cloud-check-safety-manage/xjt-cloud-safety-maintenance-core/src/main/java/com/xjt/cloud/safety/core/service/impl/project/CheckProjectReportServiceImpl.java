package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.safety.core.dao.project.CheckProjectDao;
import com.xjt.cloud.safety.core.dao.project.CheckProjectReportDao;
import com.xjt.cloud.safety.core.entity.project.CheckProject;
import com.xjt.cloud.safety.core.entity.project.CheckProjectReport;
import com.xjt.cloud.safety.core.service.service.project.CheckProjectReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CheckProjectReportServiceImpl
 * @Description 评估项目报表记录
 * @Author wangzhiwen
 * @Date 2021/5/14 10:42
 **/
@Service
public class CheckProjectReportServiceImpl extends AbstractService implements CheckProjectReportService {
    @Autowired
    private CheckProjectReportDao checkProjectReportDao;
    @Autowired
    private CheckProjectDao checkProjectDao;

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 新增评估项目报表记录
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     */
    @Override
    public Data saveCheckProjectReport(String json) {
        CheckProjectReport checkProjectReport = JSONObject.parseObject(json, CheckProjectReport.class);
        return asseData(saveCheckProjectReport(checkProjectReport));
    }

    /**
     * 新增评估项目报表记录
     *
     * @param checkProjectReport CheckProjectReport
     * @return int
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     */
    @Override
    public int saveCheckProjectReport(CheckProjectReport checkProjectReport) {
        CheckProjectReport cpp = new CheckProjectReport();
        cpp.setCheckProjectId(checkProjectReport.getCheckProjectId());
        cpp.setCheckProjectStatus(2);
        List<CheckProjectReport> list = checkProjectReportDao.findCheckProjectReportList(cpp);
        if (CollectionUtils.isNotEmpty(list)){
            checkProjectReport.setId(list.get(0).getId());
        }
        if (checkProjectReport.getId() != null){
            return checkProjectReportDao.modifyCheckProjectReport(checkProjectReport);
        }
        return checkProjectReportDao.saveCheckProjectReport(checkProjectReport);
    }

    /**
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     * @Description 修改评估项目报表记录
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     */
    @Override
    public Data modifyCheckProjectReport(String json) {
        CheckProjectReport checkProjectReport = JSONObject.parseObject(json, CheckProjectReport.class);
        int num = checkProjectReportDao.modifyCheckProjectReport(checkProjectReport);
        if (num > 0 && StringUtils.isNotEmpty(checkProjectReport.getModifyReason())){//判断是否是修改报表修改申请
            CheckProject checkProject = new CheckProject();
            checkProject.setId(checkProjectReport.getCheckProjectId());
            checkProject.setCheckProjectStatus(2);
            checkProjectDao.updCheckProject(checkProject);
        }

        return asseData(num);
    }

    /**
     * 查询评估项目报表记录列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/5/14 10:49
     */
    @Override
    public Data findCheckProjectReportList(String json) {
        CheckProjectReport checkProjectReport = JSONObject.parseObject(json, CheckProjectReport.class);
        Integer totalCount = checkProjectReport.getTotalCount();
        Integer pageSize = checkProjectReport.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = checkProjectReportDao.findCheckProjectReportListCount(checkProjectReport);
        }
        return asseData(totalCount, checkProjectReportDao.findCheckProjectReportList(checkProjectReport));
    }
}
