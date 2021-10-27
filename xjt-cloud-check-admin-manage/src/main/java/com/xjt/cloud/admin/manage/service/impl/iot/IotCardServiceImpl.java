package com.xjt.cloud.admin.manage.service.impl.iot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.admin.manage.common.abstracts.AbstractAdminService;
import com.xjt.cloud.admin.manage.common.utils.ConstantsClient;
import com.xjt.cloud.admin.manage.dao.backstage.InventoryDao;
import com.xjt.cloud.admin.manage.entity.inventory.PutStorageProduct;
import com.xjt.cloud.admin.manage.service.service.iot.IotCardService;
import com.xjt.cloud.commons.dict.DictUtils;
import com.xjt.cloud.commons.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName IotCardServiceImpl
 * @Description 物联网卡管理
 * @Author wangzhiwen
 * @Date 2020/9/9 14:15
 **/
@Service
public class IotCardServiceImpl extends AbstractAdminService implements IotCardService {
    @Autowired
    private InventoryDao inventoryDao;
    String transid = "2000857022002000002020072017235512346578";
    String appid = "200085702200200000";
    String password = "miWAVDBxi";

    /**
     * @return
     * @Description 移动平台物联网卡状态定时任务
     * @author wangzhiwen
     * @date 2020/9/8 15:54
     */
    @Override
    public void iotCardStatusOneLinkTask() {
        PutStorageProduct putStorageProduct = new PutStorageProduct();
        putStorageProduct.setProductStatus(1);
        Integer limitNum = Integer.parseInt(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                ConstantsClient.IOT_CARD_LIMIT_NUM, "itemValue"));
        putStorageProduct.setPageSize(limitNum);
        putStorageProduct.setProductTypes(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                ConstantsClient.IOT_CARD_PROJECT_TYPE, "itemValue").split(","));
        List<PutStorageProduct> list = inventoryDao.findOneLinkCardNotActiveList(putStorageProduct);
        if (CollectionUtils.isNotEmpty(list)) {
            String token = getOneLinkToken();
            if (StringUtils.isEmpty(token)) {
                SysLog.info("iotCardStatusOneLinkTask 任务token为空");
                return;
            }

            String url = "https://api.iot.10086.cn/v5/ec/query/sim-basic-info?token=" + token + "&transid=" + transid + "&msisdn=";
            JSONObject jsonObject;
            List<PutStorageProduct> saveList = new ArrayList<>();
            for (PutStorageProduct psp : list) {
                jsonObject = oneLinkResultAnalysis(url + psp.getProperty2());//
                if (jsonObject != null) {
                    psp.setActiveDate(jsonObject.getDate("activeDate"));
                    if (psp.getActiveDate() != null) {
                        psp.setOpenDate(jsonObject.getDate("openDate"));
                        psp.setProductStatus(2);
                        saveList.add(psp);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(saveList)) {
                inventoryDao.modifyCardStatusByList(saveList);
            }
        }
    }

    /**
     * @return
     * @Description 移动平台物联网卡流量查询定时任务
     * @author wangzhiwen
     * @date 2020/9/8 15:54
     */
    @Override
    public void iotCardFlowOneLinkTask() {
        PutStorageProduct putStorageProduct = new PutStorageProduct();
        putStorageProduct.setProductTypes(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                ConstantsClient.IOT_CARD_PROJECT_TYPE, "itemValue").split(","));
        Integer limitNum = Integer.parseInt(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                ConstantsClient.IOT_CARD_LIMIT_NUM, "itemValue"));
        putStorageProduct.setPageSize(limitNum);
        Date date = DateUtils.getDate();
        putStorageProduct.setFlowDandleDate(date);
        List<PutStorageProduct> list = inventoryDao.findOneLinkCardNotActiveList(putStorageProduct);
        if (CollectionUtils.isNotEmpty(list)) {
            String token = getOneLinkToken();
            if (StringUtils.isEmpty(token)) {
                SysLog.info("iotCardFlowOneLinkTask 任务token为空!");
                return;
            }
            String url = "https://api.iot.10086.cn/v5/ec/query/sim-data-margin?token=" + token + "&transid=" + transid + "&msisdn=";
            //得到剩余流量提醒值
            int warnFlow = Integer.parseInt(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                    ConstantsClient.IOT_CARD_RESIDUE_FLOW, "itemValue")) * 100;

            JSONObject jsonObject;
            List<PutStorageProduct> warnList = new ArrayList<>();
            List<PutStorageProduct> saveList = new ArrayList<>();
            String offeringId = "offeringId";
            String offeringName = "offeringName";
            String totalAmount = "totalAmount";
            String useAmount = "useAmount";
            String remainAmount = "remainAmount";
            String accmMarginList = "accmMarginList";
            int num = 100;
            for (PutStorageProduct psp : list) {
                jsonObject = oneLinkResultAnalysis(url + psp.getProperty2());//查询单卡流量
                if (jsonObject != null) {
                    jsonObject = jsonObject.getJSONArray(accmMarginList).getJSONObject(0);
                    psp.setOfferingId(jsonObject.getString(offeringId));
                    psp.setOfferingName(jsonObject.getString(offeringName));
                    psp.setTotalFlow((int) (jsonObject.getFloat(totalAmount) * num));
                    psp.setUsedFlow((int) (jsonObject.getFloat(useAmount) * num));
                    psp.setRemainFlow((int) (jsonObject.getFloat(remainAmount) * num));
                    if (psp.getRemainFlow() < warnFlow) {//判断是剩余流量是否少于设置值
                        warnList.add(psp);
                    }
                    psp.setFlowDandleDate(date);
                    saveList.add(psp);
                }
            }

            if (CollectionUtils.isNotEmpty(saveList)) {
                inventoryDao.modifyCardStatusByList(saveList);
            }
            if (CollectionUtils.isNotEmpty(warnList)) {
                StringBuilder content = new StringBuilder();
                content.append("以下物联卡的流量不足，请及时充值！\n");
                for (PutStorageProduct psp : warnList) {
                    content.append("iccid：" + psp.getProperty1() + "  msisdn：" + psp.getProperty2() + "  总流量：" + (psp.getTotalFlow() / 100.00) +
                            "  已用流量：" + (psp.getUsedFlow() / 100.00) + "剩余流量：" + (psp.getRemainFlow() / 100.00) + "\n");
                }
                sendWardMsg("物联卡的流量不足，请查看邮件或管理后台！");
                sendWardMail("物联卡的流量不足，请及时充值", content.toString(), "消检通系统");
            }
        }
    }

    /**
     * @return
     * @Description 移动平台物联网卡余额查询定时任务
     * @author wangzhiwen
     * @date 2020/9/8 15:54
     */
    @Override
    public void iotCardBalanceOneLinkTask() {
        PutStorageProduct putStorageProduct = new PutStorageProduct();
        putStorageProduct.setProductTypes(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                ConstantsClient.IOT_CARD_PROJECT_TYPE, "itemValue").split(","));
        putStorageProduct.setPageSize(1);
        List<PutStorageProduct> list = inventoryDao.findOneLinkCardNotActiveList(putStorageProduct);
        String month = DateUtils.formatDate("yyyyMM", DateUtils.getDateAddMonth(new Date(), -1));
        if (CollectionUtils.isNotEmpty(list)) {
            String token = getOneLinkToken();
            if (StringUtils.isEmpty(token)) {
                SysLog.info("iotCardBalanceOneLink任务token为空");
                return;
            }
            //查询上月账单url
            String url = "https://api.iot.10086.cn/v5/ec/query/ec-bill?token=" + token + "&transid=" + transid + "&queryDate=" + month;
            JSONObject billJson = oneLinkResultAnalysis(url);//查询上月账单
            if (billJson != null) {
                billJson = billJson.getJSONArray("billingList").getJSONObject(0);
                float invoiceAmount = billJson.getFloat("invoiceAmount");//上月账单金额
                float openAmount = billJson.getFloat("openAmount");//未缴费金额
                putStorageProduct = list.get(0);
                //单卡余额查询url（集团统一）
                String balanceUrl = "https://api.iot.10086.cn/v5/ec/query/balance-info?token=" + token + "&transid=" + transid + "&msisdn=" + putStorageProduct.getProperty2();
                JSONObject balanceJson = oneLinkResultAnalysis(balanceUrl);//查询账户余额是否足够;
                if (balanceJson != null) {
                    float amount = balanceJson.getFloat("amount");//得到账户余额
                    int balanceLimit = Integer.parseInt(DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM,
                            ConstantsClient.IOT_CARD_RESIDUE_BALANCE, "itemValue"));
                    SysLog.info("移动One Link平账户余额为：" + amount + " 上月账单金额：" + invoiceAmount + " 上月未缴金额："
                            + openAmount + " 可用余额：" + (amount - openAmount));
                    if (amount - openAmount < balanceLimit) {
                        sendWardMsg("物联卡账户余额不足，请及时充值！");
                        sendWardMail("物联卡账户余额不足，请及时充值", "物联卡账户余额不足，请及时充值", "消检通系统");
                    }
                }
            }
        }
    }

    /**
     * @param subject      标题
     * @param body         内容
     * @param personalName 称呼
     * @return void
     * @Description 发送邮件
     * @author wangzhiwen
     * @date 2020/9/11 10:22
     */
    private void sendWardMail(String subject, String body, String personalName) {
        try {
            String mail = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM, ConstantsClient.WARN_MOBILE, "itemValue");
            String[] mails = mail.split(",");
            MailUtils.send(subject, body, personalName, mails);
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * @param content 短信内容
     * @return void
     * @Description 发送短信
     * @author wangzhiwen
     * @date 2020/9/11 10:21
     */
    private void sendWardMsg(String content) {
        try {
            String mobil = DictUtils.getDictPropertyByDictItemCodePropertyName(ConstantsClient.IOT_CARD_PARAM, ConstantsClient.WARN_MAIL, "WARN_MOBILE");
            TaoBaoSendMsg.sendMsg(mobil, content);
        } catch (Exception ex) {
            SysLog.error(ex);
        }
    }

    /**
     * @return java.lang.String
     * @Description 缓存获取token
     * @author wangzhiwen
     * @date 2020/9/8 16:16
     */
    private String getOneLinkToken() {
        String key = "oneLinkTokeTime";
        String oneLinkTokeTime = redisUtils.getString(key);

        if (StringUtils.isEmpty(oneLinkTokeTime)) {
            oneLinkTokeTime = getToken(key);
        } else {
            String[] tokenTime = oneLinkTokeTime.split("_time_");
            Long time = DateUtils.getDateTime();
            if (Long.parseLong(tokenTime[1]) < time) {//判断token是否过期
                oneLinkTokeTime = getToken(key);
            } else {
                oneLinkTokeTime = tokenTime[0];
            }
        }
        return oneLinkTokeTime;
    }

    /**
     * @param key
     * @return java.lang.String
     * @Description 移动one link平台获取token
     * @author wangzhiwen
     * @date 2020/9/11 13:55
     */
    private String getToken(String key) {
        String url = "https://api.iot.10086.cn/v5/ec/get/token?appid=" + appid + "&password=" + password + "&transid=" + transid;
        JSONObject jsonObject = oneLinkResultAnalysis(url);
        if (jsonObject != null) {
            String result = jsonObject.getString("token");
            SysLog.info(" 移动token =:" + result);
            Long expiresIn = 3500L;//凭证的有效时间（秒）
            redisUtils.set(key, result + "_time_" + (DateUtils.getDateTime() + (expiresIn * 1000)), expiresIn);
            return result;
        }
        return null;
    }

    /**
     * @param url
     * @return com.alibaba.fastjson.JSONObject
     * @Description oneLink平台接口请求与结果解析
     * @author wangzhiwen
     * @date 2020/9/9 9:32
     */
    private JSONObject oneLinkResultAnalysis(String url) {
        JSONObject jsonObject = HttpUtils.httpGet(url);
        String status = jsonObject.getString("status");//出错返回码，为0表示成功，非0表示调用失败
        if (!"0".equals(status)) {
            jsonObject = HttpUtils.httpGet(url);
            status = jsonObject.getString("status");//出错返回码，为0表示成功，非0表示调用失败
        }
        if ("0".equals(status)) {
            JSONArray jsonArray = jsonObject.getJSONArray("result");//获取到的凭证，最长为512字节
            if (jsonArray != null && jsonArray.size() > 0) {
                return jsonArray.getJSONObject(0);
            }
        } else {
            SysLog.info(" oneLink请求错误误，url=" + url);
        }
        return null;
    }

}
