package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.device.core.dao.device.DeviceTypeDao;
import com.xjt.cloud.device.core.entity.DeviceType;
import com.xjt.cloud.device.core.service.service.DeviceTypeService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备系统与类型管理业务接口实现类
 *
 * @author wangzhiwen
 * @date 2019/7/17 14:52
 */
@Service
public class DeviceTypeServiceImpl extends AbstractService implements DeviceTypeService {
    @Autowired
    private DeviceTypeDao deviceTypeDao;
    @Autowired
    private Configuration configuration;

    /**
     * 功能描述:查询设备类型信息列表
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data findDeviceTypeList(String json) {
        return findDeviceTypeList(json, 9);
    }

    /**
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件 未绑定物联设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data findDeviceTypeListByCheckPoint(String json) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        deviceType.setIotId(0L);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, deviceType.getProjectId() + "", "checkItemVsType");
        deviceType.setCheckItemVsType(StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeListByCheckPoint(deviceType);
        return asseData(list);
    }

    /**
     * 功能描述:查询设备类型信息列表,以巡检点信息为条件 已绑定绑定物联设备
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data findDeviceTypeListByCheckPointBind(String json) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        deviceType.setIotId(1L);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeListByCheckPoint(deviceType);
        return asseData(list);
    }

    /**
     * 功能描述:查询设备系统信息列表
     *
     * @param json 参数
     * @param type 类型
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/8/8 10:47
     */
    private Data findDeviceTypeList(String json, int type) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, deviceType.getProjectId() + "", "checkItemVsType");
        deviceType.setCheckItemVsType(StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1);
        Integer totalCount = deviceType.getTotalCount();
        Integer pageSize = deviceType.getPageSize();
        deviceType.setType(type);
        // 判断是否存在总数，如没有，则查询总记录数
        if (null == totalCount && null != pageSize && 0 != pageSize) {
            totalCount = deviceTypeDao.findDeviceTypeListCount(deviceType);
        }
        List<DeviceType> list = deviceTypeDao.findDeviceTypeList(deviceType);
        return asseData(totalCount, list);
    }

    /**
     * 功能描述:查询设备类型信息
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/7/17 16:15
     */
    @Override
    public Data findDeviceType(String json) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        deviceType = deviceTypeDao.findDeviceType(deviceType);
        return asseData(deviceType);
    }

    /**
     * 功能描述:查询设备系统树
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/9 9:03
     */
    @Override
    public Data findDeviceSysTree(String json) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, deviceType.getProjectId() + "", "checkItemVsType");
        deviceType.setCheckItemVsType(StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1);

        List<DeviceType> deviceTypeList = deviceTypeDao.findDeviceSysByProjectId(deviceType);
        if (CollectionUtils.isNotEmpty(deviceTypeList)) {
            Long[] parentIds = new Long[deviceTypeList.size()];

            for (int i = 0; i < deviceTypeList.size(); i++) {
                parentIds[i] = deviceTypeList.get(i).getId();
            }

            deviceType.setParentIds(parentIds);
        }

        List<DeviceType> list = deviceTypeDao.findDeviceSysTree(deviceType);
        return asseData(list);
    }

    /**
     * 功能描述:查询未删除设备类型信息列表，以拼音排序
     *
     * @param json 参数
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/9/9 9:03
     */
    @Override
    public Data findDeviceTypeOrderPinYin(String json) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, deviceType.getProjectId() + "", "checkItemVsType");
        deviceType.setCheckItemVsType(StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeOrderPinYin(deviceType);
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<DeviceType> childList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                deviceType = list.get(i);
                childList.add(deviceType);
                // 复杂表达式
                boolean b = StringUtils.isEmpty(deviceType.getPinYinInitials()) && StringUtils.isNotEmpty(list.get(i + 1).getPinYinInitials());
                if (i == list.size() - 1 || b
                        || (StringUtils.isNotEmpty(deviceType.getPinYinInitials()) && StringUtils.isNotEmpty(list.get(i + 1).getPinYinInitials())
                        && !deviceType.getPinYinInitials().substring(0, 1).equals(list.get(i + 1).getPinYinInitials().substring(0, 1)))) {
                    jsonObject.put("type", StringUtils.isNotEmpty(deviceType.getPinYinInitials()) ? deviceType.getPinYinInitials().substring(0, 1) : "");
                    jsonObject.put("child", childList);
                    jsonList.add(jsonObject);
                    childList = new ArrayList<>();
                    jsonObject = new JSONObject();
                }
            }
        }
        return asseData(jsonList);
    }

    /**
     * 功能描述:设备系统缓存初使化
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2019/8/8 9:51
     */
    @Override
    public Data deviceSysCacheInit() {
        DeviceType deviceType = new DeviceType();
        deviceType.setPageSize(null);
        List<DeviceType> list = deviceTypeDao.findDeviceTypeList(deviceType);
        // 初使化缓存
        CacheUtils.setCacheByList(list, Constants.DEVICE_SYS_CACHE_KEY, DeviceType.class);
        return Data.isSuccess();
    }

    /**
     * 功能描述: 查询 设备类型(地铁)
     *
     * @param json     参数
     * @param response HttpServletResponse
     * @return com.xjt.cloud.commons.utils.Data
     * @author huanggc
     * @date 2020-04-27
     */
    @Override
    public Data findDeviceTypeMetroList(String json, HttpServletResponse response) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        deviceType.setDeviceType(9);
        String checkItemVsType = CacheUtils.getCacheValueByTypeAndKey(Constants.PROJECT_CACHE_KEY, deviceType.getProjectId() + "", "checkItemVsType");
        deviceType.setCheckItemVsType(StringUtils.isNotEmpty(checkItemVsType) ? Integer.parseInt(checkItemVsType) : 1);
        List<DeviceType> deviceTypeList = deviceTypeDao.findDeviceTypeMetroList(deviceType);

        // 导出 word文档
        if (StringUtils.isNotEmpty(deviceType.getDownType())) {
            String projectName = "";
            Map<String, Object> map = new HashMap<>(2);
            // 数据
            map.put("list", deviceTypeList);
            // 项目名称
            map.put("projectName", projectName);

            String fileName = "11号线FAS系统月度检修记录表.doc";
            String modelName = "fasMonth.xml";
            // 导出方法
            downModel(response, fileName, map, projectName, configuration, modelName);
            return asseData(200);
        }
        return null;
    }

    /**
     * 功能描述: 导出公共方法抽取
     *
     * @param fileName    导出的文件名+文件后缀
     * @param map         数据
     * @param projectName 项目名称
     * @param modelName   模板名
     * @author huanggc
     * @date 2019/11/04
     */
    private static void downModel(HttpServletResponse response, String fileName, Map<String, Object> map, String projectName, Configuration configuration,
                                  String modelName) {

        OutputStream out = null;
        OutputStreamWriter oWriter = null;
        BufferedWriter writer = null;
        try {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            out = response.getOutputStream();
            oWriter = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(oWriter);
            // 获取模板 "**.xml"
            Template template = configuration.getTemplate(modelName);
            template.setOutputEncoding("UTF-8");

            String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            writer.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (oWriter != null) {
                    oWriter.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以部门id与楼层查询设备类型列表
     *
     * @return com.xjt.cloud.commons.utils.Data
     * @author wangzhiwen
     * @date 2021/3/10 15:44
     */
    @Override
    public Data findDeviceTypeListByOrgFloor(String json) {
        DeviceType deviceType = JSONObject.parseObject(json, DeviceType.class);
        return asseData(deviceTypeDao.findDeviceTypeListByOrgFloor(deviceType));
    }

}
