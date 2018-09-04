package com.edu.snmp.groovy;

import com.edu.snmp.groovy.Base64;
import com.edu.snmp.groovy.Parser;

import java.nio.charset.Charset;


/**
 * 解析脚本用的类, 方便测试时使用.
 * 
 * @author zhangrong
 * 
 */
public class ScriptParser implements Parser {

	@Override
	public boolean validate(String script) {
		return false;
	}

	@Override
	public String parse(String script) {
		byte[] _scriptBytes = Base64.decode(script);
		String _script = new String(_scriptBytes, Charset.forName("UTF-8"));
		return _script;
	}

}
