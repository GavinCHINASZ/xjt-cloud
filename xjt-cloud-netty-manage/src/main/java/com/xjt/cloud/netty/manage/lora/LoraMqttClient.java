package com.xjt.cloud.netty.manage.lora;


/**
 * @Auther: wangzhiwen
 * @Date: 2020/4/16 16:58
 * @Description:
 */
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import com.xjt.cloud.commons.utils.StringUtils;
import com.xjt.cloud.commons.utils.SysLog;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import sun.misc.BASE64Decoder;

/**
 *  MQTT 数据处理插件
 * @author chao
 * @createDate 2017.1.23
 */
public class LoraMqttClient {
    private String file_conf = "mqtt.properties";

    private MqttClient client_sub;
    private MqttConnectOptions options_sub;
    private String topic_dev = "/taikang/rulee/#";//测试
    private String topic_online = "/gateway/+/event/+/#";
    private String topic_will = "/gateway/+/command/#";

    private static final LoraMqttClient loraMqttClient = new LoraMqttClient();

    public static LoraMqttClient getInstance() {
        return loraMqttClient;
    }

    private LoraMqttClient() {

    }

    private void initMQTTListener() {
        try {
            //获取配置信息
            StringBuilder builder = new StringBuilder();
            Properties properties = PropertiesLoaderUtils.loadProperties(new PathResource("E:\\xjtnettycloud\\xjtnettycloud\\xjt-cloud-netty-manage\\src\\main\\java\\com\\xjt\\cloud\\netty\\manage\\lora\\mqtt.properties"));

            // HOST_MQ为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client_sub = new MqttClient(properties.getProperty("mqtt.tcp.host"), properties.getProperty("mqtt.clientid.subscribe").trim(), new MemoryPersistence());
            // MQTT的连接设置
            options_sub = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
            options_sub.setCleanSession(false);
            // 设置连接的用户名
            options_sub.setUserName(properties.getProperty("mqtt.username").trim());
            // 设置连接的密码
            options_sub.setPassword(properties.getProperty("mqtt.password").trim().toCharArray());
            // 设置会话心跳时间 单位为秒 服务器会每隔90秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options_sub.setKeepAliveInterval(90);
            //订阅topic定义
            //信息传输级别
            //int[] Qos = new int[]{0, 0, 0,0,0,0};
            final int[] Qos = new int[]{0};
            //String[] topics = new String[]{topic_online, topic_will, topic_dev, "/application/+/device/+/rx","application/+/device/+/rx", "gateway/+/event/+"};
            final String[] topics = new String[]{"application/+/device/+/rx"};

            // 设置回调
            client_sub.setCallback(new MqttCallbackExtended() {
                public void connectComplete(boolean reconnect, String serverURI) {
                    //连接成功，需要上传客户端所有的订阅关系
                    try {
                        client_sub.subscribe(topics, Qos);
                    } catch (Exception e) {
                        SysLog.error(e);
                    }
                }

                public void connectionLost(Throwable cause) {
                    // 连接丢失后，一般在这里面进行重连
                    System.out.println("=======连接断开，可以做重连==============");
                    // reConnect();
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("=======交付完成: {}==============" + token.isComplete());
                }

                String pattern = "application";
                private static final String DATA = "com.base64.demo";
                public void messageArrived(String topic, MqttMessage message) {
                    String msg = message.toString();
                    if (topic.startsWith(pattern)) {
                        JSONObject jsonObject = JSONObject.parseObject(msg);
                        msg = jsonObject.getString("data");
                        // BASE64解密
                        try {
                            BASE64Decoder decoder = new BASE64Decoder();
                            byte[] bytes = decoder.decodeBuffer(msg);
                            System.out.println("BASE64解密：" + new String(bytes));
                        }catch (Exception ex){

                        }
                    }
                    //due arrived message...
                    System.out.println("=======收到消息topic: {}===Qos: {}" +  topic +  message.getQos());
                    System.out.println("=======message: {}" +  message.toString());
                }
            });
            //连接mqtt服务器broker
            client_sub.connect(options_sub);
            //订阅消息
            client_sub.subscribe(topics, Qos);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("init listener MQTT err info: {}" +  e.toString());
            System.exit(-1);
        }
    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    /**
     * mqtt重连
     */
    public void reConnect() {
        try {
            if (null != client_sub && !(client_sub.isConnected())) {
                client_sub.reconnect();
                System.out.println("=======尝试重新连接==============");
            }
        } catch (MqttException e) {
            System.out.println("=======重新连接失败:{}==============" +  e.toString());
        }
    }

    public static void main(String[] args) {
        //System.out.println(hexToAscii("eyZELCwwLjAwMCw5OSUsMDAxMCw2MH0"));
        // 初始化数据侦听模块
        LoraMqttClient.getInstance().initMQTTListener();
    }
}

