package com.xjt.cloud.netty.manage.common.utils;

import com.xjt.cloud.commons.utils.PropertyUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FuAnInfo
 * @Author dwt
 * @Date 2019-09-06 9:34
 * @Description： 赋安火警主机通信协议信息
 * @Version 1.0
 */
public class FuAnInfo {
    //功能属性
    private static final String FUNCTION_ATTRIBUTE = PropertyUtils.getProperty("fuan_function.attribute");
    //信息类型
    private static final String MESSAGE_TYPE = PropertyUtils.getProperty("fuan_message.type");
    //设备状态
    private static final String EQUIPMENT_STATUS = PropertyUtils.getProperty("fuan_equipment.status");
    //设备类型
    private static final String EQUIPMENT_TYPE = PropertyUtils.getProperty("fuan_equipment.type");
    //功能属性集合
    public static Map<String,String> functionAttriMap;
    //信息类型集合
    public static Map<String,String> messageTypeMap;
    //设备状态集合
    public static Map<String,String> equipmentStatusMap;
    //设备类型集合
    public static Map<String,String> equipmentTypeMap;
    static{
        //功能属性数据解析
        if(functionAttriMap == null){
            functionAttriMap = new HashMap<>();
            putValueForMap(FUNCTION_ATTRIBUTE,functionAttriMap);
        }
        //信息类型数据解析
        if(messageTypeMap == null){
            messageTypeMap = new HashMap<>();
            putValueForMap(MESSAGE_TYPE,messageTypeMap);
        }
        //设备状态数据解析
        if(equipmentStatusMap == null){
            equipmentStatusMap = new HashMap<>();
            putValueForMap(EQUIPMENT_STATUS,equipmentStatusMap);
        }
        //设备类型数据解析
        if(equipmentTypeMap == null){
            equipmentTypeMap = new HashMap<>();
            putValueForMap(EQUIPMENT_TYPE,equipmentTypeMap);
        }
    }
    /**
     *@Author: dwt
     *@Date: 2019-09-06 10:00
     *@Param: java.lang.String,java.util.Map
     *@Return: void
     *@Description 拆分字符串封装到Map集合
     */
    private static void putValueForMap(String args,Map<String,String> map){
        if(StringUtils.isEmpty(args)){
            return;
        }
        String[] functions = args.split(";");
        if(functions != null && functions.length > 0){
            String[] function;
            for(String str : functions){
                function = str.split(",");
                if(function != null && function.length >= 2){
                    map.put(function[0],function[1]);
                }
            }
        }
    }
}
