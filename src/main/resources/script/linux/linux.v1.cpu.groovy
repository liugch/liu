import com.edu.snmp.groovy.util.SnmpGetUtil

def perfList = []

String[] OIDS = new String[2];
OIDS[0] = ".1.3.6.1.2.1.25.3.3.1.1";
OIDS[1] = ".1.3.6.1.2.1.25.3.3.1.2";

String[][] _values = SnmpGetUtil.getTableValue(config, OIDS);

double _cpuAvgLoad = 0.0;
int count = 0; // 没有采集到值时，忽略那个 CPU 做 total 计算

for (int i = 0; i < _values.length; i++) {
	String[] value = _values[i];
	
	perfList.add(['CPU利用率', i, value[2]]);
	
	try {
		_cpuAvgLoad += Double.parseDouble(value[2]);
		// 在 linux 上出现了没有采集到值的情况。
		count++;
	} catch (RuntimeException e) {
	}
}

_cpuAvgLoad /= (count == 0 ? 1 : count);
perfList.add(['CPU利用率', "_Total", _cpuAvgLoad]);

return perfList;