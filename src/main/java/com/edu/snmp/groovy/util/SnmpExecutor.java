package com.edu.snmp.groovy.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.DefaultTimeoutModel;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.transport.UdpTransportMapping;

/**
 * 描述通过SNMP协议，访问远程带有并开启SNMP访问功能的网络设备，执行指定数据
 * 访问的SNMP执行器实现。
 * <p>
 * SNMP执行器管理与SNMP Agent的连接建立和关闭。通过SnmpHandler回调机制， 在SNMP执行器和SNMP Agent的通信过程中，由外部具体实现类实现对SNMP Agent的
 * 查询及结果的处理。最后在将查询结果处理完成后，SNMP执行器将会释放执行过程中占 用的系统资源。
 * </p>
 * <p>
 * 本类支持SNMP V1，V2c和V3版本的协议。
 * </p>
 * 
 * @author James Gao
 * @since 1.3 2008-3-13
 */
public class SnmpExecutor {
	/**
	 * SNMP协议版本：version 1。
	 */
	public final static int V1 = SnmpConstants.version1;

	/**
	 * SNMP协议版本：version 2c（带有Community方式的安全认证）。
	 */
	public final static int V2C = SnmpConstants.version2c;

	/**
	 * 日志记录。
	 */
	private final static Logger logger = LogManager.getLogger(SnmpExecutor.class);

	/**
	 * SNMP Agent的地址，默认为：udp:127.0.0.1/161。
	 */
	private Address agentAddress = GenericAddress.parse("udp:127.0.0.1/161");

	/**
	 * 只读方式访问SNMP Agent的密码，缺省为：public。
	 */
	private String readCommunity = "public";

	/**
	 * 与SNMP Agent通信使用的SNMP协议版本（V1，V2C或V3）。
	 */
	private int version = V2C;

	/**
	 * 重试次数，缺省次数为：3。
	 * <p>
	 * 如果在与SNMP Agent通信的过程中发生错误，那么将执行指定次数的重试操作。
	 * </p>
	 * <p>
	 * 如果经过重试后，仍然无法正常操作，那么将中断处理被那个抛出异常。
	 * </p>
	 */
	private int retries = 0;

	/**
	 * 请求响应的超时时间，单位为：ms（毫秒），默认为：10000（即：10秒）。
	 * <p>
	 * 由于与SNMP Agent的通信方式，采用异步通信模型。通过超时机制，实现异步 消息的接收。如果SNMP Agent的请求响应没有在超时时间内达到，那么将查询不到 任何信息。
	 * </p>
	 */
	private long timeout = 20000;

	/**
	 * 新建SNMP Agent访问执行器实例。
	 */
	public SnmpExecutor() {
	}

	/**
	 * 访问SNMP Agent，使用指定SNMP处理器，执行数据处理。
	 * 
	 * @param handler SNMP处理器。
	 * @throws Exception 
	 * @throws IOException 如果在通信的过程中发生异常情况，那么将抛出该异常。
	 */
	public void execute(SnmpHandler handler) throws IOException{
		logger.debug("Connecting to snmp agent: " + agentAddress);
		UdpTransportMapping transport = null;
		Snmp snmp = null;
		try {
			transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			// 开启对SNMP Agent的访问
			snmp.listen();
			snmp.setTimeoutModel(new DefaultTimeoutModel());
			Target target = create();
			// 访问SNMP agent，执行查询操作
			handler.handle(snmp, target);

		} catch (IOException e) {
			logger.warn("execute error!", e);
			throw e;
		} catch (Exception e) {
			logger.warn("execute error!", e);
		}finally {
			try {
				if(snmp!=null)
				{
				snmp.close();
				}
				if(transport!=null)
				{
				transport.close();
				}
			} catch (Exception e) {
				logger.warn("close error!", e);
			}
		}

		logger.debug("Disconnected.");
	}

	/**
	 * 根据配置信息，创建到SNMP Agent的信息。
	 * 
	 * @param snmp SNMP访问器。
	 * @return SNMP Agent的信息。
	 */
	private Target create() {
		Target retVal = null;
		if ((version == V2C) || (version == V1)) {
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString(readCommunity));
//			target.setRetries(retries);
//			target.setTimeout(timeout);
			target.setVersion(version);
			target.setAddress(agentAddress);
			retVal = target;
		}

		return retVal;
	}

	/**
	 * 得到SNMP Agent的地址，默认为：udp:127.0.0.1/161。
	 * 
	 * @return SNMP Agent的地址。
	 */
	public String getAgentAddress() {
		return agentAddress.toString();
	}

	/**
	 * 设置SNMP Agent的地址，默认为：udp:127.0.0.1/161。
	 * 
	 * @param agentAddress SNMP Agent的地址。
	 */
	public void setAgentAddress(String agentAddress) {
		this.agentAddress = GenericAddress.parse(agentAddress);
	}

	/**
	 * 得到只读方式访问SNMP Agent的密码，缺省为：public。
	 * 
	 * @return 通信密码。
	 */
	public String getReadCommunity() {
		return readCommunity;
	}

	/**
	 * 设置只读方式访问SNMP Agent的密码，缺省为：public。
	 * 
	 * @param readCommunity 通信密码。
	 */
	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}

	/**
	 * 得到与SNMP Agent通信使用的SNMP协议版本（V1，V2C或V3）。
	 * <p>
	 * 缺省为V2C版本。
	 * </p>
	 * 
	 * @return 协议版本。
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 设置与SNMP Agent通信使用的SNMP协议版本（V1，V2C或V3）。
	 * 
	 * @param version 协议版本。
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * 得到重试次数，缺省次数为：3。
	 * <p>
	 * 如果在与SNMP Agent通信的过程中发生错误，那么将执行指定次数的重试操作。
	 * </p>
	 * <p>
	 * 如果经过重试后，仍然无法正常操作，那么将中断处理被那个抛出异常。
	 * </p>
	 * 
	 * @return 重试次数。
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * 重试次数，缺省次数为：3。
	 * <p>
	 * 如果在与SNMP Agent通信的过程中发生错误，那么将执行指定次数的重试操作。
	 * </p>
	 * <p>
	 * 如果经过重试后，仍然无法正常操作，那么将中断处理被那个抛出异常。
	 * </p>
	 * 
	 * @param retries 重试次数，重试次数应大于0。
	 */
	public void setRetries(int retries) {
		if (retries < 1) {
			throw new IllegalArgumentException("The retries should be greater than 0.");
		}
		this.retries = retries;
	}

	/**
	 * 得到请求响应的超时时间，单位为：ms（毫秒），默认为：10000（即：10秒）。
	 * <p>
	 * 由于与SNMP Agent的通信方式，采用异步通信模型。通过超时机制，实现异步 消息的接收。如果SNMP Agent的请求响应没有在超时时间内达到，那么将查询不到 任何信息。
	 * </p>
	 * 
	 * @return 超时时间。
	 */
	public long getTimeout() {
		return timeout;
	}

	/**
	 * 设置请求响应的超时时间，单位为：ms（毫秒），默认为：10000（即：10秒）。
	 * <p>
	 * 由于与SNMP Agent的通信方式，采用异步通信模型。通过超时机制，实现异步 消息的接收。如果SNMP Agent的请求响应没有在超时时间内达到，那么将查询不到 任何信息。
	 * </p>
	 * 
	 * @param timeout 超时时间（ms），取值应大于0。
	 * 
	 */
	public void setTimeout(long timeout) {
		if (timeout < 1) {
			throw new IllegalArgumentException("The timeout should be greater than 0.");
		}
		this.timeout = timeout;
	}

	
	
}
