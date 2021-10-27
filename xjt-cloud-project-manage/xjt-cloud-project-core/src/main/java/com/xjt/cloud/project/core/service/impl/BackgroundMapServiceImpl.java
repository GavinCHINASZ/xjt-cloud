package com.xjt.cloud.project.core.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.HttpUtils;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.project.core.common.ConstantsProjectMsg;
import com.xjt.cloud.project.core.dao.project.BackgroundMapDao;
import com.xjt.cloud.project.core.entity.BackgroundMap;
import com.xjt.cloud.project.core.service.service.BackgroundMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BackgroundMapServiceImpl
 * @Author dwt
 * @Date 2020-09-02 15:50
 * @Version 1.0
 */
@Service
public class BackgroundMapServiceImpl extends AbstractService implements BackgroundMapService {

    @Autowired
    private BackgroundMapDao backgroundMapDao;

    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:44
     *@Param: json
     *@Return: Data
     *@Description 保存背景图
     */
    @Override
    public Data saveBackgroundMap(String json) {
        BackgroundMap backgroundMap = JSONObject.parseObject(json,BackgroundMap.class);
        int count;
        if(backgroundMap.getId() != null && backgroundMap.getId() != 0){
            count = backgroundMapDao.modifyBackgroundMap(backgroundMap);
        }else {
            count = backgroundMapDao.saveBackgroundMap(backgroundMap);
        }
        if(count > 0){
            return Data.isSuccess();
        }
        return Data.isFail();
    }

    /**
     *@Author: dwt
     *@Date: 2020-09-02 15:46
     *@Param: josn
     *@Return: Data
     *@Description 查询背景图
     */
    @Override
    public Data findBackgroundMap(String json) {
        BackgroundMap backgroundMap = JSONObject.parseObject(json,BackgroundMap.class);
        backgroundMap = backgroundMapDao.findBackgroundMap(backgroundMap);
        if(backgroundMap == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeCode",ConstantsProjectMsg.BACKGROUND);
            String token = SecurityUserHolder.getToken();
            jsonObject.put("access_token",token);
            jsonObject = HttpUtils.httpPostParaJson(ConstantsProjectMsg.FIND_CLOUD_BACKGROUND_MAP_PATH,jsonObject,"json");//HttpUtils.httpPost(ConstantsProjectMsg.FIND_CLOUD_BACKGROUND_MAP_PATH,jsonObject.toString());
            if(jsonObject != null){
                JSONArray jsonArray = jsonObject.getJSONArray("listObj");
                if(jsonArray != null && jsonArray.size() > 0){
                    backgroundMap = new BackgroundMap();
                    String content;
                    for(int i=0;i<jsonArray.size();i++) {
                        content = jsonArray.getJSONObject(i).get("content").toString();
                        if(StringUtils.isNotEmpty(content)){
                            content = content.substring(content.indexOf("http"),content.indexOf("alt") - 2);
                            switch (i){
                                case 0:
                                    backgroundMap.setImg1(content);
                                    break;
                                case 1:
                                    backgroundMap.setImg2(content);
                                    break;
                                case 2:
                                    backgroundMap.setImg3(content);
                                    break;
                                case 3:
                                    backgroundMap.setImg4(content);
                                    break;
                                case 4:
                                    backgroundMap.setImg5(content);
                                    break;
                                case 5:
                                    backgroundMap.setImg6(content);
                                    break;
                            }
                        }

                    }
                }

            }

        }
        return asseData(backgroundMap);
    }

}
