package com.xjt.cloud.report.core.dao.report;

import com.xjt.cloud.report.core.entity.report.DutyRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: zhangzf
 * @Date: 2019/11/04
 * @Description: 值班记录
 */
@Repository
public interface DutyRecordDao {
    /**@MethodName: save 保存值班记录
     * @Description: 
     * @Param: [dr]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/12 14:39 
     **/
    void save(DutyRecord dr);

    /**@MethodName: findByDutyRecord 查询值班信息列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/11/13 9:33
     **/
    List<DutyRecord> findByDutyRecordList(DutyRecord dr);

    /**@MethodName: findByDutyRecord 查询记录详情
     * @Description: 
     * @Param: [dr]
     * @Return: com.xjt.cloud.report.core.entity.report.DutyRecord
     * @Author: zhangZaiFa
     * @Date:2019/11/13 13:56
     **/
    DutyRecord findByDutyRecord(DutyRecord dr);

    /**@MethodName: findByDataChart 查询图表数据
     * @Description:
     * @Param: [dr]
     * @Return: java.util.List<com.xjt.cloud.report.core.entity.report.DutyRecord>
     * @Author: zhangZaiFa
     * @Date:2019/11/13 15:42
     **/
    List<DutyRecord> findByDataChart(DutyRecord dr);

    /**@MethodName: findByDutyRecordImageUrlList 查询记录图片
     * @Description: 
     * @Param: [id]
     * @Return: java.util.List<java.lang.String>
     * @Author: zhangZaiFa
     * @Date:2019/11/15 9:35 
     **/
    List<String> findByDutyRecordImageUrlList(@Param("dutyRecordId")Long dutyRecordId);

    /**@MethodName: deleteImageUrl 删除记录图片
     * @Description: 
     * @Param: [id]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/15 9:35 
     **/
    void deleteImageUrl(@Param("dutyRecordId")Long dutyRecordId);

    /**@MethodName: saveImageUrl 保存记录图片
     * @Description: 
     * @Param: [dr]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/11/15 9:35 
     **/
    void saveImageUrl(DutyRecord dr);

    /**@MethodName: deleteRecord
     * @Description: 删除记录
     * @Param: [oldDr]
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2020/3/20 16:17
     **/
    void deleteRecord(DutyRecord oldDr);

    /**@MethodName: findByProjectMonthRecordCount
     * @Description: 查询项目月记录数
     * @Param: [dr]
     * @Return: com.xjt.cloud.report.core.entity.report.DutyRecord
     * @Author: zhangZaiFa
     * @Date:2020/3/26 14:13
     **/
    DutyRecord findByProjectMonthRecordCount(DutyRecord dr);
}
