import com.edu.snmp.groovy.util.SnmpGetUtil

def perfList = []

String charset = config.encode


println("======="+name+"=========================");

// hrMemorySize
SnmpValue memorySize = SnmpGetUtil.getOneValue(config, ".1.3.6.1.2.1.25.2.2.0");

String[] storageTableOIDS = new String[7];
storageTableOIDS[0] = ".1.3.6.1.2.1.25.2.3.1.1";
storageTableOIDS[1] = ".1.3.6.1.2.1.25.2.3.1.2";
storageTableOIDS[2] = ".1.3.6.1.2.1.25.2.3.1.3";
storageTableOIDS[3] = ".1.3.6.1.2.1.25.2.3.1.4";
storageTableOIDS[4] = ".1.3.6.1.2.1.25.2.3.1.5";
storageTableOIDS[5] = ".1.3.6.1.2.1.25.2.3.1.6";
storageTableOIDS[6] = ".1.3.6.1.2.1.25.2.3.1.7";

// hrStorageTable
String[][] strogies = SnmpGetUtil.getTableValue(config, storageTableOIDS);

HrStorageEntry virtualMemory = null;
HrStorageEntry physicalMemory = null;

List<HrStorageEntry> storagis = new ArrayList<HrStorageEntry>();

// Linux 系统中包括 Other 类型的内存使用分类，需要从内存使用率中剔除
long linuxUsedMemory = 0;

for (String[] strings : strogies) {
    HrStorageEntry entry = new HrStorageEntry();

    entry.index = Integer.parseInt(strings[1]);
    entry.type = StorageType.getType(strings[2]);
    entry.descr = DecodingUtil.decoding(strings[3], charset);
    entry.allocationUnits = Long.parseLong(StringUtils.defaultString(strings[4], "0"));
    entry.size = Long.parseLong(StringUtils.defaultString(strings[5], "0")) * entry.allocationUnits / 1024 / 1024;
    entry.used = Long.parseLong(StringUtils.defaultString(strings[6], "0")) * entry.allocationUnits / 1024 / 1024;
    entry.allocationFailures = Long.parseLong(StringUtils.defaultString(strings[7], "0"));

    if (StorageType.VirtualMemory.name().equals(entry.type)) {
        if (virtualMemory == null
        && entry.descr.toLowerCase().contains("virtual"))
        virtualMemory = entry;
        if (entry.descr.toLowerCase().contains("swap"))
        virtualMemory = entry;
    } else if (StorageType.Ram.name().equals(entry.type)) {
        physicalMemory = entry;
    } else if (StorageType.FixedDisk.name().equals(entry.type)) {
        storagis.add(entry);
    } else if (StorageType.Other.name().equals(entry.type)) {
        linuxUsedMemory += entry.used;
    }
}

if (physicalMemory != null) {
    // MB
    perfList.add(['物理内存空余量', "total", physicalMemory.size - (physicalMemory.used - linuxUsedMemory)]);
    // MB
    perfList.add(['物理内存总量', "total", physicalMemory.size]);
    // 单位 %
    perfList.add(['物理内存利用率', "total", String.format('%.2f', (physicalMemory.used - linuxUsedMemory) * 100 / defaultLong(physicalMemory.size))]);
}

if (virtualMemory != null) {
    // MB
    perfList.add(['虚拟内存空余量', "total", virtualMemory.size - virtualMemory.used]);
    // MB
    perfList.add(['虚拟内存总量', "total", virtualMemory.size]);
    // %
    perfList.add(['虚拟内存利用率', "total", String.format('%.2f', virtualMemory.used * 100 / defaultLong(virtualMemory.size))]);
}

return perfList;

private long defaultLong(long value) {
    return value == 0 ? 1 : value;
}

class HrStorageEntry {
    int index;
    String type;
    String descr;
    long allocationUnits;
    long size;
    long used;
    long allocationFailures;
}
