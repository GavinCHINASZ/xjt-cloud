package com.xjt.cloud.iot.netty.controller.electrical;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.iot.core.service.service.electrical.ElectricalFireEventRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ElectricalFireEventRecordController
 * @Author Administrator
 * @Date 2020-09-24 15:52
 * @Description TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/electrical/record/")
public class ElectricalFireEventRecordController  extends AbstractController {

    @Autowired
    private ElectricalFireEventRecordService recordService;

    @RequestMapping(value = "receiveMobileIotInformation", method = RequestMethod.POST)
    public String receiveMobileIotInformation(HttpServletRequest request, String msg, String nonce, String signature){
        recordService.receiveMobileIotInformation(request,msg,nonce,signature);
        return "ok";
    }

    @RequestMapping(value = "receiveMobileIotInformation", method = RequestMethod.GET)
    public String check(String msg, String nonce, String signature){
        return recordService.check(msg, nonce, signature);
    }
}
