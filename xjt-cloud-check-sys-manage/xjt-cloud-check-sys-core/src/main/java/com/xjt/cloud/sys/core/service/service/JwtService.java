package com.xjt.cloud.sys.core.service.service;

import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.entity.JwtEntity;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * jwt Service
 *
 * @author huanggc
 * @date 2020/11/23
 */
public interface JwtService {

    /**
     * 功能描述: JWT验证 接口
     *
     * @param jwtEntity JwtEntity
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/23
     */
    Data authorizeCallback(JwtEntity jwtEntity, String cloudType, HttpServletRequest request);

}
