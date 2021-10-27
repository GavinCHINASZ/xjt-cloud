package com.xjt.cloud.sys.core.dao.sys;

import com.xjt.cloud.sys.core.entity.Version;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:46
 * @Description: app版本管理DAO
 */
@Repository
public interface VersionDao {
    /**
     *
     * 功能描述:查询版本列表
     *
     * @param version
     * @return: List<Version>
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    List<Version> findVersionList(Version version);

    /**
     *
     * 功能描述:查询版本列表总数量
     *
     * @param version
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    int findVersionListTotalCount(Version version);

    /**
     *
     * 功能描述:查询版本信息
     *
     * @param version
     * @return: Version
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    Version findVersion(Version version);

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param version
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    int saveVersion(Version version);

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param version
     * @return: int
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    int modifyVersion(Version version);
}
