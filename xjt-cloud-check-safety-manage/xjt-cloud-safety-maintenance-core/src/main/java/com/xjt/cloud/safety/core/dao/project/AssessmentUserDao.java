package com.xjt.cloud.safety.core.dao.project;

import com.xjt.cloud.safety.core.entity.project.AssessmentUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估成员
 *
 * @author huanggc
 * @date 2021/05/08
 */
@Repository
public interface AssessmentUserDao {
    /**
     * 查询 评估成员 数量
     *
     * @param assessmentUser AssessmentUser
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    Integer findAssessmentUserCount(AssessmentUser assessmentUser);

    /**
     * 查询 评估成员 list
     *
     * @param assessmentUser AssessmentUser
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    List<AssessmentUser> findAssessmentUserList(AssessmentUser assessmentUser);

    /**
     * 查询 评估成员 数量
     *
     * @param assessmentUser AssessmentUser
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    Integer findAssessmentMemberCount(AssessmentUser assessmentUser);

    /**
     * 查询 评估成员 list
     *
     * @param assessmentUser AssessmentUser
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    List<AssessmentUser> findAssessmentMemberList(AssessmentUser assessmentUser);

    /**
     * 保存 评估成员
     *
     * @param assessmentUser AssessmentUser
     * @return int
     * @author huanggc
     * @date 2021/05/08
     **/
    int saveAssessmentUser(AssessmentUser assessmentUser);

    /**
     * 保存 评估成员list
     *
     * @param assessmentUserList AssessmentUser
     * @return int
     * @author huanggc
     * @date 2021/05/08
     **/
    int saveAssessmentUserList(List<AssessmentUser> assessmentUserList);

    /**
     * 更新/删除 评估成员
     *
     * @param assessmentUser AssessmentUser
     * @return int
     * @author huanggc
     * @date 2021/05/08
     **/
    int updateAssessmentUser(AssessmentUser assessmentUser);

    /**
     * 删除 评估成员
     *
     * @param assessmentUser AssessmentUser
     * @return int
     * @author huanggc
     * @date 2021/05/08
     **/
    int delAssessmentUser(AssessmentUser assessmentUser);
}
