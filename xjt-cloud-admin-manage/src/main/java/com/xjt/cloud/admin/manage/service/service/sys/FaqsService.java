package com.xjt.cloud.admin.manage.service.service.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.Faqs;
import com.xjt.cloud.commons.utils.Data;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:48
 * @Description: 问题帮助管理接口
 */
public interface FaqsService {
    /**
     *
     * 功能描述:查询常见问题信息列表
     *
     * @param ajaxPage
     * @param faqs
     * @return: ScriptPage<Faqs>
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    ScriptPage<Faqs> findFaqsList(AjaxPage ajaxPage, Faqs faqs);

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data saveFaqs(Faqs faqs);

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data modifyFaqs(Faqs faqs);

    /**
     *
     * 功能描述:删除常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    Data delFaqs(Faqs faqs);
}
