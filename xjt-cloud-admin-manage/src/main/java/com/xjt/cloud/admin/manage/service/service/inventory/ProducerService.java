package com.xjt.cloud.admin.manage.service.service.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.Producer;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/29 16:03
 * @Description: 物联卡厂家管理接口
 */
public interface ProducerService {
    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @param ajaxPage
     * @param producer
     * @return: ScriptPage<Producer>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    ScriptPage<Producer> findProducerList(AjaxPage ajaxPage, Producer producer);

    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @return: ScriptPage<Producer>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    List<Producer> findProducerAll();

    /**
     *
     * 功能描述:新增厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Data saveProducer(Producer producer);

    /**
     *
     * 功能描述:修改厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Data modifyProducer(Producer producer);

    /**
     *
     * 功能描述:删除厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Data delProducer(Producer producer);
}
