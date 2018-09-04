def perfList = []

//wuhao
SnmpValue hrHostname = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.5.0");
if (!StringUtils.isBlank(hrHostname)){
perfList.add(['主机名', "hrSystem", hrHostname.getValue()]);
}

SnmpValue hrHostinfo = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.1.0");
if (!StringUtils.isBlank(hrHostinfo)){
perfList.add(['主机描述', "hrSystem", hrHostinfo.getValue()]);
}
SnmpValue hrMonitorTime = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.3.0");
if (!StringUtils.isBlank(hrMonitorTime)){
perfList.add(['监控持续时间', "hrSystem", hrMonitorTime.getValue()]);
}
//时间不会处理-吴昊

SnmpValue hrUser = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.4.0");
if (!StringUtils.isBlank(hrUser)){
perfList.add(['负责人', "hrSystem", hrUser.getValue()]);
}

SnmpValue hrAddress = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.6.0");
if (!StringUtils.isBlank(hrAddress)){
perfList.add(['所在位置', "hrSystem", hrAddress.getValue()]);
}

SnmpValue hrUserNum = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.5.0");
if (!StringUtils.isBlank(hrUserNum)){
perfList.add(['当前连接用户数', "hrSystem", hrUserNum.getValue()]);
}

SnmpValue hrProcessesNum = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.6.0");
if (!StringUtils.isBlank(hrProcessesNum)){
perfList.add(['当前系统正在运行的进程', "hrSystem", hrProcessesNum.getValue()]);
}

SnmpValue hrSystemUptime = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.1.0");
SnmpValue hrSystemDate = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.2.0");
if (!StringUtils.isBlank(hrSystemDate)){
Date _hrSystemDate = parseDateAndTime(hrSystemDate.getValue());
SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
perfList.add(['远程主机时间', "hrSystem", sdf.format(_hrSystemDate)]);
}
if (!StringUtils.isBlank(hrSystemUptime)){
perfList.add(['启动时间', "hrSystem", hrSystemUptime.getValue()]);
}



private Date parseDateAndTime(final String dateAndTime) {
	String[] strs = dateAndTime.split(":");
	int[] bytes = new int[strs.length];

	for (int i = 0; i < strs.length; i++)
		bytes[i] = Integer.parseInt(strs[i], 16);

	Calendar cal = Calendar.getInstance();
	cal.set(bytes[0] * 256 + bytes[1], bytes[2] - 1, bytes[3], bytes[4], bytes[5], bytes[6]);

	return cal.getTime();
}

//
return perfList;