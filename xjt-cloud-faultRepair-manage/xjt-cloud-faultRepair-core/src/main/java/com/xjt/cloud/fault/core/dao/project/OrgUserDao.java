package com.xjt.cloud.fault.core.dao.project;

import com.xjt.cloud.fault.core.entity.project.OrgUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName OrgUserDao 平台成员Dao
 * @Author huanggc
 * @Date 2019/10/28
 * @Description
 */
@Repository
public interface OrgUserDao {

    /**
     * 功能描述: 根据 项目ID 查询成员
     *
     * @param orgUser
     * @auther: huanggc
     * @date: 2019/10/28
     * @return: List<OrgUser>
     */
    List<OrgUser> findByProject(OrgUser orgUser);

    /**
     * 功能描述: 查询成员
     *
     * @param orgUser
     * @auther: huanggc
     * @date: 2019/10/28
     * @return: List<OrgUser>
     */
    OrgUser getOrgUser(OrgUser orgUser);
}
