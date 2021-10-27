package com.xjt.cloud.admin.manage.service.service.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.DictItem;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.utils.Data;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 10:29
 * @Description: 数据词典管理接口
 */
public interface DictService extends BaseService {

    /**
     *
     * 功能描述:以父类型code查询子类型列表
     *
     * @param ajaxPage
     * @param dict
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    ScriptPage<Dict> findDictList(AjaxPage ajaxPage, Dict dict);

    /**
     *
     * 功能描述: 新增父词典信息
     *
     * @param dict
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    Data saveDict(Dict dict);

    /**
     *
     * 功能描述: 修改父词典信息
     *
     * @param dict
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    Data modifyDict(Dict dict);

    ///////////////////////////////////////词典项管理/////////////////////////////////
    /**
     *
     * 功能描述:查询词典项列表
     *
     * @param ajaxPage
     * @param dict
     * @return: ScriptPage<Dict>
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    ScriptPage<Dict> findDictItemList(AjaxPage ajaxPage, Dict dict);

    /**
     *
     * 功能描述: 新增词典项信息
     *
     * @param dict
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    Data saveDictItem(Dict dict);

    /**
     *
     * 功能描述: 修改词典项信息
     *
     * @param dict
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/28 16:22
     */
    Data modifyDictItem(Dict dict);

    /**
     *
     * 功能描述:以父id从缓存中得到数据词典项信息列表
     *
     * @param dict
     * @param dict
     * @return: ScriptPage<Dict>
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    List<Dict> findCacheDictItemListByCode(Dict dict);

    /**
     * 查询权限树结构
     *
     * @param dict 字典荐信息
     * @return List<DictItem>
     * @author huanggc
     * @date 2021/01/15
     **/
    List<DictItem> findDictItemTreeMsgLevel(DictItem dict);
}
