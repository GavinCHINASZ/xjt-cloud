package com.xjt.cloud.admin.manage.service.impl.sys;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.sys.FaqsDao;
import com.xjt.cloud.admin.manage.entity.sys.Faqs;
import com.xjt.cloud.admin.manage.service.service.sys.FaqsService;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:49
 * @Description: 问题帮助管理接口实现
 */
@Service
public class FaqsServiceImpl extends AbstractAdminService implements FaqsService {

    @Autowired
    private FaqsDao faqsDao;

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
    @Override
    public ScriptPage<Faqs> findFaqsList(AjaxPage ajaxPage, Faqs faqs){
        faqs = asseFindObj(faqs, ajaxPage);
        return asseScriptPage(faqsDao.findFaqsList(faqs), faqsDao.findFaqsListTotalCount(faqs));
    }

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data saveFaqs(Faqs faqs){
        int num = faqsDao.saveFaqs(faqs);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data modifyFaqs(Faqs faqs){
        int num = faqsDao.modifyFaqs(faqs);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除常见问题信息
     *
     * @param faqs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data delFaqs(Faqs faqs){
        int num = faqsDao.modifyFaqs(faqs);
        return asseData(num);
    }
}
