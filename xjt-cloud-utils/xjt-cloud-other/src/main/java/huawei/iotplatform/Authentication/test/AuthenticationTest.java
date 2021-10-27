package huawei.iotplatform.Authentication.test;

import com.huawei.iotplatform.client.NorthApiClient;

import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshInDTO;
import com.huawei.iotplatform.client.dto.AuthRefreshOutDTO;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.utils.PropertyUtil;
import com.xjt.cloud.commons.utils.SysLog;
import common.utils.CtCloudAccessUtil;
import huawei.iotplatform.utils.AuthUtil;

/**
 * 身份验证 test
 *
 * @author
 */
public class AuthenticationTest {
    public static void main(String args[]) throws Exception {
		CtCloudAccessUtil.CtCloudAccessInit();
		
    	/**---------------------initialize northApiClient------------------------*/
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        northApiClient.getVersion();
        
        /**----------------------get access token-------------------------------*/
        SysLog.debug("======get access token======");
        Authentication authentication = new Authentication(northApiClient);

        // get access token
        AuthOutDTO authOutDTO = authentication.getAuthToken();
        SysLog.debug(authOutDTO.toString());
        
        /**----------------------refresh token--------------------------------*/
        SysLog.debug("\n======refresh token======");
        AuthRefreshInDTO authRefreshInDTO = new AuthRefreshInDTO();
        
        authRefreshInDTO.setAppId(PropertyUtil.getProperty("appId"));
        authRefreshInDTO.setSecret(northApiClient.getClientInfo().getSecret());
        
        //get refreshToken from the output parameter (i.e. authOutDTO) of Authentication
        String refreshToken = authOutDTO.getRefreshToken();
        authRefreshInDTO.setRefreshToken(refreshToken);
        
        AuthRefreshOutDTO authRefreshOutDTO = authentication.refreshAuthToken(authRefreshInDTO);
        
        SysLog.debug("authRefreshOutDTO = " + authRefreshOutDTO.toString());
    }
}
