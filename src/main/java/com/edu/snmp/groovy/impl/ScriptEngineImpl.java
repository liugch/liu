package com.edu.snmp.groovy.impl;

import com.edu.snmp.groovy.Parser;
import com.edu.snmp.groovy.ScriptEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.groovy.jsr223.GroovyCompiledScript;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ScriptEngineImpl implements ScriptEngine {

    private static final Logger LOGGER = LogManager.getLogger(ScriptEngineImpl.class);

    private ScriptContext scriptContext = new SimpleScriptContext();

    private List<String> imports;

    private Parser parser;

    private static final int MAX_ENTRIES = 10000;

    GroovyScriptEngineImpl gse = new GroovyScriptEngineImpl();

    private final Map<String, CompiledScript> scriptMap = new LinkedHashMap<String, CompiledScript>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, CompiledScript> eldest) {
            return size() > MAX_ENTRIES;
        }
    };

    public ScriptEngineImpl() {

    }

    public ScriptEngineImpl(ScriptEngineContext context) {
        init(context);
    }

    private static String getmd5str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));

            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (byte element : byteArray) {
                if (Integer.toHexString(0xFF & element).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & element));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & element));
                }
            }

            return md5StrBuff.toString();
        } catch (Exception e) {
            LOGGER.warn("", e);
        }
        return str;
    }

    @Override
    public Object doAction(final String script) throws ScriptException {

        String _script;

        if (parser != null) {
            _script = parser.parse(script);
        } else {
            _script = script;
        }

        LOGGER.debug("script:\n" + _script);

        Object obj = null;

        if (imports != null) {
            StringBuffer sb = new StringBuffer();
            for (String i : imports) {
                sb.append("import " + i + "\n");
            }
            sb.append(_script);
            _script = sb.toString();
        }

        LOGGER.debug("final script:\n" + _script);

        String sMd5 = getmd5str(_script);
        LOGGER.debug("final sMd5:\n" + _script);
        CompiledScript cscript = scriptMap.get(sMd5);
        if (cscript == null) {
            LOGGER.debug("compile script:\n" + _script);
            // 执行编译后的脚本
            cscript = gse.compile(_script);
            if (cscript != null) {
                scriptMap.put(sMd5, cscript);
            }
        }
        if (cscript == null) {
            LOGGER.info("groovy validate not pass");
        } else {
            if (scriptContext != null) {
                //通过脚本上下文执行编译代码
                obj = cscript.eval(scriptContext);
            } else {
                obj = cscript.eval();
            }
        }

        return obj;
    }

    @Override
    public Object doAction(final String script, ScriptEngineContext context) throws ScriptException {

        String _script;

        if (parser != null) {
            _script = parser.parse(script);
        } else {
            _script = script;
        }

        LOGGER.debug("script:\n" + _script);

        Object obj = null;

        StringBuffer sb = new StringBuffer();
        for (String i : context.getImportList()) {
            sb.append("import " + i + "\n");
        }
        sb.append(_script);
        _script = sb.toString();

        LOGGER.debug("final script:\n" + _script);

        String sMd5 = getmd5str(_script);
        CompiledScript cscript = scriptMap.get(sMd5);
        if (cscript == null) {
            cscript = gse.compile(_script);
            if (cscript != null) {
                scriptMap.put(sMd5, cscript);
            }
        }
        if (cscript == null) {
            LOGGER.info("groovy validate not pass");
        } else {
            //LOGGER.info("excute script:"+cscript);

            // 添加上下文属性
            ScriptContext scontext = context.getScriptContext();
            //通过脚本上下文执行编译代码
            obj = cscript.eval(scontext);
        }

        return obj;
    }

    @Override
    public void init(ScriptEngineContext context) {
        if (context != null) {
            imports = context.getImportList();
            scriptContext = context.getScriptContext();
        }
    }

    @Override
    public boolean validate(final String script) {

        String _script = script;

        boolean ret = false;
        try {
            String sMd5 = getmd5str(_script);
            CompiledScript cscript = scriptMap.get(sMd5);
            if (cscript == null) {
                if (parser != null) {
                    _script = parser.parse(_script);
                }
                LOGGER.debug("script:\n" + _script);
                cscript = gse.compile(_script);
                if (cscript != null) {
                    scriptMap.put(sMd5, cscript);
                }
            }
            if (cscript != null) {
                ret = true;
            }
        } catch (ScriptException e) {
            LOGGER.info("compile error!");
        }
        return ret;
    }

    @Override
    public ScriptContext getScriptEngineContext() {
        return scriptContext;
    }

    @Override
    public Parser getParser() {
        return parser;
    }

    @Override
    public void setParser(Parser parser) {
        this.parser = parser;
    }


    @Override
    public void clearCache() {
        gse.getClassLoader().clearCache();
    }

}
