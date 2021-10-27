package com.xjt.cloud.data.source.dynamic;

import com.xjt.cloud.commons.utils.SysLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 多数据源切面
 *
 * @author wangzhiwen
 * @date 2017-08-15 11:37
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     *
     * 功能描述: dao切面 切入的路径
     *
     * @param
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/3 17:11
     */
    @Pointcut("(execution( * com.xjt.cloud..*.dao..*.*(..)) || execution( * com.xjt.cloud..*.service..*.transaction*(..)))")
    public void daoAspect() {

    }

    /**
     *
     * 功能描述: 据源切换 前置通知, 在方法执行之前执行
     *
     * @param point
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/3 17:10
     */
    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        //获取当前访问的方法名
        String methodName = point.getSignature().getName();
        try {
            //判断是否是事务方法，如是，则切换到默认数据源
            if(methodName.toUpperCase().startsWith("TRANSACTION")){
                DynamicDataSourceContextHolder.removeDataSource();
                DynamicDataSourceContextHolder.useDefaultDataSource();//
            }else{
                DynamicDataSourceContextHolder.useDataSource(point.getSignature().getDeclaringTypeName(), methodName);
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }

        logger.debug("在方法[{}]中将数据源切换到[{}]", point.getSignature(), DynamicDataSourceContextHolder.getDataSourceKey());
    }

    /**
     *
     * 功能描述:恢复数据源 后置通知, 在方法执行之后执行
     *
     * @param point
     * @return: void
     * @auther: wangzhiwen
     * @date: 2019/7/3 17:09
     */
    @After("daoAspect()")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.removeDataSource();
        DynamicDataSourceContextHolder.useDefaultDataSource();//切换失败时切换到默认数据源
        logger.debug("在方法[{}]中将数据源还原为[{}]", point.getSignature(),  DynamicDataSourceContextHolder.getDataSourceKey());
    }
}
