package huawei.utils;


public class Constant {

    // 请向管理员获取RTC业务平台语音通话能力API的调用地址，并替换BASE_URL的取值。
    public static final String BASE_URL = "https://117.78.29.66:10443";

    // 请向管理员获取app_key替换APPID的取值，app_secret替换SECRET的取值。
    public static final String APPID = "Y994RuxtuE8cWO7Q06S3D69l26rz";
    public static final String SECRET = "0Qd3p36G2o9V1G8p1SQ3YFmRH432";

    //*************************** 下列常量无需修改  *********************************//

    /*
     * 请求头域
     */
    public static final String HEADER_APP_KEY = "app_key";
    public static final String HEADER_APP_AUTH = "Authorization";
    
    /*
     * 鉴权接口请求URL
     */
    public static final String APP_AUTH = BASE_URL + "/rest/fastlogin/v1.0";
    public static final String REFRESH_TOKEN = BASE_URL + "/omp/oauth/refresh";
    public static final String DELETE_AUTH = BASE_URL + "/rest/logout/v1.0";
    public static final String REFRESH_OCEANSTOR = BASE_URL + "/rest/refreshkey/v2.0";
    

    /*
     * 业务能力接口请求URL
     */
    public static final String VOICE_CALLBACK_TEST = BASE_URL + "/sandbox/rest/httpsessions/click2Call/v2.0";
    public static final String VOICE_CALLBACK_COMERCIAL = BASE_URL + "/rest/httpsessions/click2Call/v2.0";
    public static final String VOICE_VERIFICATION_TEST = BASE_URL + "/sandbox/rest/httpsessions/callVerify/v1.0";
    public static final String VOICE_VERIFICATION_COMERCIAL = BASE_URL + "/rest/httpsessions/callVerify/v1.0";    
    public static final String CALL_NOTIFY_TEST = BASE_URL + "/sandbox/rest/httpsessions/callnotify/";
    public static final String CALL_NOTIFY_COMERCIAL = BASE_URL + "/rest/httpsessions/callnotify/";
}