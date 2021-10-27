package com.xjt.cloud.task.core.service.service.protect;

import com.xjt.cloud.commons.utils.Data;

/**
 * 地铁 防护等级 Service
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
public interface ProtectGradeService {

    /**
     * 查询 班前防护 名称list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/28
     * @return com.xjt.cloud.commons.utils.Data
     */
    Data findProtectGradeNameList(String json);
}
