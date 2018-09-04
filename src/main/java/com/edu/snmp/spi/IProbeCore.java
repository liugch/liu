package com.edu.snmp.spi;


import com.edu.snmp.template.Template;

/**
 * 采集器核心接口.
 * 
 * @author zhangrong
 */
public interface IProbeCore {

	/**
	 * 创建采集器核心.<br/>
	 * 用于采集器核心的生命周期管理.
	 * 
	 * @param id 采集器标识
	 * @param template 模版
	 */
	void create(String id, Template template);

	/**
	 * 设置采集器核心参数.
	 * 
	 * @param id 采集器标识
	 * @param probeProfile 采集器场景描述
	 * @param template 模版
	 */
	void setCfg(String id, ProbeProfile probeProfile, Template template);

	/**
	 * 初始化采集器核心.
	 * 
	 * @param id 采集器标识
	 */
	void init(String id);

	/**
	 * 停止采集器核心.
	 * 
	 * @param id 采集器标识
	 */
	void stop(String id);

	/**
	 * 销毁采集器核心.<br/>
	 * 用于采集器核心的生命周期管理.
	 * 
	 * @param id 采集器标识
	 */
	void destroy(String id);

	/**
	 * 直接调用一次采集.
	 * 
	 * @param id 采集器标识
	 */
	Object exec(String id);

}
