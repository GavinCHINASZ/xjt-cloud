package com.xjt.cloud.task.core.dao.task;

import com.xjt.cloud.task.core.entity.check.CheckRecordImage;

import java.util.List;

/**
 * 巡检记录图片
 *
 * @author zxb
 * @date 2019-12-02 09:46
 **/
public interface CheckRecordImageDao {

    /**
     * saveCheckRecordImageList
     * 保存巡检记录图片
     *
     * @param list CheckRecordImage实体
     * @author zhangZaiFa
     * @date 2019/12/2 9:48
     **/
    void saveCheckRecordImageList(List<CheckRecordImage> list);

    /**
     * findCheckRecordImageList
     * 查询图片   type不能为null
     *
     * @param checkRecordImage CheckRecordImage实体
     * @author zhangZaiFa
     * @date 2019/12/2 10:24
     * @return List<String>
     **/
    List<String> findCheckRecordImageList(CheckRecordImage checkRecordImage);

    /**
     * findCheckRecordImageList
     * 查询图片   type不能为null
     *
     * @param checkRecordImage CheckRecordImage实体
     * @author zhangZaiFa
     * @date 2019/12/2 10:24
     * @return List<CheckRecordImage>
     **/
    List<CheckRecordImage> findCheckRecordImageEntityList(CheckRecordImage checkRecordImage);
}
