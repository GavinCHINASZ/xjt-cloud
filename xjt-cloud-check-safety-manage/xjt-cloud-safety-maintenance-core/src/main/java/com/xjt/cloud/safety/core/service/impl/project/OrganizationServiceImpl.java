package com.xjt.cloud.safety.core.service.impl.project;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.CacheUtils;
import com.xjt.cloud.commons.utils.Constants;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.commons.utils.SysLog;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import com.xjt.cloud.safety.core.dao.project.OrganizationDao;
import com.xjt.cloud.safety.core.common.utils.ProjectThread;
import com.xjt.cloud.safety.core.common.utils.ThreadPoolUtils;
import com.xjt.cloud.safety.core.entity.project.OrgUser;
import com.xjt.cloud.safety.core.entity.project.Organization;
import com.xjt.cloud.safety.core.service.service.project.OrgUserService;
import com.xjt.cloud.safety.core.service.service.project.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @ClassName OrganizationServiceImpl 公司/部门实现类
 * @Author zhangZaiFa
 * @Date 2019-07-29 15:15
 * @Description
 */
@Service
public class OrganizationServiceImpl extends AbstractService implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private OrgUserService orgUserService;
    @Autowired
    private SecurityLogService securityLogService;


    @Override
    public Organization findByOrgId(Long orgId) {
        return organizationDao.findByOrgId(orgId);
    }

    /**
     * @MethodName: updateDepartment 修改公司信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:10
     **/
    @Override
    public Data updateDepartment(String json) {
        Organization dep = JSONObject.parseObject(json, Organization.class);
        Organization org = organizationDao.findByOrgName(dep);
        if (org != null) {
            return asseData(600, "部门名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        dep.setUpdateUserId(userId);
        dep.setUpdateUserName(userName);
        Organization oldOrg = findByOrgId(dep.getId());
        organizationDao.updateOrg(dep);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_DEPARTMENT", SecurityUserHolder.getUserName(), dep, oldOrg, oldOrg.getProjectId(), 2);
        ThreadPoolUtils.getInstance().execute(new ProjectThread(this, "orgCacheInit"));
        return Data.isSuccess();
    }

    /**
     * @MethodName: deleteDepartment 删除部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:10
     **/
    @Override
    public Data deleteDepartment(String json) {
        Organization org = JSONObject.parseObject(json, Organization.class);
        List<OrgUser> orgUsers = orgUserService.findByDepIdOrgUserList(org.getOrgIds());
        if (orgUsers.size() != 0) {
            return asseData(600, "请先删除部门下面的人员");
        }
        for (Long depId : org.getOrgIds()) {
            Organization oldOrg = findByOrgId(depId);
            org.setOrgName(oldOrg.getOrgName());
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_DEPARTMENT", SecurityUserHolder.getUserName(), org, null, oldOrg.getProjectId(), 2);
        }
        organizationDao.deleteOrgIds(org.getOrgIds());
        ThreadPoolUtils.getInstance().execute(new ProjectThread(this, "orgCacheInit"));
        return Data.isSuccess();
    }

    /**
     * @MethodName: saveDepartment 添加部门
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:10
     **/
    @Override
    public Data saveDepartment(String json) {
        Organization dep = JSONObject.parseObject(json, Organization.class);
        dep.setOrgType(3);
        dep.setComType(0);
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        dep.setCreateUserId(userId);
        dep.setCreateUserName(userName);
        Organization org = organizationDao.findByOrgName(dep);
        if (org != null) {
            return asseData(600, "部门名称已存在");
        }
        organizationDao.saveOrg(dep);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_DEPARTMENT", SecurityUserHolder.getUserName(), dep, null, dep.getProjectId(), 2);
        ThreadPoolUtils.getInstance().execute(new ProjectThread("OrganizationServiceImpl", "orgCacheInit"));
        return asseData(dep);
    }

    /**
     * @MethodName: saveCompany 添加公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:10
     **/
    @Override
    public Data saveCompany(String json) {
        Organization company = JSONObject.parseObject(json, Organization.class);
        company.setOrgType(2);
        Organization org = organizationDao.findByOrgName(company);
        if (org != null) {
            return asseData(600, "公司名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        company.setCreateUserName(userName);
        company.setCreateUserId(userId);
        company.setOwerCompany(0L);
        company.setParentId(0L);
        organizationDao.saveOrg(company);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_ADD_COMPANY", SecurityUserHolder.getUserName(), company, null, company.getProjectId(), 2);
        ThreadPoolUtils.getInstance().execute(new ProjectThread("OrganizationServiceImpl", "orgCacheInit"));
        return Data.isSuccess();
    }

    /**
     * @MethodName: deleteCompany 删除公司
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:10
     **/
    @Override
    public Data deleteCompany(String json) {
        Organization org = JSONObject.parseObject(json, Organization.class);
        List<Organization> deps = organizationDao.findByDepList(org.getIds());
        if (deps.size() != 0) {
            return asseData(600, "请先删除公司下面的部门");
        }
        for (Long comId : org.getIds()) {
            Organization oldOrg = findByOrgId(comId);
            org.setOrgName(oldOrg.getOrgName());
            securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_DEL_COMPANY", SecurityUserHolder.getUserName(), org, null, oldOrg.getProjectId(), 2);
        }
        organizationDao.deleteOrgIds(org.getIds());
        ThreadPoolUtils.getInstance().execute(new ProjectThread("OrganizationServiceImpl", "orgCacheInit"));
        return Data.isSuccess();
    }

    /**
     * @MethodName: updateCompany 修改公司信息
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:10
     **/
    @Override
    public Data updateCompany(String json) {
        Organization company = JSONObject.parseObject(json, Organization.class);
        Organization org = organizationDao.findByOrgName(company);
        if (org != null) {
            return asseData(600, "公司名称已存在");
        }
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        company.setUpdateUserName(userName);
        company.setUpdateUserId(userId);
        Organization oldOrg = findByOrgId(company.getId());
        organizationDao.updateOrg(company);
        securityLogService.saveSecurityLog("SECURITY_LOG_TYPE_MODIFY_COMPANY", SecurityUserHolder.getUserName(), company, oldOrg, oldOrg.getProjectId(), 2);
        ThreadPoolUtils.getInstance().execute(new ProjectThread(this, "orgCacheInit"));

        return Data.isSuccess();
    }

    /**
     * @MethodName: findByCompanyList 查询公司列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:11
     **/
    @Override
    public Data findByCompanyList(String json) {
        Organization company = JSONObject.parseObject(json, Organization.class);
        company.setOrgType(2);
        return findByOrgList(company);
    }

    /**
     * @MethodName: findByDepartmentList 查询部门列表
     * @Description:
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:11
     **/
    @Override
    public Data findByDepartmentTree(String json) {
        Organization org = JSONObject.parseObject(json, Organization.class);
        List<Organization> list = organizationDao.findByDepartmentTree(org);
        return asseData(list);
    }

    /**
     * @MethodName: findByOrgList 查询公司或部门列表
     * @Description:
     * @Param: [org]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2019/9/9 15:30
     **/
    private Data findByOrgList(Organization org) {
        List<Organization> list = organizationDao.findByOrgList(org);
        Integer totalCount = org.getTotalCount();
        Integer pageSize = org.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize) {//判断是否存在总数，如没有，则查询总记录数
            totalCount = organizationDao.findByOrgListCount(org);
        }
        return asseData(totalCount, list);
    }

    /**
     * @MethodName: orgCacheInit 添加初始化缓存
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: zhangZaiFa
     * @Date:2019/10/29 11:07
     **/
    @Override
    public void orgCacheInit() {
        SysLog.debug("添加缓存---------------》");
        List<Organization> list = organizationDao.findByAllList();
        CacheUtils.setCacheByList(list, Constants.ORG_CACHE_KEY, Organization.class);//初使化缓存
    }

    /**
     * 功能描述: 以sql文查询部门信息列表
     *
     * @param json
     * @return:
     * @auther: wangzhiwen
     * @date: 2019/11/26 11:06
     */
    @Override
    public Data findOrgListBySql(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        List<Organization> list = organizationDao.findOrgListBySql(jsonObject.getString("sql"));
        return asseData(list);
    }
}
