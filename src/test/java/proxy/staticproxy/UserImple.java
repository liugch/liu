package proxy.staticproxy;

// 代理类  也可用继承 委托类来实现
// 一个是实现相同接口, 另一个是继承委托要被代理的类
public class UserImple implements IUserService{
	
	private UserServiceImpl service = null;
	
	public UserImple(UserServiceImpl service) {
		this.service = service;
	}
	
	@Override
	public void save() {
		System.out.println("在执行要被代理类的方法  前执行一下操作");
		System.out.println("执行被代理类的方法");
		service.save();
		System.out.println("在执行要被代理类的方法  后执行一下操作");
	}

}
