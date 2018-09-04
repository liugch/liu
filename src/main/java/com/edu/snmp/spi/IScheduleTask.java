package com.edu.snmp.spi;

/**
 * 具有可调度功能的任务接口类.
 * <p>
 * 用户在实现一个可由PMP容器调度的采集器核时, 必须实现此类的run()方法, 且中run()方法实现具体的业务逻辑.
 * </p>
 */
public interface IScheduleTask {

	/**
	 * 由调度任务调用一次采集, 此方法由容器的调度服务来调用.
	 * 
	 * @param id 采集器标识
	 */
	Object run(String id);

}
