package com.xjt.cloud.log.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.abstracts.AbstractService;
import com.xjt.cloud.commons.dict.Dict;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import com.xjt.cloud.log.manage.dao.log.SecurityLogDao;
import com.xjt.cloud.log.manage.entity.SecurityLog;
import com.xjt.cloud.log.manage.service.service.SecurityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/2 10:29
 * @Description: 安全日志信息管理接口实现类
 */
@Service
public class SecurityLogServiceImpl extends AbstractService implements SecurityLogService {
    @Autowired
    private SecurityLogDao securityLogDao;

    private final String SECURITY_LOG_TYPE = "SECURITY_LOG_TYPE";

    private final String LOG_TYPE = "LOG_TYPE";

    /**
     *
     * 功能描述:保存日志信息
     *
     * @param type 日志类型，数据词典表中的code值，
     * @param loginName 登录用户名
     * @param content 日志内容
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/6 10:41
     */
    @Override
    public Data saveSecurityLog(String type, String loginName,String content, Long sourceId, Integer sourceType){
        try {
            Dict dict = DictUtils.getDictByDictAndItemCode(SECURITY_LOG_TYPE, type);
            SecurityLog securityLog = asseSecurityLogTypeAndUser(dict, loginName, sourceId, sourceType);//组装日志的用户信息与类型信息
            securityLog.setContent(securityLog.getUserName() + content);
            int num = securityLogDao.saveSecurityLog(securityLog);
            return asseData(num);
        }catch (Exception ex){
            SysLog.error(ex);
            return Data.isFail();
        }
    }

    /**
     *
     * 功能描述:
     *
     * @param type
     * @param loginName
     * @param list
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/19 15:54
     */
    public <T> Data saveSecurityLog(String type, String loginName, List<T> list, Long sourceId, Integer sourceType){
        try{
            if (CollectionUtils.isEmpty(list)){
                return Data.isFail();
            }
            Dict dict = DictUtils.getDictByDictAndItemCode(SECURITY_LOG_TYPE, type);
            String content = dict.getMemo();
            List<String> keyList = getPropertyList(content);//得到属性名称
            SecurityLog securityLog = asseSecurityLogTypeAndUser(dict, loginName, sourceId, sourceType);//组装日志的用户信息与类型信息

            T newT = list.get(0);
            Field field;
            Object object;
            Class clazz = newT.getClass();
            Map<String, String> map = new HashMap<>();
            try {
                for (T t : list) {//从列表中得到属性的值，多个值以‘、’分隔
                    for (String key : keyList) {
                        field = clazz.getDeclaredField(key);
                        field.setAccessible(true);
                        if (field != null) {
                            object = field.get(t);
                            if (null != object) {
                                map.put(key,map.get(key) + "、" + field.get(t).toString());//把值以健值对的方式保存在map中
                            }
                        }
                    }
                }

                //组装结果字符串
                String str;
                for (int i = 0;i < keyList.size(); i++){
                    str = map.get(keyList.get(i));
                    str = StringUtils.isEmpty(str) ? "" : str.substring(1);
                    content = content.replace(keyList.get(i), str) + ";";
                }
            }catch (Exception ex){
                SysLog.error(ex);
            }

            content = content.replaceAll("\\{"," ").replaceAll("\\}"," ");
            content = content.substring(0, content.length() - 1);
            securityLog.setContent(securityLog.getUserName() + content);
            int num = securityLogDao.saveSecurityLog(securityLog);
            return asseData(num);
        }catch (Exception ex){
            SysLog.error(ex);
            return Data.isFail();
        }
    }

    /**
     *
     * 功能描述:保存日志信息
     *
     * @param type 日志类型，数据词典表的值
     * @param loginName 登录用户名
     * @param newT 信息对象
     * @param oldT 旧的信息对象，只有在修改时需要
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @loginName
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/6 10:41
     */
    @Override
    public <T> Data saveSecurityLog(String type, String loginName, T newT, T oldT, Long sourceId, Integer sourceType){
        try {
            Dict dict = DictUtils.getDictByDictAndItemCode(SECURITY_LOG_TYPE, type);
            int itemValue = Integer.parseInt(dict.getItemValue());
            String content = dict.getMemo();
            List<String> keyList = getPropertyList(content);
            SecurityLog securityLog = asseSecurityLogTypeAndUser(dict, loginName, sourceId, sourceType);//组装日志的用户信息与类型信息

            List<String> valueList = getValueByObjKeys(newT, keyList);
            if (2 >= itemValue){//添加/删除
                for (int i = 0;i < keyList.size(); i++){
                    content = content.replace(keyList.get(i), valueList.get(i)) ;
                }
            }else if (3 == itemValue){//修改
                List<String> oldValueList = getValueByObjKeys(oldT, keyList);
                String beginStr = content.substring(0, content.indexOf("[") );
                String endStr = content.substring(content.indexOf("]") - 1).replace("]","");
                StringBuilder str = new StringBuilder();
                int temp = 0;
                for (int i = 0;i < keyList.size(); i++){
                    //判断修改的新值是否为空，是否与老值不相等
                    if (StringUtils.isNotEmpty(valueList.get(i)) && !valueList.get(i).equals(oldValueList.get(i))){
                        temp++;
                        //str.append( keyList.get(i) + " " + oldValueList.get(i)+ " 已修改为 " + valueList.get(i) + " ,");
                        str.append( " " + oldValueList.get(i)+ " 已修改为 " + valueList.get(i) + " ,");

                    }
                }

                if(temp == 0){
                    return Data.isSuccess();
                }
                content = beginStr + str.substring(0, str.length() - 1) + endStr;
            }else {
                return Data.isSuccess();
            }

            content = content.replaceAll("\\{"," ").replaceAll("\\}"," ");
            securityLog.setContent(securityLog.getUserName() +" " + content);
            int num = securityLogDao.saveSecurityLog(securityLog);
            return asseData(num);
        }catch (Exception ex){
            SysLog.error(ex);
            return Data.isFail();
        }
    }

    /**
     *
     * 功能描述:从数据词典中获取要得到的属性名
     *
     * @param content
     * @return: java.util.List<java.lang.String>
     * @auther: wangzhiwen
     * @date: 2019/8/19 16:01
     */
    private List<String> getPropertyList(String content){
        //从数据词典信息中，取得要添加日志的属性
        Pattern p = Pattern.compile("\\{([^}]*)\\}");
        Matcher m = p.matcher(content);
        //得到要获取的属性名称
        List<String> keyList = new ArrayList<>();
        String key;
        while(m.find()) {
            key = m.group();
            keyList.add(key.substring(1, key.length() - 1));
        }
        return keyList;
    }

    /**
     *
     * 功能描述:组装日志的用户信息与类型信息
     *
     * @param dict
     * @param loginName
     * @param sourceId 所属ID(平台ID或项目ID
     * @param sourceType 所属:0工程 1平台 2项目
     * @return: com.xjt.cloud.log.manage.entity.SecurityLog
     * @auther: wangzhiwen
     * @date: 2019/8/19 11:21
     */
    private SecurityLog asseSecurityLogTypeAndUser(Dict dict, String loginName, Long sourceId, Integer sourceType){
        SecurityLog securityLog = new SecurityLog();
        String itemDescription = dict.getItemDescription();
        securityLog.setModelType(Integer.parseInt(itemDescription));// description 值 与LOG_TYPE项子项的code值，
        Dict modelDict = DictUtils.getDictByDictAndItemValue(LOG_TYPE, itemDescription);//得到模块
        securityLog.setModelName(modelDict.getDictName());
        securityLog.setActionType(Integer.parseInt(dict.getItemValue()));
        securityLog.setActionName(dict.getItemName());
        securityLog.setSourceId(sourceId);
        securityLog.setSourceType(sourceType);
        if (StringUtils.isNotEmpty(loginName)) {//添加当前登录的用户，操作用户
            Long userId = getLoginUserId(loginName);
            String userName = getOrgUserName(userId, sourceId);
            if(userName!=null){
                securityLog.setUserName(userName);
            }else{
                securityLog.setUserName(loginName);
            }
            securityLog.setUserId(userId + "");
        }
        return securityLog;
    }

    /**
     *
     * 功能描述:获取信息对象
     *
     * @param t
     * @param keyList
     * @return: java.util.List<java.lang.String>
     * @auther: wangzhiwen
     * @date: 2019/8/6 11:45
     */
    private <T> List<String> getValueByObjKeys(T t, List<String> keyList){
        List<String> list = new ArrayList<>();
        Field field;
        try {
            for (String key : keyList) {
                field = t.getClass().getDeclaredField(key);
                field.setAccessible(true);
                if (field != null) {
                    list.add(field.get(t) == null ? "" : field.get(t).toString());//设置每一列的值
                }
            }
        }catch (Exception ex){
            SysLog.error(ex);
        }
        return list;
    }
}
