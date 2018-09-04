package proxy.cglibproxy;


// 委托类, 要被代理的类  ,没有实现任何的接口
public class UserServiceImpl {

	public void save() {
		System.out.println("调用dao层数据库操作");
	}

}
