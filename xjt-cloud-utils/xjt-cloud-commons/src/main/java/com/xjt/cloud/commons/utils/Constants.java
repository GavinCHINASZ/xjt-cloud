package com.xjt.cloud.commons.utils;

/**
* @ClassName: Constants 
* @Description:  工程公共常量
* @author wangzhiwen
* @date 2015-11-16 下午02:21:51 
*
 */
public interface Constants {
	/**字符集转编码*/
    String CHAR_SET="ISO-8859-1";
    String UTF_CHAR_SET="UTF-8";

    //成功状态
    int SUCCESS_CODE = 200;
    //没有数据状态
    int NOT_DATA_CODE = 220;
    //没有数据状态
    String NOT_DATA_MSG = "没有数据！";
    //失败状态
    int FAIL_CODE = 600;
    //不处理状态
    int DISCARD_CODE= 9;
    //缓存过期时间 单位秒
    int CACHE_CANCEL = 30 * 60;
    Long WEEK_CACHE_CANCEL = 7 * 24 * 60 * 60L;
    Long INIT_CACHE_CANCEL = 6 * 24 * 60 * 60L;
    Long CACHE_INIT_SPACE = 5 * 60 * 1000L;//缓存初使化时间间隔

    //十分钟过期时间
    int TEN_TIME_SECONDS = 10 * 60;
    //删除状态
    int DEL_STAUTS = 99;

    int SOURCE_SYS = 0;//系统级
    int SOURCE_CLOUD = 1;//平台级
    int SOURCE_PROJECT = 2;//项目级

    //获取用户信息权限接口
    String GET_USER_URL = PropertyUtils.getProperty("authorizationparam.user-url");
    String SECRET = PropertyUtils.getProperty("we.chat.secret");
    String APPID = PropertyUtils.getProperty("we.chat.app.id");

    /////////////////////////////////////////////////////////缓存key与url///////////////////////////////////////
    //数据词典缓存key
    String DICT_CACHE_KEY = "dict_cache_key";
    //数据词典初使化方法路径 dict.cache.init.url=http://127.0.0.1:7081/dict/dictCacheInit
    String DICT_CACHE_INIT_URL = "dict.cache.init.url" ;
    //设备系统数据 deviceSysCache=http://192.168.0.100:7083/device/type/deviceSysCacheInit
    String DEVICE_SYS_CACHE_KEY = "deviceSysCache";
    //项目数据 projectCacheKey=http://192.168.0.100:7082/project/projectCacheInit
    String PROJECT_CACHE_KEY = "projectCacheKey";
    //组织结构数据 orgCacheKey=http://192.168.0.100:7082/project/organization/orgCacheInit
    String ORG_CACHE_KEY = "orgCacheKey";
    //建筑物 buildingCacheKey=http://192.168.0.100:7082/project/building/buildingCacheInit
    String BUILDING_CACHE_KEY= "buildingCacheKey";
    //楼层 buildingFloorCacheKey=http://192.168.0.100:7082/project/building/buildingCacheInit
    String BUILDING_FLOOR_CACHE_KEY="buildingFloorCacheKey";
    //表格列配置信息缓存key　tableColConfigCacheKey=http://127.0.0.1:7081/table/col/config/findTableColConfig
    String TABLE_COL_CONFIG_CACHE_KEY="tableColConfigCacheKey";
    //员工缓存信息key orgUserCacheKey=http://192.168.0.100:7082/project/orgUser/orgUserCacheInit?json={"projectId":1,"userId":1}
    String ORG_USER_CACHE_KEY = "orgUserCacheKey";
    //员工项目权限key orgUserProjectPermissionCacheKey=http://192.168.0.100:7082/project/orgUser/orgUserPojectPermissionInit?json={"projectId":1,"userId":1}
    String ORG_USER_PROJECT_PERMISSION_CACHE_KEY = "orgUserProjectPermissionCacheKey";
    //项目信息级别缓存初使化 projectMsgLevelCacheKey=http://192.168.0.100:7082/project/projectMsgLevelCacheInit
    String PROJECT_MSG_LEVEL_CACHE_KEY = "projectMsgLevelCacheKey";

    //数据词典appId与项目对应关系缓存key
    String APP_ID_CONFIG = "APP_ID_CONFIG";
    //多线程推送信息线程最大数
    String MAXIMUM_POOL_SIZE = PropertyUtils.getProperty("maximum.pool.size");
    //福德花园接口地址
    String FORD_GARDEN_WEBSITE = PropertyUtils.getProperty("ford.garden.website");
    //福德花园项目id
    String FORD_GARDEN_PROJECTID = PropertyUtils.getProperty("ford.garden.projectId");
    //#权限与信息兼容版本
    String COMPATIBLE_VERSION = PropertyUtils.getPropertyNull("compatible.version");
}
