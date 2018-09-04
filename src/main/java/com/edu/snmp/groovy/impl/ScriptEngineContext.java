package com.edu.snmp.groovy.impl;

import org.codehaus.groovy.runtime.MethodClosure;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptEngineContext {

    private Map<String, Object> props = new HashMap<String, Object>();

    private List<String> importList = new ArrayList<String>();

    public void addImport(Class clazz) {
        importList.add(clazz.getName());
    }

    public void addImport(String clazz) {
        importList.add(clazz);
    }

    public void clearImport() {
        importList.clear();
    }

    public void addClosure(Object owner, String method) {
        MethodClosure cl = new MethodClosure(owner, method);
        props.put(method, cl);
    }

    public void addProp(String key, Object value) {
        props.put(key, value);
    }

    public void clear() {
        props.clear();
    }

    public Map<String, Object> getProps() {
        return props;
    }

    public void setProps(Map<String, Object> props) {
        this.props = props;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public ScriptContext getScriptContext() {
        ScriptContext scriptContext = new SimpleScriptContext();

        for (Map.Entry<String, Object> entry : props.entrySet()) {
            scriptContext.setAttribute(entry.getKey(), entry.getValue(), ScriptContext.ENGINE_SCOPE);
        }
        //   scriptContext.setAttribute("name", "刘广长", ScriptContext.ENGINE_SCOPE);
        return scriptContext;
    }

}
