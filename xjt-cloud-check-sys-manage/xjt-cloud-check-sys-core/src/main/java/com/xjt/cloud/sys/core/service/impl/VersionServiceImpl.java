package com.xjt.cloud.sys.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.Data;
import com.xjt.cloud.sys.core.dao.sys.VersionDao;
import com.xjt.cloud.sys.core.entity.Version;
import com.xjt.cloud.sys.core.service.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/9/3 09:46
 * @Description: app版本管理接口实现
 */
@Service
public class VersionServiceImpl extends AbstractService implements VersionService {

    @Autowired
    private VersionDao versionDao;

    /**
     *
     * 功能描述:查询版本列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data findVersionList(String json){
        Version version = JSONObject.parseObject(json, Version.class);
        Integer totalCount = version.getTotalCount();
        Integer pageSize = version.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){//判断是否存在总数，如没有，则查询总记录数
            totalCount = versionDao.findVersionListTotalCount(version);
        }
        List<Version> list = versionDao.findVersionList(version);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data findVersion(String json){
        Version version = JSONObject.parseObject(json, Version.class);
        version = versionDao.findVersion(version);
        return asseData(version);
    }

    /**
     *
     * 功能描述:新增版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data saveVersion(String json){
        Version version = JSONObject.parseObject(json, Version.class);
        int num = versionDao.saveVersion(version);
        return asseData(num);
    }

    /**
     *
     * 功能描述:修改版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data modifyVersion(String json){
        Version version = JSONObject.parseObject(json, Version.class);
        int num = versionDao.modifyVersion(version);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除版本信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/9/3 11:07
     */
    @Override
    public Data delVersion(String json){
        Version version = JSONObject.parseObject(json, Version.class);
        int num = versionDao.modifyVersion(version);
        return asseData(num);
    }

    /**@MethodName: findByVersion
     * @Description: 查询版本信息
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/6/16 23:59
     **/
    @Override
    public Map<String,Object> findByVersion() {
        Map<String,Object> retval = new HashMap<>();
        retval.put("version", "5.0.0");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("versionNumber","5.0.0");
        jsonObject.put("versionTitle","版本更新");
        jsonObject.put("updateContent","1、新增月度工单功能，统计本月工单的执行情况和设备状态，支持通过微、QQ分享；\n" +
                "2、新增NFC扫码巡检、设备登记功能，支持多设备类型巡检；\n" +
                "3、优化巡查功能，统计工单数量实时提示巡查进度；\n" +
                "4、优化火警主机功能，区分已恢复、未恢复事件提升工作效率；\n" +
                "5、优化极早期预警功能，统计事件故障原因、报警事件，提升工作效率；\n" +
                "6、优化水压监测功能；实时统计水压设备状态数量，提醒用户及时处理故障；\n" +
                "7、优化智能水浸功能，实时统计水浸设备状态数量，提醒用户及时处理故障；\n" +
                "8、优化设备管理功能，按巡查点、设备维度进行统计项目中的设备、建筑物数量；\n" +
                "9、优化故障报修功能，按设备维度进行报修时可选设备故障项；\n" +
                "10、优化消息列表，便于快速查看消息分类；");
        jsonObject.put("versionSize","31M");
        jsonObject.put("versionType",0);
        retval.put("versionLocation", jsonObject);
        jsonObject.put("versionType",2);
        retval.put("ios_version", "5.0.0");
        retval.put("ios_versionLocation", jsonObject);
        retval.put("andriod_version", "5.0.0");
        jsonObject.put("versionType",1);
        retval.put("andriod_versionLocation", jsonObject);
        retval.put("success", "true");
        return retval;
    }
}
