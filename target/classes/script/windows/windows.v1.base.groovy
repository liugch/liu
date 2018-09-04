
def perfList = []

//wuhao
SnmpValue hrHostname = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.5.0");
if(hrHostname !=null)
{
	perfList.add(['主机名', "hrSystem", hrHostname.getValue()]);
}

SnmpValue hrHostinfo = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.1.0");
if(hrHostinfo!=null)
{
	perfList.add(['操作系统', "hrSystem", osName(hrHostinfo.getValue())]);
}
SnmpValue hrMonitorTime = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.3.0");
if(hrMonitorTime!=null)
{
	perfList.add(['监控持续时间', "hrSystem", hrMonitorTime.getValue()]);
}
//时间不会处理-吴昊

SnmpValue hrUser = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.4.0");
if(hrUser!=null)
{
	perfList.add(['负责人', "hrSystem", hrUser.getValue()]);
}
SnmpValue hrAddress = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.6.0");
if(hrAddress!=null)
{
	perfList.add(['所在位置', "hrSystem", hrAddress.getValue()]);
}
// SnmpValue hrServicesNum = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.7.0");
// if(hrServicesNum!=null)
// {
// perfList.add(['服务类型', "hrSystem", hrServicesNum.getValue()]);
// }
SnmpValue hrUserNum = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.5.0");
if(hrUserNum!=null)
{
	perfList.add(['连接用户数', "hrSystem", hrUserNum.getValue()]);
}
SnmpValue hrProcessesNum = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.6.0");
if(hrProcessesNum!=null)
{
	perfList.add(['系统进程数', "hrSystem", hrProcessesNum.getValue()]);
}

//系统进程列表和软件列表不会写，正在运行的进程 .1.3.6.1.2.1.25.4.2.1.2 安装的软件 .1.3.6.1.2.1.25.6.3.1.2


//例子-张荣写好的
SnmpValue hrSystemUptime = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.1.0");
SnmpValue hrSystemDate = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.1.2.0");
if(hrSystemUptime !=null)
{
	String tmp = hrSystemUptime.getValue()
	String str = tmp.split(",")[0]
	perfList.add(['启动时间', "hrSystem", str]);
}
if(hrSystemDate!=null)
{
	Date _hrSystemDate = parseDateAndTime(hrSystemDate.getValue());

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	perfList.add(['主机时间', "hrSystem", sdf.format(_hrSystemDate)]);
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
perfList.add(['地址', "hrSystem",config.host]);
return perfList;

private String osName(String str){
	if(str.contains("7601")){
		return "Windows 2008/Windows 7"
	}else if(str.contains("9200")){
		return "Windows 8"
	}else if(str.contains("Windows Version 6.1")){
		return "Windows Server 2008 R2"
	}else if(str.contains("6000")){
		return "Windows  Vista"
	}else if(str.contains("Windows Version 6.0")){
		return "Windows Server 2008"
	}else if(str.contains("3790")){
		return "Windows XP Professional x64 Edition"
	}else if(str.contains("Windows Version 5.1")){
		return "Windows XP"
	}else if(str.contains("Windows Version 5.0")){
		return "Windows 2000 Professional"
	}else if(str.contains("Windows Version 4.9")){
		return "Windows Me"
	}

}