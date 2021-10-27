package com.xjt.cloud.report.core.service.impl;

import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.report.core.dao.task.CheckRecordDao;
import com.xjt.cloud.report.core.entity.task.CheckRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CheckRecordServiceImpl
 * @Author dwt
 * @Date 2019-07-26 11:31
 * @Description 巡检记录
 * @Version 1.0
 */
@Service
public class CheckRecordServiceImpl extends AbstractService {
    @Autowired
    private CheckRecordDao checkRecordDao;

    /**
     * 功能描述: 根据 巡检记录实体 获取
     *
     * @param checkRecord 巡检记录实体
     * @auther: huanggc
     * @date: 2019/11/11
     * @return: List<CheckRecord>
     */
    public List<CheckRecord> findGroupBySql(CheckRecord checkRecord) {
        List<CheckRecord> checkRecordList = checkRecordDao.findGroupBySql(checkRecord);
        return checkRecordList;
    }

    /**
     * 功能描述: (报表)根据 巡检记录实体 获取 状态
     *
     * @param cr CheckRecord巡检记录实体
     * @auther: huanggc
     * @date: 2019/11/11
     * @return: String
     */
    public String checkStatusBySql(CheckRecord cr) {
        if (cr != null) {
            if (cr.getDataValue() != null) {
                if (!cr.getDataValue()) {
                    // 巡检方式为 默认
                    if (cr.getResultDescription() == null || !cr.getResultDescription().equals("正常")) {
                        return "故障";
                    } else {
                        return "正常";
                    }
                } else {
                    // 巡检方式为 数值
                    if (cr.getMax() != null && cr.getMin() != null) {
                        if (cr.getCheckValue() < cr.getMin() || cr.getCheckValue() > cr.getMax()) {
                            return "故障";
                        } else {
                            return "正常";
                        }
                    } else {
                        if (cr.getCheckValue() < cr.getMinValue() || cr.getCheckValue() > cr.getMaxValue()) {
                            return "故障";
                        } else {
                            return "正常";
                        }
                    }
                }
            }

            if (cr.getCheckType() != null) {
                if (cr.getCheckType() == 0 || cr.getCheckType() == 2) {
                    if (cr.getResultDescription() == null || !cr.getResultDescription().equals("正常")) {
                        return "故障";
                    } else {
                        return "正常";
                    }
                } else if (cr.getCheckType() == 1) {
                    if (cr.getCheckValue() != null) {
                        if (cr.getMax() != null && cr.getMin() != null) {
                            if (cr.getCheckValue() < cr.getMin() || cr.getCheckValue() > cr.getMax()) {
                                return "故障";
                            } else {
                                return "正常";
                            }
                        } else {
                            if (cr.getCheckValue() < cr.getMinValue() || cr.getCheckValue() > cr.getMaxValue()) {
                                return "故障";
                            } else {
                                return "正常";
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

}
