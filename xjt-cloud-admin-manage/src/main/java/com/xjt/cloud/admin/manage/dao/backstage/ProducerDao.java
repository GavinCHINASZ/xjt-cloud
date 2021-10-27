package com.xjt.cloud.admin.manage.dao.backstage;

import com.xjt.cloud.admin.manage.entity.inventory.Producer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/29 16:49
 * @Description: 物联卡厂家管理DAO
 */
@Repository
public interface ProducerDao {
    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    List<Producer> findProducerList(Producer producer);

    /**
     *
     * 功能描述:查询厂商管理
     *
     * @param producer
     * @return: Producer
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    Producer findProducer(Producer producer);

    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    int findProducerListTotalCount(Producer producer);

    /**
     *
     * 功能描述:新增厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    int saveProducer(Producer producer);

    /**
     *
     * 功能描述:修改厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    int modifyProducer(Producer producer);
}
