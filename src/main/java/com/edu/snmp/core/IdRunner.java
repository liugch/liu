package com.edu.snmp.core;

import java.util.UUID;

public class IdRunner {
	public static String uniqueID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
