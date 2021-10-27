package com.xjt.cloud.admin.manage.dao.sys;

import com.xjt.cloud.admin.manage.entity.sys.DictItem;
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

    /**
     * 功能描述: 查询父词典列表
     *
     * @param dict
     * @return: List<Dict>
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    List<Dict> findDictList(Dict dict);

    /**
     * 功能描述: 查询父词典列表总数
     *
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    int findDictListTotalCount();


    /**
     * 功能描述: 新增父词典信息
     *
     * @param dict
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    int saveDict(Dict dict);

    /**
     * 功能描述: 修改父词典信息
     *
     * @param dict
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    int modifyDict(Dict dict);

    ///////////////////////////////////////词典项管理/////////////////////////////////

    /**
     * 功能描述:查询词典项列表
     *
     * @param dict
     * @return: ScriptPage<Dict>
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    List<Dict> findDictItemList(Dict dict);

    /**
     * 功能描述:查询词典项列表
     *
     * @param dictItem DictItem
     * @return ScriptPage<Dict>
     * @author huanggc
     * @date 2021/01/15
     */
    List<DictItem> findDictItemLists(DictItem dictItem);

    /**
     * 功能描述: 查询词典项列表总数
     *
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 14:41
     */
    int findDictItemListTotalCount(Dict dict);

    /**
     * 功能描述: 新增词典项信息
     *
     * @param dict
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    int saveDictItem(Dict dict);

    /**
     * 功能描述: 修改词典项信息
     *
     * @param dict
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    int modifyDictItem(Dict dict);
}
