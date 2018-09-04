package com.edu.snmp.groovy.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 编码工具。解决 Snmp 可能采集到中文的问题。
 * 
 * @author zhangrongc
 */
public class DecodingUtil {

	public static final Logger logger = LogManager.getLogger(DecodingUtil.class);

	public static String decoding(final String octetString, final String charset) {
		if ((octetString == null) || (octetString.trim().length() == 0)
				|| !octetString.matches("[0-9a-f]{2}(:[0-9a-f]{2})+")) {
			return octetString;
		}

		String aOctetString = octetString;

		if (aOctetString.endsWith(":00")) {
			aOctetString = aOctetString.substring(0, aOctetString.lastIndexOf(":00"));
		}

		String value = "";

		try {
			String[] values = aOctetString.split(":");
			byte[] bytes = new byte[values.length];

			for (int j = 0; j < values.length; j++) {
				bytes[j] = (byte) Integer.parseInt(values[j], 16);
			}

			value = new String(bytes, charset);
		} catch (Exception e) {
			logger.warn("转码错误", e);
		}

		return value;
	}

	public static void main(String[] args) {
		String sss = "4d:53:20:54:43:50:20:4c:6f:6f:70:62:61:63:6b:20:69:6e:74:65:72:66:61:63:65:00";
		sss = "48:50:20:4e:43:31:30:35:69:20:50:43:49:65:20:47:69:67:61:62:69:74:20:53:65:72:76:65:72:20:41:64:61:70:74:65:72:00";
		sss = "45:3a:5c:20:4c:61:62:65:6c:3a:b2:e2:ca:d4:20:20:53:65:72:69:61:6c:20:4e:75:6d:62:65:72:20:63:62:36:38";
		System.out.println(decoding(sss, "utf8"));
	}

}
