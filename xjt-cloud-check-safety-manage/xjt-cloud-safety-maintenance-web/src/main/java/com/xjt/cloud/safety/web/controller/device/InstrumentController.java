package com.xjt.cloud.safety.web.controller.device;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.safety.core.service.service.device.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName InstrumentController
 * @Description 仪器管理
 * @Author wangzhiwen
 * @Date 2021/5/7 16:14
 **/
@RestController
@RequestMapping("/instrument/")
public class InstrumentController  extends AbstractController {
    @Autowired
    private InstrumentService instrumentService;

    /**
     * @Description 新增仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveInstrument")
    public Data saveInstrument(String json){
        return instrumentService.saveInstrument(json);
    }

    /**
     * @Description 修改仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "modifyInstrument")
    public Data modifyInstrument(String json){
        return instrumentService.modifyInstrument(json);
    }

    /**
     * @Description 查询仪器信息列表
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findInstrumentList")
    public Data findInstrumentList(String json){
        return instrumentService.findInstrumentList(json);
    }

    /////////////////////////////////////评估项目仪器管理 //////////////////////////////////////

    /**
     * @Description 新增评估项目仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveCheckProjectInstrument")
    public Data saveCheckProjectInstrument(String json){
        return instrumentService.saveCheckProjectInstrument(json);
    }

    /**
     * @Description 删除评估项目仪器信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/5/7 16:21
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "delCheckProjectInstrument")
    public Data delCheckProjectInstrument(String json){
        return instrumentService.delCheckProjectInstrument(json);
    }
}
