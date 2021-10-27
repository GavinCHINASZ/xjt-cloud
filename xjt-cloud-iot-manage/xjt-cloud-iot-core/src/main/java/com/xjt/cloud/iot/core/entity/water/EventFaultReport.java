package com.xjt.cloud.iot.core.entity.water;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xjt.cloud.commons.utils.DateUtils;
import com.xjt.cloud.commons.utils.StringUtils;

import java.util.Date;

/**
 * 报表信息实体类
 *
 * @author wangzhiwen
 * @date 2020/12/8 14:50
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventFaultReport {
    /**
     * 异常总数
     */
    private Integer faultCount;
    /**
     * 总数
     */
    private Integer totalCount;
    /**
     * 异常类型
     */
    private Integer faultEventType;
    /**
     * 事件类型
     */
    private Integer eventType;
    /**
     * 设备类型 默认 1 默认设备  2水压监测  3智能水浸 4火警主机 5极早期预警 6火眼报警 7声光 8智能消火栓 9可然气  10电气火灾 11烟感 ',
     */
    private Integer deviceType;
    /**
     * 异常类型
     */
    private String faultType;
    /**
     * 颜色
     */
    private String faultColor;
    private Date beginTime;
    private Date endTime;
    private Long projectId;
    /**
     * 已处理
     */
    private Integer processed;
    /**
     * 未处理
     */
    private Integer untreated;
    /**
     * 维护建议/处理建议
     */
    private String repairProposal;
    /**
     * 设备故障信息id
     */
    private Long deviceFaultTypeId;
    private String[] images;

    /**
     * 设备数量
     */
    private Integer deviceSum;
    /**
     * 事件数量
     */
    private Integer eventSum;
    /**
     * 异常数量
     */
    private Integer faultSum;

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Long getDeviceFaultTypeId() {
        return deviceFaultTypeId;
    }

    public void setDeviceFaultTypeId(Long deviceFaultTypeId) {
        this.deviceFaultTypeId = deviceFaultTypeId;
    }

    public String getRepairProposal() {
        return repairProposal;
    }

    public String getRepairProposalDesc() {
        if (StringUtils.isEmpty(repairProposal)){
            return "";
        }
        return repairProposal;
    }

    public void setRepairProposal(String repairProposal) {
        this.repairProposal = repairProposal;
    }

    public Integer getProcessed() {
        return processed;
    }

    public String getProcessedDesc() {
        if (processed != null && processed > 0){
            return "已处理 " + processed + " 次";
        }else {
            return "待处理 " + untreated + " 次";
        }
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public Integer getUntreated() {
        return untreated;
    }

    public void setUntreated(Integer untreated) {
        this.untreated = untreated;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Date getEndTimeDesc() {
        if (null == endTime) {
            return null;
        }
        return DateUtils.add24Hours(endTime);
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDeviceTypeDesc() {
        if (deviceType != null) {
            if (deviceType == 1) {
                return "默认设备";
            } else if (deviceType == 2) {
                return "水压监测";
            } else if (deviceType == 3) {
                return "智能水浸";
            } else if (deviceType == 4) {
                return "火警主机";
            } else if (deviceType == 5) {
                return "极早期预警";
            } else if (deviceType == 6) {
                return "火眼报警";
            } else if (deviceType == 7) {
                return "声光报警";
            } else if (deviceType == 8) {
                return "智能消火栓";
            } else if (deviceType == 9) {
                return "可然气";
            } else if (deviceType == 10) {
                return "电气火灾";
            } else if (deviceType == 11) {
                return "智能烟感";
            }
        }
        return null;
    }

    public String getDevicesTypeDesc() {
        String deviceTypeString = "";
        if (deviceType != null) {
            if (deviceType == 1) {
                deviceTypeString = "默认设备";
            } else if (deviceType == 2) {
                deviceTypeString = "水压监测";
            } else if (deviceType == 3) {
                deviceTypeString = "智能水浸";
            } else if (deviceType == 4) {
                deviceTypeString = "火警主机";
            } else if (deviceType == 5) {
                deviceTypeString = "极早期预警";
            } else if (deviceType == 6) {
                deviceTypeString = "火眼报警";
            } else if (deviceType == 7) {
                deviceTypeString = "声光报警";
            } else if (deviceType == 8) {
                deviceTypeString = "智能消火栓";
            } else if (deviceType == 9) {
                deviceTypeString = "可然气";
            } else if (deviceType == 10) {
                deviceTypeString = "电气火灾";
            } else if (deviceType == 11) {
                deviceTypeString = "智能烟感";
            }
            if (StringUtils.isNotEmpty(deviceTypeString)){
                return deviceTypeString + (deviceSum != null ? deviceSum + "次" :"");
            }
        }
        return deviceTypeString;
    }

    public String getEventTypeDesc() {
        if (deviceType == null || faultEventType == null) {
            return "未处理";
        }
        String res = "其它";
        if (deviceType == 8) {
            //消火栓
            if (faultEventType == 1) {
                //报警事件（说明：包含超高、超低、漏水、取水）
                res = "报警事件";
            } else if (faultEventType == 2) {//撞击/开盖
                res = "撞击损坏";
            } else if (faultEventType == 3) {
                //故障/撞击传感器故障/漏水传感器故障
                res = "故障事件";
            } else if (faultEventType == 4) {
                res = "低电量";
            } else if (faultEventType == 5) {
                res = "离线";
            }
        } else if (deviceType == 2) {
            //水压
            if (faultEventType == 1) {
                res = "超高";
            } else if (faultEventType == 2) {
                res = "超低";
            } else if (faultEventType == 3) {
                res = "电量低";
            } else if (faultEventType == 4) {
                res = "离线";
            }
        } else if (deviceType == 3) {
            //水浸，7为信息判断时的值，
            if (faultEventType == 1) {//漏水
                res = "漏水";
            } else if (faultEventType == 2) {//电量低
                res = "电量低";
            } else if (faultEventType == 3) {//离线
                res = "离线";
            }
        } else if (deviceType == 4) {
            // 火警主机 1火警, 7故障, 2监视, 21离线
            if (faultEventType == 1) {//
                res = "火警事件";
            } else if (faultEventType == 2) {//监视事件
                res = "监视事件";
            } else if (faultEventType == 7) {//故障事件
                res = "故障事件";
            } else if (faultEventType == 21) {//
                res = "离线";
            }
        } else if (deviceType == 5) {//
            // 极早期 事件类型: 火警1=3, 火警2=4, 行动=2, 警告=1, 故障0, 离线5
            if (faultEventType == 3) {//火警1
                res = "火警1";
            } else if (faultEventType == 4) {//火警2
                res = "火警2";
            } else if (faultEventType == 2) {//行动
                res = "行动";
            } else if (faultEventType == 1) {//警告
                res = "警告";
            } else if (faultEventType == 0) {//故障
                res = "故障";
            } else if (faultEventType == 5) {//
                res = "离线";
            }
        } else if (deviceType == 7) {
            // 声光报警 事件类型: 2报警, 3离线
            if (faultEventType == 2) {//报警
                res = "报警";
            } else if (faultEventType == 3) {//离线
                res = "离线";
            }
        } else if (deviceType == 10) {
            // 电气火灾
            if (faultEventType == 1) {//漏电报警
                res = "漏电报警";
            } else if (faultEventType == 2) {//温度报警
                res = "温度报警";
            } else if (faultEventType == 3) {//故障事件
                res = "故障事件";
            } else if (faultEventType == 4) {//离线
                res = "离线";
            }
        } else if (deviceType == 11) {
            // 烟感 1烟雾报警（报警）, 4低电量, 5传感器故障 （故障）, 21离线
            if (faultEventType == 1) {//火警事件
                res = "火警事件";
            } else if (faultEventType == 5) {//故障事件
                res = "故障事件";
            } else if (faultEventType == 4) {//低电量
                res = "低电量";
            } else if (faultEventType == 21) {//
                res = "离线";
            }
        }

        return res;
    }

    public String getEventsTypeDesc() {
        if (deviceType == null || faultEventType == null) {
            return "";
        }
        String res = "其它";
        if (deviceType == 8) {
            //消火栓
            if (faultEventType == 1) {
                //报警事件（说明：包含超高、超低、漏水、取水）
                res = "报警事件";
            } else if (faultEventType == 2) {//撞击/开盖
                res = "撞击损坏";
            } else if (faultEventType == 3) {
                //故障/撞击传感器故障/漏水传感器故障
                res = "故障事件";
            } else if (faultEventType == 4) {
                res = "低电量";
            } else if (faultEventType == 5) {
                res = "离线";
            }
        } else if (deviceType == 2) {
            //水压
            if (faultEventType == 1) {
                res = "超高";
            } else if (faultEventType == 2) {
                res = "超低";
            } else if (faultEventType == 3) {
                res = "电量低";
            } else if (faultEventType == 4) {
                res = "离线";
            }
        } else if (deviceType == 3) {
            //水浸，7为信息判断时的值，
            if (faultEventType == 1) {//漏水
                res = "漏水";
            } else if (faultEventType == 2) {//电量低
                res = "电量低";
            } else if (faultEventType == 3) {//离线
                res = "离线";
            }
        } else if (deviceType == 4) {
            // 火警主机 1火警, 7故障, 2监视, 21离线
            if (faultEventType == 1) {//
                res = "火警事件";
            } else if (faultEventType == 2) {//监视事件
                res = "监视事件";
            } else if (faultEventType == 7) {//故障事件
                res = "故障事件";
            } else if (faultEventType == 21) {//
                res = "离线";
            }
        } else if (deviceType == 5) {//
            // 极早期 事件类型: 火警1=3, 火警2=4, 行动=2, 警告=1, 故障0, 离线5
            if (faultEventType == 3) {//火警1
                res = "火警1";
            } else if (faultEventType == 4) {//火警2
                res = "火警2";
            } else if (faultEventType == 2) {//行动
                res = "行动";
            } else if (faultEventType == 1) {//警告
                res = "警告";
            } else if (faultEventType == 0) {//故障
                res = "故障";
            } else if (faultEventType == 5) {//
                res = "离线";
            }
        } else if (deviceType == 7) {
            // 声光报警 事件类型: 2报警, 3离线
            if (faultEventType == 2) {//报警
                res = "报警";
            } else if (faultEventType == 3) {//离线
                res = "离线";
            }
        } else if (deviceType == 10) {
            // 电气火灾
            if (faultEventType == 1) {//漏电报警
                res = "漏电报警";
            } else if (faultEventType == 2) {//温度报警
                res = "温度报警";
            } else if (faultEventType == 3) {//故障事件
                res = "故障事件";
            } else if (faultEventType == 4) {//离线
                res = "离线";
            }
        } else if (deviceType == 11) {
            // 烟感 1烟雾报警（报警）, 4低电量, 5传感器故障 （故障）, 21离线
            if (faultEventType == 1) {
                res = "火警事件";
            } else if (faultEventType == 5) {
                res = "故障事件";
            } else if (faultEventType == 4) {
                res = "低电量";
            } else if (faultEventType == 21) {
                res = "离线";
            }
        }
        return res + (eventSum != null ? eventSum + "次" : "");
    }

    public String getEventColorDesc() {
        if (deviceType == null || faultEventType == null) {
            return null;
        }
        String res = "000000";
        if (deviceType == 8) {
            //消火栓
            if (faultEventType == 1) {
                //报警事件（说明：包含超高、超低、漏水、取水）
                res = "af161b";
            } else if (faultEventType == 2) {//撞击/开盖
                res = "f5481e";
            } else if (faultEventType == 3) {
                //故障/撞击传感器故障/漏水传感器故障
                res = "f9a825";
            } else if (faultEventType == 4) {
                res = "f57f17";
            } else if (faultEventType == 5) {
                res = "756e75";
            }
        } else if (deviceType == 2) {
            //水压
            if (faultEventType == 1) {
                res = "f44336";
            } else if (faultEventType == 2) {
                res = "4fc3f7";
            } else if (faultEventType == 3) {
                res = "f57f17";
            } else if (faultEventType == 4) {
                res = "433942";
            }
        } else if (deviceType == 3) {
            //水浸，7为信息判断时的值，
            if (faultEventType == 1) {//漏水
                res = "4fc3f7";
            } else if (faultEventType == 2) {//电量低
                res = "f57f17";
            } else if (faultEventType == 3) {//离线
                res = "433942";
            }
        } else if (deviceType == 4) {
            // 火警主机
            if (faultEventType == 1) {//
                res = "c81e29";
            } else if (faultEventType == 2) {//监视事件
                res = "4fc3f7";
            } else if (faultEventType == 7) {//故障事件
                res = "f57f17";
            } else if (faultEventType == 21) {//
                res = "756e75";
            }
        } else if (deviceType == 5) {//
            // 极早期
            if (faultEventType == 1) {//火警1
                res = "c81e29";
            } else if (faultEventType == 2) {//火警2
                res = "d83a13";
            } else if (faultEventType == 3) {//行动
                res = "4fc3f7";
            } else if (faultEventType == 4) {//警告
                res = "2576fc";
            } else if (faultEventType == 5) {//故障
                res = "f57f17";
            } else if (faultEventType == 0) {//
                res = "756e75";
            }
        } else if (deviceType == 7) {
            // 声光报警
            if (faultEventType == 1) {//报警
                res = "c81e29";
            } else if (faultEventType == 2) {//离线
                res = "756e75";
            }
        } else if (deviceType == 10) {
            // 电气火灾
            if (faultEventType == 1) {//漏电报警
                res = "c81e29";
            } else if (faultEventType == 2) {//温度报警
                res = "d83a13";
            } else if (faultEventType == 3) {//故障事件
                res = "f57f17";
            } else if (faultEventType == 4) {//离线
                res = "756e75";
            }
        } else if (deviceType == 11) {
            // 烟感
            if (faultEventType == 1) {//火警事件
                res = "c81e29";
            } else if (faultEventType == 2) {//故障事件
                res = "f57f17";
            } else if (faultEventType == 3) {//低电量
                res = "f57f17";
            } else if (faultEventType == 4) {//
                res = "756e75";
            }
        }

        return res;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public Integer getFaultEventType() {
        return faultEventType;
    }

    public void setFaultEventType(Integer faultEventType) {
        this.faultEventType = faultEventType;
    }

    public String getFaultType() {
        return faultType;
    }

    public String getFaultTypeDesc() {
        if (processed != null && processed > 0){
            return faultType + " " + faultCount + " 次";
        }
        return "";
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getFaultColor() {
        if(faultEventType == null){
            return "d9d6cf";
        }
        return faultColor;
    }

    public void setFaultColor(String faultColor) {
        this.faultColor = faultColor;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Integer getDeviceSum() {
        return deviceSum;
    }

    public void setDeviceSum(Integer deviceSum) {
        this.deviceSum = deviceSum;
    }

    public Integer getEventSum() {
        return eventSum;
    }

    public void setEventSum(Integer eventSum) {
        this.eventSum = eventSum;
    }

    public Integer getFaultSum() {
        return faultSum;
    }

    public void setFaultSum(Integer faultSum) {
        this.faultSum = faultSum;
    }
}
