 package com.xjt.cloud.iot.core.entity.linkage;

import com.xjt.cloud.commons.base.BaseEntity;

 /**
  * @Description: 联动动作 实体类
  * @Author huanggc
  * @Date 2019/09/19
  **/
public class LinkageAction extends BaseEntity {
	 //序号
	private Integer orderNumber;

	//设备名称
	private String deviceName;

	//设备ID
	private Long deviceId;

	//设备二维码
	private String  deviceQrNo;

	//触发动作  1、开   2、关
	private Integer action;

	//联动控制ID
	private Long linkageControlId;

	//联动设备ID
	private Long linkageDeviceId;

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

	 public Integer getAction() {
		 return action;
	 }

	 public void setAction(Integer action) {
		 this.action = action;
	 }

	 public Long getLinkageControlId() {
		 return linkageControlId;
	 }

	 public void setLinkageControlId(Long linkageControlId) {
		 this.linkageControlId = linkageControlId;
	 }

	 public Long getLinkageDeviceId() {
		 return linkageDeviceId;
	 }

	 public void setLinkageDeviceId(Long linkageDeviceId) {
		 this.linkageDeviceId = linkageDeviceId;
	 }
 }