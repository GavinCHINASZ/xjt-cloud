package com.xjt.cloud.task.api.controller.protect;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.service.service.protect.ProtectGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  地铁 防护等级 Controller
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
@RestController
@RequestMapping("/protect/grade/")
public class ProtectGradeController extends AbstractController {
    @Autowired
    private ProtectGradeService protectGradeService;

    /**
     * 查询 班前防护 名称list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/28
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findProtectGradeNameList/{projectId}")
    public Data findProtectGradeNameList(String json) {
        return protectGradeService.findProtectGradeNameList(json);
    }

}
