package com.xjt.cloud.other.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.other.core.service.service.CccfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CccfController cccf查询
 * @Author zhangZaiFa
 * @Date 2020-01-13 15:15
 * @Description
 */
@RestController
@RequestMapping("/other/cccf")
public class CccfController extends AbstractController {
    @Autowired
    private CccfService cccfService;

    /**@MethodName: findLabelInfoByCode
     * @Description: CCCF按条形码查询产品信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/1/13 14:02 
     **/
    @RequestMapping(value = "/findLabelInfoByCode")
    public Data findLabelInfoByCode(String json) {
        return cccfService.findLabelInfoByCode(json);
    }
}
