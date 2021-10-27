package com.xjt.cloud.admin.manage.dao.info;

import com.xjt.cloud.admin.manage.entity.info.RecruitInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitInfoDao {

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: List<RecruitInfo>
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    List<RecruitInfo> findRecruitInfoList(RecruitInfo recruitInfo);

    /**
     *
     * 功能描述:查询资讯信息管理列表
     *
     * @param
     * @return: List<RecruitInfo>
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    Integer findRecruitInfoListTotalCount(RecruitInfo recruitInfo);

    /**
     *
     * 功能描述:保存资讯信息管理
     *
     * @param
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    int saveRecruitInfo(RecruitInfo recruitInfo);

    /**
     *
     * 功能描述:修改资讯信息管理
     *
     * @param
     * @return: int
     * @auther: wangzhiwen
     * @date: 2020/8/4 16:55
     */
    int modifyRecruitInfo(RecruitInfo recruitInfo);
}
