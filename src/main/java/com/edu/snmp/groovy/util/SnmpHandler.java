package com.edu.snmp.groovy.util;

import java.io.IOException;

import org.snmp4j.Snmp;
import org.snmp4j.Target;

/**
 * 描述执行向SNMP Agent中发送请求，并对返回结果进行处理的功能接口。
 * 
 * @author James Gao
 * @since 1.3 2008-3-13
 */
public interface SnmpHandler {

	/**
	 * 执行向SNMP Agent中发送请求，并对返回结果进行处理。
	 * 
	 * @param snmp SNMP Agent访问器。
	 * @param target SNMP Agent信息。
	 * @throws IOException 如果在与SNMP Agent的通信过程中发生异常情况，那么
	 *             将抛出该异常。
	 */
	public void handle(Snmp snmp, Target target) throws IOException;

}
