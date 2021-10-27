package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.service.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 10:28
 * @Description: 数据词典管理Controller
 */
@RestController
@RequestMapping("/dict/")
public class DictController extends AbstractController {

    @Autowired
    private DictService dictService;

    /**
     *
     * 功能描述: 数据词典缓存初使化
     *
     * @param
     * @return: javax.xml.crypto.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:41
     */
    @RequestMapping(value = "dictCacheInit")
    @ResponseBody
    public Data dictCacheInit(){
        return dictService.dictCacheInit();
    }

    /**
     *
     * 功能描述:以父类型code查询子类型列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @RequestMapping(value = "findDictItemListByParentCode")
    public Data findDictItemListByParentCode(String json){
        return dictService.findDictItemListByParentCode(json);
    }

    /**
     *
     * 功能描述:以父类型code与子类型code查询子类型
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 10:16
     */
    @RequestMapping(value = "findDictItemByParentCodeItemCode")
    public Data findDictItemByParentCodeItemCode(String json){
        return dictService.findDictItemByParentCodeItemCode(json);
    }
}
