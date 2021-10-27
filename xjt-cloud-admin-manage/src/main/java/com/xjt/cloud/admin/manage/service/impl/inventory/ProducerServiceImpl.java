package com.xjt.cloud.admin.manage.service.impl.inventory;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.backstage.ProducerDao;
import com.xjt.cloud.admin.manage.entity.inventory.Producer;
import com.xjt.cloud.admin.manage.service.service.inventory.ProducerService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/29 16:04
 * @Description: 物联卡厂家管理接实现
 */
@Service
public class ProducerServiceImpl extends AbstractAdminService implements ProducerService {

    @Autowired
    private ProducerDao producerDao;

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
    @Override
    public ScriptPage<Producer> findProducerList(AjaxPage ajaxPage, Producer producer){
        producer = asseFindObj(producer, ajaxPage);
        return asseScriptPage(producerDao.findProducerList(producer), producerDao.findProducerListTotalCount(producer));
    }

    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @return: ScriptPage<Producer>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public List<Producer> findProducerAll(){
        Producer producer = new Producer();
        producer.setPageSize(null);
        return producerDao.findProducerList(producer);
    }

    /**
     *
     * 功能描述:新增厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public Data saveProducer(Producer producer){
        return asseData(producerDao.saveProducer(producer));
    }

    /**
     *
     * 功能描述:修改厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public Data modifyProducer(Producer producer){
        return asseData(producerDao.modifyProducer(producer));
    }

    /**
     *
     * 功能描述:删除厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @Override
    public Data delProducer(Producer producer){
        return asseData(producerDao.modifyProducer(producer));
    }
}
