package com.xjt.cloud.iot.api.linkage;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.iot.core.service.service.linkage.LinkageControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 联动控制 Controller
 * @Author huanggc
 * @Date 2019/09/19
 **/
@RestController
@RequestMapping("/linkageControl")
public class LinkageControlController extends AbstractController {
    @Autowired
    private LinkageControlService linkageControlService;

    /**
     * 功能描述:查询 联动控制 列表
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/19
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "findLinkageControlList/{projectId}")
    public Data findLinkageControlList(String json) {
        return linkageControlService.findLinkageControlList(json);
    }

    /**
     * 功能描述: 删除
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "deleteLinkageControl/{projectId}")
    public Data deleteLinkageControl(String json) {
        return linkageControlService.deleteLinkageControl(json);
    }

    /**
     * 功能描述: 新增
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/26
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "saveLinkageControl/{projectId}")
    public Data saveLinkageControl(String json) {
        return linkageControlService.saveLinkageControl(json);
    }

    /**
     * 功能描述: 启用 或 停止(开或关)
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/09/30
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "openOrClose/{projectId}")
    public Data openOrClose(String json) {
        return linkageControlService.openOrClose(json);
    }

    /**
     * 功能描述: 进入"水系统联运控制"新增页面
     *
     * @param json
     * @auther: huanggc
     * @date: 2019/10/22
     * @return: com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "addPage/{projectId}")
    public Data addPage(String json) {
        return linkageControlService.addPage(json);
    }
}
