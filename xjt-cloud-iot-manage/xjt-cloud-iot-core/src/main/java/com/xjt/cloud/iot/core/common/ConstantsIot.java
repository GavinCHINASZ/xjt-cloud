package com.xjt.cloud.iot.core.common;


import com.xjt.cloud.commons.utils.PropertyUtils;

/**
 * 客户端资源文件信息与常量类
 *
 * @author wangzhiwen
 * @date 2019/4/25 10:43
 */
public interface ConstantsIot {
    Long ONE_DAY_TIME = 24 * 60 * 60 * 1000L;
    Long ONE_DAY_SECONDS = 24 * 60 * 60 * 1000L;

    // 物联网设备默认IP
    String iot_url = PropertyUtils.getProperty("iot.url");
    // 物联网设备默认端口
    String iot_port = PropertyUtils.getProperty("iot.port");
    // 物联网水压水浸设备默认上传时间心跳发送间隔
    String iot_water_heartbeat = PropertyUtils.getProperty("iot.water.heartbeat");
    // 物联网水压设备默认告警方式
    String iot_water_alarmMode = PropertyUtils.getProperty("iot.water.alarmMode");
    // 物联水压水浸设备默认采样间隔
    String iot_water_dataInterval = PropertyUtils.getProperty("iot.water.dataInterval");
    // 物联网水压设备默认波动告警值
    String iot_water_waveAlarmValue = PropertyUtils.getProperty("iot.water.waveAlarmValue");
    // 物联网液位设备默认波动告警值
    String iot_water_Liquid_waveAlarmValue = PropertyUtils.getProperty("iot.water.Liquid.waveAlarmValue");
    // 水压设备
    String WATER_GAGE = "WATERGAGE";
    // 电气火灾设备
    String ELECTRICAL_FIRE = "ELECTRICALFIRE";
    // 烟感设备设备
    String SMOKE_DEVICE = "SMOKEDEVICE";
    // 水浸设备
    String WATER_IMMERSION = "WATERIMMERSION";
    // 消火栓
    String HYDRANT = "HYDRANT";
    // 周期任务处理接口
    String EDWARD_STATUS_HANDLE_URL= PropertyUtils.getProperty("edward.status.handle.url");

    // 水压设备报表模板文件路径
    String REPORT_WATER_DEVICE_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.device.model.file.path");
    String REPORT_WATER_HYDRANT_DEVICE_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.hydrant.device.model.file.path");
    String REPORT_WATER_SOAKING_DEVICE_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.soaking.device.model.file.path");
    // 水压设备事件报表模板文件路径
    String REPORT_WATER_DEVICE_EVENT_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.device.event.model.file.path");
    String REPORT_WATER_HYDRANT_DEVICE_EVENT_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.hydrant.device.event.model.file.path");
    String REPORT_WATER_SOAKING_DEVICE_EVENT_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.soaking.device.event.model.file.path");
    // 水压设备单个设备事件报表模板文件路径
    String REPORT_WATER_DEVICE_DEVICE_EVENT_MODEL_FILE_PATH = PropertyUtils.getProperty("report.water.device.device.event.model.file.path");
    // 极早期设备
    String VESA = "VESA";
    // VESA设备事件报表模板文件路径
    String REPORT_VESA_DEVICE_EVENT_MODEL_FILE_PATH = PropertyUtils.getProperty("report.vesa.device.event.model.file.path");

    // 火警主机
    String FIRE_ALARM = "FIREALARM";
    // 火警主机事件导出模板文件路径
    String REPORT_FIRE_DEVICE_EVENT_MODEL_FILE_PATH = PropertyUtils.getProperty("report.fire.device.event.model.file.path");
    // 火警主机类型
    String FIRE_ALARM_TYPE = PropertyUtils.getProperty("iot.fire.alarm.type");
    // 电气火灾设备列表导出模板文件路径
    String ELECTRICAL_FIRE_DEVICE_LIST_MODEL_FIRE_PATH = PropertyUtils.getProperty("electrical.fire.device.list");
    // 电气火灾设备事件列表导出模板文件路径
    String ELECTRICAL_FIRE_DEVICE_EVENT_LIST_MODEL_FIRE_PATH = PropertyUtils.getProperty("electrical.fire.device.event.list");
    // 日志key
    String SECURITY_LOG_TYPE_ADD_WATER_DEVICE = "SECURITY_LOG_TYPE_ADD_WATER_DEVICE";//添加水压设备
    String SECURITY_LOG_TYPE_DEL_WATER_DEVICE = "SECURITY_LOG_TYPE_DEL_WATER_DEVICE";//删除水压设备
    String SECURITY_LOG_TYPE_MODIFY_WATER_DEVICE = "SECURITY_LOG_TYPE_MODIFY_WATER_DEVICE";//修改水压设备

    // VESA日志key
    String SECURITY_LOG_TYPE_ADD_VESA_DEVICE = "SECURITY_LOG_TYPE_ADD_VESA_DEVICE";//添加VESA设备
    String SECURITY_LOG_TYPE_DEL_VESA_DEVICE = "SECURITY_LOG_TYPE_DEL_VESA_DEVICE";//删除VESA设备
    String SECURITY_LOG_TYPE_MODIFY_VESA_DEVICE = "SECURITY_LOG_TYPE_MODIFY_VESA_DEVICE";//修改VESA设备

    String OFF_LINE_TIME = "OFF_LINE_TIME";//数据词典离线时间key
    String OFF_LINE_TIME_IOT_WATER = "OFF_LINE_TIME_IOT_WATER";//数据词典离线时间 水压设备离线时间key
    String OFF_LINE_TIME_IOT_VESA = "OFF_LINE_TIME_IOT_VESA";//数据词典离线时间 极早期设备离线时间key
    String OFF_LINE_TIME_IOT_SMOKE = "OFF_LINE_TIME_IOT_SMOKE";// 数据词典离线时间 烟感设备离线时间key
    String OFF_LINE_TIME_IOT_LINKAGE = "OFF_LINE_TIME_IOT_LINKAGE";// 数据词典离线时间 声光设备离线时间key
    String OFF_LINE_TIME_IOT_FIRE_EYE_DEVICE = "OFF_LINE_TIME_IOT_FIRE_EYE_DEVICE";// 数据词典离线时间 火眼设备离线时间key

    String AIR_SAMPLING_DEVICE_LIST = PropertyUtils.getProperty("air.sampling.device.list");//下载空气采样设备信息xls表格路径配置
    String AIR_SAMPLING_DEVICE_RECORD_LIST = PropertyUtils.getProperty("air.sampling.device.record.list");//下载空气采样单个设备记录信息xls表格路径配置
    String AIR_SAMPLING_DEVICES_RECORD_LIST = PropertyUtils.getProperty("air.sampling.devices.record.list");//下载空气采样多个设备记录信息xls表格路径配置
    String AIR_SAMPLING_EVENT_LIST = PropertyUtils.getProperty("air.sampling.event.list");//下载空气采样事件信息xls表格路径配置
    //空气采样设备缓存过期时间
    Long AIR_SAMPLING_EXPIRES_TIME = 24 * 60 * 60L;

    ////////////////////////// 监测信息提醒  ////////////////////////////
    //水压电量超低提醒
    String WATER_GAGE_LACK_OF_ELECTRICITY = "waterGage_lackOf_electricity";
    //水压超高超低提醒
    String WATER_GAGE_PRESSURE_ABNORMALITY_EVENT = "waterGage_pressure_abnormality_event";
    //水压离线
    String WATER_GAGE_OFFLINE_EVENT = "waterGage_offline_event";
    //水浸电量超低提醒
    String WATER_IMMERSION_LACK_OF_ELECTRICITY = "waterImmersion_lackOf_electricity";
    //水浸漏水
    String WATER_IMMERSION_OVERFLOW_EVENT = "waterImmersion_overflow_event";
    //水浸离线
    String WATER_IMMERSION_OFFLINE_EVENT = "waterImmersion_offline_event";

    //消火栓事件类型
    //离线 事件
    String HYDRANT_OFFLINE_EVENT = "hydrant_offline_event";
    //电量低事件
    String HYDRANTMONITOR_LACKOF_ELECTRICITY = "hydrantMonitor_lackOf_electricity";
    //超高超低事件
    String HYDRANTMONITOR_PRESSURE_ABNORMALITY_EVENT = "hydrantMonitor_pressure_abnormality_event";
    //消火栓故障事件
    String HYDRANT_FAULT_EVENT = "hydrant_fault_event";

    String WATER_TANK_MSG = "，当前液位为";
    String WATER_BEGIN_MSG = "，当前压力值为";
    String WATER_SUPPLY_MSG = "，当前供水压力值为";
    String WATER_SYS_MSG = "，当前系统压力值为";
    String WATER_OFFLINE_MSG = "，已离线，请前往【水压监测】查看详情";
    String WATER_IMMERSION_OFFLINE_MSG = "，已离线，请前往【智能水浸】查看详情";
    String WATER_ONLINE_MSG = "，已恢复在线，请前往【水压监测】查看详情";
    String SUPER_HIGH_MSG = "，已超出正常压力范围，请前往【水压监测】查看详情";
    String ULTRALOW_MSG = "，已低于正常压力范围，请前往【水压监测】查看详情";
    String LOW_POWER_MSG = "，电量已低于30%，请及时更换设备电池";
    String WATER_LEAKAGE = "，发生漏水报警，请及时处理";
    String WATER_ONLINE_LEAKAGE = "，漏水已恢复，详情请前往【智能水浸】查看";
    String SEND_MSG_NUM_20 = "，今天已推送20条数据，请查看现场是否出现异常";
    String TO_HIT = "消火栓撞击提醒";
    String WATER_SOAKING_STATUS_TITLE = "水浸设备状态通知";
    String WATER_STATUS_TITLE = "水压设备状态通知";
    String FIRE_COCK_STATUS_TITLE = "消火栓设备状态通知";
    String HYDRANT_SUPER_HIGH_MSG = "，当前水压已超出正常范围，请及时处理";
    String HYDRANT_ULTRALOW_MSG = "，当前水压已低于正常范围，请及时处理";
    String HYDRANT_HIT_MSG = "，发生撞击报警，请及时处理";
    String HYDRANT_OPEN_COVER = "，发生开盖报警，请及时处理";
    String HYDRANT_ONLINE_MSG = "，已恢复在线，请前往【智能消火栓】查看";
    String HYDRANT_INTAKE = "，发生取水事件，请及时处理";
    String HYDRANT_LEAKAGE_FAULT = "，发生漏水传感器故障事件，请及时处理";
    String HYDRANT_HIT_FAULT = "，发生撞击传感器故障事件，请及时处理";
    // 水压监测提醒信息跳转路径
    String WATER_GAGE_MSG_URL = PropertyUtils.getProperty("water.gage.msg.url");
    // 水浸监测提醒信息跳转路径
    String WATER_LEACHING_MSG_URL = PropertyUtils.getProperty("water.leaching.msg.url");
    // 消火栓提醒信息跳转路径
    String HYDRANT_MSG_URL = PropertyUtils.getProperty("hydrant.msg.url");
    // 查询设备信息url
    String FIND_DEVICE_MODULE_CALLS = "find.device.module.calls";

    //VESA message config
    String vesaRoleSignListFireAlarm2 = "vesa_fireAlarm2_event";//火警2
    String vesaRoleSignListFireAlarm1 = "vesa_fireAlarm1_event";//火警1
    String vesaRoleSignListAction = "vesa_action_event";//行动
    String vesaRoleSignListAlarm = "vesa_alarm_event";//警告
    String vesaRoleSignListFault = "vesa_fault_event";//故障
    String vesaRoleSignListOffline = "vesa_offline_event";//离线
    // 权限数组
    //String[] vesaRoleSignList = {"vesa_manage_edit"};
    // VESA消息跳转路径
    String  VESA_MSG_URL = PropertyUtils.getProperty("vesa.msg.url");

    // 拓普索尔
    String TOPSAIL_DEVICE = "TOPSAIL";

    // 联动设备
    String LINKAGE_DEVICE = "LINKAGEDEVICE";
    String LINKAGE_DEVICE_REG_ID = "$$_LINKAGE_DEVICE_";

    // 联动设备
    // 给联动设备下发信息url     http://120.76.193.19:5081/netty/nettySendMsg
    String SEND_LINKAGE_DEVICE_MSG_URL = PropertyUtils.getProperty("send.linkage.device.msg.url");
    // 联动设备页面webSocket
    String SEND_LINKAGE_DEVICE_WEB_MSG_URL = PropertyUtils.getProperty("send.linkage.device.web.msg.url");

    //webSocket发送信息urls
    String WEB_SOCKET_SEND_MSG_URLS = PropertyUtils.getProperty("web.socket.send.msg.urls");
    // 友人设备各路通道16进制指令
    // 1通道开
    String LINKAGE_DEVICE_1_CHANNEL_OPEN_MSG = "11050000FF008EAA";
    // 2通道开
    String LINKAGE_DEVICE_2_CHANNEL_OPEN_MSG = "11050001FF00DF6A";
    // 3通道开
    String LINKAGE_DEVICE_3_CHANNEL_OPEN_MSG = "11050002FF002F6A";
    // 4通道开
    String LINKAGE_DEVICE_4_CHANNEL_OPEN_MSG = "11050003FF007EAA";

    // 1通道关
    String LINKAGE_DEVICE_1_CHANNEL_ON_MSG = "110500000000CF5A";
    // 2通道关
    String LINKAGE_DEVICE_2_CHANNEL_ON_MSG = "1105000100009E9A";
    // 3通道关
    String LINKAGE_DEVICE_3_CHANNEL_ON_MSG = "1105000200006E9A";
    // 4通道关
    String LINKAGE_DEVICE_4_CHANNEL_ON_MSG = "1105000300003F5A";

    //火眼
    String FIRE_EYE = "FIREEYE";
    // 火眼设备列表导出表格模板路径
    String DOWN_FIRE_EYE_DEVICE_LIST_PATH = PropertyUtils.getProperty("down.fire.eye.device.list.path");
    // 火眼事件列表导出表格模板路径
    String DOWN_FIRE_EYE_EVENT_LIST_PATH = PropertyUtils.getProperty("down.fire.eye.event.list.path");

    //火警主机是否发送离线事件
    String FIRE_ALARM_SEND_OFF_LINE_EVENT = PropertyUtils.getProperty("fire.alarm.send.off.line.event");
    //地铁抽检设备自动检测
    String FIRE_ALARM_METRO_DEVICE_AUTO_CHECK = PropertyUtils.getProperty("fire.alarm.metro.device.auto.check");
    //是否是地铁项目 1是，2不是
    String IS_METRO_PROJECT = PropertyUtils.getProperty("is.metro.project");

    // PC快照报表导出模版路径
    String PC_SNAPSHOT_REPORT_EXCEL_PATH = PropertyUtils.getProperty("pc.snapshot.report.excel.path");

    // 移动 智慧消防平台https://smartsensor.eastcmiot.com  AESkey
    String SMARTSENSOR_EASTCMIOT_COM_AES_KEY = PropertyUtils.getProperty("smartsensor.eastcmiot.com.aes.key");

    // 用户自定义token和OneNet第三方平台配置里的token一致
    String ONE_NET_TOKEN = PropertyUtils.getProperty("one.net.token");
    // aeskey和OneNet第三方平台配置里的aeskey一致
    String ONE_NET_AESKEY = PropertyUtils.getProperty("one.net.aeskey");
    // apiKey
    String NB_API_KEY = PropertyUtils.getProperty("nb.api.key");

    // 烟感列表 导出表格模板
    String SMOKE_LIST_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("smoke.list.excel.model.file.path");
    // 烟感批量导入表格模板
    String SMOKE_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("smoke.excel.model.file.path");
    // 烟感告警事件列表 导出表格模板
    String SMOKE_EVENT_LIST_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("smoke.event.list.excel.model.file.path");
    // 烟感 GS524N-C 属性名
    String GS524N_C_PROPERTY_NAME_FORTY_FOUR = PropertyUtils.getProperty("gs524n.c.property.name.forty.four");
    String GS524N_C_PROPERTY_NAME_FORTY_THREE = PropertyUtils.getProperty("gs524n.c.property.name.forty.three");
    String GS524N_C_PROPERTY_NAME_FORTY_ONE = PropertyUtils.getProperty("gs524n.c.property.name.forty.one");
    // 烟感支持的平台
    String FIND_SMOKE_CLOUD_KEY = "FIND_SMOKE_CLOUD_KEY";
    String FIND_SMOKE_CLOUD_VALUE = "FIND_SMOKE_CLOUD_VALUE";

    // 声光报警列表 导出表格模板
    String LINKAGE_DEVICE_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("linkage.device.excel.model.file.path");
    // 声光报警事件列表 导出表格模板
    String LINKAGE_EVENT_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("linkage.event.excel.model.file.path");

    // 查询 消息推送管理 数量url     http://120.76.193.19:5081/netty/nettySendMsg
    String FIND_MSG_PUSH_MANAGE_NUM_URL = PropertyUtils.getProperty("find.msg.push.manage.num.url");

    // 查询 成员名称  http://120.76.193.19:7082/project/orgUser/findUsersName
    String FIND_ORG_USER_NAME_URL = PropertyUtils.getProperty("find.org.user.name.url");

    ///////////////////// 监控物联网设备的相关配置 begin /////////////////////
    // 物联设备配置
    String IOT_DEVICE_CONFIG = "IOT_DEVICE_CONFIG";
    // 短信模板
    String MONITOR_PROJECT_EVENT = "91";
    // 邮件接收人
    String MAIL_RECEIVE = "MAIL_RECEIVE";
    // 短信接收人
    String PHONE_RECEIVE = "PHONE_RECEIVE";
    // 烟感单次离线的数量发邮件
    String SMOKE_DEVICE_OFF_LINE_NUM_MAIL = "SMOKE_DEVICE_OFF_LINE_NUM_MAIL";
    // 烟感单次离线的数量发短信
    String SMOKE_DEVICE_OFF_LINE_NUM_SMS = "SMOKE_DEVICE_OFF_LINE_NUM_SMS";
    // 用户自定义token和OneNet第三方平台配置里的token一致
    String ELECTRICAL_FIRE_ONE_NET_TOKEN = PropertyUtils.getProperty("electrical.fire.one.net.token");
    ///////////////////// 监控物联网设备的相关配置 end /////////////////////

    // 项目是否存在modbus
    String IS_EXIST_MODBUS = PropertyUtils.getPropertyNull("is.exist.modbus");

    // 巡检设备统计表 word生成路径
    String IOT_DEVICE_REPORT_WORD_FILE_URL = PropertyUtils.getProperty("iot.device.report.word.file.url");
    // 物联设备运营报表 导出表格模版路径
    String IOT_DEVICE_REPORT_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("iot.device.report.excel.model.file.path");
    // 物联设备运营报表 导出详情表格模版路径
    String IOT_DEVICE_REPORT_DETAILS_EXCEL_MODEL_FILE_PATH = PropertyUtils.getProperty("iot.device.report.details.excel.model.file.path");

    // 地铁 修改 班前防护 状态
    String METRO_PROTECT_UPDATE_PATH = PropertyUtils.getProperty("metro.protect.update.path");
    // 地铁 作业 综合监测
    String INTEGRATED_MONITORING = "INTEGRATED_MONITORING";
    // 根据设备编码请求设备的全部测点实时数据
    String DEVICE_CODE = "DEVICE_CODE";
    // 根据测点编码请求具体测点的实时数据
    String MEASURING_POINT_CODE = "MEASURING_POINT_CODE";
    String MONITORING_MEMO = "MONITORING_MEMO";
}
