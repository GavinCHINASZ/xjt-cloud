package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.sys.DictItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 词典项管理
 *
 * @author huanggc
 * @date 2021/01/20
 */
@Repository
public interface DictItemDao {

    /**
     * 功能描述:查询词典项列表
     *
     * @param dictItem DictItem
     * @return ScriptPage<Dict>
     * @author huanggc
     * @date 2021/01/15
     */
    List<DictItem> findDictItemLists(DictItem dictItem);

}
