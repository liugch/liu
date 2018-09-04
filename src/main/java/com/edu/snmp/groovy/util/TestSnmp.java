package com.edu.snmp.groovy.util;

import com.edu.snmp.groovy.ScriptEngine;
import com.edu.snmp.groovy.impl.ScriptEngineContext;
import com.edu.snmp.groovy.impl.ScriptEngineImpl;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 何有悠然
 * @ClassName: SnmpTest
 * @CreateDate: 2018/8/16 17:07
 **/
public class TestSnmp {
    private static Logger LOGGER = null;


    @BeforeClass
    public static void setLogger() throws MalformedURLException {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        LOGGER = LogManager.getLogger();
    }

    @Test
    public void testGetOne() throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("host", "127.0.0.1");
        map.put("port", "161");
        map.put("readCommunity", "public");
        SnmpValue oneValue = SnmpGetUtil.getOneValue(map, ".1.3.6.1.2.1.1.1.0");
        System.out.println(oneValue);

    }

    @Test
    public void testGetTableValue() throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("host", "127.0.0.1");
        map.put("port", "161");
        map.put("readCommunity", "public");
        map.put("encode", "utf-8");
        String[] OIDS = new String[6];
        OIDS[0] = "1.3.6.1.2.1.25.2.3.1.1";
        OIDS[1] = "1.3.6.1.2.1.25.2.3.1.2";
        OIDS[2] = "1.3.6.1.2.1.25.2.3.1.3";
        OIDS[3] = "1.3.6.1.2.1.25.2.3.1.4";
        OIDS[4] = "1.3.6.1.2.1.25.2.3.1.5";
        OIDS[5] = "1.3.6.1.2.1.25.2.3.1.6";

        String[][] snmpValue = SnmpGetUtil.getTableValue(map, OIDS);
        //System.out.println(snmpValue);
        if (snmpValue != null) {
            StringBuffer sb = null;
            for (String[] v : snmpValue) {
                //  System.out.println(Arrays.toString(v));
                System.out.println(DecodingUtil.decoding(v[3], "GBK"));
                /*for (String f : v) {
                    System.out.println(DecodingUtil.decoding(f, "gbk"));
                }*/
            }
        }
        //.1.3.6.1.2.1.25.2.3.1.1
    }

    @Test
    public void testGetOneTable() throws IOException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("host", "127.0.0.1");
        map.put("port", "161");
        map.put("readCommunity", "public");
        String[] OIDS = new String[7];
        OIDS[0] = "1.3.6.1.2.1.25.2.3.1.1";
        OIDS[1] = "1.3.6.1.2.1.25.2.3.1.2";
        OIDS[2] = "1.3.6.1.2.1.25.2.3.1.3";
        OIDS[3] = "1.3.6.1.2.1.25.2.3.1.4";
        OIDS[4] = "1.3.6.1.2.1.25.2.3.1.5";
        OIDS[5] = "1.3.6.1.2.1.25.2.3.1.6";
        OIDS[6] = "1.3.6.1.2.1.25.2.3.1.7";

        String[] oneTableValue = SnmpGetUtil.getOneTableValue(map, OIDS);
        for (int i = 0; i < oneTableValue.length; i++) {
            LOGGER.info(oneTableValue[i]);
        }

    }

    @Test
    public void groovyScript() throws Exception {

        String groovy = Files.toString(new File("F:\\mavenProject\\liu\\src\\main\\resources\\script\\windows\\windows.v1.base.groovy"), Charsets.UTF_8);

        ScriptEngineContext scriptEngineContext = new ScriptEngineContext();
        scriptEngineContext.addImport("java.text.*");
        scriptEngineContext.addImport("org.snmp4j.*");
        scriptEngineContext.addImport("org.snmp4j.util.*");
        scriptEngineContext.addImport("org.apache.commons.lang3.*");
        scriptEngineContext.addImport("com.edu.snmp.groovy.util.*");

        Map<String, String> config = new HashMap<String, String>();
        config.put("host", "127.0.0.1");
        config.put("port", "161");
        config.put("readCommunity", "public");
        config.put("encode", "utf-8");


        scriptEngineContext.addProp("config", config);

        ScriptEngine e = new ScriptEngineImpl(scriptEngineContext);
        List o;
        try {
            // e.setParser(new ScriptParser());
            o = (List) e.doAction(groovy);
            for (Object object : o) {
                System.out.println(object);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

}
