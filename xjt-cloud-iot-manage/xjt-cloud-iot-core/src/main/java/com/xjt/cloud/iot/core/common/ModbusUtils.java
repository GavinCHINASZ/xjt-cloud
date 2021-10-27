package com.xjt.cloud.iot.core.common;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.ip.listener.TcpListener;
import com.serotonin.modbus4j.ip.listener.TcpMultiListener;
import com.serotonin.modbus4j.msg.*;
import com.xjt.cloud.commons.utils.SysLog;

import java.net.UnknownHostException;

public class ModbusUtils {
	
	private static ModbusFactory modbusFactory;

	/**
	 * 静态块初始化
	 */
	static {
		if (modbusFactory == null) {
			modbusFactory = new ModbusFactory();
		}
	}

	/**
	 * 获取单连接对象TcpListener
	 * 
	 * @param port
	 * @param retries
	 * @return
	 */
	public static TcpListener getTcpListener(int port, int retries) {
		IpParameters params = new IpParameters();
		params.setPort(port);
		params.setEncapsulated(false);
		TcpListener listener = (TcpListener) modbusFactory.createTcpListener(params);
		// 设置重连次数
		listener.setRetries(retries);
		try {
			listener.init();
		} catch (ModbusInitException e) {
			SysLog.error(e);
		}
		return listener;
	}

	/**
	 * 获取多连接对象TcpMultiListener
	 * 
	 * @param port
	 * @param retries
	 * @return
	 */
	public static TcpMultiListener getTcpMultiListener(int port, int retries) {
		IpParameters params = new IpParameters();
		params.setPort(port);
		params.setEncapsulated(false);
		TcpMultiListener listener = (TcpMultiListener) modbusFactory.createTcpMultiListener(params);
		// 设置重连次数
		listener.setRetries(retries);
		try {
			listener.init(listener);
		} catch (ModbusInitException e) {
			SysLog.error(e);
		}
		return listener;
	}

	/**
	 * 获取ModbusMaster
	 * 
	 * @param port
	 * @param ip
	 * @return
	 */
	public static ModbusMaster getModbusMaster(int port, String ip) {
		IpParameters params = new IpParameters();
		params.setHost(ip);
		params.setPort(port);
		ModbusFactory modbusFactory = new ModbusFactory();
		ModbusMaster master = modbusFactory.createTcpMaster(params, true);
		try {
			master.init();
		} catch (ModbusInitException e) {
			SysLog.error(e);
		}
		return master;
	}
	/**
	 * 读取HLI系统区判断HLI是否在线
	 * @param listener
	 * @param slaveId
	 * @param startOffset
	 * @param numberOfRegisters
	 * @param registerCode
	 * @return
	 */
	public static short[] readHLIOnOrOff(TcpMultiListener listener,int slaveId,int startOffset,int numberOfRegisters, String registerCode) {
		try {
			if (listener.getMap().containsKey(registerCode)) {
				ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, startOffset, numberOfRegisters);
				ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) listener.send(request, registerCode);
				if (response == null) {
					return null;
				}
				if (response.isException()) {
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}

	/**
	 * 读取保持寄存器数据
	 *
	 * @param slaveId
	 *            slave Id
	 * @param
	 *
	 */
	public static short[] readHoldingRegister(TcpMultiListener listener, int slaveId, int startOffset, int numberOfRegisters, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, startOffset,
						numberOfRegisters);
				ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) listener.getResponse(request, registerCode);
				if (response == null) {
					return null;
				}
				if (response.isException()) {
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}

	/**
	 * 开关数据 读取外围设备输入的开关量
	 * 
	 * @throws UnknownHostException
	 */
	public static boolean[] readInputStatus(TcpMultiListener listener, int slaveId, int startOffset,
			int numberOfRegisters, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, startOffset, numberOfRegisters);
				ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) listener.getResponse(request,registerCode);
				if (response == null) {
					return null;
				}
				if (response.isException()) {
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getBooleanData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}
	/**
	 * 读取自定义功能码68
	 *
	 * @param slaveId
	 */
	public static short[] readAutoDefine68(TcpMultiListener listener, int slaveId, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadAutoDefine68Request request = new ReadAutoDefine68Request(slaveId);
				ReadAutoDefine68Response response = (ReadAutoDefine68Response) listener.getResponse(request, registerCode);
				if (response == null) {
					SysLog.info("读取自定义功能码68返回值为：null  !!!!!!!!!!!!!!!!!!!!!!");
					return null;
				}
				if (response.isException()) {
					SysLog.info("读取自定义功能码68返回异常？？？？？？？？？？？？？？？？？");
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}
	/**
	 * 读取自定义功能码6B
	 *
	 * @param slaveId  从机Id
	 *            
	 */
	public static short[] readAutoDefine6B(TcpMultiListener listener, int slaveId, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadAutoDefine6BRequest request = new ReadAutoDefine6BRequest(slaveId);
				ReadAutoDefine6BResponse response = (ReadAutoDefine6BResponse) listener.getResponse(request, registerCode);
				if (response == null) {
					SysLog.info("读取自定义功能码6B返回值为：null  !!!!!!!!!!!!!!!!!!!!!!");
					return null;
				}
				if (response.isException()) {
					SysLog.info("读取自定义功能码6B返回异常？？？？？？？？？？？？？？？？？");
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}
	/**
	 * 读取自定义功能码64
	 *
	 * @param slaveId  从机Id
	 *            
	 */
	public static short[] readAutoDefine64(TcpMultiListener listener, int slaveId, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadAutoDefine64Request request = new ReadAutoDefine64Request(slaveId);
				ReadAutoDefine64Response response = (ReadAutoDefine64Response) listener.getResponse(request, registerCode);
				if (response == null) {
					SysLog.info("读取自定义功能码64返回值为：null  !!!!!!!!!!!!!!!!!!!!!!");
					return null;
				}
				if (response.isException()) {
					SysLog.info("读取自定义功能码64返回异常？？？？？？？？？？？？？？？？？");
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}
	/**
	 * 读取自定义功能码65
	 *
	 * @param slaveId  从机Id
	 *            
	 */
	public static short[] readAutoDefine65(TcpMultiListener listener, int slaveId, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadAutoDefine65Request request = new ReadAutoDefine65Request(slaveId);
				ReadAutoDefine65Response response = (ReadAutoDefine65Response) listener.getResponse(request, registerCode);
				if (response == null) {
					SysLog.info("读取自定义功能码65返回值为：null  !!!!!!!!!!!!!!!!!!!!!!");
					return null;
				}
				if (response.isException()) {
					SysLog.info("读取自定义功能码65返回异常？？？？？？？？？？？？？？？？？");
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}
	/**
	 * 读取自定义功能码69
	 *
	 * @param slaveId  从机Id
	 *            
	 */
	public static short[] readAutoDefine69(TcpMultiListener listener, int slaveId, String registerCode) {
		try {
			if(listener.getMap().containsKey(registerCode)){
				ReadAutoDefine69Request request = new ReadAutoDefine69Request(slaveId);
				ReadAutoDefine69Response response = (ReadAutoDefine69Response) listener.getResponse(request, registerCode);
				if (response == null) {
					SysLog.info("读取自定义功能码69返回值为：null  !!!!!!!!!!!!!!!!!!!!!!");
					return null;
				}
				if (response.isException()) {
					SysLog.info("读取自定义功能码69返回异常？？？？？？？？？？？？？？？？？");
					SysLog.info("Exception response:message" + response.getExceptionMessage());
					return null;
				}
				return response.getShortData();
			}
		} catch (ModbusTransportException e) {
			SysLog.error(e);
		} catch (Exception e) {
			SysLog.error(e);
		}
		return null;
	}
}
