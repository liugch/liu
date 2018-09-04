package proxy.cglibproxy;


/*import net.sf.cglib.proxy.Enhancer;*/

import org.springframework.cglib.proxy.Enhancer;

public class Test {
	
	/**
	 * 
	 * cglib 动态代理
	 * @param args
	 */
/*	public static void main(String[] args) {
	
		Enhancer enhancer = new Enhancer();
		enhancer.setClassLoader(UserServiceImpl.class.getClassLoader());
		enhancer.setSuperclass(UserServiceImpl.class);
		enhancer.setCallback(new CglibProxy());// 回调生成的对象
		
		
		UserServiceImpl userServiceImpl = (UserServiceImpl) enhancer.create();
		
		userServiceImpl.save();
		
	}*/
	
}
