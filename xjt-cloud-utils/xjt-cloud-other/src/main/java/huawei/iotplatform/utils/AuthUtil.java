package huawei.iotplatform.utils;

import java.util.Properties;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.utils.PropertyUtil;
import com.xjt.cloud.commons.utils.SysLog;
import common.utils.ReadPropertiesUtils;

/**
 *
 * @author
 */
public class AuthUtil {
	
	private static NorthApiClient northApiClient = null;
	
	public static NorthApiClient initApiClient() {
		if (northApiClient != null) {
			return northApiClient;
		}
		northApiClient = new NorthApiClient();

        //PropertyUtil.init("xjt/application.properties");
		ClientInfo clientInfo = new ClientInfo();
		try{
		
		Properties properties = new Properties();
		properties.load(ReadPropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties"));
		//ReadPropertiesUtils.getProperties().getProperty("platformIp");
        clientInfo.setPlatformIp(properties.getProperty("platformIp"));
        clientInfo.setPlatformPort(properties.getProperty("platformPort"));
        clientInfo.setAppId(properties.getProperty("appId"));
        clientInfo.setSecret(properties.getProperty("secret"));
//        clientInfo.setSecret(getAesPropertyValue("secret"));
		}catch(Exception ex){
			
		}
        try {
			northApiClient.setClientInfo(clientInfo);
			northApiClient.initSSLConfig();
		} catch (NorthApiException e) {
			SysLog.debug(e.toString());
		}

		return northApiClient;
    }
	
	public static String getAesPropertyValue(String propertyName) {
		String aesPwd = "123987"; //this is a test AES password
        
//      String originalProperty = "2fvT3PUazXev3pv_8jW4QH6Oq2Ya";
//      byte[] temp = AesUtil.encrypt(originalProperty, aesPwd);
//      String hexStrResult = HexParser.parseByte2HexStr(temp);
//      System.out.println("==>encrypted secret hex sting is ："  + hexStrResult);
      
		PropertyUtil.init("./src/main/resources/application.properties");
		byte[] secret = HexParser.parseHexStr2Byte(PropertyUtil.getProperty(propertyName));
		return new String(AesUtil.decrypt(secret, aesPwd));
	}
}
