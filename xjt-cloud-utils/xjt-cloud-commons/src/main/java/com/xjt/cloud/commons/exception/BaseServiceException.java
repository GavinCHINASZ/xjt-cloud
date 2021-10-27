package com.xjt.cloud.commons.exception;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/12 09:59
 * @Description:
 */
public class BaseServiceException extends RuntimeException {

    private int code;

    /**
     *
     * 功能描述:初使化自定义异常处理方法
     *
     * @param message
     * @param code
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/1 14:58
     */
    public static BaseServiceException initException(String message, int code){
       return new BaseServiceException(message, code);
    }

    /**
     *
     * 功能描述:初使化自定义异常处理方法
     *
     * @param serviceErrCode
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/8/1 14:58
     */
    public static void initException(ServiceErrCode serviceErrCode){
        new BaseServiceException(serviceErrCode.getMsg(), serviceErrCode.getCode());
    }

    public BaseServiceException(String message, int code) {//构造器的第二个参数是上面创建的那个枚举，之后把枚举里面定义的code给了这个code
        super(code + "-" + message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
