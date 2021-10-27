package com.xjt.cloud.project.core.dao.device;

import com.xjt.cloud.project.core.entity.device.OtherInstructions;
import org.springframework.stereotype.Repository;

/**
 *@ClassName OtherInstructionsDao
 *@Author dwt
 *@Date 2020-04-10 15:56
 *@Version 1.0
 */
@Repository
public interface OtherInstructionsDao {
    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:32
     *@Param: OtherInstructions
     *@Return: OtherInstructions
     *@Description 查询设备登记其他说
     */
    OtherInstructions findOtherInstructions(OtherInstructions o);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:34
     *@Param: OtherInstructions
     *@Return: OtherInstructions
     *@Description 保存设备登记其他说明
     */
    Integer saveOtherInstructions(OtherInstructions o);
    /**
     *@Author: dwt
     *@Date: 2020-04-11 14:35
     *@Param: OtherInstructions
     *@Return: OtherInstructions
     *@Description 修改设备登记其他说明
     */
    Integer modifyOtherInstructions(OtherInstructions o);
}
