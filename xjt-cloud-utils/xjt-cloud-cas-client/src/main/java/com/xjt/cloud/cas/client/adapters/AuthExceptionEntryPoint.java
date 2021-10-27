package com.xjt.cloud.cas.client.adapters;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.ConstantsMsg;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/12 15:58
 * @Description: token验证异常处理
 */
public class AuthExceptionEntryPoint extends  OAuth2AuthenticationEntryPoint {
    @Autowired
    private AuthorizationParam authorizationParam;
    private WebResponseExceptionTranslator<?> exceptionTranslator = new DefaultWebResponseExceptionTranslator();
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisTokenStoreSerializationStrategy serializationStrategy;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws ServletException {
        try {
            //解析异常，如果是401则处理
            ResponseEntity<?> result = exceptionTranslator.translate(authException);
            if (result.getStatusCode() == HttpStatus.UNAUTHORIZED) {//判断是否是验证异常
                MultiValueMap<String, String> formDataMap = new LinkedMultiValueMap<>();//组装刷新token的参数
                formDataMap.add("client_id", authorizationParam.getClientId());
                formDataMap.add("client_secret", authorizationParam.getSecret());
                formDataMap.add("grant_type", "refresh_token");

                RedisConnection conn = redisConnectionFactory.getConnection();
                try {
                    String token = request.getParameter("access_token");//从请求路径中得到token信息
                    String key = "access_to_refresh:" + token;
                    byte[] serializedKey = serializationStrategy.serialize(key);//以token组装获取refresh_token key
                    byte[] bytes = conn.get(serializedKey);
                    String accessToRefresh = serializationStrategy.deserializeString(bytes);
                    formDataMap.add("refresh_token", accessToRefresh);
                }catch (Exception ex){
                    SysLog.error("刷新token，获取refresh_token失败" + ex.fillInStackTrace());
                    throw BaseServiceException.initException(ConstantsMsg.REFRESH_TOKEN_FAIL, ServiceErrCode.REQ_TOKEN_ERR.getCode());
                }finally {
                    conn.close();
                }

                //Http Basic 验证
                String clientAndSecret = authorizationParam.getClientId() + ":" + authorizationParam.getSecret();
                //这里需要注意为 Basic 而非 Bearer 注意：Basic  与密码串之间为一个空格
                clientAndSecret = "Basic "+ Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization",clientAndSecret);
                //HttpEntity
                HttpEntity httpEntity = new HttpEntity(formDataMap,httpHeaders);
                //获取 Token
                RestTemplate restTemplate = new RestTemplate();
                Object mapToken = restTemplate.exchange(authorizationParam.getCasServerHostUrl(), HttpMethod.POST,httpEntity,Object.class);
                String tokenStr = mapToken.toString();
                tokenStr = tokenStr.substring(tokenStr.indexOf("{"), tokenStr.indexOf("}") + 1);
                JSONObject jsonObjectToken = StringUtils.mapStringToJson(tokenStr);

                /*HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);//设置请求格式
                Map map = restTemplate.exchange(authorizationParam.getCasServerHostUrl(), HttpMethod.POST,
                        new HttpEntity<>(formDataMap, headers), Map.class).getBody();*/
                //如果刷新异常,则坐进一步处理
                if(jsonObjectToken.get("error") == null){
                    /*//如果刷新成功则存储cookie并且跳转到原来需要访问的页面
                    for(Object key : map.keySet()){
                        response.addCookie(new Cookie(key.toString(),map.get(key).toString()));
                    }*/
                    request.getRequestDispatcher(request.getRequestURI()).forward(request,response);
                    return;
                }
            }
        } catch (Exception e) {
            SysLog.error(e);
        }
        throw BaseServiceException.initException(ConstantsMsg.EXPIRE_TOKEN, ServiceErrCode.REQ_TOKEN_ERR.getCode());
    }
}
