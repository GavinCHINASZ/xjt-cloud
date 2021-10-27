package com.xjt.cloud.task.core.service.impl.protect;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.task.core.dao.protect.ProtectGradeDao;
import com.xjt.cloud.task.core.entity.protect.ProtectGrade;
import com.xjt.cloud.task.core.service.service.protect.ProtectGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地铁 防护等级 ServiceImpl
 *
 * @author huangGuiChuan
 * @date 2020/09/27
 */
@Service
public class ProtectGradeServiceImpl extends AbstractService implements ProtectGradeService {
    @Autowired
    protected ProtectGradeDao protectGradeDao;

    /**
     * 查询 班前防护 名称list
     *
     * @param json 参数
     * @author huangGuiChuan
     * @date 2020/09/28
     * @return com.xjt.cloud.commons.utils.Data
     */
    @Override
    public Data findProtectGradeNameList(String json) {
        ProtectGrade protectGrade = JSONObject.parseObject(json, ProtectGrade.class);

        Integer totalCount = protectGrade.getTotalCount();
        Integer pageSize = protectGrade.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            totalCount = protectGradeDao.findProtectGradeListTotalCount(protectGrade);
        }

        List<ProtectGrade> protectGradeList = protectGradeDao.findProtectGradeNameList(protectGrade);
        return asseData(totalCount, protectGradeList);
    }
}
