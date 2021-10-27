package com.xjt.cloud.report.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.message.manage.service.service.MessageService;
import com.xjt.cloud.report.core.common.utils.ConstantsReport;
import com.xjt.cloud.report.core.dao.report.DutyNoticeDao;
import com.xjt.cloud.report.core.entity.report.DutyNotice;
import com.xjt.cloud.report.core.service.service.DutyNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: zhangzf
 * @Date: 2020/05/19
 * @Description: 值班记录
 */
@Service
public class DutyNoticeServiceImpl extends AbstractService implements DutyNoticeService {
    @Autowired
    private DutyNoticeDao dutyNoticeDao;
    @Autowired
    private MessageService messageService;

    /**@MethodName: saveDutyNotice
     * @Description: 保存值班记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/19 16:48
     **/
    @Override
    public Data saveDutyNotice(String json) {
        DutyNotice dutyNotice = JSONObject.parseObject(json, DutyNotice.class);
        dutyNoticeDao.delDutyNotice(dutyNotice);
        String userName = SecurityUserHolder.getUserName();
        //Long userId = getLoginUserId(userName);
        dutyNotice.setCreateUserId(getLoginUserId(userName));
        dutyNoticeDao.saveDutyNotice(dutyNotice);
        return Data.isSuccess();
    }

    /**@MethodName: findDutyNotice
     * @Description: 查询值班记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/19 16:49
     **/
    @Override
    public Data findDutyNotice(String json) {
        DutyNotice dutyNotice = JSONObject.parseObject(json, DutyNotice.class);
        List<DutyNotice> list = dutyNoticeDao.findDutyNotice(dutyNotice);

        JSONObject data = new JSONObject(1);
        data.put("list", list);
        return asseData(data);
    }

    /**@MethodName: sendDutyNoticeMsg
     * @Description: 发送值班提醒消息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/5/20 15:16
     **/
    @Override
    public Data sendDutyNoticeMsg() {
        //【智慧消防】您好，智慧消防提示您到 粤智新消防APP 巡视，并将巡检结果上传到“广东消防网”。
        String time = DateUtils.getTimeNow(new Date());
        DutyNotice dutyNotice = new DutyNotice();
        dutyNotice.setTime(time);
        List<DutyNotice> list = dutyNoticeDao.findDutyNoticeProjects(dutyNotice);
        String content = "您好，智慧消防提示您到 粤智新消防APP 巡视，并将巡检结果上传到“广东消防网”。";

        JSONObject json = new JSONObject(4);
        json.put("date", "2020-05-20");
        json.put("stationId", 27);
        json.put("lineId", 11);
        json.put("type", 0);
        for (DutyNotice dn: list) {
            JSONObject result = HttpUtils.httpPost(ConstantsReport.DI_TIE_USER_URL, json.toJSONString());
            if(result!= null && result.getInteger("retcode")  == 1){
                JSONArray data = result.getJSONArray("data");
                List<Long> userIds = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    Long userId = getLoginUserId(data.getJSONObject(i).getString("jobNumber"));
                    if(userId != null){
                        userIds.add(userId);
                    }
                }
                if(userIds.size() != 0){
                    messageService.saveMessageUser(14, userIds, "值班通知", 0, 0, "【"+dn.getProjectName()+"】"+content, 
                            null, dn.getProjectId(), null, null, null);
                }
            }
        }
        return Data.isSuccess();
    }

}
