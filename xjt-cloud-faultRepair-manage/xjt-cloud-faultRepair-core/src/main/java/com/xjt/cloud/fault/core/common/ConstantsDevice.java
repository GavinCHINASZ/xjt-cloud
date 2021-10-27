package com.xjt.cloud.fault.core.common;

import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 *
 * @Auther: huangGuiChuan
 * @Date: 2019-12-06
 * @Description: 故障报修公共参数
 */
public interface ConstantsDevice {
    /**
     * 任务管理列表导出模版路径(在application.properties中配置)
     */
    String FAULT_REPAIR_RECORD_MODEL_FILE_PATH = PropertyUtils.getProperty("fault.repair.record.model.file.path");

    /**
     * orgUser缓存
     */
    String ORG_USER_CACHE_URL = PropertyUtils.getProperty("org.user.cache.url");

    Integer NO_MAXVALUE_999 = 999;

    /**
     * 微信公众号故障消息模板ID
     */
    String WE_CHAT_TEMPLATE_ID = PropertyUtils.getProperty("we.chat.template.id");
    /**
     * orgUser
     */
    String ORG_USER_URL = PropertyUtils.getProperty("org.user.url");
}
