def perfList = []

// 定义常量
// SNMP协议中使用32位存储的整数的最大值。
final double INTEGER32_MAX_VALUE = 4294967295d;
// SNMP协议中使用64位存储的整数的最大值。
final double INTEGER64_MAX_VALUE = 18446744073709551615d;

SnmpValue hrHostcpu = SnmpGetUtil.getOneValue(config, ".1.3.6.1.4.1.9.9.109.1.1.1.1.3.1");
perfList.add(['CPU利用率', "hrSystem", hrHostcpu.getValue()]);

return perfList;
