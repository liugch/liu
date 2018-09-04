def perfList = []

// 定义常量
// SNMP协议中使用32位存储的整数的最大值。
final double INTEGER32_MAX_VALUE = 4294967295d;
// SNMP协议中使用64位存储的整数的最大值。
final double INTEGER64_MAX_VALUE = 18446744073709551615d;

SnmpValue hrFanInfo = SnmpGetUtil.getOneValue(config, " 1.3.6.1.4.1.9.9.13.1.4.1.2.1");
perfList.add(['风扇描述', "hrSystem", hrFanInfo.getValue()]);

SnmpValue hrFanStat = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.13.1.4.1.3.1");
perfList.add(['风扇状态', "hrSystem", hrFanStat.getValue()]);

SnmpValue hrPowerInfo = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.13.1.5.1.2.1");
perfList.add(['电源描述', "hrSystem", hrPowerInfo.getValue()]);

SnmpValue hrPowerStat = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.13.1.5.1.3.1");
perfList.add(['电源状态', "hrSystem", hrPowerStat.getValue()]);

SnmpValue hrvoltageInfo = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.13.1.3.1.2.1");
perfList.add(['温度描述', "hrSystem", hrvoltageInfo.getValue()]);

SnmpValue hrvoltageStat = SnmpGetUtil.getOneValue(config, "1.3.6.1.4.1.9.9.13.1.3.1.6.1");
perfList.add(['当前温度', "hrSystem", hrvoltageStat.getValue()]);




return perfList;