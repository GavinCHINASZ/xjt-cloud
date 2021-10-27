package com.xjt.cloud.admin.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.admin.manage.entity.project.Permission;
import com.xjt.cloud.admin.manage.service.service.LoginService;
import com.xjt.cloud.admin.manage.service.service.project.PermissionService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:系统用户控制类
 */
@Controller
public class LoginController extends AbstractController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private PermissionService permissionService;

    /**
     *
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @RequestMapping(value = "/")
    public ModelAndView defaultIndex(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    /**
     *
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    /**
     *
     * 功能描述:用户登录 通过密码授权方式向授权服务器获取令牌
     *
     * @param user
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, User user){
        Data data = loginService.login(user);
        ModelAndView modelAndView;
        if (data.getStatus() == Constants.SUCCESS_CODE){
            modelAndView = new ModelAndView("home/main");
            modelAndView.addObject("userInfo", JSONObject.toJSONString(data));
            JSONObject json = JSONObject.parseObject(data.getObject().toString());
            user = JSONObject.parseObject(json.getString("user"), User.class);
            Map<Permission, List<Permission>> map =  permissionService.findUserMenuList(user.getId());
            modelAndView.addObject("menuMap", map);
        }else{
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("loginInfo", JSONObject.toJSON(data));
        }

        return modelAndView;
    }

    /**
     *
     * 功能描述:注销登录
     *
     * @param request
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/10 18:06
     */
    @RequestMapping(value = "/userLogout")
    public void logout(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        loginService.logout(user);
        response.sendRedirect("/index");
    }

    /**
     *
     * 功能描述:以缓存的key清除缓存
     *
     * @param keys
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/29 15:12
     */
    @RequestMapping(value = "clearCache")
    @ResponseBody
    public Data clearCache(String keys){
        return loginService.clearCache(keys);
    }

}
