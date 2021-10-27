package com.xjt.cloud.commons.exception;

import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/12 14:10
 * @Description:
 */
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping
public class BaseErrorController extends AbstractErrorController  {
    @Autowired
    private HttpServletResponse response;

    public BaseErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        SysLog.logger.info("出错啦！进入自定义错误控制器");
        return "/error";
    }

    @RequestMapping(value = "/error")
    @ResponseBody
    public Map<String, Object> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,false);
        Object obj = body.get("message");
        SysLog.logger.error(body.toString());
        if (obj != null){
            String[] codeMsg = obj.toString().split("-");
            body.remove("message");
            body.remove("error");

            if (codeMsg != null && codeMsg.length >= 2){
                if (StringUtils.isNumber(codeMsg[0]) && Integer.valueOf(codeMsg[0]) > 600){
                    body.put("status", codeMsg[0]);
                    body.put("msg", codeMsg[1]);
                }else {
                    body.put("status", body.get("status").toString());
                    body.put("msg", ServiceErrCode.REQ_SERVICE_ERR.getMsg());
                }
            }else{
                body.put("msg", ServiceErrCode.REQ_SERVICE_ERR.getMsg() + obj.toString());
            }
            try {
                response.setStatus(403, body.toString());
            }catch (Exception ex){

                SysLog.error(ex);
            }
        }
        return body;
    }
}
