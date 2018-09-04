package com.edu.snmp.groovy.util;

/**
 * 存储分类
 * 
 * @author zhangrongc
 */
public enum StorageType {

	Other(".1.3.6.1.2.1.25.2.1.1"), Ram(".1.3.6.1.2.1.25.2.1.2"), VirtualMemory(".1.3.6.1.2.1.25.2.1.3"), FixedDisk(
			".1.3.6.1.2.1.25.2.1.4"), RemovableDisk(".1.3.6.1.2.1.25.2.1.5"), FloppyDisk(".1.3.6.1.2.1.25.2.1.6"), CompactDisc(
			".1.3.6.1.2.1.25.2.1.7"), RamDisk(".1.3.6.1.2.1.25.2.1.8"), hrStorageFlashMemory(".1.3.6.1.2.1.25.2.1.9"), NetworkDisk(
			".1.3.6.1.2.1.25.2.1.10");

	private String oid;

	StorageType(String oid) {
		this.oid = oid;
	}

	public static String getType(String oid) {
		for (StorageType type : StorageType.values()) {
			if (type.oid.contains(oid)) {
				return type.name();
			}
		}

		return oid;
	}

}
