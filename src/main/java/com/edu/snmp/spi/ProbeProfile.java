package com.edu.snmp.spi;

/**
 * 采集器场景描述.
 * 
 * @author zhangrong
 * 
 */
public enum ProbeProfile {
	// 生产环境
	PRODUCTION,
	// 开发环境
	DEVELOPMENT,
	// 功能测试环境
	FUNCTIONAL_TEST,
	// 集成测试环境
	INTEGRATION_TEST,
	// 单元测试环境
	UNIT_TEST,
	// 自定义集成测试(在模版相关方法没完成前，用于模版和采集器间的集成测试，此时脚本要用 Base64 编码)
	CUSTOM_INTEGRATION_TEST;
}
