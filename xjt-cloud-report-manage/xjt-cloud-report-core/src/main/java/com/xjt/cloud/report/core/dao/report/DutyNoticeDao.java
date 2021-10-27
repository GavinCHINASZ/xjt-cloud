package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.DutyNotice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: xjt-cloud-report-manage
 * @description:值班提醒
 * @author: zxb
 * @create: 2020-05-19 16:48
 **/
@Repository
public interface DutyNoticeDao {

    /**@MethodName: saveDutyNotice
     * @Description: 保存值班提醒
     * @Param: [dutyNotice]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/5/20 13:58
     **/
    void saveDutyNotice(DutyNotice dutyNotice);

    /**@MethodName: findDutyNotice
     * @Description: 查询值班提醒
     * @Param: [dutyNotice]
     * @Return: com.xjt.cloud.report.core.entity.report.DutyNotice
     * @Author: zhangZaiFa
     * @Date:2020/5/20 13:58
     **/
    List<DutyNotice> findDutyNotice(DutyNotice dutyNotice);

    /**@MethodName: delDutyNotice
     * @Description: 删除值班提醒
     * @Param: [dutyNotice]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/5/20 13:57
     **/
    void delDutyNotice(DutyNotice dutyNotice);

    /**@MethodName: findDutyNoticeUserId
     * @Description: 查询值班提醒用户
     * @Param: [dutyNotice]
     * @Return: java.util.List<DutyNotice>
     * @Author: zhangZaiFa
     * @Date:2020/5/20 15:45
     **/
    List<DutyNotice> findDutyNoticeProjects(DutyNotice dutyNotice);
}
