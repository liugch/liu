import com.edu.snmp.groovy.util.InterfaceVO
import com.edu.snmp.groovy.util.SnmpGetUtil

def perfList = []

String[] IPOIDS = new String[2];
IPOIDS[0] = ".1.3.6.1.2.1.4.20.1.1";
IPOIDS[1] = ".1.3.6.1.2.1.4.20.1.2";

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
long inv = 60;
/* get ip address */
String[][] _ipValues = SnmpGetUtil.getTableValue(config, IPOIDS);
Map<String, String> ipMap = new HashMap<String, String>();
for (int i = 0; i < _ipValues.length; i++) {
    String[] _ipValue = _ipValues[i];
    ipMap.put(_ipValue[2],_ipValue[1]);
}
String lastDataTime = lastData.get("lastTime","lastTime");

long l = (new Date()).getTime();
lastData.put(["lastTime","lastTime",""+l]);


if(lastDataTime!=null&&!"".equals(lastDataTime)){
    long lTime = Long.parseLong(lastDataTime);
    inv = ((new Date().getTime())-lTime)/1000+1;
}

String[][] _ifValues = SnmpGetUtil.getTableValue(config, OIDS);

InterfaceVO _ifVo;
for (int i = 0; i < _ifValues.length; i++) {
    String[] _ifValue = _ifValues[i];
    _ifVo = new InterfaceVO(_ifValue[1], _ifValue[2], _ifValue[3],_ifValue[4], _ifValue[5], _ifValue[6], _ifValue[7], _ifValue[8], _ifValue[9],_ifValue[10], _ifValue[11], _ifValue[12], _ifValue[13], _ifValue[14],_ifValue[15], _ifValue[16], _ifValue[17], _ifValue[18], _ifValue[19],_ifValue[20], _ifValue[21], _ifValue[22]);

    if (needFilter(_ifVo))
        continue;

    if(lastData.get("端口入包数",_ifVo.getDescr())!=null&&!"".equals(lastData.get("端口入包数",_ifVo.getDescr()))){
        create(perfList,_ifVo,lastData,ipMap,inv);
    }else{
        lastData.put(['端口状态', _ifVo.getDescr(), "up"]);
        lastData.put(['端口入流量', _ifVo.getDescr(), "0"]);
        lastData.put(['端口入包数', _ifVo.getDescr(),"0"]);
        lastData.put(['端口入丢包数', _ifVo.getDescr(),"0"]);
        lastData.put(['端口出流量', _ifVo.getDescr(),"0"]);
        lastData.put(['端口出包数', _ifVo.getDescr(),"0"]);
        lastData.put(['端口出丢包数',_ifVo.getDescr(),"0"]);
        create(perfList,_ifVo,lastData,ipMap,inv);
    }
    feedLastData(_ifVo);
}


return perfList;

private void create(List perfList, InterfaceVO current, Object lastData, Map<String, String> ipMap, long inv) {
    //get ip address
    String ifName = switchIfDesc(current.getDescr());
    Double ifSpeed = new Double(current.getSpeed().doubleValue() / 1000);
    String ip="";
    if(ipMap.get(""+current.getIndex())!=null)
    {
        ip=ipMap.get(""+current.getIndex());
    }
    String str = config.agentip;
    if(!"".equals(str)&&str!=null){
        String[] oList = str.split(";");
        for (String tmp : oList) {
            String[] tList = tmp.split(":");
            if(tList[0].equals(ifName)){
                ifSpeed=Double.parseDouble(tList[1])*1000;
            }
        }
    }


    //inByte单位byte
    Double inByte = computeReset(current.getInOctets().doubleValue(),Double.parseDouble(lastData.get("端口入流量",current.getDescr())));
    Double outByte = computeReset(current.getOutOctets().doubleValue(),Double.parseDouble(lastData.get("端口出流量",current.getDescr())));
    Double outPacket = computeReset(current.getOutUcastPkts().doubleValue(),Double.parseDouble(lastData.get("端口出包数",current.getDescr())));
    Double inPacket = computeReset(current.getInUcastPkts().doubleValue(),Double.parseDouble(lastData.get("端口入包数",current.getDescr())));
    Double outDiscard = computeReset(current.getOutDiscards().doubleValue(),Double.parseDouble(lastData.get("端口出丢包数",current.getDescr())));
    Double inDiscard = computeReset(current.getInDiscards().doubleValue(),Double.parseDouble(lastData.get("端口入丢包数",current.getDescr())));

    Double inSpeed = inByte/1024/inv;   //KB/s
    Double outSpeed = outByte/1024/inv; //KB/s
    Double inPercent = 0;
    Double outPercent = 0;
    if(ifSpeed!=0){
        inPercent = inByte/ifSpeed*8/10/inv; //KB/s
        outPercent = outByte/ifSpeed*8/10/inv;
    }
    Double discardPercent = 0;
    if((inDiscard+outDiscard)==0||(inPacket+outPacket)==0){
        discardPercent=0;
    }else{
        discardPercent = (inDiscard+outDiscard)/(inPacket+outPacket)*100;
    }
    String lastStatus = lastData.get("端口状态",current.getDescr());
    String status = current.getOperStatus();
    String nowStatus = "up";
    if("down".equals(lastStatus)&&"down".equals(status)){
        nowStatus="Not Enabled"
    }else if("up".equals(lastStatus)&&"down".equals(status)){
        nowStatus="down"
    }else if("Not Enabled".equals(lastStatus)&&"up".equals(status)){
        nowStatus="Recovery";
    }else if("down".equals(lastStatus)&&"up".equals(status)){
        nowStatus="Recovery";
    }else if("Recovery".equals(lastStatus)&&"up".equals(status)){
        nowStatus="up";
    }else if("Recovery".equals(lastStatus)&&"down".equals(status)){
        nowStatus="down";
    }

    perfList.add(['端口状态', ifName,nowStatus]);
    perfList.add(['端口编号', ifName, current.getIndex()]);
    // perfList.add(['端口类型', ifName, current.getType()]);
    perfList.add(['端口地址', ifName, ip]);
    perfList.add(['端口速率', ifName, String.format("%.2f", ifSpeed/1000)]);
    perfList.add(['端口上行速率', ifName, String.format("%.2f", outSpeed)]);
    perfList.add(['端口上行利用率', ifName, String.format("%.2f", outPercent)]);
    perfList.add(['端口上行包数', ifName, String.format("%.2f", outPacket)]);
    perfList.add(['端口下行速率', ifName, String.format("%.2f", inSpeed)]);
    perfList.add(['端口下行利用率', ifName, String.format("%.2f", inPercent)]);
    perfList.add(['端口下行包数', ifName, String.format("%.2f", inPacket)]);
    perfList.add(['端口丢包率',ifName,String.format("%.2f",discardPercent)]);
}

private boolean needFilter(InterfaceVO current) {
    boolean filter = false;

    String descLowerCase = current.getDescr().toLowerCase();
    String physAddress = current.getPhysAddress();

    if (descLowerCase.contains("backplane"))
        filter = true;
    else if (descLowerCase.contains("e1"))
        filter = true;
    else if (descLowerCase.contains("null0"))
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
private Double computeRate(Number current, Number previous) {
    Double retVal = null;
    if (current != null && previous != null) {
        double delta = current.doubleValue() - previous.doubleValue();
        if (delta < 0) {
            double delta2 = delta + 4294967295d;
            if (delta2 >= 0) {
                delta = delta2;
            } else {
                delta = delta + 18446744073709551615d + 1;
                // delta=0;
            }
        }
        //Delta数据为字节数，这里要计算出比特率，因此要将单位换算为bit
        //单位：KBit/s，对于流量数据使用1024作为单位由Bit/s到KBit/s的换算
        retVal = new Double(delta * 1000 / interval / 1024);
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
            util = (rate.doubleValue()* 100) / speed.doubleValue()*8 ;
        retVal = new Double(util);
    }

    return String.format("%.2f", retVal);
}

private Double computeResetRate(Number current, Number previous, long interval) {
    Double retVal = null;
    if (current != null && previous != null) {
        double delta = current.doubleValue() - previous.doubleValue();
        if (delta < 0) {
            double delta2 = delta + 4294967295d;
            if (delta2 >= 0) {
                delta = delta2;
            } else {
                delta = delta + 18446744073709551615d + 1;
                // delta=0;
            }
        }
        //Delta数据为字节数，这里要计算出比特率，因此要将单位换算为bit
        //单位：KBit/s，对于流量数据使用1024作为单位由Bit/s到KBit/s的换算
        retVal = new Double(delta * 1000 / interval);
    }
    return retVal;
}

private Double computeReset(Number current, Number previous) {
    Double retVal = null;
    if (current != null && previous != null) {
        double delta = current.doubleValue() - previous.doubleValue();
        if (delta < 0) {
            double delta2 = delta + 4294967295d;
            if (delta2 >= 0) {
                delta = delta2;
            } else {
                delta = delta + 18446744073709551615d + 1;
            }
        }
        retVal=delta;
    }
    return retVal;
}

private void feedLastData(InterfaceVO current){
    Double inByte = current.getInOctets().doubleValue();
    Double outByte = current.getOutOctets().doubleValue();
    Double bandwidth = current.getSpeed().doubleValue();
    Double outPacket = current.OutUcastPkts.doubleValue();
    Double inPacket = current.InUcastPkts.doubleValue();
    Double inDiscards = current.InDiscards.doubleValue();
    Double outDiscards = current.OutDiscards.doubleValue();

    lastData.put(['端口编号', current.getDescr(), current.getIndex()]);
    lastData.put(['端口状态', current.getDescr(), current.getOperStatus()]);
    lastData.put(['端口入流量', current.getDescr(), String.format("%.2f", inByte)]);
    lastData.put(['端口入包数', current.getDescr(), String.format("%.2f", inPacket)]);
    lastData.put(['端口入丢包数', current.getDescr(), String.format("%.2f", inDiscards)]);
    lastData.put(['端口出流量', current.getDescr(), String.format("%.2f", outByte)]);
    lastData.put(['端口出包数', current.getDescr(), String.format("%.2f", outPacket)]);
    lastData.put(['端口出丢包数',current.getDescr(), String.format("%.2f", outDiscards)]);
}
private String switchIfDesc(String ifDesc) {
    String ifName = ifDesc.substring(0, 2);
    switch (ifName.toUpperCase()) {
        case "GI": return ifDesc.replace("GigabitEthernet", "G");
        case "PO": return ifDesc.replace("Port-channel", "P");
        case "FA": return ifDesc.replace("FastEthernet", "F");
    // case "ET":return ifDesc.replace("eth","E");
        default: return ifDesc;
    }
    return ifDesc;
}