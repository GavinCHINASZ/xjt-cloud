package com.xjt.cloud.admin.manage.controller.info;

import com.xjt.cloud.admin.manage.entity.info.InfoContent;
import com.xjt.cloud.admin.manage.entity.info.RecruitInfo;
import com.xjt.cloud.admin.manage.service.service.info.InfoApiService;
import com.xjt.cloud.admin.manage.service.service.info.RecruitInfoService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Auther: wangzhiwen
 * @Date: 2020/8/3 14:41
 * @Description: 资讯信息初使化信息请求接口Controller
 */
@RestController
@RequestMapping("/into/api/")
public class InfoApiController extends AbstractController{
    @Autowired
    private InfoApiService infoApiService;
    @Autowired
    private RecruitInfoService recruitInfoService;

    /**
     *
     * 功能描述:
     *
     * @param infoContent
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 10:38
     */
    @RequestMapping(value = "findInfoList")
    public Data findInfoList(InfoContent infoContent){
        return infoApiService.findInfoList(infoContent);
    }


    /**
     *
     * 功能描述:
     *
     * @param recruitInfo
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/8/6 10:38
     */
    @RequestMapping(value = "saveRecruitInfo")
    public Data saveRecruitInfo(RecruitInfo recruitInfo){
        return recruitInfoService.saveRecruitInfo(recruitInfo);
    }
}
