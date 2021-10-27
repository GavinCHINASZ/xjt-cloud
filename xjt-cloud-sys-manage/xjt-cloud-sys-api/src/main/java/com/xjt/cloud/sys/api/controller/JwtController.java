package com.xjt.cloud.sys.api.controller;

import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.entity.JwtEntity;
import com.xjt.cloud.sys.core.service.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * jwt
 *
 * @author huanggc
 * @date 2020/11/23
 */
@RestController
@RequestMapping("/jwt/")
public class JwtController extends AbstractController {

    @Autowired
    private JwtService jwtService;

    /**
     * 功能描述: JWT验证 接口
     *
     * @param jwtEntity JwtEntity
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020/11/23
     */
    @RequestMapping(value = "authorizeCallback")
    public Data authorizeCallback(JwtEntity jwtEntity, HttpServletRequest request)  {
        return jwtService.authorizeCallback(jwtEntity,"APP", request);
    }

}
