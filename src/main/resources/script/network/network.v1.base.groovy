def perfList = []

//wuhao
SnmpValue hrHostname = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.5.0");
perfList.add(['主机名', "hrSystem", hrHostname.getValue()]);

SnmpValue hrHostinfo = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.1.0");
perfList.add(['主机描述', "hrSystem", hrHostinfo.getValue()]);

SnmpValue hrMonitorTime = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.3.0");
perfList.add(['监控持续时间', "hrSystem", hrMonitorTime.getValue()]);
//时间不会处理-吴昊

SnmpValue hrUser = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.4.0");
perfList.add(['负责人', "hrSystem", hrUser.getValue()]);

SnmpValue hrAddress = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.6.0");
perfList.add(['所在位置', "hrSystem", hrAddress.getValue()]);

SnmpValue hrServicesNum = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.1.7.0");
perfList.add(['服务类型', "hrSystem", hrServicesNum.getValue()]);





return perfList;