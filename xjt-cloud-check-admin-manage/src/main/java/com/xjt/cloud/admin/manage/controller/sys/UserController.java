package com.xjt.cloud.admin.manage.controller.sys;

import com.xjt.cloud.admin.manage.common.utils.AjaxPage;
import com.xjt.cloud.admin.manage.common.utils.ScriptPage;
import com.xjt.cloud.admin.manage.entity.sys.User;
import com.xjt.cloud.admin.manage.service.service.sys.UserService;
import com.xjt.cloud.commons.abstracts.AbstractController;
import com.xjt.cloud.commons.utils.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/8/29 11:39
 * @Description:
 */
@Controller
@RequestMapping("/user/")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    /**
     * 功能描述:获取用户信息
     * @Author huanggc
     * @Date 2019/5/23
     * @param json
     * @return com.xjt.cloud.commons.utils.Data
     */
    @RequestMapping(value = "getUser")
    @ResponseBody
    public Data getUser(String json){
        return userService.getUser(json);
    }

    /**
     *
     * 功能描述:
     *
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/1/15 9:30
     */
    @RequestMapping(value = "toUserListPage")
    public ModelAndView toUserListPage(){
        return new ModelAndView("sys/userList");
    }

    /**
     *
     * 功能描述:分页查询用户信息列表
     *
     * @param ajaxPage
     * @param user
     * @return:
     * @auther: wangzhiwen
     * @date: 2020/1/15 9:34
     */
    @RequestMapping(value = "findUserList")
    @ResponseBody
    public ScriptPage<User> findUserList(AjaxPage ajaxPage,User user){
        return userService.findUserList(ajaxPage,user);
    }

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "modifyUser")
    @ResponseBody
    public Data modifyUser(User user){
        return userService.modifyUser(user);
    }

    /**
     *
     * 功能描述:修改用户信息
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/11/25 9:37
     */
    @RequestMapping(value = "saveUser")
    @ResponseBody
    public Data saveUser(User user){
        return userService.saveUser(user);
    }

    /**
     *
     * 功能描述:修改用户密码
     *
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/27 16:15
     */
    @RequestMapping(value = "toModifyPasswordPage")
    public ModelAndView toModifyPasswordPage(){
        return new ModelAndView("sys/modifyPass");
    }
    /**
     *
     * 功能描述:修改用户密码
     *
     * @param user
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2020/7/27 16:15
     */
    @RequestMapping(value = "modifyPassword")
    @ResponseBody
    public Data modifyPassword(User user){
        return userService.modifyPassword(user);
    }
}
