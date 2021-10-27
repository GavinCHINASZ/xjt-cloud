package com.xjt.cloud.admin.manage.service.impl.sys;

import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.dao.project.ProjectMsgLevelDao;
import com.xjt.cloud.admin.manage.dao.sys.DictDao;
import com.xjt.cloud.admin.manage.dao.sys.DictItemDao;
import com.xjt.cloud.admin.manage.entity.project.ProjectMsgLevel;
import com.xjt.cloud.admin.manage.entity.sys.DictItem;
import com.xjt.cloud.admin.manage.service.service.sys.DictService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据词典管理接口实现类
 *
 * @author wangzhiwen
 * @date 2019/7/2 10:29
 */
@Service
public class DictServiceImpl extends AbstractAdminService implements DictService {
    @Autowired
    private DictDao dictDao;
    @Autowired
    private DictItemDao dictItemDao;
    @Autowired
    private ProjectMsgLevelDao projectMsgLevelDao;

    /**
     * 功能描述:以父类型code查询子类型列表
     *
     * @param ajaxPage AjaxPage
     * @param dict     Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/19 10:16
     */
    @Override
    public ScriptPage<Dict> findDictList(AjaxPage ajaxPage, Dict dict) {
        dict = asseFindObj(dict, ajaxPage);
        return asseScriptPage(dictDao.findDictList(dict), dictDao.findDictListTotalCount());
    }


    /**
     * 功能描述: 新增父词典信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @Override
    public Data saveDict(Dict dict) {
        int num = dictDao.saveDict(dict);
        dictCacheInit();
        return asseData(num);
    }

    /**
     * 功能描述: 修改父词典信息
     *
     * @param dict *
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @Override
    public Data modifyDict(Dict dict) {
        int num = dictDao.modifyDict(dict);
        dictCacheInit();
        return asseData(num);
    }

    ///////////////////////////////////////词典项管理/////////////////////////////////

    /**
     * 功能描述:查询词典项列表
     *
     * @param ajaxPage AjaxPage
     * @param dict     Dict
     * @return ScriptPage<Dict>
     * @author wangzhiwen
     * @date 2019/7/19 10:16
     */
    @Override
    public ScriptPage<Dict> findDictItemList(AjaxPage ajaxPage, Dict dict) {
        dict = asseFindObj(dict, ajaxPage);
        return asseScriptPage(dictDao.findDictItemList(dict), dictDao.findDictItemListTotalCount(dict));
    }

    /**
     * 功能描述: 新增词典项信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @Override
    public Data saveDictItem(Dict dict) {
        int num = dictDao.saveDictItem(dict);
        dictCacheInit();
        return asseData(num);
    }

    /**
     * 功能描述: 修改词典项信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @Override
    public Data modifyDictItem(Dict dict) {
        int num = dictDao.modifyDictItem(dict);
        dictCacheInit();
        return asseData(num);
    }

    /**
     * 功能描述:以父id从缓存中得到数据词典项信息列表
     *
     * @param dict Dict
     * @return ScriptPage<Dict>
     * @author wangzhiwen
     * @date 2019/7/19 10:16
     */
    @Override
    public List<Dict> findCacheDictItemListByCode(Dict dict) {
        return DictUtils.getDictListByDictCode(dict.getDictCode());
    }

    /**
     * 查询权限树结构
     *
     * @param dictItem 字典荐信息
     * @return List<DictItem>
     * @author huanggc
     * @date 2021/01/15
     **/
    @Override
    public List<DictItem> findDictItemTreeMsgLevel(DictItem dictItem) {
        // ALARM_LEVEL
        dictItem.setDictCode("ALARM_LEVEL");
        // 不分页
        dictItem.setPageSize(0);
        List<DictItem> daoDictItemLists = dictItemDao.findDictItemLists(dictItem);

        // 查询 方案
        ProjectMsgLevel projectMsgLevel = new ProjectMsgLevel();
        // 默认配置的项目ID是0L
        projectMsgLevel.setProjectId(0L);
        List<ProjectMsgLevel> projectMsgLevelList = projectMsgLevelDao.findProjectMsgLevelList(projectMsgLevel);

        // 组装数据
        List<DictItem> dictItemList = new ArrayList<>(projectMsgLevelList.size());
        for (ProjectMsgLevel msgLevel : projectMsgLevelList) {
            DictItem d = new DictItem();
            String modelName = msgLevel.getModelName();
            d.setItemName(modelName);

            List<DictItem> dictItems = new ArrayList<>(4);
            if (msgLevel.getEventTypeLevel1() != null){
                DictItem dictItem1 = new DictItem();
                dictItem1.setItemName("级别1");
                List<DictItem> dictItems1 = setDictItemList(daoDictItemLists, modelName, "级别1");
                dictItem1.setChildren(dictItems1);
                dictItems.add(dictItem1);
            }

            if (msgLevel.getEventTypeLevel2() != null){
                DictItem dictItem1 = new DictItem();
                dictItem1.setItemName("级别2");
                List<DictItem> dictItems1 = setDictItemList(daoDictItemLists, modelName, "级别2");
                dictItem1.setChildren(dictItems1);
                dictItems.add(dictItem1);
            }

            if (msgLevel.getEventTypeLevel3() != null){
                DictItem dictItem1 = new DictItem();
                dictItem1.setItemName("级别3");
                List<DictItem> dictItems1 = setDictItemList(daoDictItemLists, modelName, "级别3");
                dictItem1.setChildren(dictItems1);
                dictItems.add(dictItem1);
            }

            if (msgLevel.getEventTypeLevel4() != null){
                DictItem dictItem1 = new DictItem();
                dictItem1.setItemName("级别4");
                List<DictItem> dictItems1 = setDictItemList(daoDictItemLists, modelName, "级别4");
                dictItem1.setChildren(dictItems1);
                dictItems.add(dictItem1);
            }

            if (CollectionUtils.isNotEmpty(dictItems)) {
                d.setChildren(dictItems);
                dictItemList.add(d);
            }
        }

        return dictItemList;
    }

    /**
     * 功能描述: 数据词典缓存初使化
     *
     * @author wangzhiwen
     * @date 2019/12/12 11:12
     */
    private List<DictItem> setDictItemList(List<DictItem> dictItemList, String modelName, String eventTypeNameLevel) {
        List<DictItem> list = new ArrayList<>(dictItemList.size());
        for (DictItem dictItem : dictItemList) {
            DictItem entity = new DictItem();
            entity.setId(dictItem.getId());
            entity.setItemName(dictItem.getItemName());
            entity.setProjectId(dictItem.getProjectId());
            entity.setModelName(modelName);
            entity.setEventTypeNameLevel(eventTypeNameLevel);
            list.add(entity);
        }
        return list;
    }

    /**
     * 功能描述: 数据词典缓存初使化
     *
     * @author wangzhiwen
     * @date 2019/12/12 11:12
     */
    private void dictCacheInit() {
        try {
            HttpUtils.httpGet(ConstantsClient.DICT_CACHE_INIT_URL);
        } catch (Exception ex) {
            SysLog.error("数据词典缓存初使化失败!");
            SysLog.error(ex);
        }
    }
}
