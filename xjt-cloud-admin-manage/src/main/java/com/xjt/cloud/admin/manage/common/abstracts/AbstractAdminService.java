package com.xjt.cloud.admin.manage.common.abstracts;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.base.BaseEntity;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/11/28 11:45
 * @Description:
 */
public abstract class AbstractAdminService extends AbstractService {
    /**
     *组装返回的ScriptPage对象
     * user:wangzhiwen
      * param: list 页面显示信息列表
      * param: total 总查询行数
     * return:ScriptPage<V>
     * date:2017/7/14 9:58
     **/
    protected <K> ScriptPage<K> asseScriptPage(List<K> list, int total){
        ScriptPage<K> scriptPage = new ScriptPage<>();
        scriptPage.setRows(list);
        scriptPage.setTotal(total);
        return scriptPage;
    }

    /**
     * 以AjaxPage对象信息组装查询条件的对象
     * user:wangzhiwen
     * param: k 查询能数对象
      * param: ajaxPage 信息对象
     * return:<K extends AjaxPage> K
     * date:2017/7/17 9:36
     **/
    protected <K extends BaseEntity> K asseFindObj(K  k, AjaxPage ajaxPage){
        k.setPageIndex(ajaxPage.getPage());
        k.setPageSize(ajaxPage.getRows());
        return k;
    }
}
