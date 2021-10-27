package cmcciot.onenet.nbapi.sdk.config;

import cmcciot.onenet.nbapi.sdk.exception.NBStatus;
import cmcciot.onenet.nbapi.sdk.exception.OnenetNBException;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhuocongbin
 * date 2018/3/15
 * Loading global properties
 */
public class Config {
    public static String domainName;
    static {
        Properties properties = new Properties();
        try {
            properties = PropertiesLoaderUtils.loadProperties(new PathResource(System.getProperty("user.dir") + "/config/application.properties"));
            domainName = properties.getProperty("domainName");
        } catch (IOException e) {
            throw new OnenetNBException(NBStatus.LOAD_CONFIG_ERROR);
        }
    }
    public static String getDomainName() {
        return domainName;
    }
}
