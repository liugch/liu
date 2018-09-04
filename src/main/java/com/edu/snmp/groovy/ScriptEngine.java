package com.edu.snmp.groovy;

import com.edu.snmp.groovy.impl.ScriptEngineContext;
import groovy.util.ScriptException;

import javax.script.ScriptContext;


public interface ScriptEngine {

    public void init(ScriptEngineContext context);

    public ScriptContext getScriptEngineContext();

    /**
     * 得到脚本的解析类.
     *
     * @return 解析类的实现
     */
    public Parser getParser();

    /**
     * 设置脚本的解析类.
     *
     * @param parser 解析类的实现
     */
    public void setParser(Parser parser);

    /**
     * 执行脚本
     *
     * @param script 脚本全文
     * @return 脚本执行结果
     * @throws ScriptException 执行脚本出现错误时F
     */
    public Object doAction(String script) throws ScriptException, javax.script.ScriptException;

    /**
     * @param script  脚本全文
     * @param context 脚本引擎上下文
     * @return 脚本执行结果
     * @throws ScriptException 执行脚本出现错误时F
     */
    public Object doAction(String script, ScriptEngineContext context) throws ScriptException, javax.script.ScriptException;

    /**
     * 验证脚本的内容
     *
     * @param script 脚本全文
     * @return true, 当脚本无错误时
     */
    public boolean validate(String script);

    /**
     * 清理脚本引擎产生的缓存数据.
     */
    public void clearCache();

}
