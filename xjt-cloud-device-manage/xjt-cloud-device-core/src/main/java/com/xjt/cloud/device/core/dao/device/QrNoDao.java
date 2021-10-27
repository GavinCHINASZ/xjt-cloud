package com.xjt.cloud.device.core.dao.device;

import com.xjt.cloud.device.core.entity.QrNo;
import com.xjt.cloud.device.core.entity.QrNoConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: wangzhiwen
 * @Date: 2019/7/18 17:32
 * @Description:二维码管理
 */
@Repository
public interface QrNoDao {
    /**
     *
     * 功能描述:查询现有最大二维码
     *
     * @return: QrNo
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    QrNo findMaxQrNo(QrNo qrNo);

    /**
     *
     * 功能描述:批量生成二维码
     *
     * @param list
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/22 14:00
     */
    Integer saveQrNoList(List<QrNo> list);

    /**
     *
     * 功能描述:批量增加空设备
     *
     * @param list
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer saveDeviceList(List<QrNo> list);

    /**
     *
     * 功能描述:批量增加空巡检点
     *
     * @param list
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/7/19 14:51
     */
    Integer saveCheckPointList(List<QrNo> list);
    
    /**
     *
     * 功能描述: 以sql文查询二维码信息列表
     *
     * @param sql
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/8/8 18:11
     */
    List<QrNo> findQrNoListBySql(String sql);

    /**
     *
     * 功能描述:查询二维码列表
     *
     * @param qrNo
     * @return: QrNo
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:01
     */
    List<QrNo> findQrNoList(QrNo qrNo);

    /**
     *
     * 功能描述:查询二维码列表
     *
     * @param qrNoUrl
     * @param qrNo
     * @return: QrNo
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:01
     */
    List<QrNo> findDownloadQrNoList(@Param("qrNo")QrNo qrNo,@Param("qrNoUrl")String qrNoUrl);

    /**
     *
     * 功能描述:查询二维码列表
     *
     * @param qrNo
     * @return: Integer
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:01
     */
    Integer findQrNoListCount(QrNo qrNo);

    /**
     *
     * 功能描述: 以二维码查询二维码信息
     *
     * @param qrNo
     * @return: 
     * @auther: wangzhiwen
     * @date: 2019/12/17 14:19
     */
    QrNo findQrNo(@Param("qrNo")String qrNo);

    /////////////////////////////////////////////////// 二维码 配置 //////////////////////////////////////////////////////
    /**
     *
     * 功能描述:查询二维码模版信息列表
     *
     * @param qrNoConfig
     * @return: QrNoConfig
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:37
     */
    List<QrNoConfig> findQrNoConfigList(QrNoConfig qrNoConfig);

    /**
     *
     * 功能描述:查询二维码模版信息
     *
     * @param qrNoConfig
     * @return: QrNoConfig
     * @auther: wangzhiwen
     * @date: 2019/8/22 9:37
     */
    QrNoConfig findQrNoConfigById(QrNoConfig qrNoConfig);

    /**
     *
     * 功能描述:保存二维码模版
     *
     * @param qrNoConfig
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    int saveQrNoConfig(QrNoConfig qrNoConfig);

    /**
     *
     * 功能描述:修改二维码模版
     *
     * @param qrNoConfig
     * @return: com.xjt.cloud.commons.utils.Data
     * @auther: wangzhiwen
     * @date: 2019/8/26 10:45
     */
    int modifyQrNoConfig(QrNoConfig qrNoConfig);
}
