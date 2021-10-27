package com.xjt.cloud.admin.manage.controller.inventory;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.inventory.Producer;
import com.xjt.cloud.admin.manage.service.service.inventory.ProducerService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2020/7/29 16:02
 * @Description: 物联卡厂家管理Controller
 */
@Controller
@RequestMapping("/inventory/producer/")
public class ProducerController extends AbstractController {

    @Autowired
    private ProducerService producerService;

    /**
     *
     * 功能描述:跳转到厂商管理页面
     *
     * @param
     * @return: org.springframework.web.servlet.ModelAndView
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:53
     */
    @RequestMapping("toProducerListPage")
    public ModelAndView toProducerListPage(){
        return new ModelAndView("inventory/producerList");
    }

    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @return: ScriptPage<Producer>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "findProducerAll")
    @ResponseBody
    public List<Producer> findProducerAll(){
        return producerService.findProducerAll();
    }

    /**
     *
     * 功能描述:查询厂商管理列表
     *
     * @param ajaxPage
     * @param producer
     * @return: ScriptPage<Producer>
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "findProducerList")
    @ResponseBody
    public ScriptPage<Producer> findProducerList(AjaxPage ajaxPage, Producer producer){
        return producerService.findProducerList(ajaxPage, producer);
    }

    /**
     *
     * 功能描述:新增厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "saveProducer")
    @ResponseBody
    public Data saveProducer(Producer producer){
        return producerService.saveProducer(producer);
    }

    /**
     *
     * 功能描述:修改厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "modifyProducer")
    @ResponseBody
    public Data modifyProducer(Producer producer){
        return producerService.modifyProducer(producer);
    }

    /**
     *
     * 功能描述:删除厂商管理列表
     *
     * @param producer
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/29 16:55
     */
    @RequestMapping(value = "delProducer")
    @ResponseBody
    public Data delProducer(Producer producer){
        return producerService.delProducer(producer);
    }
}
