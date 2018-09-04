def perfList = []

// 定义常量
// SNMP协议中使用32位存储的整数的最大值。
final double INTEGER32_MAX_VALUE = 4294967295d;
// SNMP协议中使用64位存储的整数的最大值。
final double INTEGER64_MAX_VALUE = 18446744073709551615d;

String[] OIDS = new String[22];
OIDS[0] = ".1.3.6.1.2.1.2.2.1.1";
OIDS[1] = ".1.3.6.1.2.1.2.2.1.2";
OIDS[2] = ".1.3.6.1.2.1.2.2.1.3";
OIDS[3] = ".1.3.6.1.2.1.2.2.1.4";
OIDS[4] = ".1.3.6.1.2.1.2.2.1.5";
OIDS[5] = ".1.3.6.1.2.1.2.2.1.6";
OIDS[6] = ".1.3.6.1.2.1.2.2.1.7";
OIDS[7] = ".1.3.6.1.2.1.2.2.1.8";
OIDS[8] = ".1.3.6.1.2.1.2.2.1.9";
OIDS[9] = ".1.3.6.1.2.1.2.2.1.10";
OIDS[10] = ".1.3.6.1.2.1.2.2.1.11";
OIDS[11] = ".1.3.6.1.2.1.2.2.1.12";
OIDS[12] = ".1.3.6.1.2.1.2.2.1.13";
OIDS[13] = ".1.3.6.1.2.1.2.2.1.14";
OIDS[14] = ".1.3.6.1.2.1.2.2.1.15";
OIDS[15] = ".1.3.6.1.2.1.2.2.1.16";
OIDS[16] = ".1.3.6.1.2.1.2.2.1.17";
OIDS[17] = ".1.3.6.1.2.1.2.2.1.18";
OIDS[18] = ".1.3.6.1.2.1.2.2.1.19";
OIDS[19] = ".1.3.6.1.2.1.2.2.1.20";
OIDS[20] = ".1.3.6.1.2.1.2.2.1.21";
OIDS[21] = ".1.3.6.1.2.1.2.2.1.22";

Date _oldMeasureTime = new Date();
String[][] _oldValues = SnmpGetUtil.getTableValue(config, OIDS);
Thread.sleep(10000);
Date _newMeasureTime = new Date();
String[][] _newValues = SnmpGetUtil.getTableValue(config, OIDS);

Map<String, InterfaceVO> interfaceCache = new HashMap<String, InterfaceVO>();

for (int i = 0; i < _oldValues.length; i++) {
    String[] _oldValue = _oldValues[i];

    InterfaceVO _oldVo = new InterfaceVO(_oldValue[1], _oldValue[2], _oldValue[3],
            _oldValue[4], _oldValue[5], _oldValue[6], _oldValue[7], _oldValue[8], _oldValue[9],
            _oldValue[10], _oldValue[11], _oldValue[12], _oldValue[13], _oldValue[14],
            _oldValue[15], _oldValue[16], _oldValue[17], _oldValue[18], _oldValue[19],
            _oldValue[20], _oldValue[21], _oldValue[22]);

    _oldVo.setMeasureTime(_oldMeasureTime);

    if (needFilter(_oldVo))
        continue;

   // 仅保留处于运行状态(1)的端口数据
    if ("up".equals(_oldVo.getOperStatus())) {
        String rn = _oldVo.getDescr();
        interfaceCache.put(rn, _oldVo);
    }
}

for (int i = 0; i < _newValues.length; i++) {
    String[] _newValue = _newValues[i];

    InterfaceVO _newVo = new InterfaceVO(_newValue[1], _newValue[2], _newValue[3],
            _newValue[4], _newValue[5], _newValue[6], _newValue[7], _newValue[8], _newValue[9],
            _newValue[10], _newValue[11], _newValue[12], _newValue[13], _newValue[14],
            _newValue[15], _newValue[16], _newValue[17], _newValue[18], _newValue[19],
            _newValue[20], _newValue[21], _newValue[22]);

    _newVo.setMeasureTime(_newMeasureTime);

    if (needFilter(_newVo))
        continue;
    
    //仅保留处于运行状态(1)的端口数据
    if ("up".equals(_newVo.getOperStatus())) {
        //从缓存中得到上一次采集得到的该端口的数据，并于当前数据比较，得到
        //端口的流量数据，作为采集数据保存
        String rn = _newVo.getDescr();
        InterfaceVO previous = (InterfaceVO) interfaceCache.get(rn);
        if (previous != null) {
            create(perfList, _newVo, previous);
        }
    }
}

return perfList;

private void create(List perfList, InterfaceVO current, InterfaceVO previous) {
    long interval = current.getMeasureTime().getTime() - previous.getMeasureTime().getTime();
    // String.format('%.2f', virtualMemory.used * 100 / defaultLong(virtualMemory.size))
    perfList.add(['端口编号', current.getDescr(), current.getIndex()]);
    perfList.add(['端口类型', current.getDescr(), current.getType()]);
    perfList.add(['端口管理状态', current.getDescr(), current.getAdminStatus()]);
    perfList.add(['端口操作状态', current.getDescr(), current.getOperStatus()]);

    Double ifSpeed = new Double(current.getSpeed().doubleValue() / 1000);
    perfList.add(['端口速率', current.getDescr(), String.format("%.2f", ifSpeed)]);

    Double intfPrefIfSpeedIn = computeRate(current.getInOctets().doubleValue(), previous.getInOctets().doubleValue(), interval);
    perfList.add(['端口入速率', current.getDescr(), String.format("%.2f", intfPrefIfSpeedIn)]);

    Double intfPrefIfSpeedOut = computeRate(current.getOutOctets().doubleValue(), previous.getOutOctets().doubleValue(), interval);
    perfList.add(['端口出速率', current.getDescr(), String.format("%.2f", intfPrefIfSpeedOut)]);

    Double intfPrefIfInErrors = computeRate(current.getInErrors().doubleValue(), previous.getInErrors().doubleValue(), interval);
    perfList.add(['端口入错误帧速', current.getDescr(), String.format("%.2f", intfPrefIfInErrors)]);

    Double intfPrefIfOutErrors = computeRate(current.getOutErrors().doubleValue(), previous.getOutErrors().doubleValue(), interval);
    perfList.add(['端口出错误帧速', current.getDescr(), String.format("%.2f", intfPrefIfOutErrors)]);

    Double intfPrefIfInUcastPkts = computeRate(current.getInUcastPkts().doubleValue(), previous.getInUcastPkts().doubleValue(), interval);
    perfList.add(['端口单播入帧数', current.getDescr(), String.format("%.2f", intfPrefIfInUcastPkts)]);

    Double intfPrefIfOutUcastPkts = computeRate(current.getOutUcastPkts().doubleValue(), previous.getOutUcastPkts().doubleValue(), interval);
    perfList.add(['端口单播出帧速', current.getDescr(), String.format("%.2f", intfPrefIfOutUcastPkts)]);

    Double intfPrefIfInNUcastPkts = computeRate(current.getInNUcastPkts().doubleValue(), previous.getInNUcastPkts().doubleValue(), interval);
    perfList.add(['端口非单播入帧速', current.getDescr(), String.format("%.2f", intfPrefIfInNUcastPkts)]);

    Double intfPrefIfOutNUcastPkts = computeRate(current.getOutNUcastPkts().doubleValue(), previous.getOutNUcastPkts().doubleValue(), interval);
    // perfList.add(['非单播出帧数', current.getDescr(), String.format("%.2f", intfPrefIfOutNUcastPkts)]);

    Double intfPrefIfInDiscards = computeRate(current.getInDiscards().doubleValue(), previous.getInDiscards().doubleValue(), interval);
    perfList.add(['端口入丢帧速', current.getDescr(), String.format("%.2f", intfPrefIfInDiscards)]);

    Double intfPrefIfOutDiscards = computeRate(current.getOutDiscards().doubleValue(), previous.getOutDiscards().doubleValue(), interval);
    perfList.add(['端口出丢帧速', current.getDescr(), String.format("%.2f", intfPrefIfOutDiscards)]);

    // 单播入帧数 + 端口非单播入帧速 + 端口入错误帧速 + 端口入丢帧速
    perfList.add(['端口入帧速', current.getDescr(), String.format("%.2f", intfPrefIfInUcastPkts + intfPrefIfInNUcastPkts + intfPrefIfInErrors + intfPrefIfInDiscards)]);

    // 端口单播出帧速 + 非单播出帧数 + 端口出错误帧速 + 端口出丢帧速
    perfList.add(['端口出帧速', current.getDescr(), String.format("%.2f", intfPrefIfOutUcastPkts + intfPrefIfOutNUcastPkts + intfPrefIfOutErrors + intfPrefIfOutDiscards)]);

    /*
    perfList.add(['端口入丢包率', current.getDescr(), computeBandwidthUtil(current.getInDiscards(), current.getInNUcastPkts() + current.getInUcastPkts())]);
    perfList.add(['端口出丢包率', current.getDescr(), computeBandwidthUtil(current.getOutDiscards(), current.getOutNUcastPkts() + current.getOutUcastPkts())]);
    perfList.add(['端口入错误率', current.getDescr(), computeBandwidthUtil(current.getInErrors(), current.getInNUcastPkts() + current.getInUcastPkts())]);
    perfList.add(['端口出错误率', current.getDescr(), computeBandwidthUtil(current.getOutErrors(), current.getOutNUcastPkts() + current.getOutUcastPkts())]);
    */
    
    Double bandwidth = current.getSpeed().doubleValue() / 8;
    perfList.add(['带宽利用率', current.getDescr(), computeBandwidthUtil(intfPrefIfSpeedIn + intfPrefIfSpeedOut, bandwidth / 1024)]);
}

private boolean needFilter(InterfaceVO current) {
    boolean filter = false;

    String descLowerCase = current.getDescr().toLowerCase();
    String physAddress = current.getPhysAddress();

    if (StringUtils.isBlank(physAddress))
        filter = true;

    if (descLowerCase.contains("Serial0/0"))
        filter = true;
    else if (descLowerCase.contains("Null0"))
        filter = true;
    else if (descLowerCase.contains("wan miniport"))
        filter = true;
    else if (descLowerCase.contains("loopback interface"))
        filter = true;
    else if (descLowerCase.contains("ras async adapter"))
        filter = true;
    else if (descLowerCase.contains("winpkfilter"))
        filter = true;
    else if (descLowerCase.contains("virtualbox"))
        filter = true;
    else if (descLowerCase.contains("vmware"))
        filter = true;
    else if (descLowerCase.contains("microsoft isatap"))
        filter = true;
    else if (descLowerCase.contains("native mac layer"))
        filter = true;
    else if (descLowerCase.contains("虚拟"))
        filter = true;

    return filter;
}

/**
 * 计算端口的流量，单位为KBit/s。
 * <p>端口流量的计算公式，采用与MRTG相同的算法，详细信息请参考该文档：
 * http://oss.oetiker.ch/rrdtool/tut/rrdtutorial.en.html。</p>
 * @param current 当前数据。
 * @param previous 前一个数据。
 * @param interval 时间间隔（毫秒数）。
 * @return 流量数据。
 */
private Double computeRate(Number current, Number previous, long interval) {
    Double retVal = null;
    if (current != null && previous != null) {
        double delta = current.doubleValue() - previous.doubleValue();
        if (delta < 0) {
            double delta2 = delta + INTEGER32_MAX_VALUE + 1;
            if (delta2 >= 0) {
                delta = delta2;
            } else {
                delta = delta + INTEGER64_MAX_VALUE + 1;
            }
        }
        //Delta数据为字节数，这里要计算出比特率，因此要将单位换算为bit
        //单位：KBit/s，对于流量数据使用1024作为单位由Bit/s到KBit/s的换算
        retVal = new Double(delta * 8 / interval / 1024);
    }
    return retVal;
}

/**
 * 计算带宽利用率。
 * @param rate 端口速率(Kbit/s)。
 * @param speed 端口带宽(Kbit/s)。
 * @return 带宽利用率的百分数形式。
 */
private String computeBandwidthUtil(Number rate, Number speed) {
    Double retVal = null;
    if (rate != null && speed != null) {
        double util = 0;
        if (speed.doubleValue() > 0)
            util = rate.doubleValue() / speed.doubleValue() * 100;
        retVal = new Double(util);
    }

    return String.format("%.2f", retVal);
}