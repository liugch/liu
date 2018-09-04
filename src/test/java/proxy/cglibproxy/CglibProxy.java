package proxy.cglibproxy;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/*import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;*/

/**
 * cglib 代理 生成一个子类 , 需要委托的类不能是 final, 要代理的方法不能用private static final 修饰
 *
 *	这是一个代理生成器\
 *  拓展父类
 * 
 * MethodInterceptor Enhancer
 * 
 * @author Administrator
 *
 */
/*public class CglibProxy implements MethodInterceptor {
	
	//private Object targer = null;
	
	*//*
	public <T> T createProxyInstance(T targer){
		this.targer = targer;
		Enhancer enhancer = new Enhancer();
		enhancer.setClassLoader(targer.getClass().getClassLoader());
		enhancer.setSuperclass(targer.getClass()); // 设置父类
		enhancer.setCallback(this); //设置回调对象为自己
		
		return (T) enhancer.create();
	}
	*//*
	
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodproxy) throws Throwable {
		Object rs = null;
		
		// 打开事务
		System.out.println("保存数据前打开事务");

		try {
			// 通过代理类的这个拦截器这个方式 执行父类的方法
			rs = methodproxy.invokeSuper(proxy, args);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			// 关闭事务
			System.out.println("保存数据后关闭");
		}

		return rs;
	}

}*/
