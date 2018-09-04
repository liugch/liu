package proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Jdk动态代理依赖接口实现, 委托的类要继承接口
 * JDKProxy 类就是一个实现InvocationHandler接口生成 的    代理生成器
 * 包装委托类
 * 
 * InvocationHandler 接口  Proxy 类 
 * @author Administrator
 *
 */
public class JDKProxy implements InvocationHandler{
	
	Object targer = null;
	
	public JDKProxy(Object targer) {
		this.targer = targer;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		// 打开事务
		System.out.println("保存数据前打开事务");
		
		// 通过反射 调用 targer 中的方法 
		Object obj = method.invoke(targer, args);
		
		// 关闭事务
		System.out.println("保存数据后关闭");
		
		return obj;
	}
	
}
