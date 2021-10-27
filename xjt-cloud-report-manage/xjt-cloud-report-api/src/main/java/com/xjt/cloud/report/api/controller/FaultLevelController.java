package com.xjt.cloud.report.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.report.core.service.service.FaultLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: huanggc
 * @Date: 2019/11/04
 * @Description: 报表
 */
@RestController
@RequestMapping("/report/faultLevel")
public class FaultLevelController extends AbstractController {

    @Autowired
    private FaultLevelService faultLevelService;

    /**@MethodName: save 保存故障等级设置
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/12 14:41
     **/
    @RequestMapping(value = "save/{projectId}")
    public Data save(String json){
        return faultLevelService.save(json);
    }

    /**@MethodName: findByFaultLevel 查询值班设置信息
     * @Description: 
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:40
     **/
    @RequestMapping(value = "findByFaultLevel/{projectId}")
    public Data findByFaultLevel(String json){
        return faultLevelService.findByFaultLevel(json);
    }
    

}
