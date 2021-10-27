package common.utils;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.huawei.iotplatform.client.invokeapi.DeviceManagement;
import com.huawei.iotplatform.client.invokeapi.SubscriptionManagement;
import huawei.iotplatform.utils.AuthUtil;

/**
 *
 * @author
 */
public class CtCloudAccessUtil {
	
	static String THIRD_PARTY_PLT_PARAMETERS ="THIRD_PARTY_PLATFORM_PARAMETERS"; 
	static String CT_PLT_ACCESS_TOKEN_KEY ="CHINE_TELECOM_PALTFORM_ACCESS_TOKEN_KEY";
	
	static NorthApiClient northApiClient = null;
	static String chinaTelecomPltAccessToken = null;

    //static String callbackUrl = "https://10.63.163.181:8099/v1.0.0/messageReceiver";
    static String callbackUrl = "http://test4.xiaojiantong.com/xjt/report/intelligentSmokeEvent/ctReceive";
	
	String accessToken;
	
	public static void CtCloudAccessInit(){

        System.out.println("======CtCloudAccessInit begin======");

		/**---------------------initialize northApiClient------------------------*/
        northApiClient = AuthUtil.initApiClient();
        northApiClient.getVersion();
            
        /**----------------------get access token-------------------------------*/
        System.out.println("======get access token======");
        Authentication authentication = new Authentication(northApiClient);
            
        // get access token
        AuthOutDTO authOutDTO = null;
		try {
			authOutDTO = authentication.getAuthToken();
		} catch (NorthApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		System.out.println(authOutDTO.toString());

	    chinaTelecomPltAccessToken = authOutDTO.getAccessToken();        		

		//String accessToken = chinaTelecomPltAccessToken;
		
		/** --------------------start 定时刷新电信云平台 token   --------------------*/
		try {
			authentication.startRefreshTokenTimer();
			System.out.println("=====================startRefreshTokenTimer started =================");
		} catch (NorthApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		CtCloudAccessUtil.subDevice();
	}
	
	
	public static String addDevice(String sensorId) throws NorthApiException{
		
		DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
		
		RegDirectDeviceInDTO2 rddid = new RegDirectDeviceInDTO2();

		rddid.setNodeId(sensorId);
		rddid.setVerifyCode(sensorId);
		rddid.setTimeout(0);// 该值必须设置为0，否则电信平台会在超时后，自动删除设备
		String deviceId = null;

		//try {
		RegDirectDeviceOutDTO rddod = deviceManagement.regDirectDevice(rddid, null, null);
			System.out.println(rddod.toString());

			if (rddod != null) {
				System.out.println("\n=========NB CT add device success, output======:" + rddod.toString());
		        
	        	deviceId = rddod.getDeviceId();
		        	
	        	/**---------------------modify device info------------------------*/
	        	// use verifyCode as the device name
	        	System.out.println("\n======modify device info======");
	            modifyDeviceInfo(deviceManagement, deviceId, rddod.getVerifyCode());				

			}
		//} catch (NorthApiException e) {
//			System.out.println("CT NB add device error: " + e.toString());
	//		if ("100416".equals(e.getError_code())){
		//		e.get
			//}
				
			
			//deviceId = null;
		//}
		
		return deviceId;
	}		

	public static String delDevice(String deviceId) throws NorthApiException {
		
		DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
		
//		try {
			deviceManagement.deleteDirectDevice(deviceId, null, null, null);
			
	//	} catch (NorthApiException e) {
		//	System.out.println(e.toString());
		//}
		
		return deviceId;
	}		
	
	 private static void modifyDeviceInfo(DeviceManagement deviceManagement, String deviceId, String deviceName) {
	    	ModifyDeviceInforInDTO mdiInDTO = new ModifyDeviceInforInDTO();
	        mdiInDTO.setName(deviceName);
	        mdiInDTO.setDeviceType("Smoke");
	        mdiInDTO.setManufacturerId("SITERWELLELECTRONICS");
	        mdiInDTO.setManufacturerName("SITERWELLELECTRONICS");
	        mdiInDTO.setModel("GS524N");
	        mdiInDTO.setProtocolType("CoAP");
	        try {
				deviceManagement.modifyDeviceInfo(mdiInDTO, deviceId, null, null);
				System.out.println("modify device info succeeded");
			} catch (NorthApiException e) {
				e.printStackTrace();
				System.out.println("modify device info error, " + e.toString());
			}
	 }
	
	public static void subDevice(){
	
		SubscriptionManagement subscriptionManagement = new SubscriptionManagement(northApiClient);

    	/**---------------------sub deviceAdded notification------------------------*/
        //note: 10.X.X.X is a LAN IP, not a public IP, so subscription callbackUrl's IP cannot be 10.X.X.X
        System.out.println("======subscribe to device data notification begin======");
        
        SubscriptionDTO subDTO;
        
//        subDTO = subDeviceData(subscriptionManagement, "deviceAdded");
        subDTO = subDeviceData(subscriptionManagement, "deviceInfoChanged");
        subDTO = subDeviceData(subscriptionManagement, "deviceDataChanged");
        subDTO = subDeviceData(subscriptionManagement, "deviceEvent");
        subDTO = subDeviceData(subscriptionManagement, "serviceInfoChanged");
        
        /**---------------------sub device upgrade result notification------------------------*/
        System.out.println("\n====subscribe to device data notification end======");
    	
	}
	
    private static SubscriptionDTO subDeviceData(SubscriptionManagement subscriptionManagement, String notifyType) {
    	SubDeviceDataInDTO sddInDTO = new SubDeviceDataInDTO();
    	sddInDTO.setNotifyType(notifyType);
    	sddInDTO.setCallbackUrl(callbackUrl);
    	System.out.println("===============begin sub=================");
    	
    	try {
    		SubscriptionDTO subDTO = subscriptionManagement.subDeviceData(sddInDTO, null, null);
    		System.out.println(notifyType + "return = " + subDTO.toString());
			return subDTO;
		} catch (Exception e) { //NorthApi
			System.out.println(notifyType + "Exception:----" + e.toString());
		}
    	
    	System.out.println("===============end sub=================");
    	return null;
    }	
	
	
}
