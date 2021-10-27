package cmcciot.onenet.nbapi.sdk.samples;

import cmcciot.onenet.nbapi.sdk.api.online.*;
import cmcciot.onenet.nbapi.sdk.entity.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuocongbin
 * date 2018/3/15
 */
public class ApiSample {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiSample.class);
    public static void main(String[] args) throws Exception {
        String apiKey = "YiQfw6RLDNh9l8iyRdpoIkWLIcM=";
        String imei = "869976035865469";
        Integer objId = 3200;
        Integer objInstId = 0;
        Integer readResId = 5500;
        Integer executeResId = 5501;
        Integer writeResId = 5750;
        Integer writeMode = 2;
        
        System.out.println("test start ... ...");
        
        // Create device
        CreateDeviceOpe deviceOpe = new CreateDeviceOpe(apiKey);
        Device device = new Device("samples", imei, "320023320");
        //LOGGER.info(deviceOpe.operation(device, device.toJsonObject()).toString());
        String s = deviceOpe.operation(device, device.toJsonObject()).toString();
        LOGGER.info(s);
        System.out.println("create ：" + s);
        
        //Read
        ReadOpe readOperation = new ReadOpe(apiKey);
        Read read = new Read(imei, objId);
        read.setObjInstId(objInstId);
        read.setResId(readResId);
        //LOGGER.info(readOperation.operation(read, null).toString());
        String s1 = readOperation.operation(read, (JSONObject) null).toString();
        LOGGER.info(s1);
        System.out.println("Read ：" + s1);
        
        // Write
        WriteOpe writeOpe = new WriteOpe(apiKey);
        Write write = new Write(imei, objId, objInstId, writeMode);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("res_id", writeResId);
        jsonObject.put("val", "data1");
        jsonArray.add(jsonObject);
        JSONObject data = new JSONObject();
        data.put("data", jsonArray);
        //LOGGER.info(writeOpe.operation(write, data).toString());
        String s2 = writeOpe.operation(write, data).toString();
        LOGGER.info(s2);
        System.out.println("Write ：" + s2);
        
        // Execute
        ExecuteOpe executeOpe = new ExecuteOpe(apiKey);
        Execute execute = new Execute(imei, objId, objInstId, executeResId);
        
        //下发命令内容，JSON格式
        JSONObject body = new JSONObject();
        body.put("args", "ping");
        //LOGGER.info(executeOpe.operation(execute, body).toString());
        String s3 =executeOpe.operation(execute, body).toString();
        LOGGER.info(s3);
        System.out.println("Ping ：" + s3);

        
        // Resource
        ResourcesOpe resourcesOpe = new ResourcesOpe(apiKey);
        Resources resources = new Resources(imei);
        //LOGGER.info(resourcesOpe.operation(resources, null).toString());
        String s4 = resourcesOpe.operation(resources, (JSONObject) null).toString();
        LOGGER.info(s4);
        System.out.println("Execute ：" + s4);
        
        // Observe
        ObserveOpe observeOpe = new ObserveOpe(apiKey);
        Observe observe = new Observe(imei, objId, false);
        //LOGGER.info(observeOpe.operation(observe, null).toString());
        String s5 = observeOpe.operation(observe, (JSONObject) null).toString();
        LOGGER.info(s5);
        System.out.println("Observe ：" + s5);

    }
}
