package com.xjt.cloud.sys.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.sys.core.common.utils.ConstantsClient;
import com.xjt.cloud.sys.core.entity.JwtEntity;
import com.xjt.cloud.sys.core.service.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @author huanggc
     * @date 2020/11/23
     */
    @RequestMapping(value = "authorizeCallback")
    public void authorizeCallback(JwtEntity jwtEntity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Data data = jwtService.authorizeCallback(jwtEntity, "APP", request);
        if (data.getStatus() == 200){
            String content = data.getObject().toString();
            JSONObject object = JSONObject.parseObject(content);
            String accessToken = object.get("access_token").toString();
            String refreshToken = object.get("refresh_token").toString();
            String expiresIn = object.get("expires_in").toString();
            String user = object.get("user").toString();
            String jwtSendRedirectUrl = ConstantsClient.JWT_SEND_REDIRECT_URL;

            //request.setAttribute("data", data);

            //ModelAndView modelAndView = new ModelAndView();
            //modelAndView.addObject("date", data);

            StringBuilder sb = new StringBuilder();
            sb.append("access_token=").append(accessToken);
            sb.append("&refresh_token=").append(refreshToken);
            sb.append("&expires_in=").append(expiresIn);
            Object projectObj = object.get("project");
            if (projectObj != null){
                String project = projectObj.toString();
                sb.append("&project=").append(project);
            }
            sb.append("&user=").append(user);

            // 重定向到登录页面
            response.sendRedirect(jwtSendRedirectUrl + StringUtils.urlEncode(sb.toString()));
        }
        //return data;
    }

}
