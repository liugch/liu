package com.edu.snmp.probe;

import com.edu.snmp.groovy.impl.ScriptEngineContext;

import java.util.HashMap;
import java.util.Map;


/**
 * 实现 SqlServer Session v1 接口.
 * 
 * @author zhangrong
 * 
 */
class ProbeCoreImpl extends AbstractProbeCoreImpl implements ProbeCore {

	/**
	 * 用于子类增加导入语句.
	 * 
	 * @param scriptEngineContext
	 */
	@Override
	protected void addImportToContext(ScriptEngineContext scriptEngineContext) {
		scriptEngineContext.addImport("java.text.*");
		scriptEngineContext.addImport("org.snmp4j.*");
		scriptEngineContext.addImport("org.snmp4j.util.*");
		scriptEngineContext.addImport("org.apache.commons.lang3.*");
		scriptEngineContext.addImport("com.szk.monitor.probe.probeCorePlugin.snmp.common.v1.util.*");
	}

	@Override
	protected Map<String, String> getConfig() {
		Map<String, String> config = new HashMap<String, String>();
		config.putAll(template.getAppInfos());
		return config;
	}

	@Override
	protected String getScriptVersion() {
		return "v1";
	}

}
