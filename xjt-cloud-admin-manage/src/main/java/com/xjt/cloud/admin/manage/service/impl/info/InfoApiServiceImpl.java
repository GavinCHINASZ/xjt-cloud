package com.xjt.cloud.admin.manage.service.impl.info;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.dao.info.InfoManageDao;
import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import com.xjt.cloud.admin.manage.service.service.info.InfoApiService;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:42
 * @Description: 资讯信息初使化信息请求接口实现类
 */
@Service
public class InfoApiServiceImpl extends AbstractAdminService implements InfoApiService {

    @Autowired
    private InfoManageDao infoManageDao;

    /**
     *
     * 功能描述:
     *
     * @param infoContent
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 10:38
     */
    @Override
    public Data findInfoList(InfoContent infoContent){
        Integer totalCount = infoContent.getTotalCount();
        Integer pageSize = infoContent.getPageSize();
        String [] order = {"sort"};
        infoContent.setOrderCols(order);
        infoContent.setOrderDesc(true);
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = infoManageDao.findInfoContentListTotalCount(infoContent);
        }
        List<InfoContent> list = infoManageDao.findInfoContentList(infoContent);
        return asseData(totalCount,list);
    }
}
