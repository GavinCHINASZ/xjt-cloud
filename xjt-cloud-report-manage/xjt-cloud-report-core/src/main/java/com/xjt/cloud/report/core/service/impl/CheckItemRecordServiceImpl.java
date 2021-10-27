package com.xjt.cloud.report.core.service.impl;

import com.taobao.api.internal.spi.CheckResult;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CollectionUtils;
import com.xjt.cloud.report.core.dao.task.CheckItemRecordDao;
import com.xjt.cloud.report.core.entity.task.CheckItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemRecordServiceImpl
 * @Author dwt
 * @Date 2019-07-26 13:52
 * @Description 巡检项记录 Service
 * @Version 1.0
 */
@Service
public class CheckItemRecordServiceImpl extends AbstractService {
    @Autowired
    private CheckItemRecordDao checkItemRecordDao;

}
