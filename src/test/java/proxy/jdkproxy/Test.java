package proxy.jdkproxy;

import java.lang.reflect.Proxy;

public class Test {
	
	/**
	 * 
	 * Jdk 动态代理
	 * @param args
	 */
	public static void main(String[] args) {
		
		IUserService userService = (IUserService) Proxy.newProxyInstance(
						UserServiceImpl.class.getClassLoader()// 类加载器
						,UserServiceImpl.class.getInterfaces() //类接口
						,new JDKProxy(new UserServiceImpl())); //委托类被代理
		
		userService.save();
		
		IUserService u = (IUserService) Proxy.newProxyInstance(UserServiceImpl.class.getClassLoader(), UserServiceImpl.class.getInterfaces(), new JDKProxy(new UserServiceImpl()));
	}
	
}
