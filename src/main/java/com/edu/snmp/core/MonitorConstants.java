package com.edu.snmp.core;

import java.io.Serializable;

public interface MonitorConstants extends Serializable {

	public static final String TIME_FORMAT_TO_SECOND = "yyyy-MM-dd HH:mm:ss";

	public static final String TIME_FORMAT_TO_DAY = "yyyy-MM-dd";

	public static final String TIME_FORMAT_TO_WEEK = "yyyy-ww";

	public static final String TIME_FORMAT_TO_MONTH = "yyyy-MM";

	public static final String SYSLOG_FILTER_STATUS_UP = "1";

	public static final String SYSLOG_FILTER_STATUS_DOWN = "0";

	public static final String POLICY_EVENT_EXCHANGE = "amp.pevent";

	public static final String EVENT_EXCHANGE = "amp.event";

	public static final String FORMAT_FOR_EVENT_FROM_SYSLOG = "e.syslog.%s.%s";

	public static final String EVENT_LEVEL_CRITICAL = "critical";

	public static final String EVENT_LEVEL_MAJOR = "major";

	public static final String EVENT_LEVEL_MINOR = "minor";

	public static final String EVENT_LEVEL_WARNING = "warning";

	public static final String EVENT_LEVEL_INFO = "info";

	public static final String EVENT_LEVEL_OK = "ok";

	public static final String EVENT_LEVEL_UNKNOWN = "unknown";

	public static final String TEMPLATE_DISCOVE_TYPE_LINUX = "linux";

	public static final String TEMPLATE_DISCOVE_TYPE_WINDOWS = "windows";

	public static final String TEMPLATE_DISCOVE_TYPE_AIX = "aix";

	public static final String TEMPLATE_DISCOVE_TYPE_HOST = "主机";

	public static final String TEMPLATE_DISCOVE_TYPE_WEBLOGIC = "weblogic";

	public static final String TEMPLATE_DISCOVE_TYPE_WEBSPHERE = "websphere";

	public static final String TEMPLATE_DISCOVE_TYPE_DB2 = "db2";

	public static final String TEMPLATE_DISCOVE_TYPE_MYSQL = "mysql";

	public static final String TEMPLATE_DISCOVE_TYPE_ORACLE = "oracle";

	public static final String TEMPLATE_DISCOVE_TYPE_SQLSERVER = "sqlserver";

	public static final String TEMPLATE_DISCOVE_TYPE_KCXP = "KCXP";

	public static final String TEMPLATE_DISCOVE_TYPE_KCBP = "KCBP";

	public static final String TEMPLATE_DISCOVE_TYPE_KDMID = "KDMID";

	public static final String TEMPLATE_DISCOVE_TYPE_SFJY = "三方交易";

	public static final String TEMPLATE_DISCOVE_TYPE_SFCG = "三方存管";

	public static final String TEMPLATE_DISCOVE_TYPE_BPJ = "报盘机";

	public static final String TEMPLATE_DISCOVE_TYPE_BPJK = "报盘接口库";

	public static final String TEMPLATE_DISCOVE_TYPE_GTSJ = "柜台数据";

	public static final String TEMPLATE_DISCOVE_TYPE_HQKU = "行情库";

	public static final String TEMPLATE_DISCOVE_TYPE_ROUTER = "路由器";

	public static final String TEMPLATE_DISCOVE_TYPE_SWITCH = "交换机";

	public static final String TEMPLATE_DISCOVE_TYPE_FIREWALL = "防火墙";

	public static final String TEMPLATE_DISCOVE_TYPE_AP = "中继器";

	public static final String TEMPLATE_DISCOVE_TYPE_PRINTER = "打印机";

	public static final String TEMPLATE_DISCOVE_TYPE_UNKNOWN = "未知设备";

	public static final String TEMPLATE_ACQUISE_MODE_TELNET = "telnet";

	public static final String TEMPLATE_ACQUISE_MODE_SSH = "ssh";

	public static final String TEMPLATE_ACQUISE_MODE_WMI = "wmi";

	public static final String TEMPLATE_ACQUISE_MODE_AOM = "aom";

	public static final String TEMPLATE_ACQUISE_MODE_SNMP = "snmp";

	public static final String TEMPLATE_ACQUISE_MODE_KCMM = "kcmm";

	public static final String TEMPLATE_ACQUISE_MODE_JDBC = "jdbc";

	public static final String TEMPLATE_SCHEDULE_TYPE_SIMPLE = "simple";

	public static final String TEMPLATE_SCHEDULE_TYPE_CRONTAB = "crontab";

	public static final String TEMPLATE_TYPE_DEVICE = "device";

	public static final String TEMPLATE_TYPE_BUSINESS = "business";

	public static final String EXT_ATT_DISCOVER_TYPE = "discover_type";

	public static final String EXT_ATT_ACQUISE_MODE = "acquise_mode";

	public static final String MO_TYPE_COMMON = "common";

	public static final String MO_TYPE_DEVICE = "device";

	public static final String MO_TYPE_BUSINESS = "business";

	public static final String EVENT_NOTIFY_EXCH = "amp.notify";

	public static final String EVENT_NOTIFY_KEY_SYS = "N.0";

	public static final String EVENT_NOTIFY_KEY_MAIL = "N.1";

	public static final String EVENT_NOTIFY_KEY_SM = "N.2";

	public static final String EVENT_NOTIFY_KEY_ITIL = "N.3";

	public static final String EVENT_NOTIFY_KEY_AOM = "N.4";
}
