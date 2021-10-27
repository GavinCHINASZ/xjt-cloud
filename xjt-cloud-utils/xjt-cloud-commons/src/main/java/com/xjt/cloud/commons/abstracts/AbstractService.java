package com.xjt.cloud.commons.abstracts;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.base.BaseEntity;
import com.xjt.cloud.commons.base.BaseService;
import com.xjt.cloud.commons.exception.BaseServiceException;
import com.xjt.cloud.commons.exception.ServiceErrCode;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/4/24 17:50
 * @Description:
 */
public abstract class AbstractService implements BaseService {
    @Autowired
    protected RedisUtils redisUtils;

    /**
     *
     * 功能描述: 以对象信息为条件，查询对象信息
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    @Override
    public <T extends BaseEntity> Data findObj(T t){
        return null;
    }

    /**
     *
     * 功能描述:  以对象信息为条件，查询对象信息列表
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    @Override
    public <T extends BaseEntity> Data findList(T t){
        return null;
    }

    /**
     *
     * 功能描述: 保存对象
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    @Override
    public <T extends BaseEntity> Data saveObj(T t){
        return null;
    }

    /**
     *
     * 功能描述: 修改对象，或逻辑删除
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    @Override
    public <T extends BaseEntity> Data modifyObj(T t){
        return null;
    }

    /**
     *
     * 功能描述: 查询总行数
     *
     * @param t
     * @return: Data
     * @auther: wangzhiwen
     * @date: 2019-04-26 10:30
     */
    @Override
    public <T extends BaseEntity> Data countObj(T t){
        return null;
    }
    /**
     *
     * 功能描述: 组装返回对象
     *
     * @param code
     * @param objs
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected <T extends BaseEntity> Data asseData(int code, T... objs){
        Data data = new Data(code);
        data.setMsg(objs.length > 0 ? objs[0].toString() : null);
        data.setObj(objs.length > 1 ? objs[1] : null);
        return data;
    }

    /**
     *
     * 功能描述: 组装返回对象
     *
     * @param code
     * @param msg
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected Data asseData(int code, String msg){
        Data data = new Data(code);
        data.setMsg(msg);
        data.setStatus(code);
        return data;
    }

    /**
     *
     * 功能描述: 组装返回对象
     *
     * @param code
     * @param msg
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected Data asseData(int code,String content, String msg){
        Data data = new Data(code);
        data.setObject(content);
        data.setMsg(msg);
        data.setStatus(code);
        return data;
    }


    /**
     *
     * 功能描述: 组装返回对象
     *
     * @param serviceErrCode
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected Data asseData(ServiceErrCode serviceErrCode){
        Data data = new Data();
        data.setMsg(serviceErrCode.getMsg());
        data.setStatus(serviceErrCode.getCode());
        return data;
    }


    /**
     *
     * 功能描述: 组装返回列表对象
     *
     * @param code
     * @param listObj
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:49
     */
    protected <T extends BaseEntity> Data asseData(int code, List<T> listObj){
        Data data = new Data(code);
        data.setListObj(listObj);
        return data;
    }
    /**
     *
     * 功能描述: 组装返回列表对象
     *
     * @param code
     * @param listObj
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:49
     */
    protected  Data asseData(int code, Object listObj, String msg){
        Data data = new Data(code);
        data.setObject(listObj);
        data.setMsg(msg);
        return data;
    }

    protected Data asseSaveData(int num, Object object){
        if (num > 0){
            Data data = new Data(Constants.SUCCESS_CODE);
            data.setObject(object);
            return data;
        }
        return Data.isFail();
    }

    /**
     *
     * 功能描述: 根据传入的信息对象，返回，是否有信息的组装结果
     *
     * @param object
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected <T extends BaseEntity> Data asseData(T object){
        if (object != null){
            return new Data(Constants.SUCCESS_CODE, object);
        }else{
            return new Data(Constants.NOT_DATA_CODE, Constants.NOT_DATA_MSG);
        }
    }

    /**
     *
     * 功能描述: 根据传入的信息对象，返回，是否有信息的组装结果
     *
     * @param object
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected  Data asseData(Object object){
        if (object != null){
            Data data = new Data(Constants.SUCCESS_CODE);
            data.setObject(object);
            return data;
        }else{
            return new Data(Constants.NOT_DATA_CODE, Constants.NOT_DATA_MSG);
        }
    }

    /**
     *
     * 功能描述: 根据操作影响的行数，返回组装结果，如果影响行数大于0，表示操作成功
     *
     * @param num
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:48
     */
    protected Data asseData(int num){
        if (num > 0){
            return new Data(Constants.SUCCESS_CODE);
        }else{
            return new Data(Constants.FAIL_CODE);
        }
    }


    /**
     *
     * 功能描述: 根据传入的信息对象列表，返回，是否有列表的组装结果
     *
     * @param listObj
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:49
     */
    protected <T extends BaseEntity> Data asseData(List<T> listObj){
        if (CollectionUtils.isNotEmpty(listObj)){
            return new Data(Constants.SUCCESS_CODE, listObj);
        }else{
            return new Data(Constants.NOT_DATA_CODE, Constants.NOT_DATA_MSG);
        }
    }

    /**
     *
     * 功能描述: 根据传入的信息对象列表，返回，是否有列表的组装结果
     *
     * @param listObj
     * @param totalCount
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/7/2 13:49
     */
    protected <T extends BaseEntity> Data asseData(Integer totalCount, List<T> listObj){
        if (CollectionUtils.isNotEmpty(listObj)){
            return new Data(Constants.SUCCESS_CODE, totalCount, listObj);
        }else{
            return new Data(Constants.NOT_DATA_CODE, Constants.NOT_DATA_MSG);
        }
    }

    /**
     *
     * 功能描述:为实体对象，添加登录用户的用户名与id
     *
     * @param loginName　登录账号
     * @param t　实体对象
     * @return: T
     * @auther: wangzhiwen
     * @date: 2019/9/26 14:12
     */
    protected <T extends BaseEntity> T setEntityUserIdName(String loginName, Long projectId, T t){
        Long userId = getLoginUserId(loginName);
        t.setCreateUserId(userId);
        t.setCreateUserName(getOrgUserName(userId, projectId));
        return t;
    }

    /**
     *
     * 功能描述:以登录用户名得到用户的主键id
     *
     * @param loginName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:47
     */
    protected Long getLoginUserId(String loginName){
        return getLoginJson(loginName).getLong("id");
    }

    /**
     *
     * 功能描述: 以登录用户名得到用户的姓名
     *
     * @param loginName
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:47
     */
    protected String getLoginUserName(String loginName){
        return getLoginJson(loginName).getString("userName");
    }

    /**
     *
     * 功能描述:获取登录用户成员名称
     *
     * @param loginName
     * @param projectId
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/11/8 13:43
     */
    protected String getOrgUserName(String loginName, Long projectId){
        Long userId = getLoginUserId(loginName);
        if (userId == null){
            throw BaseServiceException.initException(ConstantsMsg.USER_LOGIN_FAIL, ServiceErrCode.NOT_URL_ERR.getCode());
        }
        return getOrgUserName(userId, projectId);
    }

    /**
     *
     * 功能描述:获取登录用户成员名称
     *
     * @param userId
     * @param projectId
     * @return: java.lang.String
     * @auther: wangzhiwen
     * @date: 2019/11/8 13:43
     */
    protected String getOrgUserName(Long userId, Long projectId){
        String key = Constants.ORG_USER_CACHE_KEY + "_" + userId + "_" + projectId;
        String orgUser = redisUtils.getString(key);
        if (StringUtils.isEmpty(orgUser)) {
            try {
                HttpUtils.httpGet(PropertyUtils.getProperty(Constants.ORG_USER_CACHE_KEY) +
                        StringUtils.urlEncode("{\"projectId\":" + projectId + ",\"userId\":" + userId + "}"));
                orgUser = redisUtils.getString(key);
            }catch (Exception ex){
                SysLog.error(ex);
            }
        }

        if (StringUtils.isNotEmpty(orgUser)) {
            JSONObject jsonObject = JSONObject.parseObject(orgUser);
            return jsonObject.getString("userName");
        }
        throw BaseServiceException.initException(ConstantsMsg.GET_USER_FAIL, ServiceErrCode.NOT_URL_ERR.getCode());
    }

    /**
     *
     * 功能描述: 从redis中以登录用户名取得登录用户信息
     *
     * @param loginName
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/7/17 16:48
     */
    protected JSONObject getLoginJson(String loginName){
        if (StringUtils.isEmpty(loginName)){
            throw BaseServiceException.initException(ConstantsMsg.USER_LOGIN_FAIL, ServiceErrCode.NOT_URL_ERR.getCode());
        }
        return getUserByIdOrLoginName(null,loginName);
    }

    /**
     * @Description 以id或登录名称获取用户属性信息
     *
     * @param id
     * @param loginName
     * @param property 要获取的用户属性信息
     * @author wangzhiwen
     * @date 2021/5/14 15:01
     * @return java.lang.String
     */
    protected String getUserPropertyByIdOrLoginName(Long id,String loginName,String property){
        JSONObject jsonObject = getUserByIdOrLoginName(id,loginName);
        return jsonObject == null ? null : jsonObject.getString(property);
    }
    /**
     * @Description 以id或登录名称获取用户信息
     *
     * @param id
     * @param loginName
     * @author wangzhiwen
     * @date 2021/5/14 14:55
     * @return com.alibaba.fastjson.JSONObject
     */
    protected JSONObject getUserByIdOrLoginName(Long id,String loginName){
        Object object;
        String key = id == null ? loginName : "userId" + id;
        object = redisUtils.get(key);

        if (object == null){//判断缓存中用户是否存在，如不存在，则重新添加缓存
            try {
                String parameter;
                if (id != null){
                    parameter = StringUtils.urlEncode("{\"id\":\"" + id + "\"}");
                }else{
                    parameter = StringUtils.urlEncode("{\"loginName\":\"" + loginName + "\"}");
                }
                HttpUtils.httpGet(Constants.GET_USER_URL + parameter);
                object = redisUtils.get(key);
            }catch (Exception ex){
                SysLog.error(ex);
            }
        }
        if (object != null){
            return JSONObject.parseObject(object.toString());
        }
        return null;
    }

    /**
     *
     * 功能描述: 以url 得到其它模块的对象
     *
     * @param urlKey 配置文件中url的key
     * @param param 参数字符串
     * @param clazz 要转化的实体类
     * @return: T
     * @auther: wangzhiwen
     * @date: 2019/11/25 17:11
     */
    protected <T> T getObjByUrl(String urlKey, String param, Class<T> clazz){
        JSONObject json = getJsonObjByUrl(urlKey, param);
        T t = JSONObject.parseObject(json.getJSONObject("obj").toJSONString(),clazz);
        return t;
    }

    /**
     *
     * 功能描述: 以url 得到其它模块的对象列表
     *
     * @param urlKey 配置文件中url的key
     * @param param 参数字符串
     * @param clazz 要转化的实体类
     * @return: T
     * @auther: wangzhiwen
     * @date: 2019/11/25 17:11
     */
    protected <T> List<T> getObjListByUrl(String urlKey, String param, Class<T> clazz){
        JSONObject json = getJsonObjByUrl(urlKey, param);
        List<T> list = JSONObject.parseArray(json.getJSONArray("listObj").toString(), clazz);
        return list;
    }

    /**
     *
     * 功能描述: 以url查询json信息
     *
     * @param urlKey
     * @param param
     * @return: com.alibaba.fastjson.JSONObject
     * @auther: wangzhiwen
     * @date: 2019/11/26 10:17
     */
    private JSONObject getJsonObjByUrl(String urlKey, String param){
        String url = PropertyUtils.getProperty(urlKey);
        JSONObject json = HttpUtils.httpGet(url + "json=" + StringUtils.urlEncode(param));
        return json;
    }
}
