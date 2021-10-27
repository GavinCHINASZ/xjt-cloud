package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.dao.project.NationalFireProtectionSettingServiceDao;
import com.xjt.cloud.project.core.dao.project.PublicityMapDao;
import com.xjt.cloud.project.core.entity.NationalFireProtectionSetting;

import com.xjt.cloud.project.core.entity.PublicityMap;
import com.xjt.cloud.project.core.service.service.NationalFireProtectionSettingService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: zhangzaifa
 * @Date: 2019/9/6 17:10
 * @Description:全民消防设置
 */
@Service
public class NationalFireProtectionSettingServiceImpl extends AbstractService implements NationalFireProtectionSettingService {
    @Autowired
    private NationalFireProtectionSettingServiceDao nationalFireProtectionSettingServiceDao;
    @Autowired
    private PublicityMapDao publicityMapDao;

    /**
     * @MethodName: findByProjectSetting
     * @Description: 查询项目全民消防设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/2 15:42
     **/
    @Override
    public Data findByProjectSetting(String json) {
        NationalFireProtectionSetting entity = JSONObject.parseObject(json, NationalFireProtectionSetting.class);
        NationalFireProtectionSetting nationalFireProtectionSetting = nationalFireProtectionSettingServiceDao.findByProjectSetting(entity);
        if (nationalFireProtectionSetting == null) {
            nationalFireProtectionSetting = new NationalFireProtectionSetting(true, true, true, true, true, entity.getProjectId());
            nationalFireProtectionSettingServiceDao.saveProjectSetting(nationalFireProtectionSetting);
            return asseData(entity);
        }
        PublicityMap pm = new PublicityMap();
        pm.setSourceId(nationalFireProtectionSetting.getProjectId());
        pm.setSourceType(1);
        //查询项目宣传图
        List<String> projectImageUrlList = publicityMapDao.findByPublicityMapImageUrlList(pm);
        nationalFireProtectionSetting.setProjectPublicityMapList(projectImageUrlList);
        pm.setSourceType(2);
        //查询故障宣传图
        List<String> faultImageUrlList = publicityMapDao.findByPublicityMapImageUrlList(pm);
        nationalFireProtectionSetting.setFaultPublicityMapList(faultImageUrlList);
        return asseData(nationalFireProtectionSetting);
    }

    /**
     * @MethodName: saveProjectSetting
     * @Description: 保存项目全民消防设置
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/2 16:50
     **/
    @Override
    public Data saveProjectSetting(String json) {
        NationalFireProtectionSetting entity = JSONObject.parseObject(json, NationalFireProtectionSetting.class);
        NationalFireProtectionSetting oldEntity = nationalFireProtectionSettingServiceDao.findByProjectSetting(entity);
        nationalFireProtectionSettingServiceDao.deleteProjectSetting(oldEntity);
        nationalFireProtectionSettingServiceDao.saveProjectSetting(entity);
        List<PublicityMap> list = new ArrayList<>();
        list = publicityMap(list, entity.getProjectPublicityMapList(), entity.getProjectId(), 1);
        list = publicityMap(list, entity.getFaultPublicityMapList(), entity.getProjectId(), 2);
        publicityMapDao.deleteProjectPublicityMap(entity.getProjectId());
        if (list.size() != 0) {
            publicityMapDao.savePublicityMapList(list);
        }
        return Data.isSuccess();
    }

    /**
     * @MethodName: publicityMap
     * @Description: 宣传图实体初始化
     * @Param: [list, projectPublicityMapList, projectId, type]
     * @Return: java.util.List<com.xjt.cloud.project.core.entity.PublicityMap>
     * @Author: zhangZaiFa
     * @Date:2019/12/6 15:00
     **/
    private List<PublicityMap> publicityMap(List<PublicityMap> list, List<String> projectPublicityMapList, Long projectId, int type) {
        if (projectPublicityMapList == null) {
            return list;
        }
        for (String imageUrl : projectPublicityMapList) {
            PublicityMap pm = new PublicityMap();
            pm.setSourceType(type);
            pm.setSourceId(projectId);
            pm.setImageUrl(imageUrl);
            list.add(pm);
        }
        return list;
    }

    /**
     * @MethodName: saveFaultRecord
     * @Description: 全民消防生成故障记录
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/6 15:00
     **/
    @Override
    public Data saveFaultRecord(String json) {
        SysLog.info(json + "--------------------->参数信息");
        List<JSONObject> list = JSONArray.parseArray(json, JSONObject.class);

        for (JSONObject jsonObject : list) {
            String openId = jsonObject.getString("openId");
            String imageUrl = "";
            JSONArray imageUrls = jsonObject.getJSONArray("imageUrls");
            if (imageUrls != null) {
                for (Object url : imageUrls) {
                    imageUrl += url.toString() + ";";
                }
                jsonObject.put("imageUrl", imageUrl);
            }
            String faultDescription = getFaultDescription(jsonObject.getString("faultDescription"));
            jsonObject.put("faultDescription", faultDescription);
            JSONObject result = HttpUtils.httpGet(ConstantsProjectMsg.SAVE_FAULT_RECORD_URL + jsonObject.getInteger("projectId"), jsonObject.toJSONString(), "json");
            SysLog.debug(result.toJSONString());

            if (result.getInteger("status") == 200) { //插入数据成功
                weChatSendMessage(result.getJSONObject("obj"), openId);
            }
        }
        return Data.isSuccess();
    }


    public String getFaultDescription(String faultDescription) {
        if (faultDescription == null) {
            return "";
        }
        for (int i = 0; i <= 7; i++) {
            faultDescription = faultDescription.replaceAll(i + ".", "");
        }
        return faultDescription;
    }

    /**
     * @MethodName: weChatSendMessage
     * @Description: 微信公众号推送通知
     * @Param: [object]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/12/6 16:00
     **/
    private void weChatSendMessage(JSONObject object, String openId) {
        JSONObject jsonObject = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject event = new JSONObject();
        first.put("value", "您好，您申报的故障/隐患正在处理中，请耐心等候。");
        first.put("color", "#000000");
        jsonObject.put("first", first);
        event.put("value", object.getString("faultDescription"));
        event.put("color", "#000000");
        jsonObject.put("event", event);
        JSONObject finish_time = new JSONObject();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        finish_time.put("value", dateString);
        finish_time.put("color", "#000000");
        jsonObject.put("finish_time", finish_time);
        JSONObject remark = new JSONObject();
        remark.put("value", "关注身边的消防安全，从你我做起！");
        remark.put("color", "#000000");
        jsonObject.put("remark", remark);
        SysLog.info(openId + "-------->报修人微信OPENID");
        SysLog.info(ConstantsProjectMsg.WE_CHAT_TEMPLATE_ID + "-------->微信模板ID");
        SysLog.info(jsonObject.toJSONString() + "-------->信息参数");
        String result = WeChatUtils.weChatSendMessage(openId, ConstantsProjectMsg.WE_CHAT_TEMPLATE_ID, null, jsonObject);
        SysLog.info(result + "-------->微信返回结果");
    }

    /*public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        JSONObject first = new JSONObject();
        JSONObject event = new JSONObject();
        first.put("value", "您好，您申报的故障/隐患正在处理中，请耐心等候。");
        first.put("color", "#000000");
        jsonObject.put("first", first);
        event.put("value", "外观不整洁、生锈、掉漆等");
        event.put("color", "#000000");
        jsonObject.put("event", event);
        JSONObject finish_time = new JSONObject();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        finish_time.put("value", dateString);
        finish_time.put("color", "#000000");
        jsonObject.put("finish_time", finish_time);
        JSONObject remark = new JSONObject();
        remark.put("value", "\n关注身边的消防安全，从你我做起！");
        remark.put("color", "#000000");
        jsonObject.put("remark", remark);
        SysLog.info(jsonObject.toJSONString()+"-------->信息参数");
        String result = WeChatUtils.weChatSendMessage("onnphwTpK-CP4Scne2OfCpLZgDTI","sUPNLzEtF9r25fL9XIY5__luDGJa-N74xec6V01-lqQ",
                "http://test4.xiaojiantong.com:6020/#/malfunDetails?id=111",jsonObject);
        SysLog.info(result+"-------->微信返回结果");
    }*/

    /**
     * @MethodName: findWeChatUser
     * @Description: 获取微信用户信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/12/6 16:12
     **/
    @Override
    public Data findWeChatUser(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json, JSONObject.class);
        //获取微信用户信息
        JSONObject weChatUser = WeChatUtils.getCodeWeChatUser(jsonObject.getString("code"));
        return asseData(weChatUser);
    }
}
