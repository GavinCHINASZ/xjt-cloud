package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.safety.core.dao.project.*;
import com.xjt.cloud.safety.core.entity.project.*;
import com.xjt.cloud.safety.core.service.service.project.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 评估成员
 *
 * @author huanggc
 * @date 2021/05/08
 */
@Service
public class AssessmentUserServiceImpl extends AbstractService implements AssessmentUserService {
    @Autowired
    private AssessmentUserDao assessmentUserDao;

    /**
     * 查询 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @Override
    public Data findAssessmentUserList(String json) {
        AssessmentUser assessmentUser = JSON.parseObject(json, AssessmentUser.class);

        Integer totalCount = assessmentUser.getTotalCount();
        Integer pageSize = assessmentUser.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = assessmentUserDao.findAssessmentUserCount(assessmentUser);
        }

        List<AssessmentUser> assessmentUserList = assessmentUserDao.findAssessmentUserList(assessmentUser);
        return asseData(totalCount, assessmentUserList);
    }

    /**
     * 查询 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @Override
    public Data findAssessmentMemberList(String json) {
        AssessmentUser assessmentUser = JSON.parseObject(json, AssessmentUser.class);

        Integer totalCount = assessmentUser.getTotalCount();
        Integer pageSize = assessmentUser.getPageSize();
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = assessmentUserDao.findAssessmentMemberCount(assessmentUser);
        }

        List<AssessmentUser> assessmentUserList = assessmentUserDao.findAssessmentMemberList(assessmentUser);
        return asseData(totalCount, assessmentUserList);
    }

    /**
     * 保存 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @Override
    public Data saveAssessmentUser(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        List<AssessmentUser> list = JSONArray.parseArray(jsonObject.getString("formQuery"), AssessmentUser.class);
        jsonObject.remove("formQuery");
        AssessmentUser assessmentUser = JSON.parseObject(json, AssessmentUser.class);
        int num = 0;
        if (CollectionUtils.isNotEmpty(list)){
            Long projectId = assessmentUser.getProjectId();
            Long checkProjectId = assessmentUser.getCheckProjectId();
            List<AssessmentUser> saveList = new ArrayList<>();
            for (AssessmentUser user:list){
                int assessmentUserType = user.getAssessmentUserType();
                Long[] userIds = user.getUserIds();
                Long[] orgIds = user.getOrgIds();
                if (userIds != null && userIds.length > 0){
                    for (int i = 0; i < userIds.length; i++) {
                        AssessmentUser entity = new AssessmentUser();
                        entity.setAssessmentUserType(assessmentUserType);
                        entity.setUserId(userIds[i]);
                        //entity.setOrgId(orgIds[i]);
                        entity.setProjectId(projectId);
                        entity.setCheckProjectId(checkProjectId);
                        saveList.add(entity);
                    }
                }
            }

            // 先删除
            assessmentUserDao.delAssessmentUser(assessmentUser);
            num = assessmentUserDao.saveAssessmentUserList(saveList);

        }
        if (num > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     * 更新/删除 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @Override
    public Data updateAssessmentUser(String json) {
        AssessmentUser assessmentUser = JSON.parseObject(json, AssessmentUser.class);
        int num = assessmentUserDao.updateAssessmentUser(assessmentUser);
        if (num > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }
}
