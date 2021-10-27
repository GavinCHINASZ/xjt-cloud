package com.xjt.cloud.safety.core.service.service.project;


import com.xjt.cloud.commons.utils.Data;

/**
 * 评估成员
 *
 * @author huanggc
 * @date 2021/05/08
 */
public interface AssessmentUserService {
    /**
     * 查询 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    Data findAssessmentUserList(String json);
    /**
     * 查询 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    Data findAssessmentMemberList(String json);

    /**
     * 保存 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    Data saveAssessmentUser(String json);

    /**
     * 保存 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    Data updateAssessmentUser(String json);
}
