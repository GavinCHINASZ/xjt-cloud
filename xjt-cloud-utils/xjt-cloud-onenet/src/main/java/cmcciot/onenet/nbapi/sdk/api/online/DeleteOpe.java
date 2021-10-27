package cmcciot.onenet.nbapi.sdk.api.online;

import cmcciot.onenet.nbapi.sdk.entity.CommonEntity;
import cmcciot.onenet.nbapi.sdk.utils.HttpSendCenter;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Callback;

/**
 * Created by zhuocongbin
 * date 2018/3/15
 * apiKey: the product of api-key which can be found on OneNET
 */
public class DeleteOpe extends BasicOpe{

    public DeleteOpe(String apiKey) {
        super(apiKey);
    }
    @Override
    public JSONObject operation(CommonEntity commonEntity, JSONObject body) {
        return HttpSendCenter.delete(apiKey, commonEntity.toUrl());
    }
    @Override
    public void operation(CommonEntity commonEntity, JSONObject body, Callback callback) {
        HttpSendCenter.postAsync(apiKey, commonEntity.toUrl(), body, callback);
    }
}
