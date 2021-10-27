package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.sys.Faqs;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:49
 * @Description: 问题帮助管理DAO
 */
@Repository
public interface FaqsDao {
    /**
     *
     * 功能描述:查询常见问题信息列表
     *
     * @param faqs
     * @return: List<Faqs>
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    List<Faqs> findFaqsList(Faqs faqs);

    /**
     *
     * 功能描述:查询常见问题信息列表总数
     *
     * @param faqs
     * @return: List<Faqs>
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    int findFaqsListTotalCount(Faqs faqs);

    /**
     *
     * 功能描述:新增常见问题信息
     *
     * @param faqs
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    int saveFaqs(Faqs faqs);

    /**
     *
     * 功能描述:修改常见问题信息
     *
     * @param faqs
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 14:23
     */
    int modifyFaqs(Faqs faqs);
}
