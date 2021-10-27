package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.sys.core.entity.ExceptionLog;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ExceptionLogDao
 * @Description
 * @Author wangzhiwen
 * @Date 2020/9/14 9:37
 **/
@Repository
public interface ExceptionLogDao {

    /**
     * @param exceptionLog
     * @return int
     * @Description 保存错误信息
     * @author wangzhiwen
     * @date 2020/9/14 9:50
     */
   int saveExceptionLog(ExceptionLog exceptionLog);
}
