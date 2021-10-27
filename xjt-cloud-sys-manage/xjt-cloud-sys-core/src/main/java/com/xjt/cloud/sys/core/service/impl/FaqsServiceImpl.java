package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.dao.sys.FaqsDao;
import com.xjt.cloud.sys.core.entity.Faqs;
import com.xjt.cloud.sys.core.service.service.FaqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:49
 * @Description: 问题帮助管理接口实现
 */
@Service
public class FaqsServiceImpl extends AbstractService implements FaqsService {

    @Autowired
    private FaqsDao faqsDao;

    /**
     *
     * 功能描述:查询常见问题信息列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data findFaqsList(String json){
        Faqs faqs = JSONObject.parseObject(json, Faqs.class);
        Integer totalCount = faqs.getTotalCount();
        Integer pageSize = faqs.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = faqsDao.findFaqsListTotalCount(faqs);
        }
        List<Faqs> list = faqsDao.findFaqsList(faqs);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data findFaqs(String json){
        Faqs faqs = JSONObject.parseObject(json, Faqs.class);
        faqs = faqsDao.findFaqs(faqs);
        return asseData(faqs);
    }

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data saveFaqs(String json){
        Faqs faqs = JSONObject.parseObject(json, Faqs.class);
        int num = faqsDao.saveFaqs(faqs);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data modifyFaqs(String json){
        Faqs faqs = JSONObject.parseObject(json, Faqs.class);
        int num = faqsDao.modifyFaqs(faqs);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除常见问题信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    @Override
    public Data delFaqs(String json){
        Faqs faqs = JSONObject.parseObject(json, Faqs.class);
        int num = faqsDao.modifyFaqs(faqs);
        return asseData(num);
    }
}
