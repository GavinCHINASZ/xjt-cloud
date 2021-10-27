package com.xjt.cloud.safety.core.dao.device;

import com.xjt.cloud.safety.core.entity.device.AssessmentTestRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估测试记录
 *
 * @author huanggc
 * @date 2021/05/10
 */
@Repository
public interface AssessmentTestRecordDao {
    /**
     * 查询 评估测试记录 数量
     *
     * @author huanggc
     * @date 2021/05/10
     * @param assessmentTestRecord AssessmentTestRecord
     * @return Integer
     */
    Integer findAssessmentTestRecordCount(AssessmentTestRecord assessmentTestRecord);

    /**
     * 查询 评估测试记录 list
     *
     * @author huanggc
     * @date 2021/05/10
     * @param assessmentTestRecord AssessmentTestRecord
     * @return List<AssessmentTestRecord>
     */
    List<AssessmentTestRecord> findAssessmentTestRecordList(AssessmentTestRecord assessmentTestRecord);

    /**
     * 查询 评估测试记录
     *
     * @author huanggc
     * @date 2021/05/10
     * @param assessmentTestRecord AssessmentTestRecord
     * @return AssessmentTestRecord
     */
    AssessmentTestRecord findAssessmentTestRecord(AssessmentTestRecord assessmentTestRecord);

    /**
     * 保存 评估测试记录
     *
     * @author huanggc
     * @date 2021/05/10
     * @param assessmentTestRecord AssessmentTestRecord
     * @return int
     */
    int saveAssessmentTestRecord(AssessmentTestRecord assessmentTestRecord);
}
