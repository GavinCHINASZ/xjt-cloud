package com.xjt.cloud.safety.core.service.service.device;

import com.xjt.cloud.commons.utils.Data;

/**
 *@ClassName OtherInstructionsService
 *@Author dwt
 *@Date 2020-04-10 15:58
 *@Version 1.0
 */
public interface OtherInstructionsService {

    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:32
     *@Param: OtherInstructions
     *@Return: OtherInstructions
     *@Description 查询设备登记其他说
     */
    Data findOtherInstructions(String json);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:34
     *@Param: OtherInstructions
     *@Return: OtherInstructions
     *@Description 保存设备登记其他说明
     */
    Data saveOtherInstructions(String json);

}
