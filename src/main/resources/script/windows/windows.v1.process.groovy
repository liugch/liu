def perfList = []

String charset = config.encode

//需要代码
String[] OIDS = new String[8];
OIDS[0] = ".1.3.6.1.2.1.25.4.2.1.2";
OIDS[1] = ".1.3.6.1.2.1.25.4.2.1.4";
OIDS[2] = ".1.3.6.1.2.1.25.4.2.1.5";
OIDS[3] = ".1.3.6.1.2.1.25.4.2.1.6";
OIDS[4] = ".1.3.6.1.2.1.25.4.2.1.7";
OIDS[5] = ".1.3.6.1.2.1.25.5.1.1.1";
OIDS[6] = ".1.3.6.1.2.1.25.5.1.1.2";
OIDS[7] = ".1.3.6.1.2.1.25.4.2.1.1";

String[][] _values = SnmpGetUtil.getTableValue(config, OIDS);
for (int i = 0; i < _values.length; i++) {
	String[] value = _values[i];
	
	String processName = DecodingUtil.decoding(value[1], charset);
	String processPath = DecodingUtil.decoding(value[2], charset);
	String processPara = DecodingUtil.decoding(value[3], charset);
	
	String processInfo = processPath + processName + " " + processPara;
	processInfo = processInfo.trim();
	
	perfList.add(['运行路径', processName, processPath]);
	perfList.add(['运行参数', processName, processPara]);
	perfList.add(['软件类型', processName, hrSWRunType(value[4])]);
	perfList.add(['运行状态', processName, hrSWRunStatus(value[5])]);
	perfList.add(['CPU使用时间', processName, Integer.valueOf(value[6])/100]);
	perfList.add(['内存占用量', processName, value[7]]);
	
	if(value[8]==1){
		perfList.add(['PID', processName, 0]);
	}else{
		perfList.add(['PID', processName, value[8]]);
	}
	
	try {
		_cpuAvgLoad += Double.parseDouble(value[2]);
		// 在 linux 上出现了没有采集到值的情况。
		count++;
	} catch (RuntimeException e) {
	}
}
return perfList;

private String hrSWRunType(def type) {
	switch (type) {
		case '1' : return 'unknown';
		case '2' : return 'operatingSystem';
		case '3' : return 'deviceDriver';
		case '4' : return 'application';
		default : return 'unknown';
	}
}
private String hrSWRunStatus(def type) {
	switch (type) {
		case '1' : return 'running';
		case '2' : return 'runnable';
		case '3' : return 'notRunnable';
		case '4' : return 'invalid';
		default : return 'unknown';
	}
}
