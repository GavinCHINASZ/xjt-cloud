package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.DictItem;
import com.xjt.cloud.admin.manage.service.service.sys.DictService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 数据词典管理Controller
 *
 * @author wangzhiwen
 * @date 2019/7/2 10:28
 */
@Controller
@RequestMapping("/dict/")
public class DictController extends AbstractController {
    @Autowired
    private DictService dictService;

    /**
     * 功能描述:打开数据词典页面
     *
     * @return ModelAndView
     * @author wangzhiwen
     * @date 2019/12/3 11:21
     */
    @RequestMapping("toDictListPage")
    public ModelAndView toDictListPage() {
        return new ModelAndView("sys/dictList");
    }

    /**
     * 功能描述:查询父词典列表
     *
     * @param ajaxPage AjaxPage
     * @param dict     Dict
     * @return ScriptPage<Dict>
     * @author wangzhiwen
     * @date 2019/7/19 10:16
     */
    @RequestMapping(value = "findDictList")
    @ResponseBody
    public ScriptPage<Dict> findDictList(AjaxPage ajaxPage, Dict dict) {
        return dictService.findDictList(ajaxPage, dict);
    }

    /**
     * 功能描述: 新增父词典信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @RequestMapping(value = "saveDict")
    @ResponseBody
    public Data saveDict(Dict dict) {
        return dictService.saveDict(dict);
    }

    /**
     * 功能描述: 修改父词典信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @RequestMapping(value = "modifyDict")
    @ResponseBody
    public Data modifyDict(Dict dict) {
        return dictService.modifyDict(dict);
    }

    /**
     * 功能描述: 删除父词典信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @RequestMapping(value = "delDict")
    @ResponseBody
    public Data delDict(Dict dict) {
        return dictService.modifyDict(dict);
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
    @RequestMapping(value = "findDictItemList")
    @ResponseBody
    public ScriptPage<Dict> findDictItemList(AjaxPage ajaxPage, Dict dict) {
        return dictService.findDictItemList(ajaxPage, dict);
    }

    /**
     * 功能描述: 新增词典项信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @RequestMapping(value = "saveDictItem")
    @ResponseBody
    public Data saveDictItem(Dict dict) {
        return dictService.saveDictItem(dict);
    }

    /**
     * 功能描述: 修改词典项信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @RequestMapping(value = "modifyDictItem")
    @ResponseBody
    public Data modifyDictItem(Dict dict) {
        return dictService.modifyDictItem(dict);
    }

    /**
     * 功能描述: 删除词典项信息
     *
     * @param dict Dict
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/11/28 16:22
     */
    @RequestMapping(value = "delDictItem")
    @ResponseBody
    public Data delDictItem(Dict dict) {
        return dictService.modifyDictItem(dict);
    }

    /**
     * 功能描述:以父id从缓存中得到数据词典项信息列表
     *
     * @param dict Dict
     * @return ScriptPage<Dict>
     * @author wangzhiwen
     * @date 2019/7/19 10:16
     */
    @RequestMapping(value = "findCacheDictItemListByCode")
    @ResponseBody
    public List<Dict> findCacheDictItemListByCode(Dict dict) {
        return dictService.findCacheDictItemListByCode(dict);
    }

    /**
     * 项目管理--报警等级--查询字典项信息树结构
     *
     * @param dictItem 字典项
     * @return List<DictItem>
     * @author huanggc
     * @date 2021/01/15
     **/
    @RequestMapping("findDictItemTreeMsgLevel")
    @ResponseBody
    public List<DictItem> findDictItemTreeMsgLevel(DictItem dictItem) {
        return dictService.findDictItemTreeMsgLevel(dictItem);
    }
}
