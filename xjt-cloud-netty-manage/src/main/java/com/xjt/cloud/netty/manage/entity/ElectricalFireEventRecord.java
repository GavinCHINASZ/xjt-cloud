package com.xjt.cloud.netty.manage.entity;

import com.xjt.cloud.commons.base.BaseEntity;

import java.util.Date;

/**
 * ElectricalFireEventRecord
 * 电气火灾事件记录
 *
 * @author dwt
 * @date 2020-09-14 14:16
 */
public class ElectricalFireEventRecord extends BaseEntity {
    /**
     * 源数据
     */
    private String rawData;

    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 温度报警值
     */
    private String tempAlarmValue;

    /**
     * 通道号
     * 回路(天津合极)
     */
    private Integer channel;

    /**
     * 探测器id
     */
    private String sensorNo;

    /**
     * 报警电气火灾id
     */
    private Long alarmDeviceId;

    /**
     * 故障状态
     * 报警3>故障2>异常1>正常0
     */
    private Integer faultState;

    /**
     * 漏电状态(天津合极1-4)
     * 0 正常; 1 剩余电流过载（报警）; 2 剩余电流端口开路（故障）; 3 剩余电流端口短路（故障）
     */
    private Integer leakageState1;
    private Integer leakageState2;
    private Integer leakageState3;
    private Integer leakageState4;

    /**
     * 漏电流报警值
     * (天津合极:漏电值1-4) 范围0 ~ 20000, 倍率0.1, 单位mA 例如：0x1388(5000)*0.1 代表漏电1值为：500mA
     */
    private String leakageAlarmValue;
    private String leakageAlarmValue2;
    private String leakageAlarmValue3;
    private String leakageAlarmValue4;

    /**
     * 温度状态
     * (天津合极) 0正常; 4过温（报警）; 5温度端口开路（故障）; 6温度端口短路（故障）
     */
    private Integer temperatureState1;
    private Integer temperatureState2;
    private Integer temperatureState3;
    private Integer temperatureState4;

    /**
     * 温度
     * 天津合极(温度值1-4): 范围0~2500, 实际值=上传值-50，例：上传1000，表示1000/10 – 50 = 50℃
     */
    private String temperature1;
    private String temperature2;
    private String temperature3;
    private String temperature4;
    private String temperature5;
    private String temperature6;
    private String temperature7;
    private String temperature8;

    /**
     * 电压状态
     * (天津合极1-3) 0正常; 7缺相（报警）; 8电压过压（报警）; 9 电压欠压（报警）
     */
    private Integer voltageState1;
    private Integer voltageState2;
    private Integer voltageState3;

    /**
     * 电压值
     * 天津合极(1-3): 范围0~6000, 倍率0.1 单位V, 例：上传0x094b，表示2379/10 = 237.9V
     */
    private String voltageValue1;
    private String voltageValue2;
    private String voltageValue3;

    /**
     * 电流状态
     * (天津合极)0 正常; 10电流过载（报警）; 11电流端口开路（故障）; 12电流端口短路（故障）
     */
    private Integer flowState1;
    private Integer flowState2;
    private Integer flowState3;

    /**
     * 漏电流 leakage_current1
     * 天津合极(电流值1-3): 范围0~10000, 倍率0.1 单位A, 例：上传0x004b，表示75/10 = 7.5A
     */
    private String leakageCurrent1;
    private String leakageCurrent2;
    private String leakageCurrent3;
    private String leakageCurrent4;
    private String leakageCurrent5;
    private String leakageCurrent6;
    private String leakageCurrent7;
    private String leakageCurrent8;

    /**
     * 有功功率
     * 天津合极(1-3): 倍率0.001; 单位kW; 例：上传100，表示100/1000= 0.1kW
     */
    private String meritoriousPowerValue1;
    private String meritoriousPowerValue2;
    private String meritoriousPowerValue3;

    /**
     * 功率因数
     * 天津合极(1-3): 范围-1000~1000; 倍率0.1 单位%; 最高位bit15为1表示为负; 最高位bit15为0表示为正
     */
    private String powerFactorValue1;
    private String powerFactorValue2;
    private String powerFactorValue3;

    /**
     * 频率
     * 天津合极(A1-3C): 范围0~1000; 倍率0.1; 单位Hz
     */
    private String frequencyValue1;
    private String frequencyValue2;
    private String frequencyValue3;

    /**
     * 电量
     * 天津合极(A-C 1-3): 倍率0.001; 单位kw*h; 例如：上传0x00000175 = 373/1000=0.373KW
     */
    private String electricValue1;
    private String electricValue2;
    private String electricValue3;

    private Date startTime;
    private Date endTime;

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLeakageAlarmValue() {
        return leakageAlarmValue;
    }

    public void setLeakageAlarmValue(String leakageAlarmValue) {
        this.leakageAlarmValue = leakageAlarmValue;
    }

    public String getTempAlarmValue() {
        return tempAlarmValue;
    }

    public void setTempAlarmValue(String tempAlarmValue) {
        this.tempAlarmValue = tempAlarmValue;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getSensorNo() {
        return sensorNo;
    }

    public void setSensorNo(String sensorNo) {
        this.sensorNo = sensorNo;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getAlarmDeviceId() {
        return alarmDeviceId;
    }

    public void setAlarmDeviceId(Long alarmDeviceId) {
        this.alarmDeviceId = alarmDeviceId;
    }

    public String getLeakageCurrent1() {
        return leakageCurrent1;
    }

    public void setLeakageCurrent1(String leakageCurrent1) {
        this.leakageCurrent1 = leakageCurrent1;
    }

    public String getLeakageCurrent2() {
        return leakageCurrent2;
    }

    public void setLeakageCurrent2(String leakageCurrent2) {
        this.leakageCurrent2 = leakageCurrent2;
    }

    public String getLeakageCurrent3() {
        return leakageCurrent3;
    }

    public void setLeakageCurrent3(String leakageCurrent3) {
        this.leakageCurrent3 = leakageCurrent3;
    }

    public String getLeakageCurrent4() {
        return leakageCurrent4;
    }

    public void setLeakageCurrent4(String leakageCurrent4) {
        this.leakageCurrent4 = leakageCurrent4;
    }

    public String getLeakageCurrent5() {
        return leakageCurrent5;
    }

    public void setLeakageCurrent5(String leakageCurrent5) {
        this.leakageCurrent5 = leakageCurrent5;
    }

    public String getLeakageCurrent6() {
        return leakageCurrent6;
    }

    public void setLeakageCurrent6(String leakageCurrent6) {
        this.leakageCurrent6 = leakageCurrent6;
    }

    public String getLeakageCurrent7() {
        return leakageCurrent7;
    }

    public void setLeakageCurrent7(String leakageCurrent7) {
        this.leakageCurrent7 = leakageCurrent7;
    }

    public String getLeakageCurrent8() {
        return leakageCurrent8;
    }

    public void setLeakageCurrent8(String leakageCurrent8) {
        this.leakageCurrent8 = leakageCurrent8;
    }

    public String getTemperature1() {
        return temperature1;
    }

    public void setTemperature1(String temperature1) {
        this.temperature1 = temperature1;
    }

    public String getTemperature2() {
        return temperature2;
    }

    public void setTemperature2(String temperature2) {
        this.temperature2 = temperature2;
    }

    public String getTemperature3() {
        return temperature3;
    }

    public void setTemperature3(String temperature3) {
        this.temperature3 = temperature3;
    }

    public String getTemperature4() {
        return temperature4;
    }

    public void setTemperature4(String temperature4) {
        this.temperature4 = temperature4;
    }

    public String getTemperature5() {
        return temperature5;
    }

    public void setTemperature5(String temperature5) {
        this.temperature5 = temperature5;
    }

    public String getTemperature6() {
        return temperature6;
    }

    public void setTemperature6(String temperature6) {
        this.temperature6 = temperature6;
    }

    public String getTemperature7() {
        return temperature7;
    }

    public void setTemperature7(String temperature7) {
        this.temperature7 = temperature7;
    }

    public String getTemperature8() {
        return temperature8;
    }

    public void setTemperature8(String temperature8) {
        this.temperature8 = temperature8;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getFaultState() {
        return faultState;
    }

    public void setFaultState(Integer faultState) {
        this.faultState = faultState;
    }

    public Integer getLeakageState1() {
        return leakageState1;
    }

    public void setLeakageState1(Integer leakageState1) {
        this.leakageState1 = leakageState1;
    }

    public Integer getLeakageState2() {
        return leakageState2;
    }

    public void setLeakageState2(Integer leakageState2) {
        this.leakageState2 = leakageState2;
    }

    public Integer getLeakageState3() {
        return leakageState3;
    }

    public void setLeakageState3(Integer leakageState3) {
        this.leakageState3 = leakageState3;
    }

    public Integer getLeakageState4() {
        return leakageState4;
    }

    public void setLeakageState4(Integer leakageState4) {
        this.leakageState4 = leakageState4;
    }

    public String getLeakageAlarmValue2() {
        return leakageAlarmValue2;
    }

    public void setLeakageAlarmValue2(String leakageAlarmValue2) {
        this.leakageAlarmValue2 = leakageAlarmValue2;
    }

    public String getLeakageAlarmValue3() {
        return leakageAlarmValue3;
    }

    public void setLeakageAlarmValue3(String leakageAlarmValue3) {
        this.leakageAlarmValue3 = leakageAlarmValue3;
    }

    public String getLeakageAlarmValue4() {
        return leakageAlarmValue4;
    }

    public void setLeakageAlarmValue4(String leakageAlarmValue4) {
        this.leakageAlarmValue4 = leakageAlarmValue4;
    }

    public Integer getTemperatureState1() {
        return temperatureState1;
    }

    public void setTemperatureState1(Integer temperatureState1) {
        this.temperatureState1 = temperatureState1;
    }

    public Integer getTemperatureState2() {
        return temperatureState2;
    }

    public void setTemperatureState2(Integer temperatureState2) {
        this.temperatureState2 = temperatureState2;
    }

    public Integer getTemperatureState3() {
        return temperatureState3;
    }

    public void setTemperatureState3(Integer temperatureState3) {
        this.temperatureState3 = temperatureState3;
    }

    public Integer getTemperatureState4() {
        return temperatureState4;
    }

    public void setTemperatureState4(Integer temperatureState4) {
        this.temperatureState4 = temperatureState4;
    }

    public Integer getVoltageState1() {
        return voltageState1;
    }

    public void setVoltageState1(Integer voltageState1) {
        this.voltageState1 = voltageState1;
    }

    public Integer getVoltageState2() {
        return voltageState2;
    }

    public void setVoltageState2(Integer voltageState2) {
        this.voltageState2 = voltageState2;
    }

    public Integer getVoltageState3() {
        return voltageState3;
    }

    public void setVoltageState3(Integer voltageState3) {
        this.voltageState3 = voltageState3;
    }

    public String getVoltageValue1() {
        return voltageValue1;
    }

    public void setVoltageValue1(String voltageValue1) {
        this.voltageValue1 = voltageValue1;
    }

    public String getVoltageValue2() {
        return voltageValue2;
    }

    public void setVoltageValue2(String voltageValue2) {
        this.voltageValue2 = voltageValue2;
    }

    public String getVoltageValue3() {
        return voltageValue3;
    }

    public void setVoltageValue3(String voltageValue3) {
        this.voltageValue3 = voltageValue3;
    }

    public Integer getFlowState1() {
        return flowState1;
    }

    public void setFlowState1(Integer flowState1) {
        this.flowState1 = flowState1;
    }

    public Integer getFlowState2() {
        return flowState2;
    }

    public void setFlowState2(Integer flowState2) {
        this.flowState2 = flowState2;
    }

    public Integer getFlowState3() {
        return flowState3;
    }

    public void setFlowState3(Integer flowState3) {
        this.flowState3 = flowState3;
    }

    public String getMeritoriousPowerValue1() {
        return meritoriousPowerValue1;
    }

    public void setMeritoriousPowerValue1(String meritoriousPowerValue1) {
        this.meritoriousPowerValue1 = meritoriousPowerValue1;
    }

    public String getMeritoriousPowerValue2() {
        return meritoriousPowerValue2;
    }

    public void setMeritoriousPowerValue2(String meritoriousPowerValue2) {
        this.meritoriousPowerValue2 = meritoriousPowerValue2;
    }

    public String getMeritoriousPowerValue3() {
        return meritoriousPowerValue3;
    }

    public void setMeritoriousPowerValue3(String meritoriousPowerValue3) {
        this.meritoriousPowerValue3 = meritoriousPowerValue3;
    }

    public String getPowerFactorValue1() {
        return powerFactorValue1;
    }

    public void setPowerFactorValue1(String powerFactorValue1) {
        this.powerFactorValue1 = powerFactorValue1;
    }

    public String getPowerFactorValue2() {
        return powerFactorValue2;
    }

    public void setPowerFactorValue2(String powerFactorValue2) {
        this.powerFactorValue2 = powerFactorValue2;
    }

    public String getPowerFactorValue3() {
        return powerFactorValue3;
    }

    public void setPowerFactorValue3(String powerFactorValue3) {
        this.powerFactorValue3 = powerFactorValue3;
    }

    public String getFrequencyValue1() {
        return frequencyValue1;
    }

    public void setFrequencyValue1(String frequencyValue1) {
        this.frequencyValue1 = frequencyValue1;
    }

    public String getFrequencyValue2() {
        return frequencyValue2;
    }

    public void setFrequencyValue2(String frequencyValue2) {
        this.frequencyValue2 = frequencyValue2;
    }

    public String getFrequencyValue3() {
        return frequencyValue3;
    }

    public void setFrequencyValue3(String frequencyValue3) {
        this.frequencyValue3 = frequencyValue3;
    }

    public String getElectricValue1() {
        return electricValue1;
    }

    public void setElectricValue1(String electricValue1) {
        this.electricValue1 = electricValue1;
    }

    public String getElectricValue2() {
        return electricValue2;
    }

    public void setElectricValue2(String electricValue2) {
        this.electricValue2 = electricValue2;
    }

    public String getElectricValue3() {
        return electricValue3;
    }

    public void setElectricValue3(String electricValue3) {
        this.electricValue3 = electricValue3;
    }
}
