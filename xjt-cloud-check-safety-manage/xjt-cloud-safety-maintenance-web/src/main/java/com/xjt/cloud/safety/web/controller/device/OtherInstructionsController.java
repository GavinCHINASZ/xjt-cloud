package com.xjt.cloud.safety.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.device.OtherInstructionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OtherInstructonsController
 * @Author dwt
 * @Date 2020-04-11 14:57
 * @Version 1.0
 */
@RestController
@RequestMapping("/other")
public class OtherInstructionsController extends AbstractController {

    @Autowired
    private OtherInstructionsService instructionsService;

    /**
     *@Author: dwt
     *@Date: 2020-04-11 15:00
     *@Param: json
     *@Return: Data
     *@Description 查询设备登记其他说明
     */
    @RequestMapping(value = "/findOtherInstructions/{projectId}")
    public Data findOtherInstructions(String json) {
        return instructionsService.findOtherInstructions(json);
    }
    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:59
     *@Param: json
     *@Return: Data
     *@Description 保存设备登记其他说明
     */
    @RequestMapping(value = "/saveOtherInstructions/{projectId}")
    public Data saveOtherInstructions(String json) {
        return instructionsService.saveOtherInstructions(json);
    }
}
