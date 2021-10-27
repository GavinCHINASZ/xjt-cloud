package com.xjt.cloud.safety.api.controller.project;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.project.AssessmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 评估成员
 *
 * @author huanggc
 * @date 2021/05/08
 */
@RestController
@RequestMapping("/assessment/user/")
public class AssessmentUserController extends AbstractController {

    @Autowired
    private AssessmentUserService assessmentUserService;

    /**
     * 查询 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @RequestMapping(value = "findAssessmentUserList/{projectId}")
    public Data findAssessmentUserList(String json) {
       return assessmentUserService.findAssessmentUserList(json);
    }

    /**
     * 查询 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @RequestMapping(value = "findAssessmentMemberList/{projectId}")
    public Data findAssessmentMemberList(String json) {
        return assessmentUserService.findAssessmentMemberList(json);
    }

    /**
     * 保存 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @RequestMapping(value = "saveAssessmentUser/{projectId}")
    public Data saveAssessmentUser(String json) {
        return assessmentUserService.saveAssessmentUser(json);
    }

    /**
     * 修改/删除 评估成员
     *
     * @param json 参数
     * @return Data
     * @author huanggc
     * @date 2021/05/08
     **/
    @RequestMapping(value = "updateAssessmentUser/{projectId}")
    public Data updateAssessmentUser(String json) {
        return assessmentUserService.updateAssessmentUser(json);
    }
}
