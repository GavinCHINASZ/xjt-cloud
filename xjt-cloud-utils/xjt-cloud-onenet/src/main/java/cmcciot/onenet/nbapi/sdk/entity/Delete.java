package cmcciot.onenet.nbapi.sdk.entity;

import cmcciot.onenet.nbapi.sdk.config.Config;

/**
 *
 * Created by zhuocongbin
 * date 2018/3/15
 */
public class Delete extends CommonEntity{
    private String deviceId;

    /**
     * @param deviceId
     */
    public Delete(String deviceId) {
        this.deviceId = deviceId;

    }

    @Override
    public String toUrl() {
        StringBuilder url = new StringBuilder(Config.getDomainName());
        url.append("/devices/").append(this.deviceId);
        return url.toString();
    }
}
