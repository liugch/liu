def perfList = []

// 定义常量
// SNMP协议中使用32位存储的整数的最大值。
final double INTEGER32_MAX_VALUE = 4294967295d;
// SNMP协议中使用64位存储的整数的最大值。
final double INTEGER64_MAX_VALUE = 18446744073709551615d;

SnmpValue hrMemUsed = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.48.1.1.1.5.1");
long usedMemory = Long.parseLong(StringUtils.defaultString(hrMemUsed.getValue(), "0"))/1024/1024;
perfList.add(['内存使用量', "hrSystem", usedMemory]);

SnmpValue free = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.48.1.1.1.6.1");
long freeMemory = Long.parseLong(StringUtils.defaultString(free.getValue(), "0"))/1024/1024;
long size=usedMemory+freeMemory;
perfList.add(['内存剩余量', "hrSystem",freeMemory]);
perfList.add(['内存总量', "hrSystem",size]);
perfList.add(['内存使用率', "hrSystem", String.format('%.2f', usedMemory * 100 / freeMemory+usedMemory)]);


return perfList;