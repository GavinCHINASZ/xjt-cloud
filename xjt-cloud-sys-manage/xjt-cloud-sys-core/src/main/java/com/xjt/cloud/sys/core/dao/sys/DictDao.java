package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.commons.dict.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 11:52
 * @Description:数据词典DAO
 */
@Repository
public interface DictDao {

    List<Dict> findDictAllList();
}
