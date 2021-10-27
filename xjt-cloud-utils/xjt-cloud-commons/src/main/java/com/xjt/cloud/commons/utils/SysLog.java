package com.xjt.cloud.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Description 日志记录类
 * @author wangzhiwen
 * @version V0.0.1
 * @date 2016-09-08
 */
public class SysLog {
    public static Logger logger = LoggerFactory.getLogger(SysLog.class);

    /**
     * 打印警告
     *
     * @param obj
     */
    public static void warn(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
            String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
                    + "(" + stacks[2].getLineNumber() + ")";
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                logger.warn(location + str);
            } else {
                logger.warn(location + obj.toString());
            }
        }catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 打印debug
     *
     * @param obj
     */
    public static void debug(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
            String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
                    + "(" + stacks[2].getLineNumber() + ")";
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                logger.debug(location + str);
            } else {
                logger.debug(location + obj.toString());
            }
        }catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 打印信息
     *
     * @param obj
     */
    public static void info(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
            String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
                    + "(" + stacks[2].getLineNumber() + ")";
            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                logger.info(location + str);
            } else {
                logger.info(location + obj.toString());
            }
        }catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 打印错误
     *
     * @param obj
     */
    public static void error(Object obj) {
        try{
            /*** 获取输出信息的代码的位置 ***/
            String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
                    + "(" + stacks[2].getLineNumber() + ")";

            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw, true));
                String str = sw.toString();
                logger.error(location + str);
            } else {
                logger.error(location + obj.toString());
            }
        }catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * 向数据库告警表中插入信息
     * @param obj
     */
    public static void dbWarn(Object obj) {
        try{
            String printInfo = "";
            /*** 获取输出信息的代码的位置 ***/
            String location = "";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = stacks[2].getClassName() + "." + stacks[2].getMethodName()
                    + "(" + stacks[2].getLineNumber() + ")";

            /*** 是否是异常  ***/
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                printInfo = location + e.getMessage();
                logger.warn(printInfo.substring(0, printInfo.length() > 512?512:printInfo.length()));
            } else {
                printInfo = location + obj.toString();
                logger.warn(printInfo.substring(0, printInfo.length() > 512?512:printInfo.length()));
            }
        }catch (Exception e) {
            SysLog.error(e);
        }
    }

    /**
     * @Description 获取错误详细信息
     *
     * @param e
     * @author wangzhiwen
     * @date 2020/9/11 11:43
     * @return java.lang.String
     */
    public static String getExceptionInfo(Exception e){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        e.printStackTrace(pout);
        String ret = new String(out.toByteArray());
        pout.close();
        try {
            out.close();
        } catch (Exception ex) {
        }
        return ret;
    }

    /**
     * 获取调用此函数的代码的位置
     * @return 包名.类名.方法名(行数)
     */
    public static String getCodeLocation(){
        try{
            /*** 获取输出信息的代码的位置 ***/
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            StringBuilder location = new StringBuilder();
            for (StackTraceElement stack:stacks){
                location.append(stack.getClassName() + "." + stack.getMethodName()
                        + "(" + stack.getLineNumber() + "\n");
            }
            return location.toString();
        }catch (Exception e) {
            SysLog.error(e);
            return "";
        }
    }
}