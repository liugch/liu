package com.edu.snmp.probe;

import java.util.Map;

import com.edu.snmp.groovy.ScriptEngine;
import com.edu.snmp.groovy.ScriptParser;
import com.edu.snmp.groovy.impl.ScriptEngineContext;
import com.edu.snmp.groovy.impl.ScriptEngineImpl;
import com.edu.snmp.spi.IProbeCore;
import com.edu.snmp.spi.IScheduleTask;
import com.edu.snmp.spi.ProbeProfile;
import com.edu.snmp.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * 采集器端 ProbeCore 的抽象父类.
 * 
 * @author zhangrong
 * 
 */
public abstract class AbstractProbeCoreImpl implements IProbeCore, IScheduleTask {

	private static final Logger logger = LoggerFactory.getLogger(AbstractProbeCoreImpl.class);

	/** 采集器标识符. */
	protected String id;
	/** 保存的模版. */
	protected Template template;
	/** 脚本引擎. */
	protected ScriptEngine scriptEngine;
	/** 脚本文本信息. */
	protected String script;

	@Override
	public void setCfg(final String id, final ProbeProfile probeProfile, final Template template) {
		this.id = id;
		this.template = template;

		ScriptEngineContext scriptEngineContext = new ScriptEngineContext();
		addImportToContext(scriptEngineContext);

		Map<String, String> config = getConfig();
		scriptEngineContext.addProp("config", config);

		this.scriptEngine = new ScriptEngineImpl(scriptEngineContext);

		if (ProbeProfile.CUSTOM_INTEGRATION_TEST == probeProfile) {
			this.scriptEngine.setParser(new ScriptParser());
		}

		this.script = template.getScripts().get(getScriptVersion());
	}

	/**
	 * 通过模版获取配置文件
	 * 
	 * @param template 模版
	 * @return 配置信息的键值对
	 */
	abstract protected Map<String, String> getConfig();

	/**
	 * 获取可执行脚本的版本
	 * 
	 * @return 脚本的版本
	 */
	abstract protected String getScriptVersion();

	/**
	 * 用于子类增加导入语句.
	 * 
	 * @param scriptEngineContext
	 */
	protected void addImportToContext(ScriptEngineContext scriptEngineContext) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(final String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(final String id) {
		logger.debug("Stop Probe 1 {}", id);
	}

	@Override
	public final void create(String id, Template template) {
	}

	@Override
	public final void destroy(String id) {
		scriptEngine.clearCache();
		destroy();
	}

	/**
	 * 子类如果需要销毁一些资源, 则覆盖这个方法.
	 */
	protected void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public final Object exec(final String id) {
		Object obj = null;

		try {
			logger.debug(""+scriptEngine.getScriptEngineContext().getAttribute("config"));
			obj = scriptEngine.doAction(script);
		} catch (Exception e) {

		}

		return obj;
	}

	@Override
	public final Object run(final String id) {
		return exec(id);
	}

	/**
	 * 设置 ScriptEngine, 用于测试.
	 * 
	 * @param scriptEngine 脚本引擎
	 */
	public final void setScriptEngine(ScriptEngine scriptEngine) {
		this.scriptEngine = scriptEngine;
	}

}
