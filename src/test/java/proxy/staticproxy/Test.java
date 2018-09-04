package proxy.staticproxy;

public class Test {
	
	/**
	 * 静态代理类： 由程序员创建或由特定工具自动生成源代码，再对其编译。
	 * 在程序运行前，代理类 的.class 文件就已经存在了。
	 * @param args
	 */
	/*
	@Autowired
	IUserService proxyservice;*/
	/*<--id="service"的类为委托类--->
    <bean  id="service" class="proxy.staticproxy.UserServiceImpl"></bean>
    
　　　<--id="servicetran"的类为代理类-->
    <bean  id="servicetran" class="proxy.staticproxy.UserImpl">
        <constructor-arg name="service" ref="service"></constructor-arg>
    </bean>*/
	
	public static void main(String[] args) {
		// 通过执行代理类, 来实现委托类要做的动作, 
		//userService.save();
	}
	
}
