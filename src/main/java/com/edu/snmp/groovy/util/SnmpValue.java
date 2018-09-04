package com.edu.snmp.groovy.util;

import org.snmp4j.smi.Variable;

public class SnmpValue {

	private String oid;
	private String value;
	private String syntaxString;
	private Variable sourceValue;

	public SnmpValue(String oid, String value, String syntaxString, Variable sourceValue) {
		this.oid = oid;
		this.value = value;
		this.syntaxString = syntaxString;
		this.sourceValue = sourceValue;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSyntaxString() {
		return syntaxString;
	}

	public void setSyntaxString(String syntaxString) {
		this.syntaxString = syntaxString;
	}

	public Variable getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(Variable sourceValue) {
		this.sourceValue = sourceValue;
	}

	@Override
	public String toString() {
		return "SnmpValue [oid=" + oid + ", value=" + value + ", syntaxString=" + syntaxString + ", sourceValue="
				+ sourceValue + "]";
	}

}
