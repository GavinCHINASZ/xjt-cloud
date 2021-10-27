package com.xjt.cloud.device.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.cas.client.adapters.SecurityUserHolder;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.device.core.common.ConstantsDevice;
import com.xjt.cloud.device.core.common.ConstantsDeviceMsg;
import com.xjt.cloud.device.core.dao.device.CheckPointDao;
import com.xjt.cloud.device.core.dao.device.DeviceDao;
import com.xjt.cloud.device.core.dao.device.DeviceTypeDao;
import com.xjt.cloud.device.core.dao.device.QrNoDao;
import com.xjt.cloud.device.core.entity.*;
import com.xjt.cloud.device.core.service.service.CheckPointService;
import com.xjt.cloud.device.core.service.service.QrNoService;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:31
 * @Description:巡检点管理
 */
@Service
public class CheckPointServiceImpl extends AbstractService implements CheckPointService {
    @Autowired
    private CheckPointDao checkPointDao;
    @Autowired
    private QrNoService qrNoService;
    @Autowired
    private DeviceTypeDao deviceTypeDao;
    @Autowired
    private QrNoDao qrNoDao;
    @Autowired
    private SecurityLogService securityLogService;
    @Autowired
    private DeviceDao deviceDao;

    /**
     *
     * 功能描述:查询巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @Override
    public Data findCheckPointList(String json){
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        if(checkPoint.getOrderCols() == null){
            String[] orderCols = {"createTime"};
            checkPoint.setOrderCols(orderCols);
            checkPoint.setOrderDesc(true);
        }
        Integer totalCount = checkPoint.getTotalCount();
        Integer pageSize = checkPoint.getPageSize();
        if (null == totalCount && null != pageSize && 0 != pageSize){
            // 判断是否存在总数，如没有，则查询总记录数
            totalCount = checkPointDao.findCheckPointListCount(checkPoint);
        }
        List<CheckPoint> list = checkPointDao.findCheckPointList(checkPoint);
        return asseData(totalCount, list);
    }

    /**
     *
     * 功能描述:查询巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @Override
    public Data findCheckPoint(String json){
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        checkPoint = checkPointDao.findCheckPoint(checkPoint);
        return asseData(checkPoint);
    }

    /**
     *
     * 功能描述:添加巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @Override
    public Data saveCheckPoint(String json){
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        String qrNo = qrNoService.getOneQrNo(checkPoint.getProjectId(), 1);
        String loginName = SecurityUserHolder.getUserName();
        checkPoint.setQrNo(qrNo);
        checkPoint = setEntityUserIdName(loginName,checkPoint.getProjectId(), checkPoint);
        checkPoint.setPinYinInitials(PinYinUtils.getFirstSpell(checkPoint.getPointName()).toUpperCase());
        int num = checkPointDao.saveCheckPoint(checkPoint);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_ADD_CHECK_POINT, loginName, checkPoint, null,
                checkPoint.getProjectId(), Constants.SOURCE_PROJECT);
        return asseSaveData(num, checkPoint);
    }

    /**
     *
     * 功能描述:修改巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @Override
    public Data modifyCheckPoint(String json){
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        if (StringUtils.isNotEmpty(checkPoint.getPointName())){
            checkPoint.setPinYinInitials(PinYinUtils.getFirstSpell(checkPoint.getPointName()).toUpperCase());
        }
        checkPoint = setEntityUserIdName(SecurityUserHolder.getUserName(), checkPoint.getProjectId(), checkPoint);
        CheckPoint oldCheckPoint = new CheckPoint();
        oldCheckPoint.setId(checkPoint.getId());
        oldCheckPoint = checkPointDao.findCheckPoint(oldCheckPoint);

        //判断修改的信息里,二维码与老的二维码是否不相同
        if (StringUtils.isNotEmpty(checkPoint.getQrNo()) && !oldCheckPoint.getQrNo().equals(checkPoint.getQrNo())){
            CheckPoint delCheckPoint = new CheckPoint();
            delCheckPoint.setQrNo(checkPoint.getQrNo());
            delCheckPoint = checkPointDao.findCheckPoint(delCheckPoint);//删除新二维码所在信息的记录
            CheckPoint tmpCp = new CheckPoint();
            tmpCp.setQrNo(oldCheckPoint.getQrNo());
            tmpCp.setBuildingId(0L);
            tmpCp.setBuildingFloorId(0L);
            tmpCp.setId(delCheckPoint.getId());
            checkPointDao.modifyCheckPoint(tmpCp);
        }

        if (oldCheckPoint.getBuildingId() == null || oldCheckPoint.getBuildingId() == 0){//如果是第一次创建巡检点时添加创建信息
            checkPoint = setEntityUserIdName(SecurityUserHolder.getUserName(), checkPoint.getProjectId(), checkPoint);
            checkPoint.setCreateTime(new Date());
        }
        int num = checkPointDao.modifyCheckPoint(checkPoint);
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_CHECK_POINT, SecurityUserHolder.getUserName(), checkPoint, oldCheckPoint,
                checkPoint.getProjectId(), Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     *
     * 功能描述:删除巡检点
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/19 13:54
     */
    @Override
    public Data delCheckPoint(String json){
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        int num = checkPointDao.modifyCheckPoint(checkPoint);
        //删除巡检点下的设备
        Device device = new Device();
        device.setByCheckPointId(checkPoint.getId());
        device.setByCheckPointIds(checkPoint.getIds());
        device.setStatus(99);
        deviceDao.modifyDevice(device);
        String checkPointJson = JSONObject.toJSONString(device);
        if (StringUtils.isNotEmpty(ConstantsDevice.IS_FLOOR_POINT) && "true".equals(ConstantsDevice.IS_FLOOR_POINT)){
            HttpUtils.httpGet(ConstantsDevice.DEL_FLOOR_POINT_URL, JSONObject.toJSONString(device), "json");
        }
        checkPointJson = checkPointJson.replaceAll("byCheckPointIds","checkPointIds");
        checkPointJson = checkPointJson.replaceAll("byCheckPointId","checkPointId");
        HttpUtils.httpGet(ConstantsDevice.DEL_TASK_CHECK_POINT, checkPointJson, "json");
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_DEL_CHECK_POINT, SecurityUserHolder.getUserName(), checkPoint, null,
                checkPoint.getProjectId(), Constants.SOURCE_PROJECT);
        return asseData(num);
    }

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @Override
    public Data findCheckPointAndDeviceReport(String json){
        PointDeviceReport pointDeviceReport = JSONObject.parseObject(json, PointDeviceReport.class);
        pointDeviceReport = checkPointDao.findCheckPointAndDeviceReport(pointDeviceReport);
        return asseData(pointDeviceReport);
    }

    /**
     * @Description 查询app首页设备信息
     *
     * @param json
     * @author wangzhiwen
     * @date 2021/3/25 14:23
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject findUserProjectDeviceData(String json){
        PointDeviceReport pointDeviceReport = JSONObject.parseObject(json, PointDeviceReport.class);
        pointDeviceReport = checkPointDao.findCheckPointAndDeviceReport(pointDeviceReport);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("modelIndex",13);
        if (pointDeviceReport != null) {
            jsonObject.put("deviceTotalNum", pointDeviceReport.getDeviceTotalNum());
            jsonObject.put("deviceNormalNum", pointDeviceReport.getDeviceNormalNum());
            jsonObject.put("deviceExceptionNum", pointDeviceReport.getDeviceExceptionNum());
        }else{
            jsonObject.put("deviceTotalNum", 0);
            jsonObject.put("deviceNormalNum", 0);
            jsonObject.put("deviceExceptionNum", 0);
        }
        return jsonObject;
    }

    /**
     *
     * 功能描述:查询巡检点与设备汇总信息按建筑物分组
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @Override
    public Data findCheckPointAndDeviceReportGroupBuilding(String json){
        PointDeviceReport pointDeviceReport = JSONObject.parseObject(json, PointDeviceReport.class);
        List<PointDeviceReport> list = checkPointDao.findCheckPointAndDeviceReportGroupBuilding(pointDeviceReport);
        return asseData(list);
    }

    /**
     *
     * 功能描述:下载巡检点设备列表
     *
     * @param json
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019/8/7 9:37
     */
    @Override
    public void downloadPointDeviceExcel(HttpServletResponse resp, String json){
        JSONObject jsonObject = JSONObject.parseObject(json);
        //List<PointDevice> list = JSONArray.parseArray(jsonObject.getString("list"), PointDevice.class);
        PointDevice pointDevice = JSONObject.parseObject(json, PointDevice.class);
        /*if (CollectionUtils.isNotEmpty(list)) {
            Long[] ids = new Long[list.size()];
            for (int i = 0; i < list.size();i++){
                ids[i] = list.get(i).getId();
            }
            pointDevice.setIds(ids);
        }*/
        if(pointDevice.getOrderCols() == null){
            String[] orderCols = {"createTime"};
            pointDevice.setOrderCols(orderCols);
            pointDevice.setOrderDesc(true);
        }

        List<DownloadPointDevice> list = checkPointDao.findDownloadPointDeviceList(pointDevice);
        if (CollectionUtils.isEmpty(list)){
            return ;
        }

        Long projectId = jsonObject.getLong("projectId");
        //从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");
        jsonObject.put("title", projectName + "-设备管理表");
        String[] keys = {"rowNum", "pointName", "qrNo", "deviceSysName","deviceQrNo","num", "deviceStatus","buildingName","buildingFloorName","pointLocation",
                "companyName", "orgName","brand", "model", "spec","expiryDateStr","sendModifyTimeStr","manageRegion","memo","lastModifyTimeDesc","projectName"};
        /*String[] heads = {"序号","巡查点名称","巡查码","设备名称","设备码","设备数量","设备状态","建筑物","楼层","具体位置",
                "公司名称","部门","品牌","型号","规格参数","有效期","送修期","管理区域","备注","最近巡查时间","所属项目"};*/
        ExcelUtils.createAndDownloadExcelNotStyle(resp, list, keys, ConstantsDevice.CHECK_POINT_DEVICE_MODEL_FILE_PATH,
                3, null, jsonObject, "1:0");
    }

    /**
     *
     * 功能描述: 上传设备excel表格
     *
     * @param file
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/5 9:29
     */
    @Override
    public Data uploadPointDeviceExcel(String json, MultipartFile file){
        PointDevice pointDevice = JSONObject.parseObject(json, PointDevice.class);
        Long projectId = pointDevice.getProjectId();
        //从缓存中取出项目对象
        String projectName = CacheUtils.getCacheValueByTypeAndId(Constants.PROJECT_CACHE_KEY, projectId, "projectName");

        //解析表格，设备系统名称＝表格中的设备名称
        String[] keys = {"rowNum", "pointName", "qrNo", "deviceSysName","deviceQrNo","num", "deviceStatus","buildingName","buildingFloorName","pointLocation",
                "companyName", "orgName","brand", "model", "spec","expiryDateStr","sendModifyTimeStr","manageRegion","memo","statusUpdateTime","projectName"};
        //解析表格
        List<PointDevice> list = ExcelUtils.readyExcel(file, 3,0,0, keys, PointDevice.class);

        if (CollectionUtils.isNotEmpty(list)){
            StringBuilder errProjects= new StringBuilder();//错误的项目名称
            StringBuilder buildingNames = new StringBuilder();//建筑物与楼层信息列表
            StringBuilder companyNames = new StringBuilder();//公司与部门列表
            StringBuilder deviceSysNames = new StringBuilder();//公司与部门列表
            StringBuilder quNos = new StringBuilder();//二维码列表
            String orgName;
            String buildingName;
            String deviceSysName;
            String temProjectName;
            List<PointDevice> nullList = new ArrayList<>();
            String projectIdStr = ",'" + projectId + "' projectId ";
            for (PointDevice pd:list){//列表信息检查与组装检查条件
                //判断是否为空
                if (StringUtils.isEmpty(pd.getCompanyName()) || StringUtils.isEmpty(pd.getOrgName()) ||
                        StringUtils.isEmpty(pd.getBuildingName()) || StringUtils.isEmpty(pd.getBuildingFloorName()) ||
                        StringUtils.isEmpty(pd.getDeviceSysName()) || StringUtils.isEmpty(pd.getProjectName()) ||
                        null == pd.getNum() || 0 == pd.getNum()|| StringUtils.isEmpty(pd.getPointName()) ||
                        StringUtils.isEmpty(pd.getQrNo())){
                    nullList.add(pd);
                    continue;
                }
                //解析有郊期
                String[] tims;
                if (StringUtils.isNotEmpty(pd.getExpiryDateStr()) && pd.getExpiryDateStr().indexOf("至") != -1) {
                    tims = pd.getExpiryDateStr().split("至");
                    pd.setExpiryDate(DateUtils.strToY_M_D(tims[0]));
                    pd.setExpiryDateEnd(DateUtils.strToY_M_D(tims[1]));
                }
                //解析送修时间
                if (StringUtils.isNotEmpty(pd.getSendModifyTimeStr()) && pd.getSendModifyTimeStr().indexOf("至") != -1) {
                    tims = pd.getSendModifyTimeStr().split("至");
                    pd.setSendModifyTime(DateUtils.strToY_M_D(tims[0]));
                    pd.setSendModifyTimeEnd(DateUtils.strToY_M_D(tims[1]));
                }

                temProjectName = pd.getProjectName();
                pd.setProjectId(projectId);

                orgName = " SELECT '" + pd.getCompanyName() + "' coName, '" + pd.getOrgName() + "' orgName " + projectIdStr + " UNION ";
                if (companyNames.indexOf(orgName) == -1) {//组装公司部门查询条件
                    companyNames.append(orgName);
                }
                buildingName = " SELECT '" + pd.getBuildingName() + "' buildingName, '" + pd.getBuildingFloorName() + "' buildingFloorName " + projectIdStr + " UNION ";
                if (buildingNames.indexOf(buildingName) == -1 ) {//组装建筑物楼层查询条件
                    buildingNames.append(buildingName);
                }

                deviceSysName = " SELECT '" + pd.getDeviceSysName() + "' device_sys_name UNION ";
                if (deviceSysNames.indexOf(deviceSysName) == -1 ) {//设备系统名称
                    deviceSysNames.append(deviceSysName);
                }

                if (StringUtils.isNotEmpty(pd.getQrNo()) && quNos.indexOf(pd.getQrNo()) == -1){
                    quNos.append(" SELECT '" + pd.getQrNo() + "' qrNo " + projectIdStr + " UNION ");
                }
                if (StringUtils.isNotEmpty(pd.getDeviceQrNo()) && quNos.indexOf(pd.getDeviceQrNo()) == -1){
                    quNos.append(" SELECT '" + pd.getDeviceQrNo() + "' qrNo " + projectIdStr + " UNION ");
                }

                if (!projectName.equals(temProjectName) && errProjects.indexOf(temProjectName) == -1){//判断项目名称是否与当前项目名相同
                    errProjects.append(temProjectName + "<br/>");
                }
            }

            if (CollectionUtils.isNotEmpty(nullList)){//验证信息是否有为空
                return asseData(ServiceErrCode.REQ_ERR.getCode(), nullList, ConstantsDeviceMsg.UPLOAD_PARAM_NULL_FAIL);
            }

            //如果有与当前项目不同的项目名称，则返回错误信息与不同的项目名称
            if (StringUtils.isNotEmpty(errProjects.toString())){
                return asseData(ServiceErrCode.REQ_ERR.getCode(), errProjects.toString(), ConstantsDeviceMsg.UPLOAD_PROJECT_NAME_FAIL);
            }

            Data data;
            //判断输入的二维码是否正确
            if (quNos.length() > 0) {
                data = checkQrNoList(quNos.toString());
                if (data.getStatus() != Constants.SUCCESS_CODE) {
                    return data;
                }
            }

            //判断设备系统信息否正确
            Map<String,DeviceType> deviceTypeMap = new HashMap<>();
            data = checkDeviceSysNames(deviceSysNames.toString(), deviceTypeMap);
            if (data.getStatus() != Constants.SUCCESS_CODE){
                return data;
            }

            //判断公司部门是否正确
            Map<String,Organization> orgMap = new HashMap<>();
            data = checkOrgNames(companyNames.toString(), orgMap);
            if (data.getStatus() != Constants.SUCCESS_CODE){
                return data;
            }

            //判断建筑物与楼层是否正确
            Map<String,Building> buildingMap = new HashMap<>();
            data = checkBuildingNames(buildingNames.toString(), buildingMap);
            if (data.getStatus() != Constants.SUCCESS_CODE){
                return data;
            }

            //保存信息
            data = saveDevicePointList(projectId, list, deviceTypeMap, orgMap, buildingMap);
            return data;
        }
        return Data.isSuccess();
    }
    /**
     *
     * 功能描述: 判断二维码是否正确
     *
     * @param quNos
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 17:51
     */
    private Data checkQrNoList(String quNos){
        quNos = quNos.substring(0, quNos.length() - 6);
        List<QrNo> list = qrNoDao.findQrNoListBySql(quNos);
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, ConstantsDeviceMsg.UPLOAD_QR_NO_FAIL);
        }
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述:判断设备系统是否正确
     *
     * @param deviceSysNames
     * @param deviceTypeMap
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 15:55
     */
    private Data checkDeviceSysNames(String deviceSysNames, Map<String,DeviceType> deviceTypeMap){
        deviceSysNames = deviceSysNames.substring(0, deviceSysNames.length() - 6);
        List<DeviceType> deviceTypeList = deviceTypeDao.findDeviceSysList(deviceSysNames);
        List<DeviceType> list = new ArrayList<>();
        String deviceSysName;
        for (DeviceType deviceType : deviceTypeList){
            deviceSysName = deviceType.getDeviceSysName() ;
            if (deviceType.getId() == null){//判断公司名称与部门名称是否为空
                list.add(deviceType);
            }else {
                if (!deviceTypeMap.containsKey(deviceSysName)) {
                    deviceTypeMap.put(deviceSysName, deviceType);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, ConstantsDeviceMsg.UPLOAD_DEVICE_TYPE_NAME_FAIL);
        }
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述:判断公司部门是否正确信息
     *
     * @param companyNames
     * @param orgMap
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 15:55
     */
    private Data checkOrgNames(String companyNames, Map<String,Organization> orgMap){
        companyNames = companyNames.substring(0, companyNames.length() - 6);
        List<Organization> orgList = getObjListByUrl(ConstantsDevice.FIND_ORG_LIST_BY_SQL, "{\"sql\":\"" + companyNames + "\"}", Organization.class);
        List<Organization> list = new ArrayList<>();
        String key;
        for (Organization org : orgList){
            if (null == org.getId() || null == org.getId()){//判断公司名称与部门名称是否为空
                list.add(org);
            }else {
                key = org.getOrgName() + "_" + org.getOrgDepName();
                if (!orgMap.containsKey(key)) {
                    orgMap.put(key, org);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, ConstantsDeviceMsg.UPLOAD_CO_ORG_NAME_FAIL);
        }
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述:判断建筑物与楼层是否正确信息
     *
     * @param buildingNames
     * @param buildingMap
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 15:55
     */
    private Data checkBuildingNames(String buildingNames, Map<String,Building> buildingMap){
        buildingNames = buildingNames.substring(0, buildingNames.length() - 6);
        List<Building> buildingList = getObjListByUrl(ConstantsDevice.FIND_BUILDING_LIST_BY_SQL, "{\"sql\":\"" + buildingNames + "\"}", Building.class);
        List<Building> list = new ArrayList<>();
        String key;
        for (Building building : buildingList){
            if (null == building.getFloorId() || null == building.getId()){//判断建筑物与楼层名称是否为空
                list.add(building);
            }else {
                key = building.getBuildingName() + "_" + building.getFloorName();
                if (!buildingMap.containsKey(key)) {
                    buildingMap.put(key, building);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)){
            return asseData(Constants.FAIL_CODE, list, ConstantsDeviceMsg.UPLOAD_BUILDING_FLOOR_FAIL);
        }
        return Data.isSuccess();
    }

    /**
     *
     * 功能描述:
     *
     * @param projectId 项目id
     * @param list 表格数据列表
     * @param orgMap 公司部门对应的信息map
     * @param buildingMap 建筑物与楼层对应的信息map
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/8 16:33
     */
    private Data saveDevicePointList(Long projectId, List<PointDevice> list, Map<String,DeviceType> deviceTypeMap, Map<String,Organization> orgMap,
                                     Map<String,Building> buildingMap){
        List<PointDevice> newPointList = new ArrayList<>();
        List<PointDevice> modifyPointList = new ArrayList<>();

        List<PointDevice> newDeviceList = new ArrayList<>();
        List<PointDevice> modifyDeviceList = new ArrayList<>();
        String buildingKey;
        String orgKey;
        Organization org;
        Building building;
        String userName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(userName);
        for (PointDevice pd:list) {//列表信息检查与组装检查条件
            //为列表填充id值
            pd.setProjectId(projectId);
            buildingKey = pd.getBuildingName() + "_" + pd.getBuildingFloorName();
            building = buildingMap.get(buildingKey);//建筑物与楼层id设置
            pd.setBuildingId(building.getId());
            pd.setBuildingFloorId(building.getFloorId());
            orgKey = pd.getCompanyName() + "_" + pd.getOrgName();
            org = orgMap.get(orgKey);////公司与部门id设置
            pd.setCompanyId(org.getId());
            pd.setOrgId(org.getDepId());
            pd.setDeviceSysId(deviceTypeMap.get(pd.getDeviceSysName()).getId());//设备系统类型id设置
            pd.setPinYinInitials(PinYinUtils.getFirstSpell(pd.getPointName()).toUpperCase());
            pd.setCreateUserId(userId);
            pd.setCreateUserName(getOrgUserName(userId, projectId));

            if (StringUtils.isNotEmpty(pd.getQrNo())){//判断是否是修改巡检点，二维码为空是新增
                modifyPointList.add(pd);
            }else {
                newPointList.add(pd);
            }
            if (StringUtils.isNotEmpty(pd.getDeviceQrNo())){//判断是否是修改设备，二维码为空是新增
                modifyDeviceList.add(pd);
            }else {
                newDeviceList.add(pd);
            }
        }

        StringBuilder resStr = new StringBuilder();
        //新增巡检点
        if (CollectionUtils.isNotEmpty(newPointList)){
            newPointList = pointDeviceListReplaceAll(newPointList);//去除重复值
            newPointList = setPointDeviceListQrNo(newPointList, projectId,1);
            int num = checkPointDao.saveCheckPointList(newPointList);
            resStr.append("\"savePoint\":" + num + ", ");
        }
        //修改巡检点
        if (CollectionUtils.isNotEmpty(modifyPointList)){
            modifyPointList = pointDeviceListReplaceAll(modifyPointList);//去除重复值
            int num = checkPointDao.modifyCheckPointList(modifyPointList);
            resStr.append("\"modifyPoint\":" + num + ", ");
        }

        List<PointDevice> pointIdList = new ArrayList<>();
        //查询巡检点的id
        if (CollectionUtils.isNotEmpty(modifyPointList)) {
            pointIdList = checkPointDao.findPointListByList(modifyPointList);
        }
        pointIdList.addAll(newPointList);
        //把新增的巡检点id，添加到
        newDeviceList = setListPointId(newDeviceList, pointIdList);
        modifyDeviceList = setListPointId(modifyDeviceList, pointIdList);
        //新增设备
        if (CollectionUtils.isNotEmpty(newDeviceList)){
            newDeviceList = setPointDeviceListQrNo(newDeviceList, projectId,2);
            int num = checkPointDao.saveDeviceList(newDeviceList);
            resStr.append("\"saveDevice\":" + num + ", ");
        }
        //修改设备
        if (CollectionUtils.isNotEmpty(modifyDeviceList)){
            int num = checkPointDao.modifyDeviceList(modifyDeviceList);
            resStr.append("\"modifyDevice\":" + num + ", ");
        }
        securityLogService.saveSecurityLog(ConstantsDevice.SECURITY_LOG_TYPE_MODIFY_CHECK_POINT, SecurityUserHolder.getUserName(),
                "批量：新增了 " + newPointList.size() + " 个巡检点；修改了 " + modifyPointList.size() + " 个巡检点；新增了 " + newDeviceList.size() +
                        " 台设备；修改了 " + modifyDeviceList.size() +  " 台设备！",projectId, Constants.SOURCE_PROJECT);
        return asseData(Constants.SUCCESS_CODE,resStr.toString());
    }

    /**
     *
     * 功能描述:去重复巡检点信息列表
     *
     * @param list
     * @return: java.util.List<com.xjt.cloud.device.core.entity.PointDevice>
     * @auther: wangzhiwen
     * @date: 2019/8/16 11:51
     */
    private List<PointDevice> pointDeviceListReplaceAll(List<PointDevice> list){
        List<PointDevice> pointDeviceList = new ArrayList<>();
        String qrNo;
        String pointName;
        String pointLocation;
        PointDevice pointDevice;
        PointDevice pd;
        boolean notExist;
        for (int i = 0;i < list.size();i++){
            pointDevice = list.get(i);
            notExist = true;
            qrNo = pointDevice.getQrNo();
            pointName = pointDevice.getPointName();
            pointLocation = pointDevice.getPointLocation();
            for (int n = 0;n < pointDeviceList.size();n ++){
                pd = pointDeviceList.get(n);
                if (StringUtils.isNotEmpty(qrNo)) {//判断设备列表中的巡检点二维码是否存在
                    if (qrNo.equals(pd.getQrNo())) {
                        notExist = false;
                        break;
                    }
                }else {
                    //以巡检点的名称与具体位置判断是否是同一个巡检点
                    if (pointName.equals(pd.getPointName()) && pointLocation.equals(pd.getPointLocation())){
                        notExist = false;
                        break;
                    }
                }
            }
            if (notExist){
                pointDeviceList.add(pointDevice);
            }
        }
        return pointDeviceList;
    }

    /**
     *
     * 功能描述:为新增信息添加二维码
     *
     * @param list
     * @param projectId
     * @param type 类型 1 巡检点码 2 设备码
     * @return: java.util.List<com.xjt.cloud.device.core.entity.PointDevice>
     * @auther: wangzhiwen
     * @date: 2019/8/8 18:39
     */
    private List<PointDevice> setPointDeviceListQrNo(List<PointDevice> list, Long projectId, int type){
        QrNo qrNo = new QrNo();
        qrNo.setProjectId(projectId);
        qrNo.setNum(list.size());
        qrNo.setType(type);
        List<QrNo> qrNoList = qrNoService.generationQrNo(qrNo);
        String longName = SecurityUserHolder.getUserName();
        Long userId = getLoginUserId(longName);
        String userName = getOrgUserName(userId, projectId);
        for (int i = 0;i < list.size();i++){
            PointDevice pointDevice = list.get(i);
            qrNo = qrNoList.get(i);
            pointDevice.setCreateUserId(userId);
            pointDevice.setCreateUserName(userName);
            pointDevice.setQrNo(qrNo.getQrNo());
        }
        return list;
    }
    /**
     *
     * 功能描述:把新增的巡检点的id添加到设备中
     *
     * @param list 设备列表
     * @param pointList 巡检点列表
     * @return: java.util.List<com.xjt.cloud.device.core.entity.PointDevice>
     * @auther: wangzhiwen
     * @date: 2019/8/8 18:44
     */
    private List<PointDevice> setListPointId(List<PointDevice> list, List<PointDevice> pointList){
        String qrNo;
        String pointName;
        String pointLocation;
        for (PointDevice pointDevice : list){
            qrNo = pointDevice.getQrNo();
            pointName = pointDevice.getPointName();
            pointLocation = pointDevice.getPointLocation();
            for (PointDevice pd : pointList){
                if (StringUtils.isNotEmpty(qrNo)) {//判断设备列表中的巡检点二维码是否存在
                    if (qrNo.equals(pd.getQrNo())) {
                        pointDevice.setId(pd.getId());
                        break;
                    }
                }else {
                    //以巡检点的名称与具体位置判断是否是同一个巡检点
                    if (pointName.equals(pd.getPointName()) && pointLocation.equals(pd.getPointLocation())){
                        pointDevice.setId(pd.getId());
                        break;
                    }
                }
            }
        }
        return list;
    }

    /**
     *
     * 功能描述:查询未关联水压设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @Override
    public Data findCheckPointNotBoundIot(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        device.setIotId(0L);
        List<CheckPoint> list = checkPointDao.findCheckPointBoundIot(device);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询已关联物联设备的建物物楼层列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @Override
    public Data findBuildingFloorBoundIot(String json){
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        List<Building> list = checkPointDao.findBuildingFloorBoundIot(checkPoint);
        return asseData(list);
    }

    /**
     *
     * 功能描述:查询关联水压设备的巡检点列表
     *
     * @param json
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/15 9:45
     */
    @Override
    public Data findCheckPointBoundIot(String json){
        Device device = JSONObject.parseObject(json, Device.class);
        List<CheckPoint> list = checkPointDao.findCheckPointBoundIot(device);
        return asseData(list);
    }

    /**@MethodName: findProjectCheckPointCount
     * @Description: 查询当前项目巡检点数量
     * @Param: [json]
     * @Return: com.xjt.cloud.commons.utils.Data
     * @Author: zhangZaiFa
     * @Date:2020/3/19 9:59
     **/
    @Override
    public Data findProjectCheckPointCount(String json) {
        CheckPoint checkPoint = JSONObject.parseObject(json, CheckPoint.class);
        Integer count = checkPointDao.findCheckPointListCount(checkPoint);
        return asseData(count);
    }
}
