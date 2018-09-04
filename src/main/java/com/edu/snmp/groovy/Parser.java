package com.edu.snmp.groovy;

public interface Parser {
    public boolean validate(String script);

    public String parse(String script);
}
