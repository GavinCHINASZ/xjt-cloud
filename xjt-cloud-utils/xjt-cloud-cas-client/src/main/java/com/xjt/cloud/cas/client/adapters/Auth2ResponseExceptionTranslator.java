package com.xjt.cloud.cas.client.adapters;

import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.ConstantsMsg;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @ClassName Auth2ResponseExceptionTranslator
 * @Description
 * @Author wangzhiwen
 * @Date 2020/8/25 10:57
 **/
public class Auth2ResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        throw BaseServiceException.initException(ConstantsMsg.USER_LOGIN_FAIL, ServiceErrCode.NOT_URL_ERR.getCode());
        /*SysLog.error("Auth2异常");
        Throwable throwable = e.getCause();
        if (throwable instanceof InvalidTokenException) {
            SysLog.error("token失效:{}");
            HttpHeaders headers = new HttpHeaders();
            headers.setAll(headers.toSingleValueMap());
            return new ResponseEntity("605", headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity("605", null, HttpStatus.FORBIDDEN);*/
    }
}
