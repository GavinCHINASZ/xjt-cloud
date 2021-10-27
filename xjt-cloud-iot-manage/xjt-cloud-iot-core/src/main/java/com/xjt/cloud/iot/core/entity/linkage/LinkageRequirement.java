 package com.xjt.cloud.iot.core.entity.linkage;

import com.xjt.cloud.commons.base.BaseEntity;

 /**
  * @Description: 联动条件 实体类
  * @Author huanggc
  * @Date 2019/09/19
  **/
public class LinkageRequirement extends BaseEntity {
	 //序号
	private Integer orderNumber;

	//设备名称
	private String deviceName;

	//设备ID
	private Long deviceId;

	//设备二维码
	private String  deviceQrNo;

	//触发事件 2超高　3超低
	private Integer event;

	//设备类型  1液位   2压力
	private Integer deviceType;

	//联动控制ID
	private Long linkageControlId;

	 public Integer getOrderNumber() {
		 return orderNumber;
	 }

	 public void setOrderNumber(Integer orderNumber) {
		 this.orderNumber = orderNumber;
	 }

	 public String getDeviceName() {
		 return deviceName;
	 }

	 public void setDeviceName(String deviceName) {
		 this.deviceName = deviceName;
	 }

	 public Long getDeviceId() {
		 return deviceId;
	 }

	 public void setDeviceId(Long deviceId) {
		 this.deviceId = deviceId;
	 }

	 public String getDeviceQrNo() {
		 return deviceQrNo;
	 }

	 public void setDeviceQrNo(String deviceQrNo) {
		 this.deviceQrNo = deviceQrNo;
	 }

	 public Integer getEvent() {
		 return event;
	 }

	 public void setEvent(Integer event) {
		 this.event = event;
	 }

	 public Integer getDeviceType() {
		 return deviceType;
	 }

	 public void setDeviceType(Integer deviceType) {
		 this.deviceType = deviceType;
	 }

	 public Long getLinkageControlId() {
		 return linkageControlId;
	 }

	 public void setLinkageControlId(Long linkageControlId) {
		 this.linkageControlId = linkageControlId;
	 }
 }